package ru.semkin.ivan.parttime.util;


import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class ActivityUtil {
    /**
     * Configures the ActionBar of an Activity that extends [AppCompatActivity]
     *
     * @param title @Nullable R.string of a desired title
     * @param activity Activity reference
     * @return ActionBar of that activity
     */
    public static ActionBar setActionTitle(int title, AppCompatActivity activity) {
        ActionBar actionBar = setActionBar(activity);
        actionBar.setTitle(title);
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

    /**
     * Attempts to close soft keyboard if currently open
     *
     * @param activity [Activity] reference
     */
    static public void hideKeyboard(Activity activity) {
        try {
            InputMethodManager imm =
                    (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            View currentFocus = activity.getCurrentFocus();
            if(currentFocus != null && imm != null) {
                imm.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        catch (Exception ignored) {}
    }
}
