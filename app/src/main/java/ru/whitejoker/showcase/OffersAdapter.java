package ru.whitejoker.showcase;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder> {

    private Context context;
    private OfferModel offers;
    private Activity activity;

    public OffersAdapter(OfferModel offers, Context context, Activity activity) {
        this.offers = offers;
        this.context = context;
        this.activity = activity;
    }



    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new OffersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        OfferModel.Offer offer = offers.getOffers().get(position);

        Picasso.get().load(offer.getLogo()).into(holder.ivLogo);
        holder.tvTitle.setText(offer.getName());
        holder.tvDescr.setText(offer.getDes());
        holder.btMore.setText(offer.getBtn());
    }

    @Override
    public int getItemCount() {
        if (offers == null)
            return 0;
        return offers.getOffers().size();
    }

    public void updateList(OfferModel offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }


    class OffersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_descr)
        TextView tvDescr;
        @BindView(R.id.bt_more)
        Button btMore;

        public OffersViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.bt_more)
        public void onClickButtonMore() {

            String urlOffer = offers.getOffers().get(getAdapterPosition()).getUrl();
            Intent intent = new Intent(context, OfferDescriptionActivity.class);
            context.startActivity(intent);


        }
    }
}