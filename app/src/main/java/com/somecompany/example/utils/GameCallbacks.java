package com.somecompany.example.utils;

import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

/**
 * Created by MalcolmMcFly on 12/6/17.
 */

public interface GameCallbacks {
    void addToQueue(JsonObjectRequest jsor);
    void addToQueue(StringRequest jsor);
}
