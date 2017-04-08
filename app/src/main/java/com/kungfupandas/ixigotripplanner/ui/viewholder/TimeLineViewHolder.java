package com.kungfupandas.ixigotripplanner.ui.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.vipulasri.timelineview.TimelineView;
import com.kungfupandas.ixigotripplanner.R;

/**
 * Created by tushar on 09/04/17.
 */

public class TimeLineViewHolder extends RecyclerView.ViewHolder {

    public TextView mDate, mMessage;
    public TimelineView mTimelineView;

    public TimeLineViewHolder(View itemView, int viewType) {
        super(itemView);

        mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);
        mMessage = (TextView) itemView.findViewById(R.id.text_timeline_title);
        mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
        mTimelineView.initLine(viewType);
    }
}