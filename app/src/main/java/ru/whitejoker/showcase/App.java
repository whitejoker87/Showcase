package ru.whitejoker.showcase;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application {

    private static ISandytrastAPI sandytrastAPI;
    private Retrofit retrofit;

    private static OfferModel offerModel;

    //синглтон-оъбект с инфой из JSON
    public static synchronized OfferModel getOfferModel() {
        if (offerModel == null) {
            offerModel = new OfferModel();
        }
        return offerModel;
    }

    public static synchronized void setOfferModel(OfferModel offerModel) {
        App.offerModel = offerModel;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()//запрос для загрузки JSON
                .baseUrl("http://sandytrast.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        sandytrastAPI = retrofit.create(ISandytrastAPI.class);
    }

    public static ISandytrastAPI getAPI() {
        return sandytrastAPI;
    }//интерфейс для загрузки JSON
}
