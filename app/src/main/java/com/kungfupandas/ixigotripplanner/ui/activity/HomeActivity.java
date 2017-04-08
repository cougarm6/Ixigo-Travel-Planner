package com.kungfupandas.ixigotripplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.ui.presenter.HomePresenter;

public class HomeActivity extends BaseActivity implements HomePresenter.View {
    private AutoCompleteTextView mOriginCityActv;
    private AutoCompleteTextView mDestinationActv;
    private Button mSearchBtn;
    private HomePresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_home;
    }

    private void initView() {
        mPresenter = new HomePresenter(this);
        initFromCityView();
        initDestinationCityView();
        initSearchButton();
    }

    private void initSearchButton() {
        mSearchBtn = (Button) findViewById(R.id.btn_search);
        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY, AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY);
            }
        });
    }

    private void initFromCityView() {
        mOriginCityActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_from);
    }

    private void initDestinationCityView() {
        mDestinationActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_destination);
    }

    @Override
    public void openSuggestionsActivity() {
        startActivity(new Intent(HomeActivity.this, TripResultActivity.class));
    }
}
