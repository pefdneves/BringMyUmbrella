package com.pefdneves.bringmyumbrella.broadcasts;

import android.content.Context;
import android.content.Intent;

import com.pefdneves.bringmyumbrella.dependencyinjection.BroadcastReceiverScope;
import com.pefdneves.bringmyumbrella.model.DataSource;
import com.pefdneves.bringmyumbrella.model.DayForecastRepository;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManager;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationTypes;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationUtils;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;

import org.joda.time.LocalDateTime;

import java.util.List;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

@BroadcastReceiverScope
public class AlertReceiver extends DaggerBroadcastReceiver {

    @Inject
    MySharedPreferences mySharedPreferences;

    @Inject
    NotificationManager mNotificationManager;

    @Inject
    DayForecastRepository mDayForecastRepository;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        int notificationType = intent.getIntExtra(NotificationUtils.NOTIFICATION_INTENT, -1);
        if (notificationType == NotificationTypes.REMINDER.ordinal()) {
            generateNotification(context);
        } else if (notificationType == NotificationTypes.DAILY_UPDATE.ordinal()) {
            downloadData();
        }
        mNotificationManager.scheduleNotifications(context);
    }

    private void downloadData() {
        mDayForecastRepository.getDayForecasts(mySharedPreferences.getLocation(), new DataSource.LoadDayForecastsCallback() {
            @Override
            public void onLoadForecastsLoaded(List<DayForecast> forecasts) {
                //do nothing - just to update
            }

            @Override
            public void onDataUnavailable() {
                //do nothing - just to update
            }
        });
    }

    private void generateNotification(final Context context) {
        mDayForecastRepository.getDayForecasts(mySharedPreferences.getLocation(), new DataSource.LoadDayForecastsCallback() {
            @Override
            public void onLoadForecastsLoaded(List<DayForecast> forecasts) {
                if (forecasts != null && !forecasts.isEmpty()) {
                    if (WeatherLogicUtils.getForecastWorstCase(new LocalDateTime(), forecasts).isRain()) {
                        mNotificationManager.generateNotification(context);
                    }
                }
            }

            @Override
            public void onDataUnavailable() {
                //do nothing
            }
        });
    }
}
