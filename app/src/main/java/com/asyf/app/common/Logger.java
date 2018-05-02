package com.asyf.app.common;

import android.util.Log;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * Created by efan on 2017/4/13.
 */

public class Logger {

    //设为false关闭日志
    private static final boolean LOG_ENABLE = true;

    public static void i(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.i(tag, getCurrentDateTime() + msg);
        }
    }

    public static void v(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.v(tag, getCurrentDateTime() + msg);
        }
    }

    public static void d(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.d(tag, getCurrentDateTime() + msg);
        }
    }

    public static void w(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.w(tag, getCurrentDateTime() + msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_ENABLE) {
            Log.e(tag, getCurrentDateTime() + msg);
        }
    }

    private static String getCurrentDateTime() {
        return "asyf_log_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " - ";
    }

    public static void main(String[] args){
        String a = "asyf_log_" + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss") + " - ";
        System.out.print(a);
    }
}
