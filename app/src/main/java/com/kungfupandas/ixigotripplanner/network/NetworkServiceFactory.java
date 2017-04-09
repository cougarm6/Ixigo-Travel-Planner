package com.kungfupandas.ixigotripplanner.network;

import com.kungfupandas.ixigotripplanner.AppConstants;

/**
 * Created by tushar on 08/04/17.
 */

public class NetworkServiceFactory {

    public static NetworkService getInstance(int taskCode) {
        switch (taskCode) {
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_ORIGIN:
                return new GetAutocompleteCityImpl();
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_DESTINATION:
                return new GetAutocompleteCityImpl();
            case AppConstants.NetworkTaskCodes.GET_A_TO_B_MODES:
                return new GetAToBModesImpl();
            case AppConstants.NetworkTaskCodes.GET_CITY_INSIGHTS:
                return new GetCityInsightsImpl();
            default:
                return null;
        }
    }
}
