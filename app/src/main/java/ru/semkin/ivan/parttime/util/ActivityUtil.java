package ru.semkin.ivan.parttime.util;


import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class ActivityUtil {
    public static ActionBar setActionTitle(int titile, AppCompatActivity activity) {
        ActionBar actionBar = setActionBar(activity);
        actionBar.setTitle(titile);
        return actionBar;
    }

    public static ActionBar setActionBar(AppCompatActivity activity) {
        ActionBar actionBar = activity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        return actionBar;
    }
}
