package com.kungfupandas.ixigotripplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
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
//                executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_A, AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY);
            }
        });
    }

    private void initFromCityView() {
        mOriginCityActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_from);
        mOriginCityActv.addTextChangedListener(mOriginCityTextChangeListener);
    }

    private void initDestinationCityView() {
        mDestinationActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_destination);
        mDestinationActv.addTextChangedListener(mDestinationCityTextChangeListener);
    }

    @Override
    public void openSuggestionsActivity() {
        startActivity(new Intent(HomeActivity.this, TripResultActivity.class));
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNetworkOperationSuccess(NetworkResponse result, int taskCode, Object[] params) {
        super.onNetworkOperationSuccess(result, taskCode, params);
        switch (taskCode) {
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_ORIGIN:
                mPresenter.onOriginAutocompleteResponse(result);
                break;
            case AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_DESTINATION:
                mPresenter.onDestinationAutocompleteResponse(result);
                break;
        }
    }

    private TextWatcher mOriginCityTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            onOriginTextChange(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private TextWatcher mDestinationCityTextChangeListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            onDestinationTextChange(charSequence);
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    private void onDestinationTextChange(CharSequence query) {
        if (!TextUtils.isEmpty(query) && query.length() > 1) {
            executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_DESTINATION, AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY + query);
        }
    }

    private void onOriginTextChange(CharSequence query) {
        if (!TextUtils.isEmpty(query) && query.length() > 1) {
            executeNetworkOperation(AppConstants.NetworkTaskCodes.GET_AUTOCOMPLETE_CITY_ORIGIN, AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY + query);
        }
    }

}
