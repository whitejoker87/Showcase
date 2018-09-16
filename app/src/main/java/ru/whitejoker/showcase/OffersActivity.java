package ru.whitejoker.showcase;

import android.content.SharedPreferences;
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

    private String title, currentTag;
    private SharedPreferences sPref;
    private static final String DEFAULT_TAG_FRAGMENT = App.TAG_OFFERS_LIST_FRAGMENT;
    private static final String TAG_FRAGMENT_KEY = "tag_fragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (getFragmentManager().findFragmentByTag(App.TAG_OFFERS_LIST_FRAGMENT) == null) onBackPressed();
                 //else finish();
                onBackPressed();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        sPref = getSharedPreferences("savedData", MODE_PRIVATE);
        currentTag = sPref.getString(TAG_FRAGMENT_KEY, "");
        if(currentTag.equals("")){
            currentTag = DEFAULT_TAG_FRAGMENT;
            saveCategory(currentTag);
        }

        getOffers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offers_menu, menu);
        //toolbar.getMenu().findItem(R.id.action_exit).setVisible(true);
        //toolbar.getMenu().findItem(R.id.action_exit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_about:
                currentTag = App.TAG_ABOUT_FRAGMENT;
                saveCategory(currentTag);
                AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentByTag(currentTag);
                if (aboutFragment == null) aboutFragment = new AboutFragment();
                int idAbout = offerModel.getId();
                String nameAbout = offerModel.getName();
                String infoAbout = offerModel.getInfo();
                aboutFragment.setViews(idAbout, nameAbout, infoAbout);
                setFragment(aboutFragment, currentTag );
                break;
            case R.id.action_exit:
                finish();
                break;
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
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(currentTag);
        getSupportFragmentManager().
        if (fragment != null && fragment.isVisible()) {
            if (fragment instanceof OffersListFragment) {
                setToolbar(currentTag);
                ((OffersListFragment)fragment).updateRecycle(offerModel);
            } else if (fragment instanceof DescriptionFragment) {
                setFragment(fragment, currentTag);
            } else if (fragment instanceof AboutFragment) {
                setFragment(fragment, currentTag);
            }
        } else {
            OffersListFragment offersListFragment = new OffersListFragment();
            offersListFragment.setOffers(offerModel);
            setFragment(offersListFragment, currentTag);
        }
    }

    public void openDescription(int position) {
        currentTag = App.TAG_OFFER_DESCRIPTION_FRAGMENT;
        saveCategory(currentTag);
        DescriptionFragment descriptionFragment = (DescriptionFragment) getSupportFragmentManager().findFragmentByTag(App.TAG_OFFER_DESCRIPTION_FRAGMENT);
        if (descriptionFragment == null) descriptionFragment = new DescriptionFragment();
        OfferModel.Offer offerDescr =  offerModel.getOffers().get(position);
        descriptionFragment.setOffer(offerDescr);
        setFragment(descriptionFragment, currentTag );
    }

    private void setFragment(Fragment fragment, String tag){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_offers_list, fragment, tag);
        setToolbar(tag);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void setToolbar(String tag) {
        switch (tag) {
            case App.TAG_OFFERS_LIST_FRAGMENT:
                toolbar.getMenu().findItem(R.id.action_exit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
                toolbar.getMenu().findItem(R.id.action_exit).setVisible(true);
                toolbar.getMenu().findItem(R.id.action_about).setVisible(true);
                toolbar.setNavigationIcon(null);
                toolbar.setTitle("Список офферов");
                break;
            case App.TAG_OFFER_DESCRIPTION_FRAGMENT:
                toolbar.getMenu().findItem(R.id.action_exit).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_about).setVisible(true);
                toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
                toolbar.setTitle("Описание оффера");
                break;
            case App.TAG_ABOUT_FRAGMENT:
                toolbar.getMenu().findItem(R.id.action_exit).setVisible(false);
                toolbar.getMenu().findItem(R.id.action_about).setVisible(false);
                toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_24);
                toolbar.setTitle("О программе");
                break;
        }
    }

    public void showError(String error) {
        OffersListFragment fragment = new OffersListFragment();
        fragment.setOffers(new OfferModel());
        setFragment(fragment, App.TAG_EMPTY_FRAGMENT);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void saveCategory(String tag){
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString(TAG_FRAGMENT_KEY, tag);
        editor.apply();
    }
}
