package com.pefdneves.bringmyumbrella.utils.preferences;

import android.content.Context;
import android.content.SharedPreferences;

import dagger.Module;
import dagger.Provides;

@Module
public class SharedPreferencesModule {

    private static final String PREF_NAME = "BMU_PREFS";

    @Provides
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }
}