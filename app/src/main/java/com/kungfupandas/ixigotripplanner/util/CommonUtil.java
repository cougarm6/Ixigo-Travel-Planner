package com.kungfupandas.ixigotripplanner.util;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.TypedValue;

/**
 * Created by aish on 8/4/17.
 */

public class CommonUtil {

    public static boolean isAboveMarshmallow(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
            return true;
        }
        return false;
    }

    public static boolean isAboveHoneyCombMR2(){
        if(Build.VERSION.SDK_INT> Build.VERSION_CODES.HONEYCOMB_MR2){
            return true;
        }
        return false;
    }

    public static boolean isLAndAbove(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
            return true;
        }
        return false;
    }

    public static float dpToPixel(Context context, float dp) {

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, metrics);
    }

    public static float pixelToDp(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float dp = px / (metrics.densityDpi / 160f);
        return dp;
    }
}
