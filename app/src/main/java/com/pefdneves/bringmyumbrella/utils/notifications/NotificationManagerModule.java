package com.pefdneves.bringmyumbrella.utils.notifications;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

@Module
public class NotificationManagerModule {
    
    @Provides
    android.app.NotificationManager provideNotificationManager(Context context) {
        return (android.app.NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }
}
