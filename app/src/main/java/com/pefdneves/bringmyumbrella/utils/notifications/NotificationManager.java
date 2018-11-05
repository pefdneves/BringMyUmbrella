package com.pefdneves.bringmyumbrella.utils.notifications;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.pefdneves.bringmyumbrella.R;
import com.pefdneves.bringmyumbrella.broadcasts.AlertReceiver;
import com.pefdneves.bringmyumbrella.ui.mainscreen.BringMainActivity;
import com.pefdneves.bringmyumbrella.utils.debug.CustomLogger;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;

import org.joda.time.LocalDateTime;

import java.util.Calendar;

import javax.inject.Inject;

import static com.pefdneves.bringmyumbrella.utils.notifications.NotificationUtils.NOTIFICATION_CHANNEL_APP_NOTIFICATIONS;
import static com.pefdneves.bringmyumbrella.utils.notifications.NotificationUtils.NOTIFICATION_CHANNEL_ID_APP_NOTIFICATIONS;
import static com.pefdneves.bringmyumbrella.utils.ui.UiUtils.getAppIconBitmap;

public class NotificationManager {

    private static final String TAG = NotificationManager.class.getSimpleName();

    @Inject
    MySharedPreferences mySharedPreferences;

    private android.app.NotificationManager mNotificationManager;
    private NotificationChannel mChannelNotifications;

