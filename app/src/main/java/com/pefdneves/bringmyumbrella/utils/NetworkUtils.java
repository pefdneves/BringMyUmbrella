package com.pefdneves.bringmyumbrella.utils;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils implements InternetTester {

    public static final String TAG_API_KEY = "x-api-key";
    public static final String API_KEY = "xxxxxxxxxxxxxxx"; //TODO: add your own key before compiling

    private final ConnectivityManager connectivityManager;

    public NetworkUtils(ConnectivityManager connectivityManager) {
        this.connectivityManager = connectivityManager;
    }

    @Override
    public boolean isOnline() {
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}
