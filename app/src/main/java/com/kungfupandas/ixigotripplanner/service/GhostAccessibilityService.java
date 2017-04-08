package com.kungfupandas.ixigotripplanner.service;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.Intent;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.kungfupandas.ixigotripplanner.AppConstants;
import com.kungfupandas.ixigotripplanner.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aishwarya on 02/09/16.
 */
public class GhostAccessibilityService extends AccessibilityService {
    //ListUploadModel listUploadModel = new ListUploadModel();
    private Intent floatingWindowServiceIntent;
    private String city;
    private String[] searchKeywords = {"Trip","Chalega"};
    private ArrayList<String> words;
    private boolean hadKeyword;


    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo source = event.getSource();
/*        if(event!=null && event.getSource()!=null && event.getSource().getClassName()!=null) {
            Log.i("Required Text", String.valueOf(event.getSource().getClassName()));
        }*/
        if (source == null) {
            return;
        }
        words = new ArrayList<>();
        hadKeyword = false;
        printViewHierarchy(source, "0");
        if(hadKeyword){
            searchForCity();
        }
    }

    private void searchForCity() {
        String[] cityArray = getResources().getStringArray(R.array.india_top_places);
        ArrayList<String> cityWords = new ArrayList<>();
        for(String city:cityArray){
            if(words.contains(city)){
                cityWords.add(city);
            }
        }
        Intent searchCityIntent = new Intent(this,CitySearchService.class);
        searchCityIntent.putExtra(AppConstants.IntentConfigs.WORDS_LIST,cityWords);
        startService(searchCityIntent);
    }


    private void printViewHierarchy(AccessibilityNodeInfo root, String path){
        if(root==null){
            return;
        }
        //Log.d("test",path + "->" + root.getClassName() + " " + root.getText());
        if(root.getClassName().toString().contains("TextView") && root.getText()!=null){
            Log.d("words",root.getText().toString());
            for (String keyword:searchKeywords){
                if(root.getText().toString().contains(keyword)){
                    hadKeyword = true;
                }
                List<String> splitwords = Arrays.asList(root.getText().toString().split("\\s+"));
                words.addAll(splitwords);
            }
        }
        if(root.getChildCount()>0) {
            for (int i = 0; i < root.getChildCount(); i++) {
                printViewHierarchy(root.getChild(i), path + "->" + i);
            }
        }
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    protected void onServiceConnected() {
        // Set the type of events that this service wants to listen to. Others won't be passed to this service.
        // We are only considering windows state changed event.
        AccessibilityServiceInfo info = new AccessibilityServiceInfo();
        info.eventTypes = AccessibilityEvent.TYPE_VIEW_CLICKED | AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED | AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED;
        // If you only want this service to work with specific applications, set their package names here. Otherwise, when the service is activated, it will listen to events from all applications.
        info.packageNames = new String[] {"com.whatsapp","com.nnacres.app"};
        // Set the type of feedback your service will provide. We are setting it to GENERIC.
        info.feedbackType = AccessibilityServiceInfo.FEEDBACK_GENERIC;
        // Default services are invoked only if no package-specific ones are present for the type of AccessibilityEvent generated.
        // This is a general-purpose service, so we will set some flags
        info.flags = AccessibilityServiceInfo.DEFAULT;
        info.flags = AccessibilityServiceInfo.FLAG_INCLUDE_NOT_IMPORTANT_VIEWS; info.flags = AccessibilityServiceInfo.FLAG_REPORT_VIEW_IDS;
        info.flags = AccessibilityServiceInfo.CAPABILITY_CAN_RETRIEVE_WINDOW_CONTENT;
        info.flags = AccessibilityServiceInfo.FLAG_REQUEST_ENHANCED_WEB_ACCESSIBILITY; info.flags = AccessibilityServiceInfo.FLAG_RETRIEVE_INTERACTIVE_WINDOWS;
        // We are keeping the timeout to 0 as we don’t need any delay or to pause our accessibility events
        info.notificationTimeout = 0;
        this.setServiceInfo(info);

    }
}
