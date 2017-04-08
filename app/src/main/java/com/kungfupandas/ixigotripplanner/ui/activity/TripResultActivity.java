package com.kungfupandas.ixigotripplanner.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.adapter.RoutesAdapter;
import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.pojo.Trip;

import org.json.JSONObject;

/**
 * Created by tushar on 08/04/17.
 */

public class TripResultActivity extends ToolbarActivity {
    public static final String BUNDLE_KEY_ORIGIN_CITY = "originCity";
    public static final String BUNDLE_KEY_DESTINATION_CITY = "destinationCity";
    private City mDestinationCity, mOriginCity;
    private RecyclerView mFlightsRv;
    private Trip mTripDetails;
    private RoutesAdapter mAdapter;
    private TextView mToandFromTv, mResultCountTv;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trip_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        fetchOriginToDestinationData();
    }

    private void initView() {
        setToolbarTitle("Trip _Planner");
        initList();
        initTripData();
    }

    private void initTripData() {
        mToandFromTv = (TextView) findViewById(R.id.tv_result_brief);
        mResultCountTv = (TextView) findViewById(R.id.tv_result_brief_count);
    }

    private void initList() {
        mFlightsRv = (RecyclerView) findViewById(R.id.rv_routes);
    }

    private void fetchOriginToDestinationData() {
        String url = AppConstants.ApiEndpoints.getAToBModesApiEndPoint(mOriginCity.getCityId(),
                mDestinationCity.getCityId());
        Logger.error("url", "" + url);
        executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_A_TO_B_MODES,
                url);
    }

    @Override
    public void onNetworkOperationSuccess(NetworkResponse result, int taskCode, Object[] params) {
        super.onNetworkOperationSuccess(result, taskCode, params);
        switch (taskCode) {
            case AppConstants.NetworkTaskCodes.GET_A_TO_B_MODES:
                if (result.isSuccess()) {
                    Logger.error("result", "" + result.getData());
                    onRoutesDataLoaded(result.getData());
                } else {
                    onError("Error occurred while fetching data!");
                }
                break;
        }
    }

    private void onRoutesDataLoaded(String json) {
        mFlightsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        try {
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("data");
            mTripDetails = new Gson().fromJson(data.toString(), Trip.class);
            mAdapter = new RoutesAdapter(mTripDetails.getRoutes());
            mFlightsRv.setAdapter(mAdapter);
            setTripData();
        }catch (Exception e){onError("Error while parsing data!");}
    }

    private void setTripData() {
        if (mTripDetails != null && mTripDetails.getRoutes() != null)
            mResultCountTv.setText("Showing " + mTripDetails.getRoutes().size() + " routes");
        if (mTripDetails != null && !TextUtils.isEmpty(mTripDetails.getOriginName())
                && !TextUtils.isEmpty(mTripDetails.getOriginName())){
            mToandFromTv.setText(mTripDetails.getOriginName()+" - "+mTripDetails.getDestinationName());
        }
    }

    @Override
    public void onNetworkOperationError(Exception e, int taskCode, Object[] params) {
        super.onNetworkOperationError(e, taskCode, params);
        switch (taskCode) {
            case AppConstants.NetworkTaskCodes.GET_A_TO_B_MODES:
                Logger.error("error", "" + e.getMessage());
                e.printStackTrace();
                onError("Error occurred while fetching data!");
                break;
        }
    }

    private void onError(String message) {
        showMessage(message);
        finish();
    }

    private void initData() {
        if (getIntent() != null && getIntent().getParcelableExtra(BUNDLE_KEY_DESTINATION_CITY) != null) {
            mDestinationCity = getIntent().getParcelableExtra(BUNDLE_KEY_DESTINATION_CITY);
            mOriginCity = getIntent().getParcelableExtra(BUNDLE_KEY_ORIGIN_CITY);
            Logger.info("origin", "" + mOriginCity);
            Logger.info("destination", "" + mDestinationCity);
        } else {
            onError("Destination city not added!");
        }
        if (mOriginCity == null) {
            setLastKnownLocation(mOriginCity);
        }
    }

    private void setLastKnownLocation(City mOriginCity) {
        //TODO: write last known location code
    }
}
