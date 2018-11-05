package com.pefdneves.bringmyumbrella.model;

import android.support.annotation.NonNull;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.NetworkUtils;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class DayForecastRepository implements DataSource {

    private final DataSource mRemoteDataSource;
    private final DataSource mLocalDataSource;

    @Inject
    DayForecastRepository(@Remote DataSource remoteDataSource,
                          @Local DataSource localDataSource) {
        mRemoteDataSource = remoteDataSource;
        mLocalDataSource = localDataSource;
    }

    @Inject
    NetworkUtils internetTester;

    @Override
    public void insertDayForecasts(@NonNull List<DayForecast> dayForecasts) {
        mLocalDataSource.insertDayForecasts(dayForecasts);
    }

    @Override
    public void getDayForecasts(@NonNull final String location, @NonNull final LoadDayForecastsCallback loadDayForecastsCallback) {
        if (internetTester.isOnline()) {
            mRemoteDataSource.getDayForecasts(location, new LoadDayForecastsCallback() {

                @Override
                public void onLoadForecastsLoaded(List<DayForecast> forecasts) {
                    deleteForecasts();
                    insertDayForecasts(forecasts);
                    loadDayForecastsCallback.onLoadForecastsLoaded(forecasts);
                }

                @Override
                public void onDataUnavailable() {
                    getDayForecastsLocal(location, loadDayForecastsCallback);
                }
            });
        } else {
            getDayForecastsLocal(location, loadDayForecastsCallback);
        }
    }

    private void getDayForecastsLocal(String location, final LoadDayForecastsCallback loadDayForecastsCallback) {
        mLocalDataSource.getDayForecasts(location, new LoadDayForecastsCallback() {
            @Override
            public void onLoadForecastsLoaded(List<DayForecast> forecasts) {
                loadDayForecastsCallback.onLoadForecastsLoaded(forecasts);
            }

            @Override
            public void onDataUnavailable() {
                loadDayForecastsCallback.onDataUnavailable();
            }
        });
    }

    @Override
    public void deleteForecasts() {
        mLocalDataSource.deleteForecasts();
    }
}
