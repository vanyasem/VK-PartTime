package ru.semkin.ivan.parttime.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class DataManager {
    static SharedPreferences sPreferences = null;
    static SharedPreferences.Editor sPreferencesEditor = null;

    @SuppressLint("CommitPrefEdits")
    static public void initialize(Context context) {
        if(sPreferences == null)
            sPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(sPreferencesEditor == null)
            sPreferencesEditor = sPreferences.edit();
    }

    @SuppressLint("ApplySharedPref")
    static public void clear() {
        if(sPreferencesEditor == null)
            sPreferencesEditor = sPreferences.edit();
        sPreferencesEditor.clear();
        sPreferencesEditor.commit();
    }
}
