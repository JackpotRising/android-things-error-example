package com.somecompany.example;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.somecompany.example.ProductionTree;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by MalcolmMcFly on 11/20/17.
 */

public class GoogleExample extends Application {

    private static Context mContext;

        /*
    onConfigurationChanged( )
        Called by the system when the device configuration changes while your component is running.
    onCreate( )
        Called when the application is starting, before any other application objects have been created.
    onLowMemory( )
        This is called when the overall system is running low on memory, and would like actively
        running processes to tighten their belts.
    onTerminate( )
        This method is for use in emulated process environments. It will never be called on a
        production Android device, where processes are removed by simply killing them; no user
        code (including this callback) is executed when doing so.
     */

    public void onCreate() {
        super.onCreate();
        this.mContext = this;
        Stetho.initializeWithDefaults(this);
        Fabric.with(this, new Crashlytics());

        // dev mode logs
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree() {
                // add line numbers to logs
                @Override
                protected String createStackElementTag(StackTraceElement element) {
                    return super.createStackElementTag(element) + ':' + element.getLineNumber();
                }
            });
        } else {
            Fabric.with(this, new Crashlytics());
            Timber.plant(new ProductionTree());
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static Context getContext(){
        return mContext;
    }
}