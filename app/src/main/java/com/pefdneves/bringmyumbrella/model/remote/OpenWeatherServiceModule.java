package com.pefdneves.bringmyumbrella.model.remote;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pefdneves.bringmyumbrella.network.NetworkModule;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = NetworkModule.class)
public class OpenWeatherServiceModule {

    @Provides
    @Singleton
    static Gson provideGSON() {
        return new GsonBuilder().create();
    }

    @Provides
    @Singleton
    static Retrofit provideRetrofit(Gson gson, Call.Factory callFactory, @Named("base_url") String baseUrl) {
        return new Retrofit.Builder()
                .callFactory(callFactory)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl(baseUrl)
                .build();
    }

    @Provides
    @Singleton
    static OpenWeatherMapsAPI provideRepoService(Retrofit retrofit) {
        return retrofit.create(OpenWeatherMapsAPI.class);
    }

}
