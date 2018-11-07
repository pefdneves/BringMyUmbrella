package com.pefdneves.bringmyumbrella;

import com.pefdneves.bringmyumbrella.model.local.database.DayForecast;
import com.pefdneves.bringmyumbrella.utils.WeatherLogicUtils;
import com.pefdneves.bringmyumbrella.utils.ui.UiUtils;

import org.joda.time.LocalDateTime;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BringMyUmbrellaTest {

    @Test
    public void temperatureUnits() {
        assertEquals(WeatherLogicUtils.kelvinToCelsius(1), -272.15, 0.99);
        assertEquals(WeatherLogicUtils.kelvinToFahrenheit(1), -457.87, 0.99);
    }

    @Test
    public void getForecastWithSmallestDifferenceTest() {
        List<DayForecast> dayForecasts = new ArrayList<>();
        for (int i = 10; i < 15; i++) {
            DayForecast d = new DayForecast();
            d.setDate(new LocalDateTime().plusDays(i).toDateTime().getMillis());
            dayForecasts.add(d);
            d.setId(i);
        }
        int idTomorrow = 99;
        DayForecast dTomorrow = new DayForecast();
        dTomorrow.setDate(new LocalDateTime().toDateTime().getMillis());
        dTomorrow.setId(idTomorrow);
        dayForecasts.add(dTomorrow);
        assertEquals(WeatherLogicUtils.getForecastWithSmallestDifference(new LocalDateTime(), dayForecasts).getId(), idTomorrow);
    }

    @Test
    public void isEqualRainTest() {
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.rain0));
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.rain1));
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.rain2));
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.rain3));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clear_day));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clear_night));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_day0));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_day1));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_day2));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_night0));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_night1));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.clouds_night2));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.thunderstorm0));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.thunderstorm1));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.mist0));
        assertFalse(WeatherLogicUtils.iconEqualsRain(UiUtils.mist1));
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.snow0));
        assertTrue(WeatherLogicUtils.iconEqualsRain(UiUtils.snow1));
        assertFalse(WeatherLogicUtils.iconEqualsRain(""));
        assertFalse(WeatherLogicUtils.iconEqualsRain("aa"));
    }

    @Test
    public void getForecastWorstCaseTest() {
        List<DayForecast> dayForecasts = new ArrayList<>();
        int forStart = 10;
        for (int i = forStart; i < 15; i++) {
            DayForecast d = new DayForecast();
            d.setRain(false);
            d.setDate(new LocalDateTime().toDateTime().getMillis());
            d.setId(i);
            dayForecasts.add(d);
        }
        assertEquals(WeatherLogicUtils.getForecastWorstCase(new LocalDateTime(), dayForecasts).getId(), forStart);
        DayForecast rainNotToday = new DayForecast();
        rainNotToday.setRain(true);
        rainNotToday.setId(20);
        rainNotToday.setDate(new LocalDateTime().plusDays(1).toDateTime().getMillis());
        dayForecasts.add(rainNotToday);
        assertEquals(WeatherLogicUtils.getForecastWorstCase(new LocalDateTime(), dayForecasts).getId(), forStart);
        int idRainToday = 99;
        DayForecast rainToday = new DayForecast();
        rainToday.setRain(true);
        rainToday.setId(idRainToday);
        rainToday.setDate(new LocalDateTime().toDateTime().getMillis());
        dayForecasts.add(rainToday);
        assertEquals(WeatherLogicUtils.getForecastWorstCase(new LocalDateTime(), dayForecasts).getId(), idRainToday);
    }
}