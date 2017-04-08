package com.kungfupandas.ixigotripplanner.network;

import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

import org.json.JSONException;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by tushar on 08/04/17.
 */

public interface NetworkService {

    NetworkResponse getData(Object... params) throws JSONException, SQLException, NullPointerException, IOException;

}
