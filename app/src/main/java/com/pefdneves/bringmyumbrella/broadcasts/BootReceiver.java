package com.pefdneves.bringmyumbrella.broadcasts;

import android.content.Context;
import android.content.Intent;

import com.pefdneves.bringmyumbrella.dependencyinjection.BroadcastReceiverScope;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManager;

import javax.inject.Inject;

import dagger.android.DaggerBroadcastReceiver;

@BroadcastReceiverScope
public class BootReceiver extends DaggerBroadcastReceiver {

    @Inject
    NotificationManager mNotificationManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        if (intent.getAction() != null && (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED) || intent.getAction().equals(Intent.ACTION_TIMEZONE_CHANGED))) {
            mNotificationManager.scheduleNotifications(context);
        }
    }
}
