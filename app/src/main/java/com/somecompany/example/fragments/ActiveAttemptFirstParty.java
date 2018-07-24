package com.somecompany.example.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.GpioCallback;
import com.google.android.things.pio.PeripheralManager;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.somecompany.example.R;
import com.somecompany.example.events.ShowMainMenuEvent;
import com.somecompany.example.events.StartActiveAttemptEvent;
import com.somecompany.example.events.UpdateSensorDataEvent;
import com.somecompany.example.utils.GameCallbacks;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import timber.log.Timber;

public class ActiveAttemptFirstParty extends Fragment {

    private static final String TAG = ActiveAttemptFirstParty.class.getSimpleName();

    private Gpio SENSOR_2;
    private Gpio SENSOR_6;

    private GameCallbacks button;
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create called for Active Attempt");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.game_attempt_firstparty, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showUI();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
        button = (GameCallbacks) activity;
    }

    public static ActiveAttemptFirstParty newInstance(String text) {
        ActiveAttemptFirstParty f = new ActiveAttemptFirstParty();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    public void updateReceivedData(byte[] data) {}

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        connectToSensors();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                setTimeout();
            }
        }, 20000);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void setTimeout() {
        if (SENSOR_2 != null) {
            try {
                SENSOR_2.close();
                SENSOR_2 = null;
                Timber.d("SENSOR 2 CLOSED");
            } catch (IOException e) {
                Log.w(TAG, "Unable to close GPIO", e);
            }
        }

        if (SENSOR_6 != null) {
            try {
                SENSOR_6.close();
                SENSOR_6 = null;
                Timber.d("SENSOR 6 CLOSED");
            } catch (IOException e) {
                Log.w(TAG, "Unable to close GPIO", e);
            }
        }

        EventBus.getDefault().post(new ShowMainMenuEvent());
    }

    public void updateSensorReceivedData(String data) {
        String s = new String(data);
        Timber.d("SENSOR " + s);

    }

    private void connectToSensors() {
        PeripheralManager manager = PeripheralManager.getInstance();
        List<String> portList = manager.getGpioList();
        if (portList.isEmpty()) {
            Log.i(TAG, "No GPIO port available on this device.");
        } else {
            Log.i(TAG, "List of available ports: " + portList);
        }

        try {

            SENSOR_2 = manager.openGpio("BCM18");
            SENSOR_6 = manager.openGpio("BCM23");

            configureInput2(SENSOR_2);
            configureInput6(SENSOR_6);
        } catch (IOException e) {
            Timber.e("Unable to access GPIO");
            e.printStackTrace();
        }
    }

    public void configureInput2(Gpio gpio) throws IOException {
        // Initialize the pin as an input
        gpio.setDirection(Gpio.DIRECTION_IN);
        // Low voltage is considered active
        gpio.setActiveType(Gpio.ACTIVE_HIGH);

        // Register for all state changes
        gpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        gpio.registerGpioCallback(mGpioCallback2);
        Timber.d("SENSOR 2 ENABLED? " + gpio.getValue());
    }

    public void configureInput6(Gpio gpio) throws IOException {
        // Initialize the pin as an input
        gpio.setDirection(Gpio.DIRECTION_IN);
        // Low voltage is considered active
        gpio.setActiveType(Gpio.ACTIVE_HIGH);

        // Register for all state changes
        gpio.setEdgeTriggerType(Gpio.EDGE_FALLING);
        gpio.registerGpioCallback(mGpioCallback6);
        Timber.d("SENSOR 6 ENABLED? " + gpio.getValue());
    }

    private GpioCallback mGpioCallback2 = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            // Read the active low pin state
            try {
                if (!gpio.getValue()) {
                    Log.d("SENSOR2", "2");
                    EventBus.getDefault().post(new UpdateSensorDataEvent("2"));
                }
            } catch(IOException e) {
                Log.e(TAG,"Exception Caught",e);
            }

            // Continue listening for more interrupts
            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {
            Log.w(TAG, gpio + ": Error event " + error);
        }
    };

    private GpioCallback mGpioCallback6 = new GpioCallback() {
        @Override
        public boolean onGpioEdge(Gpio gpio) {
            // Read the active low pin state
            try {
                if (!gpio.getValue()) {
                    Log.d("SENSOR6", "6");
                    EventBus.getDefault().post(new UpdateSensorDataEvent("6"));
                }
            } catch(IOException e) {}

            // Continue listening for more interrupts
            return true;
        }

        @Override
        public void onGpioError(Gpio gpio, int error) {
            Log.w(TAG, gpio + ": Error event " + error);
        }
    };

    public void showUI() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Timber.d("Active Attempt destroyed");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateSensorDataEvent(UpdateSensorDataEvent event) {
        updateSensorReceivedData(event.getString());
    }
}
