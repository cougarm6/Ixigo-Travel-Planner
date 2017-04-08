package com.kungfupandas.ixigotripplanner.ui.presenter;

import com.google.gson.Gson;
import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.network.NetworkUtils;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.CitySearch;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class HomePresenter {
    private View view;

    public HomePresenter(View view) {
        this.view = view;
    }

    public void onTripFormFilled() {

    }

    public void onOriginAutocompleteResponse(NetworkResponse result) {
        if (result.isSuccess()) {
            Logger.error("result", "" + result.getData());
            try {
                List<City> cityList = NetworkUtils.parseCityResult(result.getData());
                if (cityList != null) {
                    view.updateOriginCityResults(cityList);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            view.showMessage("Error while fetching results!");
        }
    }

    public void onDestinationAutocompleteResponse(NetworkResponse result) {
        if (result.isSuccess()) {
            Logger.error("result", "" + result.getData());
            try {
                List<City> cityList = NetworkUtils.parseCityResult(result.getData());
                if (cityList != null) {
                    view.updateDestinationCityResults(cityList);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            view.showMessage("Error while fetching results!");
        }
    }

    public interface View {
        void openSuggestionsActivity();

        void showMessage(String message);

        void updateOriginCityResults(List<City> resultCityList);

        void updateDestinationCityResults(List<City> resultCityList);
    }
}
