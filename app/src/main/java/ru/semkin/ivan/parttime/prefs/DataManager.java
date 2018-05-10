package ru.semkin.ivan.parttime.prefs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class DataManager {
    static SharedPreferences preferences = null;
    static SharedPreferences.Editor preferencesEditor = null;

    @SuppressLint("CommitPrefEdits")
    static public void initialize(Context context) {
        if(preferences == null)
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if(preferencesEditor == null)
            preferencesEditor = preferences.edit();
    }

    @SuppressLint("ApplySharedPref")
    static public void clear() {
        if(preferencesEditor == null)
            preferencesEditor = preferences.edit();
        preferencesEditor.clear();
        preferencesEditor.commit();
    }
}
