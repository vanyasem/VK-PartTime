package ru.semkin.ivan.parttime.util;

import android.content.Context;

import ru.semkin.ivan.parttime.R;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class Util {

    static public String intToGender(Context context, int gender) {
        switch (gender) {
            case 1:
                return context.getString(R.string.gender_male);
            case 2:
                return context.getString(R.string.gender_female);
            default:
                return null;
        }
    }
}
