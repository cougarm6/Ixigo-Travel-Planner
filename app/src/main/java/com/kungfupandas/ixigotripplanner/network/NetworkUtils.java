package com.kungfupandas.ixigotripplanner.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

import okhttp3.Response;

/**
 * Created by tushar on 08/04/17.
 */
public class NetworkUtils {

    public static void validateResponse(NetworkResponse nwResponse, Response response) {
        try {
            nwResponse.setResponseCode(response.code());
            nwResponse.setSuccess(response.isSuccessful());
            if (response.isSuccessful()) {
                String responseString = response.body().string();
                nwResponse.setData(responseString);
                Logger.info("networkResult",""+responseString);
                response.body().close();
            }
        }catch (Exception e){
            nwResponse.setSuccess(false);
            nwResponse.setReason(e.getMessage());
            nwResponse.setResponseCode(1);
        }

    }

    public static boolean isConnectingToInternet(Context ctx) {
        boolean netConnected = false;
        try {
            ConnectivityManager connectivity = (ConnectivityManager) ctx
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivity == null) {
                Logger.info("tag", "couldn't get connectivity manager");
                netConnected = false;
            } else {
                NetworkInfo info = connectivity.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    netConnected = true;
                }
            }
        } catch (Exception e) {
            Logger.error("Connectivity Exception",
                    "Exception AT isInternetConnection");
            netConnected = false;
        }
        return netConnected;
    }
}
