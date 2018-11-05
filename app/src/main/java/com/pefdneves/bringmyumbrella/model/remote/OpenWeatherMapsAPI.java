package com.pefdneves.bringmyumbrella.model.remote;

import com.pefdneves.bringmyumbrella.utils.NetworkUtils;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface OpenWeatherMapsAPI {

    @Headers(NetworkUtils.TAG_API_KEY + ": " + NetworkUtils.API_KEY)
    @GET("forecast/")
    Call<WeatherRootObject> downloadForecast(@Query("q") String location);
}
