package com.kungfupandas.ixigotripplanner;

/**
 * Created by tushar on 08/04/17.
 */

public class AppConstants {
    public static class NetworkTaskCodes{
        public static final int GET_AUTOCOMPLETE_CITY_ORIGIN = 1;
        public static final int GET_AUTOCOMPLETE_CITY_DESTINATION = 2;
        public static final int GET_A_TO_B_MODES = 3;
    }
    public static class ApiEndpoints{
        public static String  GET_AUTOCOMPLETE_CITY = "http://build2.ixigo.com/action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=";

        public static String getAToBModesApiEndPoint(String originCityId, String destinationCityId){
//        return "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$&originCityId="+originCityId+"&destinationCityId="+destinationCityId;}
        return "http://build2.ixigo.com/api/v2/a2b/modes?apiKey=ixicode!2$&originCityId="+originCityId+"&destinationCityId="+destinationCityId;}
    }
    public static class NetworkConfigs{
        public static final int CONNECTION_TIME_OUT = 15;
        public static final long WRITE_TIMEOUT = 10;
        public static final long READ_TIMEOUT = 10;
    }
    public static class IntentConfigs{
        public static final String WORDS_LIST = "words_list";
    }
}
