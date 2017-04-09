package com.kungfupandas.ixigotripplanner.network;

import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by tushar on 09/04/17.
 */

public class GetCityInsightsImpl extends GenericNetworkService {

    @Override
    public NetworkResponse getData(Object... params) throws JSONException, SQLException, NullPointerException, IOException {
        return doGet((String) params[0]);
    }
}
