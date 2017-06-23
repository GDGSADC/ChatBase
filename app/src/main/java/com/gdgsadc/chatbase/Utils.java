package com.gdgsadc.chatbase;

import android.content.Context;
import android.text.format.DateUtils;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by dan on 23/06/17.
 */

public class Utils {

    public static String getDate(Date date, Context mContext){
        DateTime dateTime = new DateTime(date);
        return String.valueOf(DateUtils.getRelativeTimeSpanString(dateTime.getMillis(),
                System.currentTimeMillis(),
                DateUtils.SECOND_IN_MILLIS).toString().toLowerCase());
    }

}
