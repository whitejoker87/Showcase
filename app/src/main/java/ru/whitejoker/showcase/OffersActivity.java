package ru.whitejoker.showcase;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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

//Activity дял спика офферов
public class OffersActivity extends AppCompatActivity {

    //Используем Butterknife  для биндинга View
    @BindView(R.id.toolbar_offers)
    Toolbar toolbarOffers;
    @BindView(R.id.offers_recycle_view)
    RecyclerView offersRecycleView;

    private OfferModel offerModel;

    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offers);
        ButterKnife.bind(this);
        setSupportActionBar(toolbarOffers);

        sPref = getSharedPreferences("savedData", MODE_PRIVATE);//для сохранения заголовка JSON

        getOffers();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.offers_menu, menu);//начинка toolbar
        toolbarOffers.getMenu().findItem(R.id.action_exit).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        toolbarOffers.getMenu().findItem(R.id.action_exit).setVisible(true);
        toolbarOffers.getMenu().findItem(R.id.action_about).setVisible(true);
        toolbarOffers.setNavigationIcon(null);
        toolbarOffers.setTitle("Список офферов");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {//Listener для toolbar(кроме кнопки назад)
        switch (item.getItemId()) {
            case R.id.action_about://о программе
                Intent intent = new Intent(OffersActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.action_exit://выход
                finish();
                break;
        }
        return true;
    }

    //метод для загрузки JSON
    public void getOffers(){
             App.getAPI().getOfferList(1).enqueue(new Callback<OfferModel>() {//используем Retrofit 2 и асинхронную загрузку
                @Override
                public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {//получаем ответ
                    if(response.code() == 200) {
                        offerModel = response.body();//сохраняем ответ в переменную
                        App.setOfferModel(offerModel);//и в синглтон
                        successRequest(offerModel);//вызываем метод для продолжения
                        saveHeaders(offerModel);// метод для сохранения в Shared Preferences заголовка(тело крупновато)

                    }
                }

                @Override
                public void onFailure(Call<OfferModel> call, Throwable t) {//обработка ошибок Retrofit

                    if(t instanceof SocketTimeoutException || t instanceof UnknownHostException)
                        showError("Check network connection");
                    else
                        showError("Error: " + t.getMessage());

                }
        });
    }

    public void successRequest(OfferModel offerModel) {//метод для заполнения
        OffersRecyclerViewAdapter offersAdapter = new OffersRecyclerViewAdapter(new IOnClickButtonMoreListener() {
            @Override
            public void onClickButtonCallback(View view, int position) {//метод интерфейса для обработки клика по RecyclerView
                openDescription(position);//вызываем метод для открытия описания оффера
            }
        }, this);
        offersAdapter.updateList(offerModel);
        offersRecycleView.setAdapter(offersAdapter);
        offersRecycleView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void openDescription(int position) {//тут открываем описание оффера

        Intent intent = new Intent(OffersActivity.this, DescriptionActivity.class);
        intent.putExtra("position", position);//передаем позицию выбранного view
        startActivity(intent);
    }

    public void showError(String error) {//Метод оповещения об ошибках при загрузке
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    private void saveHeaders(OfferModel offerModel){//метод для сохранения заголовка в Shared Preferences
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("id", offerModel.getId().toString());
        editor.putString("name", offerModel.getName());
        editor.putString("info", offerModel.getInfo());
        editor.apply();
    }
}
