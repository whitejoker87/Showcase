package ru.whitejoker.showcase;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OffersActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private OfferModel offerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getFragmentManager().findFragmentByTag(App.TAG_OFFERS_LIST_FRAGMENT) == null) onBackPressed();
                 else finish();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);
        getOffers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offers_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_about) {
            AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag(App.TAG_ABOUT_FRAGMENT);
            if (aboutFragment == null) aboutFragment = new AboutFragment();
            int idAbout = offerModel.getId();
            String nameAbout = offerModel.getName();
            String infoAbout = offerModel.getInfo();
            aboutFragment.setViews(idAbout, nameAbout, infoAbout);
            setFragment(aboutFragment, App.TAG_ABOUT_FRAGMENT );
        }
        return true;
    }

    public void getOffers(){
        if(offerModel != null) successRequest(offerModel);
        else App.getAPI().getOfferList(1).enqueue(new Callback<OfferModel>() {
                @Override
                public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {
                    if(response.code() == 200) {
                        offerModel = response.body();
                        successRequest(offerModel);
                    }
                }

                @Override
                public void onFailure(Call<OfferModel> call, Throwable t) {

                    if(t instanceof SocketTimeoutException || t instanceof UnknownHostException)
                        showError("Check network connection");
                    else
                        showError("Error: " + t.getMessage());

                }
        });
    }

    public void successRequest(OfferModel offerModel) {
        OffersListFragment offersListFragment = (OffersListFragment) getSupportFragmentManager().findFragmentByTag(App.TAG_OFFERS_LIST_FRAGMENT);
        if (offersListFragment != null && offersListFragment.isVisible())
            offersListFragment.updateRecycle(offerModel);
        else {
            OffersListFragment fragment = new OffersListFragment();
            fragment.setOffers(offerModel);
            setFragment(fragment, App.TAG_OFFERS_LIST_FRAGMENT );
        }
    }

    public void openDescription(int position) {
        DescriptionFragment descriptionFragment = (DescriptionFragment) getSupportFragmentManager().findFragmentByTag(App.TAG_OFFER_DESCRIPTION_FRAGMENT);
        if (descriptionFragment == null) descriptionFragment = new DescriptionFragment();
        OfferModel.Offer offerDescr =  offerModel.getOffers().get(position);
        descriptionFragment.setOffer(offerDescr);
        setFragment(descriptionFragment, App.TAG_OFFER_DESCRIPTION_FRAGMENT );
    }

    private void setFragment(Fragment fragment, String tag){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_offers_list, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void showError(String error) {
        OffersListFragment fragment = new OffersListFragment();
        fragment.setOffers(new OfferModel());
        setFragment(fragment, App.TAG_EMPTY_FRAGMENT);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
