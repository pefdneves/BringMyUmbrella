package com.pefdneves.bringmyumbrella.model.local.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface DayForecastsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertDayForecasts(List<DayForecast> forecasts);

    @Query("SELECT * FROM DayForecast WHERE location = :location")
    List<DayForecast> getDaysForecast(String location);

    @Query("DELETE FROM DayForecast")
    void deleteForecasts();

}
