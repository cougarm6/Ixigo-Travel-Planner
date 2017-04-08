package com.kungfupandas.ixigotripplanner.util;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.*;

/**
 * Created by aish on 8/4/17.
 */

public class LocationUtil implements GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks,LocationListener {

private LocationRequest mLocationRequest;
public GoogleApiClient mGoogleApiClient;
private Location mLastLocation;
private Context mContext;
private LocationUtilCallback mCallback;
public interface LocationUtilCallback{
    public void onGetLocation(Location location);
    public void onNoLocation();
}

    public LocationUtil(Context context, LocationUtilCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        createLocationRequest();
        buildGoogleApiClient();
    }

    private void createLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    public void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }

    /**
     * Stop location update and disconnect api client
     * Called when activity or fragment destroyed
     * */
    public void disconnectLocationApiClient() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.unregisterConnectionCallbacks(this);
            mGoogleApiClient.unregisterConnectionFailedListener(this);
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Retrieves last updated location
     * */
    public Location getLastLocation() {
        if (mGoogleApiClient == null) {
            return null;
        }
        if (!mGoogleApiClient.isConnected() && mLastLocation!=null) {
            return mLastLocation;
        } else {
            if (!PermissionManager.isPermissionRequestRequired(mContext, Manifest.permission.ACCESS_FINE_LOCATION)){
                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                        mGoogleApiClient);
            }
            return mLastLocation;
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        if(mGoogleApiClient==null || !mGoogleApiClient.isConnected()){
            return;
        }
        if (!PermissionManager.isPermissionRequestRequired(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
        }

        /**
         * Location is needed only once and last location is not null then send the last location
         * Otherwise call location updates.
         * */
        if(mLastLocation!=null) {
            onLocationChanged(mLastLocation);
            return;
        }
        else if(mCallback!=null){
            mCallback.onNoLocation();
        }
        if (!PermissionManager.isPermissionRequestRequired(mContext, Manifest.permission.ACCESS_FINE_LOCATION)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    public void stopLocationUpdate(){
        if(mGoogleApiClient!=null && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        if(mCallback!=null){
            mCallback.onGetLocation(location);
        }
    }
}
