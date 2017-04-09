package com.kungfupandas.ixigotripplanner.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.adapter.TimeLineAdapter;
import com.kungfupandas.ixigotripplanner.pojo.Route;
import com.kungfupandas.ixigotripplanner.pojo.Step;

/**
 * Created by tushar on 08/04/17.
 */

public class RouteDetailActivity extends ToolbarActivity {
    private RecyclerView mRecyclerView;
    public static final String BUNDLE_KEYS_ROUTE = "route";
    public static final String BUNDLE_KEYS_FROM = "from";
    public static final String BUNDLE_KEYS_TO= "to";
    private Route mRoute;
    private TimeLineAdapter mTimeLineAdapter;
    private String to, from;

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
        initShare();
    }

    private void initShare() {
        findViewById(R.id.btn_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onShareClicked();
            }
        });
    }

    private void onShareClicked() {
        String shareString = getRouteString(mRoute);
        ShareCompat.IntentBuilder
                .from(this)
                .setText(shareString)
                .setType("text/plain")
                .setChooserTitle( "Trip to "+to)
                .startChooser();
    }

    private String getRouteString(Route mRoute) {
        StringBuilder builder = new StringBuilder("");
        if(mRoute!=null && mRoute.getSteps()!=null && mRoute.getSteps().size()>0){
            builder.append("Hey, I have planned a trip from "+from+" to "+to+" \nRoute as follows :\n");
            for(int i = 0; i  < mRoute.getSteps().size(); i++){
                Step step = mRoute.getSteps().get(i);
                builder.append(step.getModePretty()+" to "+step.getDestination()+", min cost (approx): "+step.getMinPrice()+" ₹ \n");
            }
        }
        return builder.toString();
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
        from = getIntent().getStringExtra(BUNDLE_KEYS_FROM);
        to = getIntent().getStringExtra(BUNDLE_KEYS_TO);
    }
}
