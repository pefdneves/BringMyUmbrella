package com.pefdneves.bringmyumbrella.model;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.model.remote.WeatherForecast;
import com.pefdneves.bringmyumbrella.model.remote.WeatherRootObject;
import com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils;

import java.util.ArrayList;
import java.util.List;

public class ObjectConverter {

    /**
     * Converts remote object WeatherRootObject to local data object DayForecast.
     *
     * @param forecastsApiRoot the remote root object
     * @return a list of DayForecast
     */
    public static List<DayForecast> convertRemoteToLocalWeather(WeatherRootObject forecastsApiRoot) {
        ArrayList<DayForecast> dayForecasts = new ArrayList<>();
        ArrayList<WeatherForecast> inputList = new ArrayList(forecastsApiRoot.getWeatherForecasts());
        for (WeatherForecast weatherForecast : inputList) {
            DayForecast dayForecast = new DayForecast();
            dayForecast.setDate(weatherForecast.getDate() * 1000);
            dayForecast.setIcon(weatherForecast.getWeather().get(0).getIcon());
            dayForecast.setDateText(weatherForecast.getDateTxt());
            dayForecast.setRain(WeatherLogicUtils.iconEqualsRain(weatherForecast.getWeather().get(0).getIcon()));
            dayForecast.setLocation(forecastsApiRoot.getCity().getName() + ", " + forecastsApiRoot.getCity().getCountry());
            dayForecast.setTemperature((weatherForecast.getMain()).getTemperature());
            dayForecasts.add(dayForecast);
        }
        return dayForecasts;
    }

}
