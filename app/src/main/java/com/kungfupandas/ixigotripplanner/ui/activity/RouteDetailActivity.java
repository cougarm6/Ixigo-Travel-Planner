package com.kungfupandas.ixigotripplanner.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.adapter.TimeLineAdapter;
import com.kungfupandas.ixigotripplanner.pojo.Route;

/**
 * Created by tushar on 08/04/17.
 */

public class RouteDetailActivity extends ToolbarActivity {
    private RecyclerView mRecyclerView;
    public static final String BUNDLE_KEYS_ROUTE = "route";
    private Route mRoute;
    private TimeLineAdapter mTimeLineAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_route_details;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initView() {
        setToolbarTitle("Route _Details");
        initList();
    }

    private void initList() {
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_timeline);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mTimeLineAdapter = new TimeLineAdapter(mRoute.getSteps());
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    private void initData() {
        if (getIntent() == null || getIntent().getParcelableExtra(BUNDLE_KEYS_ROUTE) == null) {
            showMessage("Route data not available!");
            finish();
            return;
        }
        mRoute = getIntent().getParcelableExtra(BUNDLE_KEYS_ROUTE);
    }
}
