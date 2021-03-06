package com.kungfupandas.ixigotripplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.ui.presenter.HomePresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends BaseActivity implements HomePresenter.View {
    private AutoCompleteTextView mOriginCityActv, mDestinationActv;
    private Button mSearchBtn;
    private HomePresenter mPresenter;
    private ArrayAdapter<String> mOriginCityAdapter, mDestinationCityAdapter;
    private List<City> mOrginCitySearch, mDestinationCitySearch;
    private City mOriginCity, mDestinationCity;

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
                if(mOriginCity!=null && mDestinationCity!=null){
                    Intent intent = new Intent(HomeActivity.this, TripResultActivity.class);
                    intent.putExtra(TripResultActivity.BUNDLE_KEY_ORIGIN_CITY, mOriginCity);
                    intent.putExtra(TripResultActivity.BUNDLE_KEY_DESTINATION_CITY, mDestinationCity);
                    startActivity(intent);
                }else{
                    if(mOriginCity == null)
                    showMessage("Please select an Origin City!");
                    else
                        showMessage("Please select a Destination City!");
                }
            }
        });
    }

    private void initFromCityView() {
        mOriginCityActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_from);
        mOriginCityActv.addTextChangedListener(mOriginCityTextChangeListener);
        mOriginCityActv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               setOriginCity((String) adapterView.getAdapter().getItem(i));
            }
        });
    }

    private void setOriginCity(String cityName) {
        for(City city : mOrginCitySearch){
            if(cityName.equalsIgnoreCase(cityName)){
                mOriginCity = city;
                break;
            }
        }
    }

    private void setDestinationCity(String cityName) {
        for(City city : mDestinationCitySearch){
            if(cityName.equalsIgnoreCase(cityName)){
                mDestinationCity = city;
                break;
            }
        }
    }

    private void initDestinationCityView() {
        mDestinationActv = (AutoCompleteTextView) findViewById(R.id.autocomplete_destination);
        mDestinationActv.addTextChangedListener(mDestinationCityTextChangeListener);
        mDestinationActv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                setDestinationCity((String) adapterView.getAdapter().getItem(i));
            }
        });
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
    public void updateOriginCityResults(List<City> resultCityList) {
        mOrginCitySearch = resultCityList;
        List<String> list = new ArrayList<>();
        for (City city : resultCityList) {
            list.add(city.getCityName());
        }
        mOriginCityAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, list);
        mOriginCityActv.setAdapter(mOriginCityAdapter);
    }

    @Override
    public void updateDestinationCityResults(List<City> resultCityList) {
        mDestinationCitySearch = resultCityList;
        List<String> list = new ArrayList<>();
        for (City city : resultCityList) {
            list.add(city.getCityName());
        }
        mDestinationCityAdapter = new ArrayAdapter<>
                (this, android.R.layout.simple_list_item_1, list);
        mDestinationActv.setAdapter(mDestinationCityAdapter);
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
