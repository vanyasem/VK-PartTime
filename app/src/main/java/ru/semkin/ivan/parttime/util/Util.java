package ru.semkin.ivan.parttime.util;

import android.content.Context;
import android.text.format.DateUtils;

import ru.semkin.ivan.parttime.R;

/**
 * Created by Ivan Semkin on 5/7/18
 */
public class Util {

    static public String intToGender(Context context, int gender) {
        switch (gender) {
            case 0:
                return context.getString(R.string.not_specified);
            case 1:
                return context.getString(R.string.gender_female);
            case 2:
                return context.getString(R.string.gender_male);
            default:
                return null;
        }
    }

    static public CharSequence formatDate(Context context, long ts) {
        return DateUtils.getRelativeDateTimeString(context, ts * 1000, DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_RELATIVE);
    }
}
