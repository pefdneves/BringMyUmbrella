package com.pefdneves.bringmyumbrella.model;

import android.support.annotation.NonNull;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;

import java.util.List;

public interface DataSource {

    interface LoadDayForecastsCallback {
        void onLoadForecastsLoaded(List<DayForecast> forecasts);

        void onDataUnavailable();
    }

    void insertDayForecasts(@NonNull List<DayForecast> dayForecasts);

    void getDayForecasts(@NonNull String location, @NonNull LoadDayForecastsCallback loadDayForecastsCallback);

    void deleteForecasts();

}
