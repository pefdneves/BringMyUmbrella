package com.pefdneves.bringmyumbrella.utils;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.ui.UiUtils;

import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import java.util.List;

public class WeatherLogicUtils {

    public static float kelvinToCelsius(float kelvin) {
        return (float) Math.ceil(kelvin - 273.15F);
    }

    public static float kelvinToFahrenheit(float kelvin) {
        return (float) Math.ceil(((kelvin - 273) * (9 / 5f)) + 32);
    }

    /**
     * Gets the DayForecast object with the date closest to the given date differenceTo.
     *
     * @param differenceTo the given date
     * @param dayForecasts the list of DayForecasts to be searched
     * @return the DayForecast closest to differenceTo
     */
    public static DayForecast getForecastWithSmallestDifference(LocalDateTime differenceTo, List<DayForecast> dayForecasts) {
        float smallestDiff = DateTime.now().getMillis();
        DayForecast dayForecastSmallestDiff = dayForecasts.get(0);
        for (DayForecast dayForecast : dayForecasts) {
            if (dayForecast != null && Math.abs(differenceTo.toDateTime().getMillis() - dayForecast.getDate()) < smallestDiff && new DateTime(dayForecast.getDate()).toLocalDate().getDayOfYear() == differenceTo.getDayOfYear()) {
                dayForecastSmallestDiff = dayForecast;
                smallestDiff = Math.abs(differenceTo.toDateTime().getMillis() - dayForecast.getDate());
            }
        }
        return dayForecastSmallestDiff;
    }

    public static boolean iconEqualsRain(String icon) {
        return icon.equals(UiUtils.rain0) || icon.equals(UiUtils.rain1) || icon.equals(UiUtils.rain2) || icon.equals(UiUtils.rain3);
    }

    /**
     * Gets the worse case scenario for the given day differenceTo, ie, the moment in which rain in expected.
     *
     * @param differenceTo the day for which we want to know if it will rain
     * @param dayForecasts the list of DayForecasts, including the day to search
     * @return a DayForecast object with the worst case scenario of rain that day - if it doesn't rain, it will return the first object for that day
     */
    public static DayForecast getForecastWorstCase(LocalDateTime differenceTo, List<DayForecast> dayForecasts) {
        DayForecast ret = null;
        for (DayForecast dayForecast : dayForecasts) {
            if (new LocalDateTime(dayForecast.getDate()).dayOfMonth().equals(differenceTo.dayOfMonth())) {
                if (ret == null || dayForecast.isRain())
                    ret = dayForecast;
            }
        }
        return ret;
    }
}
