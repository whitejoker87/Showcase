package ru.whitejoker.showcase;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

//интерфейс для Retrofit запроса
public interface ISandytrastAPI {
    @GET("/ins/")
    Call<OfferModel> getOfferList(@Query("id") int id);
}
