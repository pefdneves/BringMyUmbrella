package com.pefdneves.bringmyumbrella.model.local.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {DayForecast.class}, version = 1, exportSchema = false)
public abstract class BringMyUmbrellaDatabase extends RoomDatabase {

    public abstract DayForecastsDao dayForecastsDao();

}