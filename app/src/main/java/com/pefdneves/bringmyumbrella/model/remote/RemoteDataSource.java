package com.pefdneves.bringmyumbrella.model.remote;

import android.support.annotation.NonNull;

import com.pefdneves.bringmyumbrella.model.DataSource;
import com.pefdneves.bringmyumbrella.model.ObjectConverter;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.debug.CustomLogger;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class RemoteDataSource implements DataSource {

    private final OpenWeatherMapsAPI service;
    private final static String TAG = RemoteDataSource.class.getSimpleName();

    @Inject
    RemoteDataSource(OpenWeatherMapsAPI weatherService) {
        this.service = weatherService;
    }

    @Override
    public void insertDayForecasts(@NonNull List<DayForecast> dayForecasts) {

    }

    @Override
    public void getDayForecasts(@NonNull String location, @NonNull final LoadDayForecastsCallback loadDayForecastsCallback) {
        service.downloadForecast(location).enqueue(new Callback<WeatherRootObject>() {
            @Override
            public void onResponse(Call<WeatherRootObject> call, Response<WeatherRootObject> response) {

                if (response.body() != null) {
                    loadDayForecastsCallback.onLoadForecastsLoaded(ObjectConverter.convertRemoteToLocalWeather(response.body()));
                } else {
                    loadDayForecastsCallback.onDataUnavailable();
                }
            }

            @Override
            public void onFailure(Call<WeatherRootObject> call, Throwable t) {
                CustomLogger.w(TAG, t.toString(), t);
            }
        });
    }

    @Override
    public void deleteForecasts() {

    }
}
