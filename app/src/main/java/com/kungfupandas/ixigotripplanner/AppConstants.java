package com.kungfupandas.ixigotripplanner;

/**
 * Created by tushar on 08/04/17.
 */

public class AppConstants {
    public static class NetworkTaskCodes{
        public static final int GET_AUTOCOMPLETE_CITY = 1;
    }
    public static class ApiEndpoints{
        public static String  GET_AUTOCOMPLETE_CITY = "";
    }
    public static class NetworkConfigs{
        public static final int CONNECTION_TIME_OUT = 15;
        public static final long WRITE_TIMEOUT = 10;
        public static final long READ_TIMEOUT = 10;
    }
}
