package com.kungfupandas.ixigotripplanner.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.listener.NetworkOperationListener;
import com.kungfupandas.ixigotripplanner.network.AsyncManager;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.util.LocationUtil;

/**
 * Created by tushar on 08/04/17.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetworkOperationListener, LocationUtil.LocationUtilCallback {
    private ProgressDialog mProgressDialog;
    private LocationUtil mLocationUtil;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mLocationUtil = new LocationUtil(this,this);
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
    public void onGetLocation(Location location) {

    }

    @Override
    public void onNoLocation() {

    }
}
