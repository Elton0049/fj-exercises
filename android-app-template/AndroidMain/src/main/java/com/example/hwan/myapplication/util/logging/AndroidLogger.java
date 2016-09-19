/*
 * MyAwesomeApp project template
 *
 * Distributed under no licences and no warranty.
 */
package com.example.hwan.myapplication.util.logging;

import android.util.Log;

import com.example.hwan.myapplication.util.ArrayUtils;

/**
 * Android logger. For adjusting logging level,
 * see <a href="https://developer.android.com/reference/android/util/Log.html#isLoggable(java.lang.String, int)">Adjusting loglevel</a> documentation.
 *
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 01 - Sep - 2016
 */
class AndroidLogger implements Logger {
    protected static final String NULL = "null";
    private final String tagName;

    AndroidLogger(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public void v(String format, Object... args) {
        if (Log.isLoggable(tagName, Log.VERBOSE)) {
            String logMsg = formatString(format, args);
            Log.v(tagName, logMsg);
        }
    }

    @Override
    public void v(Throwable t, String format, Object... args) {
        if (Log.isLoggable(tagName, Log.VERBOSE)) {
            String logMsg = formatString(format, args);
            Log.v(tagName, logMsg, t);
        }
    }

    @Override
    public void v(Object obj) {
        if (Log.isLoggable(tagName, Log.VERBOSE)) {
            String logMsg = obj == null ? NULL : obj.toString();
            Log.v(tagName, logMsg);
        }
    }

    @Override
    public void d(String format, Object... args) {
        if (Log.isLoggable(tagName, Log.DEBUG)) {
            String logMsg = formatString(format, args);
            Log.d(tagName, logMsg);
        }
    }

    @Override
    public void d(Throwable t, String format, Object... args) {
        if (Log.isLoggable(tagName, Log.DEBUG)) {
            String logMsg = formatString(format, args, t);
            Log.d(tagName, logMsg);
        }
    }

    @Override
    public void d(Object obj) {
        if (Log.isLoggable(tagName, Log.DEBUG)) {
            String logMsg = obj == null ? NULL : obj.toString();
            Log.d(tagName, logMsg);
        }
    }

    @Override
    public void i(String format, Object... args) {
        if (Log.isLoggable(tagName, Log.INFO)) {
            String logMsg = formatString(format, args);
            Log.i(tagName, logMsg);
        }
    }

    @Override
    public void i(Throwable t, String format, Object... args) {
        if (Log.isLoggable(tagName, Log.INFO)) {
            String logMsg = formatString(format, args, t);
            Log.i(tagName, logMsg);
        }
    }

    @Override
    public void i(Object obj) {
        if (Log.isLoggable(tagName, Log.INFO)) {
            String logMsg = obj == null ? NULL : obj.toString();
            Log.i(tagName, logMsg);
        }
    }

    @Override
    public void w(String format, Object... args) {
        if (Log.isLoggable(tagName, Log.WARN)) {
            String logMsg = formatString(format, args);
            Log.w(tagName, logMsg);
        }
    }

    @Override
    public void w(Throwable t, String format, Object... args) {
        if (Log.isLoggable(tagName, Log.WARN)) {
            String logMsg = formatString(format, args, t);
            Log.w(tagName, logMsg);
        }
    }

    @Override
    public void w(Object obj) {
        if (Log.isLoggable(tagName, Log.WARN)) {
            String logMsg = obj == null ? NULL : obj.toString();
            Log.w(tagName, logMsg);
        }
    }

    @Override
    public void e(String format, Object... args) {
        if (Log.isLoggable(tagName, Log.ERROR)) {
            String logMsg = formatString(format, args);
            Log.e(tagName, logMsg);
        }
    }

    @Override
    public void e(Throwable t, String format, Object... args) {
        if (Log.isLoggable(tagName, Log.ERROR)) {
            String logMsg = formatString(format, args, t);
            Log.e(tagName, logMsg);
        }
    }

    @Override
    public void e(Object obj) {
        if (Log.isLoggable(tagName, Log.ERROR)) {
            String logMsg = obj == null ? NULL : obj.toString();
            Log.e(tagName, logMsg);
        }
    }

    @Override
    public void log(int priority, String message, Throwable t) {
        if (Log.isLoggable(tagName, priority)) {
            switch(priority) {
                case Log.VERBOSE:
                    Log.v(tagName, message, t);
                    break;
                case Log.DEBUG:
                    Log.d(tagName, message, t);
                    break;
                case Log.INFO:
                    Log.i(tagName, message, t);
                    break;
                case Log.WARN:
                    Log.w(tagName, message, t);
                    break;
                case Log.ERROR:
                    Log.e(tagName, message, t);
                    break;
                default:
                    if (null == t) {
                        Log.println(priority, tagName, message);
                    } else {
                        Log.println(priority, tagName, message + '\n' + Log.getStackTraceString(t));
                    }
            }
        }
    }

    private static String formatString(String format, Object... args) {
        if (ArrayUtils.isEmpty(args)) {
            return format;
        } else {
            return String.format(format, args);
        }
    }
}
