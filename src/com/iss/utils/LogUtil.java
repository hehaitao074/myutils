
package com.iss.utils;

import android.util.Log;

public class LogUtil {
    public static void e(String tag, String msg) {
        Log.e(tag, msg);
    }

    public static void d(String tag, String msg) {
        Log.d(tag, msg);
    }

    public static void d(String message, Object... args) {
        d("iss", String.format(message, args));
    }
}
