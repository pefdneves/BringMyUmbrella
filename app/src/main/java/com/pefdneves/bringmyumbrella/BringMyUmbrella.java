package com.pefdneves.bringmyumbrella;

import com.pefdneves.bringmyumbrella.dependencyinjection.DaggerApplicationComponent;
import com.pefdneves.bringmyumbrella.model.DayForecastRepository;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManager;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;
import com.pefdneves.bringmyumbrella.utils.preferences.PreferencesUtils;

import javax.inject.Inject;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.HasBroadcastReceiverInjector;

public class BringMyUmbrella extends DaggerApplication implements HasBroadcastReceiverInjector {

    @Inject
    public DayForecastRepository dayForecastRepository;

    @Inject
    MySharedPreferences mySharedPreferences;

    @Inject
    NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        checkPreferences();
    }

    private void checkPreferences() {
        if (!mySharedPreferences.appUsedBefore()) {
            mySharedPreferences.setUseCelsius(PreferencesUtils.DEFAULT_USE_CELSIUS);
            mySharedPreferences.setUseReminder(PreferencesUtils.DEFAULT_USE_REMINDER);
            mySharedPreferences.setReminderTime(PreferencesUtils.DEFAULT_REMINDER_TIME);
            mySharedPreferences.setAppUsedBefore(true);
        }
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerApplicationComponent.builder().application(this).build();
    }
}
