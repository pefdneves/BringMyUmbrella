package com.pefdneves.bringmyumbrella.ui.settings;

import android.content.Context;

import com.pefdneves.bringmyumbrella.dependencyinjection.ActivityScope;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManager;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;

import org.joda.time.LocalDateTime;

import javax.inject.Inject;

@ActivityScope
public class SettingsPresenter implements SettingsContract.Presenter {

    @Inject
    NotificationManager mNotificationManager;

    @Inject
    MySharedPreferences mySharedPreferences;

    private SettingsContract.View mSettingsView;

    @Inject
    SettingsPresenter() {
    }

    @Override
    public void setLocation(String location) {
        if (location != null)
            mySharedPreferences.setLocation(location);
    }

    @Override
    public void setCelsius(boolean useCelsius) {
        mySharedPreferences.setUseCelsius(useCelsius);
    }

    @Override
    public void setReminder(boolean useReminder) {
        mySharedPreferences.setUseReminder(useReminder);
        if(!useReminder)
            mNotificationManager.cancelAlert((Context) mSettingsView);
    }

    @Override
    public void setReminderTime(int hourOfDay, int minute) {
        LocalDateTime localDateTime = new LocalDateTime().hourOfDay().setCopy(hourOfDay).minuteOfHour().setCopy(minute);
        if (localDateTime.isBefore(new LocalDateTime()))
            mySharedPreferences.setReminderTime(localDateTime.plusDays(1).toDateTime().getMillis());
        else
            mySharedPreferences.setReminderTime(localDateTime.toDateTime().getMillis());
        mNotificationManager.scheduleNotifications((Context) mSettingsView);
    }

    @Override
    public void loadSettings() {
        mSettingsView.showSettings(mySharedPreferences.getLocation(), mySharedPreferences.useCelsius(), mySharedPreferences.useReminder());
    }

    @Override
    public void takeView(SettingsContract.View view) {
        mSettingsView = view;
    }

    @Override
    public void dropView() {
        mSettingsView = null;
    }
}
