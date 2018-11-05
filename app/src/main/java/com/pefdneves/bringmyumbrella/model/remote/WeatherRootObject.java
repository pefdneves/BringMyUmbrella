package com.pefdneves.bringmyumbrella.model.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherRootObject {

    @SerializedName("list")
    private List<WeatherForecast> weatherForecasts = null;

    @SerializedName("city")
    private City city;

    public List<WeatherForecast> getWeatherForecasts() {
        return weatherForecasts;
    }

    public void setWeatherForecasts(List<WeatherForecast> weatherForecasts) {
        this.weatherForecasts = weatherForecasts;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public class City {
        @SerializedName("name")
        private String name;
        @SerializedName("country")
        private String country;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }
    }
}
