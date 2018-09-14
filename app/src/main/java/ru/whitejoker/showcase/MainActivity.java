package ru.whitejoker.showcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    //private Unbinder unbinder;
    private OfferModel offerModel;
    public static final String TAG_OFFERS_LIST_FRAGMENT = "offers_list";
    public static final String TAG_OFFER_DESCRIPTION_FRAGMENT = "description";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //unbinder = ButterKnife.bind(this);
        getOffers();

    }

    public void successRequest(OfferModel offerModel) {
        OffersFragment offersFragment = (OffersFragment) getSupportFragmentManager().findFragmentByTag(TAG_OFFERS_LIST_FRAGMENT);
        if (offersFragment != null && offersFragment.isVisible())
            offersFragment.updateRecycler(offerModel);
        else {
            OffersFragment fragment = new OffersFragment();
            fragment.setOffers(offerModel);
            setFragment(fragment, TAG_OFFERS_LIST_FRAGMENT );
        }
    }

    public void openDescription(int position) {
        DescriprionFragment descriprionFragment = (DescriprionFragment) getSupportFragmentManager().findFragmentByTag(TAG_OFFER_DESCRIPTION_FRAGMENT);
        if (descriprionFragment == null)
            descriprionFragment = new DescriprionFragment();
        OfferModel.Offer offerDescr =  offerModel.getOffers().get(position);
        descriprionFragment.setOffer(offerDescr);
        //offersFragment.updateRecycler(offerModel);
        //Сделать заполнение фрагмета


            setFragment(descriprionFragment, TAG_OFFER_DESCRIPTION_FRAGMENT );

    }

    public void getOffers(){
        if(offerModel != null)
            successRequest(offerModel);
        else
            App.getAPI().getOfferList(1).enqueue(new Callback<OfferModel>() {
                @Override
                public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {
                    if(response.code() == 200) {
                        successRequest(response.body());
                    }
                }

                @Override
                public void onFailure(Call<OfferModel> call, Throwable t) {

                }
            });
    }

    private void setFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_offers_list, fragment, tag);
        transaction.commit();
    }
}
