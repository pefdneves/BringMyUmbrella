package com.pefdneves.bringmyumbrella.ui.mainscreen;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.mvp.BasePresenter;
import com.pefdneves.bringmyumbrella.mvp.BaseView;

import java.util.List;

public interface BringMainContract {


    interface View extends BaseView<Presenter> {

        void setLoadingIndicator(boolean active);

        void showLoadingError();

        void showForecast(List<DayForecast> forecastList, boolean useCelsius);

        void showInsertLocation();

    }


    interface Presenter extends BasePresenter<View> {

        void loadForecast();

        void scheduleNotifications();

        void checkLocation();

        void setLocation(String location);

    }
}
