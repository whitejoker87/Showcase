package ru.whitejoker.showcase;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ISandytrastAPI sandytrastAPI;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder().disableHtmlEscaping().serializeNulls().create();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://sandytrast.info")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        sandytrastAPI = retrofit.create(ISandytrastAPI.class);
    }

    public static ISandytrastAPI getAPI() {
        return sandytrastAPI;
    }
}
