package ru.whitejoker.showcase;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.OffersViewHolder> {

    private OfferModel offers;

    private IOnClickButtonMoreListener mListener;

    public OffersRecyclerViewAdapter(IOnClickButtonMoreListener listener) {
        mListener = listener;
    }

    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.offer_item, parent, false);
        return new OffersViewHolder(v, mListener);
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

        public OffersViewHolder(View itemView, IOnClickButtonMoreListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.bt_more)
        public void onClickButtonMore() {
            mListener.onClickButtonCallback(itemView, getAdapterPosition());
        }
    }
}
