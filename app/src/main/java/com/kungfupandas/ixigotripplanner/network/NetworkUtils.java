package com.kungfupandas.ixigotripplanner.network;

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
                response.body().close();
            }
        }catch (Exception e){
            nwResponse.setSuccess(false);
            nwResponse.setReason(e.getMessage());
            nwResponse.setResponseCode(1);
        }

    }
}
