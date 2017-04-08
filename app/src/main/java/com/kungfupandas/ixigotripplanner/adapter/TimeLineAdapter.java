package com.kungfupandas.ixigotripplanner.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.vipulasri.timelineview.TimelineView;
import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.Step;
import com.kungfupandas.ixigotripplanner.ui.viewholder.TimeLineViewHolder;

import java.util.List;

/**
 * Created by tushar on 09/04/17.
 */

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineViewHolder> {

    private List<Step> mStepList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<Step> steps) {
        mStepList = steps;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.row_timeline_item, parent, false);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        Step step = mStepList.get(position);
        holder.mTimelineView.setMarker(ContextCompat.getDrawable(mContext, R.drawable.ic_marker), ContextCompat.getColor(mContext, R.color.colorPrimary));
        holder.mDate.setText(step.getOrigin());
        holder.mMessage.setText(step.getModePretty()+ " - "+step.getMinPrice()+" ₹ \ncarrier:"+step.getCarrierName());
    }

    @Override
    public int getItemCount() {
        return (mStepList !=null? mStepList.size():0);
    }

}