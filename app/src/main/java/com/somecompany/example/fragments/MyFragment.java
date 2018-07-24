package com.somecompany.example.fragments;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.somecompany.example.activities.MainActivity;
import com.somecompany.example.models.GameState;

import android.widget.Toast;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//abstract class that requires child fragments to overload updateReceivedData to deal with button data
public abstract class MyFragment extends Fragment {
    public static final String TAG = MyFragment.class.getSimpleName();

    public RequestQueue queue;

    //public MyFragmentAdapter fragmentAdapter;

    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    //private SerialInputOutputManager mSerialIoManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!GameState.getConnected()) {
            //startIOManager();
            GameState.setConnected(true);
        }

        queue = ((MainActivity) this.getActivity()).getQueue();
        //fragmentAdapter = ((MainActivity) this.getActivity()).getFragmentAdapter();
    }

    public abstract void setTimeout();
    public abstract void updateReceivedData(byte[] data);
    public abstract void showUI();



    public void log(String s) {

    }
}
