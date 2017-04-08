package com.kungfupandas.ixigotripplanner.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.Route;
import com.kungfupandas.ixigotripplanner.ui.activity.RouteDetailActivity;

import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {
    private final Context mContext;
    private List<Route> routeList;
    private String mDurationFastest, mDurationCheapest;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mSummaryTv;
        public TextView mPriceTv;
        public TextView mTimingTv;
        public TextView mModesTv;
        public ImageView mCheapIv;
        public ImageView mFastIv;
        public CardView mCardView;

        public ViewHolder(View v) {
            super(v);
            mSummaryTv = (TextView) v.findViewById(R.id.tv_heading);
            mPriceTv = (TextView) v.findViewById(R.id.tv_price);
            mTimingTv = (TextView) v.findViewById(R.id.tv_timing);
            mModesTv = (TextView) v.findViewById(R.id.tv_class);
            mCheapIv = (ImageView) v.findViewById(R.id.iv_cheap);
            mFastIv = (ImageView) v.findViewById(R.id.iv_fastest);
            mCardView = (CardView) v.findViewById(R.id.cv_route);
        }
    }

    public RoutesAdapter(List<Route> routeList, String durationFastest, String durationCheapest, Context context) {
        this.routeList = routeList;
        this.mDurationCheapest = durationCheapest;
        this.mDurationFastest = durationFastest;
        this.mContext = context;
    }

    @Override
    public RoutesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_routes_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Route route = routeList.get(position);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, RouteDetailActivity.class);
                intent.putExtra(RouteDetailActivity.BUNDLE_KEYS_ROUTE, route);
                mContext.startActivity(intent);
            }
        });
        holder.mPriceTv.setText("\u20B9 " + route.getPrice());
        holder.mSummaryTv.setText(route.getModeViaString());
        holder.mTimingTv.setText(route.getDurationPretty());
        holder.mModesTv.setText(route.getAllStepModes());
        if (route.getDurationPretty().equalsIgnoreCase(mDurationCheapest)) {
            holder.mCheapIv.setVisibility(View.VISIBLE);
        } else {
            holder.mCheapIv.setVisibility(View.GONE);
        }
        if (route.getDurationPretty().equalsIgnoreCase(mDurationFastest)) {
            holder.mFastIv.setVisibility(View.VISIBLE);
        } else {
            holder.mFastIv.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}