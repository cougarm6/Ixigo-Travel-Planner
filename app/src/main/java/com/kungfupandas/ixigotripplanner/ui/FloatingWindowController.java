package com.kungfupandas.ixigotripplanner.ui;

import android.content.Context;
import android.view.LayoutInflater;

import com.kungfupandas.ixigotripplanner.R;
import com.kungfupandas.ixigotripplanner.pojo.City;

/**
 * Created by aish on 8/4/17.
 */

public class FloatingWindowController {
    private Context mContext;
    private FloatingView mFLoatingView;
    FloatingWindowMainView mainView;
    public static boolean isCreatedOnce = false;

    public void createAlertWindow(Context context, City city) {
        mContext = context;
        isCreatedOnce = true;
        if (null != mFLoatingView) {
            removeAlertWindow();
        }
        LayoutInflater inflater = (LayoutInflater)   mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        FloatingWindowMainView mainView = (FloatingWindowMainView) inflater.inflate(R.layout.trip_layout,null);
        mainView.setFloatingViewDismiss((FloatingView.FloatingWindowListener) context);
        mFLoatingView = new FloatingView(mContext, mainView);
        if (null != city) {
            mainView.init(city);
        }
        animateHead();
    }

    public void animateHead(){
        if(null!=mFLoatingView){
            mFLoatingView.animateFLoatingHead();
            /*Animation shakeAnimation =
                    AnimationUtils.loadAnimation(mContext, R.anim.shakeanim);
            mFLoatingView.startAnimation(shakeAnimation);*/
        }
    }

    /**
     * Remove and destroy system alert window
     * */
    public void removeAlertWindow(){
        if(mFLoatingView != null){
            mFLoatingView.removeView();
        }
        isCreatedOnce = false;
    }

    public boolean isAlertWindowCreated(){
        return mFLoatingView==null?false:true;
    }
}
