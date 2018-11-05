package com.pefdneves.bringmyumbrella.model.local;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.pefdneves.bringmyumbrella.model.local.database.BringMyUmbrellaDatabase;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecastsDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    private static final String DATABASE_NAME = "bmu.db";

    @Provides
    @Singleton
    static BringMyUmbrellaDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, BringMyUmbrellaDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

    @Singleton
    @Provides
    static DayForecastsDao provideCourseDao(BringMyUmbrellaDatabase db) {
        return db.dayForecastsDao();
    }

}
