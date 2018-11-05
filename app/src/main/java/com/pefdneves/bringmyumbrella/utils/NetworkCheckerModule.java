package com.pefdneves.bringmyumbrella.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class NetworkCheckerModule {

    @Provides
    @Singleton
    static NetworkUtils provideOnlineChecker(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return new NetworkUtils(connectivityManager);
    }
}