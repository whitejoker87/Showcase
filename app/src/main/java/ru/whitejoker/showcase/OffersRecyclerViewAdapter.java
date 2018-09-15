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
import ru.whitejoker.showcase.OffersFragment.OnListFragmentInteractionListener;
import ru.whitejoker.showcase.dummy.DummyContent.DummyItem;

/**
 * {@link RecyclerView.Adapter} that can display a {@link DummyItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.OffersViewHolder> {

    //private final List<DummyItem> mValues;
    //private final OnListFragmentInteractionListener mListener;
    private Context context;
    private OfferModel offers;

    private OnClickButtonMoreListener mListener;

    public OffersRecyclerViewAdapter(Context context, OnClickButtonMoreListener listener) {
        this.context = context;
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

//    public void setOnClickButtonMoreListener(OnClickButtonMoreListener listener) {
//        mListener = listener;
//    }

    class OffersViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_descr)
        TextView tvDescr;
        @BindView(R.id.bt_more)
        Button btMore;

        public OffersViewHolder(View itemView, OnClickButtonMoreListener listener) {
            super(itemView);
            mListener = listener;
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.bt_more)
        public void onClickButtonMore() {
            mListener.onClickButtonCallback(itemView, getAdapterPosition());


//            String urlOffer = offers.getOffers().get(getAdapterPosition()).getUrl();
//            Intent intent = new Intent(context, OfferDescriptionActivity.class);
//            context.startActivity(intent);





        }
    }
}
