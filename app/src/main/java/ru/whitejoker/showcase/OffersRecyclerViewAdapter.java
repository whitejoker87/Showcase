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

//адаптер для списка офферов
public class OffersRecyclerViewAdapter extends RecyclerView.Adapter<OffersRecyclerViewAdapter.OffersViewHolder> {

    private OfferModel offers;
    private IOnClickButtonMoreListener mListener;//интерфейс обработчика нажатия на пункт списка
    // (остался от реализации приложения через фрагменты)
    //мне понравилась реализация поэтому оставил его
    private final LayoutInflater mInflater;

    public OffersRecyclerViewAdapter(IOnClickButtonMoreListener listener, Context context) {//передаем экземпляр интерфесаи контекст
        mListener = listener;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.offer_item, parent, false);
        return new OffersViewHolder(v, mListener);//сюда тоже передаем интерфейса экземпляр
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

        OfferModel.Offer offer = offers.getOffers().get(position);//получаем объект оффера
        if (offer.getEnabled()) {//если он разрешен заполняем
            Picasso.get().load(offer.getLogo()).into(holder.ivLogo);//используем picasso для закгрузки лого
            holder.tvTitle.setText(offer.getName());
            holder.tvDescr.setText(offer.getDes());
            if (offer.getBtn() != null) holder.btMore.setText(offer.getBtn());
        } else {//если запрещен убираем пункт списка
            holder.ivLogo.setVisibility(View.GONE);
            holder.tvTitle.setVisibility(View.GONE);
            holder.tvDescr.setVisibility(View.GONE);
            holder.btMore.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        if (offers == null)
            return 0;
        return offers.getOffers().size();
    }
//  метод для передачи объекта JSON и обновления списка
    public void updateList(OfferModel offers) {
        this.offers = offers;
        notifyDataSetChanged();
    }

    class OffersViewHolder extends RecyclerView.ViewHolder {

        //Биндим View с помощью ButterKnife
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
            mListener = listener;//интерфейс для обработки кликов
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.bt_more)
        public void onClickButtonMore() {
            mListener.onClickButtonCallback(itemView, getAdapterPosition());//передаем клик в Activity
        }
    }
}
