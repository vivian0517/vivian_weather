package com.vivian.Weather;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.IdAndName;
import com.vivian.Weather.GSON.LookUpCity;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.vivian.Weather.myMethod.setStatusBarHeight;

public class SearchCityActivity extends AppCompatActivity {

    private EditText editText;
    private RecyclerView recyclerView;
    private DemoAdapter adapter;
    private TextView cancel_button_textview;



    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchcity_activity);
        //状态栏黑色字符
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        //判断是否从city管理页打开
        Intent intent_pre = getIntent();
        boolean witch_open = false;
        witch_open = intent_pre.getBooleanExtra("witch_open", false);
        if (witch_open == false) {
            LitePal.initialize(this);
            LitePal.getDatabase();

            if (LitePal.findAll(IdAndName.class).size() != 0) {
                Intent intent = new Intent(this, MainPagerActivity.class);
                startActivity(intent);
                finish();
            }
        }
        cancel_button_textview = (TextView)findViewById(R.id.cancel_button);
        cancel_button_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyc);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);

        editText = (EditText) findViewById(R.id.search_edit_frame);
        editText.requestFocus();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //Toast toast = Toast.makeText(getApplicationContext(), s.toString(), Toast.LENGTH_LONG);
                //toast.show();
                requestCity(s.toString());


            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();

    }


    private void requestCity(String requestCityName) {
        String address = "https://geoapi.qweather.com/v2/city/lookup?location=" + requestCityName + Key.getKey();
        HttpUtil.sendOKHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //UI操作
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                final LookUpCity lookUpCity = Utility.responseOfJson(responseText, LookUpCity.class);
                if (lookUpCity.getCode().equals("200")) {
                    List<IdAndName> nameList = new ArrayList<>();
                    for (LookUpCity.LocationDTO location : lookUpCity.getLocation()) {
                        IdAndName idAndName = new IdAndName();
                        idAndName.setCity_id(location.getId());
                        idAndName.setName(location.getName() + "-" + location.getAdm2() + "," + location.getAdm1() + "," + location.getCountry());
                        //增加一个show name
                        idAndName.setShow_name(location.getName());
                        nameList.add(idAndName);
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter = new DemoAdapter(nameList);
                            recyclerView.setAdapter(adapter);
                        }
                    });

                }

            }
        });
    }

}