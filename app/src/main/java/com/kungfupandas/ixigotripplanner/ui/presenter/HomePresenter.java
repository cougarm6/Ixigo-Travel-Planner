package com.kungfupandas.ixigotripplanner.ui.presenter;

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

    public interface View {
        void openSuggestionsActivity();
    }
}
