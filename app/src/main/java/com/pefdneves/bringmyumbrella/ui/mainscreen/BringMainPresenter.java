package com.pefdneves.bringmyumbrella.ui.mainscreen;

import android.content.Context;

import com.pefdneves.bringmyumbrella.dependencyinjection.ActivityScope;
import com.pefdneves.bringmyumbrella.model.DataSource;
import com.pefdneves.bringmyumbrella.model.DayForecastRepository;
import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils;
import com.pefdneves.bringmyumbrella.utils.notifications.NotificationManager;
import com.pefdneves.bringmyumbrella.utils.preferences.MySharedPreferences;

import org.joda.time.LocalDateTime;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

@ActivityScope
public class BringMainPresenter implements BringMainContract.Presenter {

    @Inject
    MySharedPreferences mySharedPreferences;

    @Inject
    NotificationManager mNotificationManager;

    private BringMainContract.View mBringMainView;
    private DayForecastRepository mDayForecastRepository;

    @Inject
    BringMainPresenter(DayForecastRepository dayForecastRepository) {
        mDayForecastRepository = dayForecastRepository;
    }

    @Override
    public void takeView(BringMainContract.View view) {
        mBringMainView = view;
    }

    @Override
    public void dropView() {
        mBringMainView = null;
    }

    @Override
    public void loadForecast() {
        mBringMainView.setLoadingIndicator(true);

        mDayForecastRepository.getDayForecasts(mySharedPreferences.getLocation(), new DataSource.LoadDayForecastsCallback() {
            @Override
            public void onLoadForecastsLoaded(List<DayForecast> forecasts) {
                if (forecasts == null || forecasts.isEmpty()) {
                    onDataUnavailable();
                } else {
                    DayForecast todayWeather = WeatherLogicUtils.getForecastWithSmallestDifference(new LocalDateTime(), forecasts);
                    DayForecast todayRain = WeatherLogicUtils.getForecastWorstCase(new LocalDateTime(), forecasts);
                    DayForecast tomorrowRain = WeatherLogicUtils.getForecastWorstCase(new LocalDateTime().plusDays(1), forecasts);
                    DayForecast afterRain = WeatherLogicUtils.getForecastWorstCase(new LocalDateTime().plusDays(2), forecasts);
                    mBringMainView.showForecast(Arrays.asList(todayWeather, todayRain, tomorrowRain, afterRain), mySharedPreferences.useCelsius());
                    mBringMainView.setLoadingIndicator(false);
                }
            }

            @Override
            public void onDataUnavailable() {
                loadingError();
            }
        });
    }

    @Override
    public void scheduleNotifications() {
        mNotificationManager.scheduleNotifications((Context) mBringMainView);
    }

    @Override
    public void checkLocation() {
        if (mySharedPreferences.getLocation() == null || mySharedPreferences.getLocation().isEmpty()) {
            mBringMainView.showInsertLocation();
        }
    }

    @Override
    public void setLocation(String location) {
        mySharedPreferences.setLocation(location);
    }

    private void loadingError() {
        mBringMainView.setLoadingIndicator(false);
        mBringMainView.showLoadingError();
    }
}
