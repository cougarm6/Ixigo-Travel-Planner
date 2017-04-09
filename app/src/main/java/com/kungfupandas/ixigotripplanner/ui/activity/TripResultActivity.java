package com.kungfupandas.ixigotripplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.adapter.RoutesAdapter;
import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.network.NetworkUtils;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.pojo.Trip;

import org.json.JSONObject;

import java.util.List;

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
    private TextView mToandFromTv, mResultCountTv, mSummaryTv, mHotelTv, mRecommendTv, mCarRentalsTv;
    private String recommendUrl;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_trip_result;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initList();
        initTripData();
        initData();
        initRecomend();
        initHotels();
        initCarRentals();
    }

    private void initToolbar() {
        setToolbarTitle("Trip _Planner");
    }

    private void initCarRentals() {
        mCarRentalsTv = (TextView) findViewById(R.id.tv_car);
        mCarRentalsTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCarRentalsCliced();
            }
        });
    }

    private void onCarRentalsCliced() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.KEY_TITLE, "Hotels in " + mTripDetails.getDestinationName());
        intent.putExtra(WebViewActivity.KEY_URL, "" + AppConstants.ApiEndpoints.ZOOMCAR_CAR_RENTAILS + mTripDetails.getDestinationName());
    }

    private void initRecomend() {
        mRecommendTv = (TextView) findViewById(R.id.tv_recommend);
        mRecommendTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRecommendCliced();
            }
        });
    }

    private void onRecommendCliced() {
        if (!TextUtils.isEmpty(mDestinationCity.getUrl())) {
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra(WebViewActivity.KEY_TITLE, "" + mTripDetails.getDestinationName());
            intent.putExtra(WebViewActivity.KEY_URL, "" + recommendUrl);
        } else {
            showMessage("Recommendations not available!");
        }
    }

    private void initHotels() {
        mHotelTv = (TextView) findViewById(R.id.tv_hotels);
        RelativeLayout extras = (RelativeLayout) findViewById(R.id.rl_extras);
        mHotelTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHotelsCliced();
            }
        });  extras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onHotelsCliced();
            }
        });
    }

    private void onHotelsCliced() {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.KEY_TITLE, "Hotels in " + mTripDetails.getDestinationName());
        intent.putExtra(WebViewActivity.KEY_URL, "" + AppConstants.ApiEndpoints.GO_IBIBO_HOTEL + mTripDetails.getDestinationName());
    }

    private void initTripData() {
        mToandFromTv = (TextView) findViewById(R.id.tv_result_brief);
        mResultCountTv = (TextView) findViewById(R.id.tv_result_brief_count);
        mSummaryTv = (TextView) findViewById(R.id.tv_summary);
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
                onModesResponse(result);
                break;
            case AppConstants.NetworkTaskCodes.GET_CITY_INSIGHTS:
                onCityInsightsResponse(result);

        }
    }

    private void onModesResponse(NetworkResponse result) {
        if (result.isSuccess()) {
            Logger.error("result", "" + result.getData());
            onRoutesDataLoaded(result.getData());
            if (mTripDetails.getDestination() != null) {
//                getCityInsights();
            } else {
                showMessage("Data not available!");
            }
        } else {
            onError("Error occurred while fetching data!");
        }
    }

    private void getCityInsights() {
        if (mTripDetails != null && mTripDetails.getDestination() != null && mTripDetails.getDestination().getCityId() != null) {
//            String url = AppConstants.ApiEndpoints.getCityInsightsEndPoint(mTripDetails.getDestination().getCityId());
//            Logger.error("url",url);
//            executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_CITY_INSIGHTS, url);
        }
    }

    private void onCityInsightsResponse(NetworkResponse result) {
        if (result.isSuccess()) {
            setRecommendationUrl(result);
        } else {
            onRecommendationError();
        }
    }

    private void setRecommendationUrl(NetworkResponse result) {
        try {
            List<City> cityList = NetworkUtils.parseCityResult("" + result.getData());
            if (cityList != null && cityList.size() > 0) {
                City city = cityList.get(0);
                if (city != null && TextUtils.isEmpty(city.getUrl())) {
                    recommendUrl = city.getUrl();
                } else {
                    onFetchingRecommendationError();
                }
            } else {
                onFetchingRecommendationError();
            }
            Logger.error("result", "" + result.getData());
        } catch (Exception e) {
            e.printStackTrace();
            showMessage("Error while parsing recommendation!");
            finish();
            return;
        }
    }

    private void onFetchingRecommendationError() {
    }

    private void onRecommendationError() {
        onError("Error occurred while fetching data!");
        finish();
    }

    private void onRoutesDataLoaded(String json) {
        try {
            mFlightsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            JSONObject root = new JSONObject(json);
            JSONObject data = root.getJSONObject("data");
            Logger.error("data", "" + data);
            mTripDetails = new Gson().fromJson(data.toString(), Trip.class);
            if (mTripDetails == null) {
                showMessage("Unable to get Trip Data!");
                finish();
                return;
            }
            if (mTripDetails.getFastestRoute() != null && mTripDetails.getFastestRoute().getDurationPretty() != null &&
                    mTripDetails.getCheapestRoute() != null && mTripDetails.getCheapestRoute().getDurationPretty() != null) {
                mAdapter = new RoutesAdapter(mTripDetails.getRoutes(),
                        mTripDetails.getFastestRoute().getDurationPretty(),
                        mTripDetails.getCheapestRoute().getDurationPretty(), this);
            } else {
                mAdapter = new RoutesAdapter(mTripDetails.getRoutes(), "N/A", "N/A", this);
            }
            mFlightsRv.setAdapter(mAdapter);
            setTripData();
        } catch (Exception e) {
            e.printStackTrace();
            onError("Error while parsing data!");
        }
    }

    private void setTripData() {
        if (mTripDetails != null) {
            if (mTripDetails.getRoutes() != null)
                mResultCountTv.setText("Showing " + mTripDetails.getRoutes().size() + " routes");
            if (!TextUtils.isEmpty(mTripDetails.getOriginName())
                    && !TextUtils.isEmpty(mTripDetails.getOriginName())) {
                mToandFromTv.setText(mTripDetails.getOriginName() + " - " + mTripDetails.getDestinationName());
            }
            if (!TextUtils.isEmpty(mTripDetails.getDirectIndirectSentence())) {
                mSummaryTv.setText(mTripDetails.getDirectIndirectSentence());
            } else {
                mSummaryTv.setVisibility(View.GONE);
            }
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
            case AppConstants.NetworkTaskCodes.GET_CITY_INSIGHTS:
                onRecommendationError();
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
            if (mOriginCity != null) {
                Logger.info("origin", "" + mOriginCity);
                Logger.info("destination", "" + mDestinationCity);
                fetchOriginToDestinationData();
            } else if (mCity != null) {
                mOriginCity = mCity;
                fetchOriginToDestinationData();
            } else {
                showDialog(this, "Fetching Location");

                mListener = new CityListener() {
                    @Override
                    public void onCityGet(City city) {
                        dismissDialog();
                        if (city == null) {
                            onError("Not able to get your current Location!");
                        }
                        mOriginCity = city;
                        fetchOriginToDestinationData();
                    }
                };
            }
        } else {
            onError("Destination city not added!");
        }
    }


}
