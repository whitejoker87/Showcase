package ru.whitejoker.showcase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class DescriptionActivity extends AppCompatActivity {

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

    private OfferModel.Offer offerDescr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        ButterKnife.bind(this);

        if(offerDescr != null) {
            Picasso.get().load(offerDescr.getLogo()).into(logoDescr);
            titleDescr.setText(offerDescr.getName());
            fullDescr.setText(offerDescr.getDescFull());
            openUrlButton.setText(offerDescr.getBtn2());
        }
        webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }

    public void setOffer(OfferModel.Offer offer){
        offerDescr = offer;
    }

    @OnClick(R.id.bt_open_url)
    public void onUrlButtonClick() {
        if (offerDescr.getBrowser()) {
            Uri url = Uri.parse(offerDescr.getUrl());
            Intent urlIntent = new Intent(Intent.ACTION_VIEW, url);
            // Проверка на споссобность обработать intent
            PackageManager packageManager = getPackageManager();
            List<ResolveInfo> activities = packageManager.queryIntentActivities(urlIntent, 0);
            boolean isIntentSafe = activities.size() > 0;
            if (isIntentSafe) {
                startActivity(urlIntent);
            }
        } else {
            webView.setVisibility(View.VISIBLE);
            webView.loadUrl(offerDescr.getUrl());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return true;
                }
            });
        }
    }
}
