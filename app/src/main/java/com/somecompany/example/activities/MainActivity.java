package com.somecompany.example.activities;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.things.update.UpdateManager;
import com.google.common.base.Supplier;
import com.somecompany.example.R;
import com.somecompany.example.VolleySingleton;
import com.somecompany.example.events.RestartEvent;
import com.somecompany.example.events.ShowMainMenuEvent;
import com.somecompany.example.events.StartActiveAttemptEvent;
import com.somecompany.example.fragments.ActiveAttemptFirstParty;
import com.somecompany.example.fragments.MainMenu;
import com.somecompany.example.utils.GameCallbacks;
import com.somecompany.example.utils.Connectivity;
import com.somecompany.example.utils.Fragments;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import timber.log.Timber;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends FragmentActivity implements GameCallbacks {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int REQUEST_CODE = 1;

    private FrameLayout container;
    private RequestQueue queue;
    private static UsbDeviceConnection connection;
    private FragmentManager fm;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "On create called");
        super.onCreate(savedInstanceState);

        UpdateManager mUpdateManager = UpdateManager.getInstance();
        mUpdateManager.setChannel("dev-channel");

        //TimeManager timeManager = TimeManager.getInstance();
        //timeManager.setTimeZone("America/Chicago");

        //set window fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //show activity_main, which should be nothing
        setContentView(R.layout.activity_main);

        queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        queue.start();

        // TODO: Please choose a font you are setting the defualt font 8 times
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Oswald-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Oswald-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Oswald-SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-Bold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/OpenSans-SemiBold.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());

        mp = MediaPlayer.create(this, R.raw.fourshame_glomag);
        //startMusic();

        boolean isConnected = Connectivity.isConnected(this);
        Timber.d("CONNECTION: " + isConnected);
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    //check if connected!
                    while (!Connectivity.isConnected(MainActivity.this)) {
                        //Wait to connect
                        Thread.sleep(200);
                    }

                    fm = MainActivity.this.getSupportFragmentManager();
                    fm.beginTransaction().replace(R.id.fragmentcontainer, new MainMenu(), "home").commit();

                } catch (Exception e) {
                }
            }
        };
        t.start();
        if (isConnected) {

        }

    }

    // React to published events
    // TODO: Verify expected behavior
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onStartActiveAttemptEvent(StartActiveAttemptEvent event) {
        Fragments.replaceSafely(R.id.fragmentcontainer, "activeattemptfirstparty", fm, new Supplier<Fragment>() {
            @Override
            public Fragment get() {
                ActiveAttemptFirstParty fragment = new ActiveAttemptFirstParty();
                return fragment;
            }
        }, "activeattemptfirstparty");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShowMainMenuEvent(ShowMainMenuEvent event) {
        Fragments.replaceSafely(R.id.fragmentcontainer, "mainmenu", fm, new Supplier<Fragment>() {
            @Override
            public Fragment get() {
                MainMenu fragment = new MainMenu();
                return fragment;
            }
        }, "mainmenu");

        EventBus.getDefault().post(new RestartEvent());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);

        queue = VolleySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
        container = (FrameLayout) findViewById(R.id.fragmentcontainer);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        log("Main activity onResume is called");
    }

    public RequestQueue getQueue() {
        return queue;
    }

    private void log(String s) {
        Log.d(TAG, s);
    }

    @Override
    protected void onPause() {
        super.onPause();
        log("onPause() was called!");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void addToQueue(JsonObjectRequest jsor) {
        queue.add(jsor);
    }

    @Override
    public void addToQueue(StringRequest jsor) {
        queue.add(jsor);
    }
}


