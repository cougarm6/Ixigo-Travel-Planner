package com.kungfupandas.ixigotripplanner.ui.presenter;

import com.kungfupandas.ixigotripplanner.custom.Logger;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;

/**
 * Created by tushar on 08/04/17.
 */

public class HomePresenter {
    private View view;

    public HomePresenter(View view){
        this.view = view;
    }

    public void onTripFormFilled(){

    }

    public void onOriginAutocompleteResponse(NetworkResponse result) {
        if(result.isSuccess()){
            Logger.error("result",""+result.getData());
        }else{
            view.showMessage("Error while fetching results!");
        }
    }
    public void onDestinationAutocompleteResponse(NetworkResponse result) {
        if(result.isSuccess()){
            Logger.error("result",""+result.getData());
        }else{
            view.showMessage("Error while fetching results!");
        }
    }

    public interface View {
        void openSuggestionsActivity();
        void showMessage(String message);
    }
}
