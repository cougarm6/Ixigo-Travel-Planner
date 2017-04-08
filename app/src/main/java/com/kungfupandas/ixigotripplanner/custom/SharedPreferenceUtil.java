package com.kungfupandas.ixigotripplanner.custom;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tushar on 08/04/17.
 */

public class SharedPreferenceUtil {
    private static final String PREF_NAME = "Base_Prefs";
    private static final String PREF_NAME_GLOBAL = "Base_Prefs_Global";

    private SharedPreferences baseSharedPrefs;
    private SharedPreferences baseGlobalSharedPrefs;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor globalEditor;
    private static SharedPreferenceUtil sharedPreferenceUtil;

    private static void initializeSharedPref(Context context) {
        sharedPreferenceUtil = new SharedPreferenceUtil();
        sharedPreferenceUtil.baseSharedPrefs = context
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sharedPreferenceUtil.editor = sharedPreferenceUtil.baseSharedPrefs
                .edit();
    }

    public static synchronized SharedPreferenceUtil getInstance(Context context) {
        if (sharedPreferenceUtil == null || sharedPreferenceUtil.editor == null) {
            initializeSharedPref(context);
        }
        return sharedPreferenceUtil;
    }

    public static SharedPreferenceUtil getGlobalInstance(Context context) {
        SharedPreferenceUtil sharedPreferenceUtil = new SharedPreferenceUtil();
        sharedPreferenceUtil.baseGlobalSharedPrefs = context
                .getSharedPreferences(PREF_NAME_GLOBAL, Activity.MODE_PRIVATE);
        sharedPreferenceUtil.globalEditor = sharedPreferenceUtil.baseGlobalSharedPrefs
                .edit();
        return sharedPreferenceUtil;
    }

    private SharedPreferenceUtil() {
        // TODO Auto-generated constructor stub
    }

    private boolean returnIfEditorNotAvailable(SharedPreferences.Editor editor) {
        if (editor == null)
            return false;
        else
            return editor.commit();
    }

    public synchronized boolean saveData(String key, String value) {
        Logger.info("Shared Preferences:", "saving key: " + key + ", value: " + value);
        if (editor != null)
            editor.putString(key, value);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized boolean saveDataGlobal(String key, String value) {
        Logger.info("Shared Preferences:", "saving key: " + key + ", value: " + value);
        if (globalEditor != null)
            globalEditor.putString(key, value);
        return returnIfEditorNotAvailable(globalEditor);
    }

    public synchronized boolean saveData(String key, boolean value) {
        if (editor != null)
            editor.putBoolean(key, value);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized boolean saveData(String key, long value) {
        if (editor != null)
            editor.putLong(key, value);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized boolean saveData(String key, float value) {
        if (editor != null)
            editor.putFloat(key, value);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized boolean saveData(String key, int value) {
        Logger.info("Shared Preferences:", "saving key: " + key + ", value: " + value);
        if (editor != null)
            editor.putInt(key, value);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized boolean removeData(String key) {
        if (editor != null)
            editor.remove(key);
        return returnIfEditorNotAvailable(editor);
    }

    public synchronized Boolean getData(String key, boolean defaultValue) {
        if(baseSharedPrefs == null)
            return false;
        else
            return baseSharedPrefs.getBoolean(key, defaultValue);
    }

    public synchronized String getData(String key, String defaultValue) {
        if(baseSharedPrefs == null)
            return defaultValue;
        else
            return baseSharedPrefs.getString(key, defaultValue);
    }

    public synchronized String getDataGlobal(String key, String defaultValue) {
        if(baseGlobalSharedPrefs == null)
            return defaultValue;
        else
            return baseGlobalSharedPrefs.getString(key, defaultValue);
    }

    public synchronized float getData(String key, float defaultValue) {
        if(baseSharedPrefs == null)
            return defaultValue;
        else
            return baseSharedPrefs.getFloat(key, defaultValue);
    }

    public synchronized int getData(String key, int defaultValue) {
        if(baseSharedPrefs == null)
            return defaultValue;
        else
            return baseSharedPrefs.getInt(key, defaultValue);
    }

    public synchronized long getData(String key, long defaultValue) {
        if(baseSharedPrefs == null)
            return defaultValue;
        else
            return baseSharedPrefs.getLong(key, defaultValue);
    }

    public synchronized void deleteAllData() {
        sharedPreferenceUtil = null;
        if (editor != null) {
            editor.clear();
            editor.commit();
        }
    }
}
