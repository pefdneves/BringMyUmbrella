package com.pefdneves.bringmyumbrella.model.local;

import android.support.annotation.NonNull;

import com.pefdneves.bringmyumbrella.model.DataSource;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecastsDao;
import com.pefdneves.bringmyumbrella.utils.AppExecutor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class LocalDataSource implements DataSource {

    private DayForecastsDao mDayForecastsDao;

    private AppExecutor mAppExecutor;

    @Inject
    public LocalDataSource(@NonNull AppExecutor appExecutors,
                           @NonNull DayForecastsDao dayForecastsDao) {
        mAppExecutor = appExecutors;
        mDayForecastsDao = dayForecastsDao;
    }


    @Override
    public void getDayForecasts(@NonNull final String location, @NonNull final LoadDayForecastsCallback loadDayForecastsCallback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<DayForecast> forecasts = mDayForecastsDao.getDaysForecast(location);
                mAppExecutor.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (forecasts.isEmpty()) {
                            loadDayForecastsCallback.onDataUnavailable();
                        } else {
                            loadDayForecastsCallback.onLoadForecastsLoaded(forecasts);
                        }
                    }
                });
            }
        };

        mAppExecutor.diskIO().execute(runnable);
    }

    @Override
    public void deleteForecasts() {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDayForecastsDao.deleteForecasts();
            }
        };
        mAppExecutor.diskIO().execute(saveRunnable);
    }

    @Override
    public void insertDayForecasts(@NonNull final List<DayForecast> dayForecasts) {
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mDayForecastsDao.insertDayForecasts(dayForecasts);
            }
        };
        mAppExecutor.diskIO().execute(saveRunnable);
    }
}
