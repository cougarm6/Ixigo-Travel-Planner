package com.kungfupandas.ixigotripplanner.network;

import android.os.AsyncTask;

import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.listener.NetworkOperationListener;
import com.kungfupandas.ixigotripplanner.pojo.NetworkException;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

/**
 * Created by tushar on 08/04/17.
 */

public class AsyncManager extends AsyncTask<Object, Object, NetworkResponse> {

    public static final String TAG = "AsyncManager";

    private int taskCode;
    private Object[] params;
    private Exception e;
    private long startTime;
    private NetworkOperationListener networkOperationListener;

    public AsyncManager(int taskCode, NetworkOperationListener networkOperationListener) {
        this.taskCode = taskCode;
        this.networkOperationListener = networkOperationListener;
    }

    @Override
    protected void onPreExecute() {
        startTime = System.currentTimeMillis();
        Logger.info(TAG, "On Preexecute AsyncTask");
        if (networkOperationListener != null)
            networkOperationListener.onPreNetworkOperation(this.taskCode);
    }

    @Override
    protected NetworkResponse doInBackground(Object... params) {
        NetworkResponse response = null;
        NetworkService service = NetworkServiceFactory.getInstance(taskCode);
        Logger.info(TAG, "DoinBackGround");
        try {
            this.params = params;
            response = service.getData(params);
        } catch (Exception e) {
            e.printStackTrace();
            this.e = e;
        }
        return response;
    }

    @Override
    protected void onPostExecute(NetworkResponse result) {
        if (networkOperationListener != null) {
            Logger.info("servicebenchmark", networkOperationListener.getClass().getName() + " , time taken in ms: " + (System.currentTimeMillis() - startTime));
            if (e != null || !result.isSuccess()) {//background error call on BackGroundError
                if (e == null) {
                    e = new NetworkException(result.getReason(), result.getResponseCode());
                    Logger.error("error",""+result.getReason());
                }
                networkOperationListener.onNetworkOperationError(e,
                        this.taskCode, this.params);
            } else {// no exception call onNetworkOperationSuccess
                networkOperationListener.onNetworkOperationSuccess(result, this.taskCode,
                        this.params);
            }
            super.onPostExecute(result);
        }
    }
}