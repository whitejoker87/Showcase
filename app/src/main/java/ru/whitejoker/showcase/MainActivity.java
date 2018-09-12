package ru.whitejoker.showcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        App.getAPI().getOfferList(1).enqueue(new Callback<OfferModel>() {
            @Override
            public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {

            }

            @Override
            public void onFailure(Call<OfferModel> call, Throwable t) {

            }
        });
    }
}
