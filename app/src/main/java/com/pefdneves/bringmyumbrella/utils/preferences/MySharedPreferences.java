package com.pefdneves.bringmyumbrella.utils.preferences;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class MySharedPreferences {

    private SharedPreferences mSharedPreferences;
    private final static String LOCATION = "KEY_LOCATION";
    private final static String USE_REMINDER = "USE_REMINDER";
    private final static String USE_CELSIUS = "USE_CELSIUS";
    private final static String REMINDER_TIME = "REMINDER_TIME";
    private final static String APP_USED_BEFORE = "APP_USED_BEFORE";

    @Inject
    public MySharedPreferences(SharedPreferences mSharedPreferences) {
        this.mSharedPreferences = mSharedPreferences;
    }

    private void putBoolean(String key, boolean data) {
        mSharedPreferences.edit().putBoolean(key, data).apply();
    }

    private boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }


    private void putString(String key, String data) {
        mSharedPreferences.edit().putString(key, data).apply();
    }

    private String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public void setLocation(String location) {
        putString(LOCATION, location);
    }

    public void setUseReminder(boolean useReminder) {
        putBoolean(USE_REMINDER, useReminder);
    }

    public void setUseCelsius(boolean useCelsius) {
        putBoolean(USE_CELSIUS, useCelsius);
    }

    public void setAppUsedBefore(boolean usedBefore) {
        putBoolean(APP_USED_BEFORE, usedBefore);
    }

    public void setReminderTime(long reminderTime) {
        putString(REMINDER_TIME, String.valueOf(reminderTime));
    }

    public long getReminderTime() {
        return Long.parseLong(getString(REMINDER_TIME));
    }

    public boolean useCelsius() {
        return getBoolean(USE_CELSIUS);
    }

    public boolean useReminder() {
        return getBoolean(USE_REMINDER);
    }

    public boolean appUsedBefore() {
        return getBoolean(APP_USED_BEFORE);
    }

    public String getLocation() {
        return getString(LOCATION);
    }
}