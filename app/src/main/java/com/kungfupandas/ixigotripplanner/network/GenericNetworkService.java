package com.kungfupandas.ixigotripplanner.network;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

import org.json.JSONException;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by tushar on 08/04/17.
 */

public abstract class GenericNetworkService implements NetworkService {

    protected final NetworkResponse doGet(String url)
            throws JSONException, IOException {
        NetworkResponse nwResponse = new NetworkResponse();
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = getClient().newCall(request).execute();
        NetworkUtils.validateResponse(nwResponse, response);
        return nwResponse;
    }

    private OkHttpClient getClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(AppConstants.NetworkConfigs.CONNECTION_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(AppConstants.NetworkConfigs.WRITE_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(AppConstants.NetworkConfigs.READ_TIMEOUT, TimeUnit.SECONDS)
                .build();
        return client;
    }
}
