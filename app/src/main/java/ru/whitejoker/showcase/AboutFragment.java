package ru.whitejoker.showcase;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AboutFragment extends Fragment {

    int id;
    String name, descr;

    @BindView(R.id.tv_id_about)
    TextView tv_id;
    @BindView(R.id.tv_name_about)
    TextView tv_name;
    @BindView(R.id.tv_descr_about)
    TextView tv_descr;

    private Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about, container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if(id != 0 && name != null && descr != null) {
            String idString = getString(R.string.id_about) +  id;
            name = getString(R.string.name_about) + name;
            descr = getString(R.string.descr_about) +  descr;
            tv_id.setText(idString);
            tv_name.setText(name);
            tv_descr.setText(descr);
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    public void setViews(int id, String name, String descr){
        this.id = id;
        this.name = name;
        this.descr = descr;
    }

}
