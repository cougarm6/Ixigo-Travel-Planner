package com.kungfupandas.ixigotripplanner.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.ui.activity.TripResultActivity;

/**
 * Created by aish on 8/4/17.
 */

public class FloatingWindowMainView extends CardView {

    TextView textView;
    Button openTripActivity;

    private FloatingView.FloatingWindowListener mFloatingWindowListener;

    public void setFloatingViewDismiss(FloatingView.FloatingWindowListener floatingWindowListener) {
        this.mFloatingWindowListener = floatingWindowListener;
    }

    public FloatingWindowMainView(Context context) {
        super(context);

    }

    public FloatingWindowMainView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatingWindowMainView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(City city) {
        textView = (TextView) findViewById(R.id.show_text);
        openTripActivity = (Button) findViewById(R.id.bt_open_city);
        initData(city);
    }

    private void initData(final City city) {
        textView.setText("It seems you want to travel to "+city.getCityName());
        openTripActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TripResultActivity.class);
                intent.putExtra(TripResultActivity.BUNDLE_KEY_DESTINATION_CITY,city);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getContext().startActivity(intent);
                FloatingWindowController.isCreatedOnce = false;
                mFloatingWindowListener.onWindowDismissed();
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if(null!=mFloatingWindowListener) {
                mFloatingWindowListener.onWindowDismissed();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
