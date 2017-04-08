package com.kungfupandas.ixigotripplanner.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.WindowManager;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.network.GetAutocompleteCityImpl;
import com.kungfupandas.ixigotripplanner.network.NetworkUtils;
import com.kungfupandas.ixigotripplanner.pojo.City;
import com.kungfupandas.ixigotripplanner.pojo.NetworkResponse;
import com.kungfupandas.ixigotripplanner.ui.FloatingView;
import com.kungfupandas.ixigotripplanner.ui.FloatingWindowController;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aish on 8/4/17.
 */

public class CitySearchService extends Service implements FloatingView.FloatingWindowListener{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private FloatingWindowController mFloatingWindowController;
    private Context mContext;
    private Handler mHandler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(cityList!=null && cityList.size()>0 && mFloatingWindowController==null){
                mFloatingWindowController = new FloatingWindowController();
                mFloatingWindowController.createAlertWindow(mContext,cityList.get(0));
            }
        }
    };
    private List<City> cityList;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private static final int mLayoutParamFlags = WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
            | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mContext = this;
        if(mFloatingWindowController!=null){
            return super.onStartCommand(intent, flags, startId);
        }
        else if(!mFloatingWindowController.isCreatedOnce) {
        final GetAutocompleteCityImpl getAutocompleteCity = new GetAutocompleteCityImpl();
        ArrayList<String> words = intent.getStringArrayListExtra(AppConstants.IntentConfigs.WORDS_LIST);
        for(final String word:words) {
            Thread thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            NetworkResponse response = getAutocompleteCity.getData(AppConstants.ApiEndpoints.GET_AUTOCOMPLETE_CITY + word);
                            try {
                                cityList = NetworkUtils.parseCityResult(response.getData());
                                mHandler.sendEmptyMessage(1);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }catch (Exception e){}
                }};
                thread.start();
        }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onWindowDismissed() {
        FloatingWindowController.isCreatedOnce = false;
        mFloatingWindowController.removeAlertWindow();
        stopSelf();
    }
}
