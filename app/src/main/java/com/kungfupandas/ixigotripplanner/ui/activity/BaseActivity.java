package com.kungfupandas.ixigotripplanner.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.listener.NetworkOperationListener;
import com.kungfupandas.ixigotripplanner.network.AsyncManager;
import com.kungfupandas.ixigotripplanner.network.GetAutocompleteCityImpl;
import com.kungfupandas.ixigotripplanner.network.NetworkUtils;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.util.LocationUtil;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;

/**
 * Created by tushar on 08/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkOperationListener, LocationUtil.LocationUtilCallback {
    private ProgressDialog mProgressDialog;
    protected LocationUtil mLocationUtil;
    protected City mCity;
    protected CityListener mListener;
    protected interface CityListener{
        public void onCityGet(City city);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mLocationUtil = new LocationUtil(this,this);
    }

    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    protected abstract int getLayoutResId();

    protected void executeNetworkOperation(int networkTaskCode, Object... params) {
        new AsyncManager(networkTaskCode, this).execute(params);
    }

    @Override
    public void onPreNetworkOperation(int networkTaskCode) {
        if (shouldShowDialogForNetworkCall(networkTaskCode))
            showDialog(this, "Please wait!");
    }

    private boolean shouldShowDialogForNetworkCall(int networkTaskCode) {
        switch (networkTaskCode){
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_ORIGIN:
                return false;
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_DESTINATION:
                return false;
        }
        return true;
    }

    @Override
    public void onNetworkOperationSuccess(NetworkResponse result, int taskCode, Object[] params) {
        dismissDialog();
    }

    @Override
    public void onNetworkOperationError(Exception e, int taskCode, Object[] params) {
        dismissDialog();
    }

    public void showDialog(Context context, String msg) {
        if (context == null || ((Activity) context).isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
        }
        mProgressDialog.setMessage(msg);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public void dismissDialog() {
        if (mProgressDialog != null) {
            try {
                mProgressDialog.dismiss();
            } catch (Exception e) {
            }
            mProgressDialog = null;
        }
    }

    @Override
    public void onGetLocation(final Location location) {
        AsyncTask asyncTask = new AsyncTask<Object, Object, City>() {
            @Override
            protected City doInBackground(Object... params) {
                Geocoder geocoder = new Geocoder(BaseActivity.this, Locale.getDefault());
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String cityName = addresses.get(0).getLocality();
                if(cityName.equalsIgnoreCase("Gurugram")){
                    cityName = "Gurgaon";
                }
                GetAutocompleteCityImpl getAutocompleteCity = new GetAutocompleteCityImpl();
                NetworkResponse response = null;
                try {
                    response = getAutocompleteCity.getData(AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY + cityName);
                    List<City> cityList = NetworkUtils.parseCityResult(response.getData());
                    return cityList.get(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(City city) {
                mCity = city;
                super.onPostExecute(city);
                dismissDialog();
                if(mListener!=null){
                    mListener.onCityGet(city);
                }
            }
        };
        asyncTask.execute("");
    }

    @Override
    public void onNoLocation() {
        if(mListener!=null){
            mListener.onCityGet(null);
        }
    }
}