    @Inject
    public NotificationManager(android.app.NotificationManager notificationManager) {
        mNotificationManager = notificationManager;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannels(notificationManager);
        }
    }

    public void scheduleNotifications(Context context) {
        scheduleDailyUpdate(context);
        scheduleReminder(context);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannels(android.app.NotificationManager notificationManager) {
        mChannelNotifications = new NotificationChannel(NOTIFICATION_CHANNEL_ID_APP_NOTIFICATIONS, NotificationUtils.NOTIFICATION_CHANNEL_APP_NOTIFICATIONS, android.app.NotificationManager.IMPORTANCE_HIGH);
        mChannelNotifications.enableLights(true);
        mChannelNotifications.enableVibration(true);
        mNotificationManager.createNotificationChannel(mChannelNotifications);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private Notification getNotificationAndroidO(Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID_APP_NOTIFICATIONS);
        builder.setContentText(context.getString(R.string.notification_text));
        builder.setContentTitle(context.getString(R.string.app_name));
        builder.setTicker(context.getString(R.string.notification_text));
        builder.setSmallIcon(R.drawable.icons8_keep_dry_96);
        builder.setLargeIcon(getAppIconBitmap(context));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BringMainActivity.class);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder.setContentIntent(resultPendingIntent);
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.FLAG_AUTO_CANCEL);
        builder.setAutoCancel(true);
        return builder.build();
    }

    private void generateNotificationLowerAndroidO(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_APP_NOTIFICATIONS)
                        .setSmallIcon(R.drawable.icons8_keep_dry_96)
                        .setLargeIcon(getAppIconBitmap(context))
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getString(R.string.notification_text)))
                        .setContentText(context.getString(R.string.notification_text));
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N)
            mBuilder.setContentTitle(context.getString(R.string.app_name));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(BringMainActivity.class);
        Intent resultIntent = new Intent(context, BringMainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE | Notification.FLAG_AUTO_CANCEL);
        mBuilder.setAutoCancel(true);
        try {
            mNotificationManager.notify(NotificationUtils.NOTIFICATION_ID, mBuilder.build());
        } catch (Exception e) {
            CustomLogger.e(TAG, e.toString(), e);
        }
    }

    private void scheduleDailyUpdate(Context context) {
        Calendar dateTimeToAlarm = Calendar.getInstance();
        dateTimeToAlarm.set(Calendar.HOUR_OF_DAY, NotificationUtils.DEFAULT_HOUR_UPDATE);
        dateTimeToAlarm.set(Calendar.MINUTE, NotificationUtils.DEFAULT_MINUTE_UPDATE);
        if (dateTimeToAlarm.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertReceiver.class);
            intent.putExtra(NotificationUtils.NOTIFICATION_INTENT, NotificationTypes.DAILY_UPDATE.ordinal());
            PendingIntent pi = PendingIntent.getBroadcast(context, NotificationTypes.DAILY_UPDATE.ordinal(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            setExactDependingOnApi(am, dateTimeToAlarm.getTimeInMillis(), pi);
        } else {
            dateTimeToAlarm.add(Calendar.DAY_OF_YEAR, 1);
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertReceiver.class);
            intent.putExtra(NotificationUtils.NOTIFICATION_INTENT, NotificationTypes.DAILY_UPDATE.ordinal());
            PendingIntent pi = PendingIntent.getBroadcast(context, NotificationTypes.DAILY_UPDATE.ordinal(),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            setExactDependingOnApi(am, dateTimeToAlarm.getTimeInMillis(), pi);
        }
    }

    private void setExactDependingOnApi(AlarmManager am, long triggerAtMillis, PendingIntent operation) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
            } else {
                am.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, operation);
            }
        } catch (Exception e) {
            CustomLogger.e(TAG, e.toString(), e);
        }
    }

    public void cancelAlert(Context context) {
        LocalDateTime localDateTime = new LocalDateTime(mySharedPreferences.getReminderTime());
        LocalDateTime now = new LocalDateTime();
        localDateTime = localDateTime.year().setCopy(now.year().get()).dayOfYear().setCopy(now.dayOfYear().get());
        boolean isToday = now.isBefore(localDateTime);
        Calendar dateTimeToAlarm = Calendar.getInstance();
        if (!isToday)
            dateTimeToAlarm.add(Calendar.DAY_OF_YEAR, 1);
        LocalDateTime localDateTime2 = new LocalDateTime(mySharedPreferences.getReminderTime());
        dateTimeToAlarm.set(Calendar.HOUR_OF_DAY, localDateTime2.getHourOfDay());
        dateTimeToAlarm.set(Calendar.MINUTE, localDateTime2.getMinuteOfHour());
        Intent intent = new Intent(context, AlertReceiver.class);
        PendingIntent pendingIntent;
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        pendingIntent = PendingIntent.getBroadcast(context, dateTimeToAlarm.get(Calendar.DAY_OF_MONTH), intent, 0);
        if (alarmManager != null)
            alarmManager.cancel(pendingIntent);
    }

    private void scheduleReminder(Context context) {
        if (!mySharedPreferences.useReminder())
            return;
        LocalDateTime localDateTime = new LocalDateTime(mySharedPreferences.getReminderTime());
        LocalDateTime now = new LocalDateTime();
        localDateTime = localDateTime.year().setCopy(now.year().get()).dayOfYear().setCopy(now.dayOfYear().get());
        boolean isToday = now.isBefore(localDateTime);
        Calendar dateTimeToAlarm = Calendar.getInstance();
        if (!isToday) {
            dateTimeToAlarm.add(Calendar.DAY_OF_YEAR, 1);
        }
        dateTimeToAlarm.set(Calendar.HOUR_OF_DAY, localDateTime.getHourOfDay());
        dateTimeToAlarm.set(Calendar.MINUTE, localDateTime.getMinuteOfHour());
        if (dateTimeToAlarm.getTimeInMillis() > Calendar.getInstance().getTimeInMillis()) {
            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(context, AlertReceiver.class);
            intent.putExtra(NotificationUtils.NOTIFICATION_INTENT, NotificationTypes.REMINDER.ordinal());
            PendingIntent pi = PendingIntent.getBroadcast(context, dateTimeToAlarm.get(Calendar.DAY_OF_MONTH),
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            setExactDependingOnApi(am, dateTimeToAlarm.getTimeInMillis(), pi);
        }
    }

    public void generateNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            generateNotificationAndroidO(context);
        else
            generateNotificationLowerAndroidO(context);
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void generateNotificationAndroidO(Context context) {
        try {
            Notification notification = getNotificationAndroidO(context);
            mNotificationManager.notify(NotificationTypes.REMINDER.ordinal(), notification);
        } catch (Exception e) {
            CustomLogger.e(TAG, e.toString(), e);
        }
    }
}
