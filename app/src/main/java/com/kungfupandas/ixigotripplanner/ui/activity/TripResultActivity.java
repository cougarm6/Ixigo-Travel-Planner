package com.kungfupandas.ixigotripplanner.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.pojo.City;

/**
 * Created by tushar on 08/04/17.
 */

public class TripResultActivity extends BaseActivity {
    public static final String BUNDLE_KEY_ORIGIN_CITY = "originCity";
    public static final String BUNDLE_KEY_DESTINATION_CITY = "destinationCity";
    private City mDestinationCity, mOriginCity;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trip_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        fetchOriginToDestinationData();
    }

    private void fetchOriginToDestinationData() {

    }

    private void initData() {
        if (getIntent() != null && getIntent().getParcelableExtra(BUNDLE_KEY_DESTINATION_CITY) != null) {
            mDestinationCity = getIntent().getParcelableExtra(BUNDLE_KEY_DESTINATION_CITY);
            mOriginCity = getIntent().getParcelableExtra(BUNDLE_KEY_ORIGIN_CITY);
            Logger.info("origin",""+mOriginCity);
            Logger.info("destination",""+mDestinationCity);
        } else {
            showMessage("Destination city not added!");
            finish();
        }
        if (mOriginCity == null) {
            setLastKnownLocation(mOriginCity);
        }
    }

    private void setLastKnownLocation(City mOriginCity) {
        //TODO: write last known location code
    }
}
