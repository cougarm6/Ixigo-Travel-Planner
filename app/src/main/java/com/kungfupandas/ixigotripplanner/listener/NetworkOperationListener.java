package com.kungfupandas.ixigotripplanner.listener;

import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

/**
 * Created by tushar on 08/04/17.
 */

public interface NetworkOperationListener {
    void onPreNetworkOperation(int networkTaskCode);
    void onNetworkOperationSuccess(NetworkResponse result, int taskCode, Object[] params);
    void onNetworkOperationError(Exception e, int taskCode, Object[] params);
}
