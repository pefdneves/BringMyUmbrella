package com.pefdneves.bringmyumbrella.ui.settings;

import com.pefdneves.bringmyumbrella.mvp.BasePresenter;
import com.pefdneves.bringmyumbrella.mvp.BaseView;

public interface SettingsContract {

    interface View extends BaseView<Presenter> {

        void showSettings(String location, boolean useCelsius, boolean useReminder);

    }

    interface Presenter extends BasePresenter<View> {

        void setLocation(String location);

        void setCelsius(boolean useCelsius);

        void setReminder(boolean useReminder);

        void setReminderTime(int hourOfDay, int minute);

        void loadSettings();

    }

}
