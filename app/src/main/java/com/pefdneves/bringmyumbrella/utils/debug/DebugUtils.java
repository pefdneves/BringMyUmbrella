package com.pefdneves.bringmyumbrella.utils.debug;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;

import java.util.List;

public class DebugUtils {

    public static void printDayForecasts(List<DayForecast> dayForecastList) {
        for (DayForecast dayForecast : dayForecastList)
            CustomLogger.w("printDayForecasts", dayForecast.getLocation() + ", " + dayForecast.getDateText() + ", " + dayForecast.getTemperature() + ", " + dayForecast.getIcon());
    }

}
