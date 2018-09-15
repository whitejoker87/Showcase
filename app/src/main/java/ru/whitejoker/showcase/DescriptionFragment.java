package ru.whitejoker.showcase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DescriptionFragment extends Fragment {

    @BindView(R.id.iv_logo_descr)
    ImageView logoDescr;
    @BindView(R.id.tv_title_descr)
    TextView titleDescr;
    @BindView(R.id.tv_full_descr)
    TextView fullDescr;
    @BindView(R.id.bt_open_url)
    Button openUrlButton;


    private OnFragmentInteractionListener mListener;

    private OfferModel.Offer offerDescr;
    private Unbinder unbinder;

    public DescriptionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_descriprion, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(offerDescr != null) {
            Picasso.get().load(offerDescr.getLogo()).into(logoDescr);
            titleDescr.setText(offerDescr.getName());
            fullDescr.setText(offerDescr.getDescFull());
            openUrlButton.setText(offerDescr.getBtn2());
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void setOffer(OfferModel.Offer offer){
        offerDescr = offer;
    }

    @OnClick(R.id.bt_open_url)
    public void onUrlButtonClick() {

        Uri url = Uri.parse(offerDescr.getUrl());
        Intent urlIntent = new Intent(Intent.ACTION_VIEW, url);
        // Проверка на споссобность обработать intent
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> activities = packageManager.queryIntentActivities(urlIntent, 0);
        boolean isIntentSafe = activities.size() > 0;
        if (isIntentSafe) {
            startActivity(urlIntent);
        }

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
