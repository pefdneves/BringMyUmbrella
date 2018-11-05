package com.pefdneves.bringmyumbrella.dependencyinjection;

import android.app.Application;

import com.pefdneves.bringmyumbrella.BringMyUmbrella;
import com.pefdneves.bringmyumbrella.broadcasts.BroadcastModule;
import com.pefdneves.bringmyumbrella.model.DayForecastRepositoryModule;
import com.pefdneves.bringmyumbrella.model.remote.OpenWeatherServiceModule;
import com.pefdneves.bringmyumbrella.utils.NetworkCheckerModule;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManagerModule;
import com.pefdneves.bringmyumbrella.utils.preferences.SharedPreferencesModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        DayForecastRepositoryModule.class,
        ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidSupportInjectionModule.class,
        OpenWeatherServiceModule.class,
        NetworkCheckerModule.class,
        SharedPreferencesModule.class,
        NotificationManagerModule.class,
        BroadcastModule.class
})
public interface ApplicationComponent extends AndroidInjector<BringMyUmbrella> {

    @Component.Builder
    interface Builder {

        @BindsInstance
        ApplicationComponent.Builder application(Application application);

        ApplicationComponent build();
    }
}