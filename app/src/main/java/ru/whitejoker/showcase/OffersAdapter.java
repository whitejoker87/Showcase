package ru.whitejoker.showcase;


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

import java.util.TreeMap;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.ViewHolder> {

    private OfferModel offers;

    public OffersAdapter(OfferModel offers) {
        this.offers = offers;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        OfferModel.Offer offer = offers.getOffers().get(position);

        Picasso.with(convertView.getContext()).load(places.getImage()).into(holder.imageView);

        holder.ivLogo.s


    }

    @Override
    public int getItemCount() {
        if (offers == null)
            return 0;
        return offers.getOffers().size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView ivLogo;
        TextView tvTitle;
        TextView tvDescr;
        Button btMore;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLogo = itemView.findViewById(R.id.iv_logo);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescr = itemView.findViewById(R.id.tv_descr);
            btMore = itemView.findViewById(R.id.bt_more);
        }
    }
}