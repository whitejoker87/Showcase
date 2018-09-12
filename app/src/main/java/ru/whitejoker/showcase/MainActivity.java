package ru.whitejoker.showcase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.offers_recycle_view)
    RecyclerView offersRecycleView;

    private Unbinder unbinder;
    private OffersAdapter offersAdapter;
    private OfferModel offerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        getOffers();
        offersAdapter = new OffersAdapter(offerModel, this);
        offersAdapter.updateList(offerModel);
        offersRecycleView.setLayoutManager(new LinearLayoutManager(this));
        offersRecycleView.setAdapter(offersAdapter);
    }

    private void getOffers() {
        App.getAPI().getOfferList(1).enqueue(new Callback<OfferModel>() {
            @Override
            public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {
                offerModel = response.body();
            }

            @Override
            public void onFailure(Call<OfferModel> call, Throwable t) {

            }
        });


    }
}
