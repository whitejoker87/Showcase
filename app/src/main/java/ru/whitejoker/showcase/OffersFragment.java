package ru.whitejoker.showcase;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ru.whitejoker.showcase.dummy.DummyContent;
import ru.whitejoker.showcase.dummy.DummyContent.DummyItem;

import java.util.List;
import java.util.Objects;

public class OffersFragment extends Fragment {

    @BindView(R.id.offers_recycle_view)
    RecyclerView offersRecycleView;

    private Unbinder unbinder;
    private OffersRecyclerViewAdapter offersAdapter;
    private OfferModel offerModel;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OffersFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offers_list, container, false);
        unbinder = ButterKnife.bind(this,view);

        if (view instanceof RecyclerView) {
            offersAdapter = new OffersRecyclerViewAdapter(getContext());
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

    public void updateRecycler(OfferModel offers){
        offersAdapter.updateList(offers);
    }

    public void setOffers(OfferModel offers){
        this.offerModel = offers;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(DummyItem item);
    }


}
