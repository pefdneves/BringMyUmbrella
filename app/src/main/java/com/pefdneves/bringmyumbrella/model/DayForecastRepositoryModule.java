package com.pefdneves.bringmyumbrella.model;

import com.pefdneves.bringmyumbrella.model.local.DatabaseModule;
import com.pefdneves.bringmyumbrella.model.local.LocalDataSource;
import com.pefdneves.bringmyumbrella.model.remote.RemoteDataSource;
import com.pefdneves.bringmyumbrella.utils.AppExecutor;
import com.pefdneves.bringmyumbrella.utils.DiskExecutors;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = DatabaseModule.class)
public abstract class DayForecastRepositoryModule {

    @Singleton
    @Binds
    @Local
    abstract DataSource provideLocalDataSource(LocalDataSource dataSource);

    @Singleton
    @Binds
    @Remote
    abstract DataSource provideRemoteDataSource(RemoteDataSource dataSource);

    @Singleton
    @Provides
    static AppExecutor provideAppExecutor() {
        return new AppExecutor(new DiskExecutors(),
                new AppExecutor.MainThreadExecutor());
    }
}
