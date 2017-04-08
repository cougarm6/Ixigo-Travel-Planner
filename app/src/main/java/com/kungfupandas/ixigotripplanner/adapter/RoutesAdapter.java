package com.kungfupandas.ixigotripplanner.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.Route;

import java.util.List;

/**
 * Created by tushar on 08/04/17.
 */

public class RoutesAdapter extends RecyclerView.Adapter<RoutesAdapter.ViewHolder> {
    private List<Route> routeList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mSummaryTv;
        public TextView mPriceTv;
        public TextView mTimingTv;
        public ViewHolder(View v) {
            super(v);
            mSummaryTv = (TextView) v.findViewById(R.id.tv_heading);
            mPriceTv = (TextView) v.findViewById(R.id.tv_price);
            mTimingTv = (TextView) v.findViewById(R.id.tv_timing);
        }
    }
    public RoutesAdapter(List<Route> routeList) {
        this.routeList = routeList;
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
        Route route = routeList.get(position);
        holder.mPriceTv.setText("\u20B9 "+route.getPrice());
        holder.mSummaryTv.setText(route.getAllStepModes());
        holder.mTimingTv.setText(route.getDurationPretty());
    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }
}