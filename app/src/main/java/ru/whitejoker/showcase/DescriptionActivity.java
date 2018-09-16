package ru.whitejoker.showcase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//Activity описания оффера
public class DescriptionActivity extends AppCompatActivity {

    //Используем Butterknife  для биндинга View
    @BindView(R.id.iv_logo_descr)
    ImageView logoDescr;
    @BindView(R.id.tv_title_descr)
    TextView titleDescr;
    @BindView(R.id.tv_full_descr)
    TextView fullDescr;
    @BindView(R.id.bt_open_url)
    Button openUrlButton;
    @BindView(R.id.wv_descr)
    WebView webView;
    @BindView(R.id.toolbar_descr)
    Toolbar toolbarDescr;

    private OfferModel.Offer offerDescr;//объект-пункт списка

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ButterKnife.bind(this);

        setSupportActionBar(toolbarDescr);
        toolbarDescr.setNavigationIcon(R.drawable.baseline_arrow_back_24);//добавляем кнопку назад
        toolbarDescr.setNavigationOnClickListener(new View.OnClickListener() {//и ее оработчик
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //достаем объект из списка офферов с помощью позиции переданной с прошлой Activity
        offerDescr = App.getOfferModel().getOffers().get(getIntent().getIntExtra("position",0));

        if(offerDescr != null) {//если оъект наполнен - заполняем View
            Picasso.get().load(offerDescr.getLogo()).into(logoDescr);
            titleDescr.setText(offerDescr.getName());
            fullDescr.setText(offerDescr.getDescFull());
            if (offerDescr.getBtn2() != null) openUrlButton.setText(offerDescr.getBtn2());
        }
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);//для исключения ошибки при исполщовании WebView
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//начинка toolbar
        getMenuInflater().inflate(R.menu.offers_menu, menu);
        toolbarDescr.getMenu().findItem(R.id.action_exit).setVisible(false);
        toolbarDescr.getMenu().findItem(R.id.action_about).setVisible(true);
        toolbarDescr.setTitle("Описание оффера");
        return true;
    }

    @OnClick(R.id.bt_open_url)//удобный обработчик кнопки открытия ссылки ButterKnife
    public void onUrlButtonClick() {
        if (offerDescr.getBrowser()) {//если нужно открываем в браузере
            Uri url = Uri.parse(offerDescr.getUrl());
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, url);
            // Проверка на споссобность обработать intent
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(urlIntent, 0);
            boolean isIntentSafe = activities.size() > 0;
            if (isIntentSafe) {
                startActivity(urlIntent);
            }
        } else { //или в встроенном WebView
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(offerDescr.getUrl());
            webView.setWebViewClient(new WebViewClient() {//для того чтобы все происходило в встроенном WebView и не уходило по сслыка в браузер
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }
}
