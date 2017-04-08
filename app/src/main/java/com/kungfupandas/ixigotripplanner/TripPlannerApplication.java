package com.kungfupandas.ixigotripplanner;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by tushar on 08/04/17.
 */

public class TripPlannerApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
