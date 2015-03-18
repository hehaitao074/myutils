
package com.iss.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.iss.loghandler.Log;

public class PhoneInfo {

    private static final String TAG = "PhoneInfo";

    /**
     * 获得通话记录
     * 
     * @param context
     */
    @SuppressLint("SimpleDateFormat")
    public static void printCallLog(Context context) {

        String str = "";
        String str2 = "";
        int type;
        Date date;
        String time = "";
        ContentResolver cr = context.getContentResolver();
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI, new String[] {
                CallLog.Calls.NUMBER, CallLog.Calls.CACHED_NAME, CallLog.Calls.TYPE,
                CallLog.Calls.DATE
        }, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToPosition(i);
            str = cursor.getString(0);// 电话好吗
            str2 = cursor.getString(1);// 名字
            type = cursor.getInt(2);// 1，打进来的电话。2， 拨打出去的，3，未接电话
            SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            date = new Date(Long.parseLong(cursor.getString(3)));
            time = sfd.format(date);
            Log.e(TAG, "str:" + str + " , str2:" + str2 + " , type:" + type + " , time:" + time);
        }
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftInputMode(Context context, View windowToken) {
        ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(windowToken.getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 获得channel id
     * 
     * @param ctx <meta-data android:name="SZICITY_CHANNEL"
     *            android:value="CHANNEL108"/>
     * @return
     */
    public static String getMetaData(Context context, String key) {
        String CHANNELID = "000000";
        if (TextUtils.isEmpty(key)) {
            key = "SZICITY_CHANNEL";
        }
        try {
            ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(),
                    PackageManager.GET_META_DATA);
            CHANNELID = ai.metaData.getString(key);
        } catch (Exception e) {
        }
        Log.e(TAG, "CHANNELID:" + CHANNELID);
        return CHANNELID;
    }

    /**
     * 获取当前的手机号
     */
    public static String getPhoneNumber(Context context) {
        TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String number = tManager.getLine1Number();
        Log.e(TAG, "所获得的手机号：" + number);
        return number;
    }

}
