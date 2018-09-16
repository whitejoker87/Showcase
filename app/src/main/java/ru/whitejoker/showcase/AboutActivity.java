package ru.whitejoker.showcase;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

//Activity для информации о программе
public class AboutActivity extends AppCompatActivity {

    //биндим View
    @BindView(R.id.tv_id_about)
    TextView tv_id;
    @BindView(R.id.tv_name_about)
    TextView tv_name;
    @BindView(R.id.tv_descr_about)
    TextView tv_descr;
    @BindView(R.id.toolbar_about)
    Toolbar toolbarAbout;

    private SharedPreferences sPref;//для заголовка JSON

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(this);

        //достаем заголовок из Shared Preferences или из синглтона
        sPref = getSharedPreferences("savedData", MODE_PRIVATE);
        String id = sPref.getString("id", "");
        if (id.equals("")) id = App.getOfferModel().getId().toString();
        String name = sPref.getString("name","");
        if (name.equals("")) name = App.getOfferModel().getName();
        String info = sPref.getString("info", "");
        if (info.equals("")) info = App.getOfferModel().getInfo();


        setSupportActionBar(toolbarAbout);
        toolbarAbout.setNavigationIcon(R.drawable.baseline_arrow_back_24);//кнопка назад
        toolbarAbout.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //запоняем views
        if(id != null && name != null && info != null) {
            id = getString(R.string.id_about) + id;
            name = getString(R.string.name_about) + name;
            info = getString(R.string.descr_about) + info;
            tv_id.setText(id);
            tv_name.setText(name);
            tv_descr.setText(info);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {//начинка меню
        getMenuInflater().inflate(R.menu.offers_menu, menu);
        toolbarAbout.getMenu().findItem(R.id.action_exit).setVisible(false);
        toolbarAbout.getMenu().findItem(R.id.action_about).setVisible(false);
        toolbarAbout.setTitle("О программе");
        return true;
    }
}
