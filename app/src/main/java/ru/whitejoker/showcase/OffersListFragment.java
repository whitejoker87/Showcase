package ru.whitejoker.showcase;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class OffersListFragment extends Fragment {

    @BindView(R.id.offers_recycle_view)
    RecyclerView offersRecycleView;

    private Unbinder unbinder;
    private OffersRecyclerViewAdapter offersAdapter;
    private OfferModel offerModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);
        unbinder = ButterKnife.bind(this,view);

        if (view instanceof RecyclerView) {
            offersAdapter = new OffersRecyclerViewAdapter(new IOnClickButtonMoreListener() {
                @Override
                public void onClickButtonCallback(View view, int position) {
                    ((OffersActivity)getActivity()).openDescription(position);
                }
            });
            offersAdapter.updateList(offerModel);
            offersRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
            offersRecycleView.setAdapter(offersAdapter);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void updateRecycle(OfferModel offers){
        offersAdapter.updateList(offers);
    }

    public void setOffers(OfferModel offers){
        this.offerModel = offers;
    }
}
