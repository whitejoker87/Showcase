package ru.whitejoker.showcase;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ISandytrastAPI sandytrastAPI;
    private Retrofit retrofit;

    public static final String TAG_OFFERS_LIST_FRAGMENT = "offers_list";
    public static final String TAG_OFFER_DESCRIPTION_FRAGMENT = "description";
    public static final String TAG_EMPTY_FRAGMENT = "empty";
    public static final String TAG_ABOUT_FRAGMENT = "about";

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sandytrast.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sandytrastAPI = retrofit.create(ISandytrastAPI.class);
    }

    public static ISandytrastAPI getAPI() {
        return sandytrastAPI;
    }
}
