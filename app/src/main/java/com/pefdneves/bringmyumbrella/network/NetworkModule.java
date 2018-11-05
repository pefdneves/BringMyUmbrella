package com.pefdneves.bringmyumbrella.network;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import okhttp3.OkHttpClient;

@Module
public abstract class NetworkModule {

    private static String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    @Provides
    @Singleton
    static Call.Factory providesOkHttp() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Named("base_url")
    static String providesBaseUrl() {
        return BASE_URL;
    }
}
