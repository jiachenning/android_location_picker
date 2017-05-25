package com.zeonic.icity.location_picker;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by ninja on 5/10/17.
 */

public class BootstrapApplication extends Application{
    private static BootstrapApplication instance;
    public BootstrapApplication() {
    }
    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        instance = this;
    }

    public static BootstrapApplication getInstance() {
        return instance;
    }
}
