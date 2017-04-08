package com.kungfupandas.ixigotripplanner.network;

import com.kungfupandas.ixigotripplanner.AppConstants;

/**
 * Created by tushar on 08/04/17.
 */

public class NetworkServiceFactory {

    public static NetworkService getInstance(int taskCode) {
        switch (taskCode) {
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY:
                return new GetAutocompleteCityImpl();
            default:
                return null;
        }
    }
}
