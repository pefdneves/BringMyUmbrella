package com.pefdneves.bringmyumbrella.utils.debug;

import android.util.Log;

import com.pefdneves.bringmyumbrella.utils.Utils;

import java.util.logging.Logger;

/**
 * Custom logger class for debug
 */
public class CustomLogger {

    private static final String TAG = Logger.class.getSimpleName();
    private static final String EMPTY = "";

    private CustomLogger() {
        //Empty
    }

    /**
     * Send a VERBOSE log message.
     *
     * @param tag    the tag
     * @param format the format
     * @param args   the args
     * @return int
     */
    public static int v(String tag, String format, Object... args) {
        if (Utils.LOGGING)
            return Log.v(tag, format(format, args));
        else
            return -1;
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag the tag
     * @param msg the message
     * @param e   the exception
     * @return int
     */
    public static int v(String tag, String msg, Throwable e) {
        if (Utils.LOGGING)
            return Log.v(tag, msg, e);
        else
            return -1;
    }

    /**
     * Send a VERBOSE log message and log the exception.
     *
     * @param tag    the tag
     * @param format the format
     * @param e      the exception
     * @param args   the args
     * @return int
     */
    public static int v(String tag, String format, Throwable e, Object... args) {
        if (Utils.LOGGING)
            return Log.v(tag, format(format, args), e);
        else
            return -1;
    }

    /**
     * Send a DEBUG log message.
     *
     * @param tag    the tag
     * @param format the format
     * @param args   the args
     * @return int
     */
    public static int d(String tag, String format, Object... args) {
        if (Utils.LOGGING)
            return Log.d(tag, format(format, args));
        else
            return -1;
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag the tag
     * @param msg the message
     * @param e   the exception
     * @return int
     */
    public static int d(String tag, String msg, Throwable e) {
        if (Utils.LOGGING)
            return Log.d(tag, msg, e);
        else
            return -1;
    }

    /**
     * Send a DEBUG log message and log the exception.
     *
     * @param tag    the tag
     * @param format the format
     * @param e      the exception
     * @param args   the args
     * @return int
     */
    public static int d(String tag, String format, Throwable e, Object... args) {
        if (Utils.LOGGING)
            return Log.d(tag, format(format, args), e);
        else
            return -1;
    }

    /**
     * Send a WARN log message.
     *
     * @param tag    the tag
     * @param format the format
     * @param args   the args
     * @return int
     */
    public static int w(String tag, String format, Object... args) {
        if (Utils.LOGGING)
            return Log.w(tag, format(format, args));
        else
            return -1;
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag the tag
     * @param msg the msg
     * @param e   the exception
     * @return int
     */
    public static int w(String tag, String msg, Throwable e) {
        if (Utils.LOGGING)
            return Log.w(tag, msg, e);
        else
            return -1;
    }

    /**
     * Send a WARN log message and log the exception.
     *
     * @param tag    the tag
     * @param format the format
     * @param e      the exception
     * @param args   the args
     * @return int
     */
    public static int w(String tag, String format, Throwable e, Object... args) {
        if (Utils.LOGGING)
            return Log.w(tag, format(format, args), e);
        else
            return -1;
    }

    /**
     * Send a INFO log message.
     *
     * @param tag    the tag
     * @param format the format
     * @param args   the args
     * @return int
     */
    public static int i(String tag, String format, Object... args) {
        if (Utils.LOGGING)
            return Log.i(tag, format(format, args));
        else
            return -1;
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag the tag
     * @param msg the msg
     * @param e   the exception
     * @return int
     */
    public static int i(String tag, String msg, Throwable e) {
        if (Utils.LOGGING)
            return Log.i(tag, msg, e);
        else
            return -1;
    }

    /**
     * Send a INFO log message and log the exception.
     *
     * @param tag    the tag
     * @param format the format
     * @param e      the exception
     * @param args   the args
     * @return int
     */
    public static int i(String tag, String format, Throwable e, Object... args) {
        if (Utils.LOGGING)
            return Log.i(tag, format(format, args), e);
        else
            return -1;
    }

    /**
     * Send a ERROR log message.
     *
     * @param tag    the tag
     * @param format the format
     * @param args   the args
     * @return int
     */
    public static int e(String tag, String format, Object... args) {
        if (Utils.LOGGING)
            return Log.e(tag, format(format, args));
        else
            return -1;
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag the tag
     * @param msg the msg
     * @param e   the exception
     * @return int
     */
    public static int e(String tag, String msg, Throwable e) {
        if (Utils.LOGGING)
            return Log.e(tag, msg, e);
        else
            return -1;
    }

    /**
     * Send a ERROR log message and log the exception.
     *
     * @param tag    the tag
     * @param format the format
     * @param e      the exception
     * @param args   the args
     * @return int
     */
    public static int e(String tag, String format, Throwable e, Object... args) {
        if (Utils.LOGGING)
            return Log.e(tag, format(format, args), e);
        else
            return -1;
    }

    /**
     * @param format the format
     * @param args   the args
     * @return string
     */
    private static String format(String format, Object... args) {
        try {
            return String.format(format == null ? EMPTY : format, args);
        } catch (RuntimeException e) {
            CustomLogger.w(TAG, "format error. reason=%s, format=%s", e.getMessage(), format, e);
            return String.format(EMPTY, format);
        }
    }
}
