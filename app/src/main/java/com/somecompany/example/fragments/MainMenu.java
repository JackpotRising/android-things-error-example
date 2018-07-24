package com.somecompany.example.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.somecompany.example.R;
import com.somecompany.example.events.RestartEvent;
import com.somecompany.example.events.ShowMainMenuEvent;
import com.somecompany.example.events.StartActiveAttemptEvent;
import com.somecompany.example.utils.CustomLayoutManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainMenu extends Fragment {

    private Context mContext;

    private RecyclerView listView;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRestartEvent(RestartEvent event) {
        //button.connectToButton();
        //button.startButton();
        //button.startIOManager(mCallback);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.main_menu, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                EventBus.getDefault().post(new StartActiveAttemptEvent());
            }
        }, 2000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public static MainMenu newInstance(String text) {

        MainMenu f = new MainMenu();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }
}
