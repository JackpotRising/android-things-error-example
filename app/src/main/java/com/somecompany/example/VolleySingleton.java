package com.somecompany.example;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static VolleySingleton instance;
    private RequestQueue rq;
    private static Context ctx;

    private VolleySingleton(Context context) {
        ctx = context;
        rq = getRequestQueue();
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        if (instance == null) {
            instance = new VolleySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (rq == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            rq = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return rq;
    }
}

