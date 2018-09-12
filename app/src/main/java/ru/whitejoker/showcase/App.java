package ru.whitejoker.showcase;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ISandytrastAPI sandytrastAPI;
    private Retrofit retrofit;

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
