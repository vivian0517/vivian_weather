package com.vivian.Weather;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.vivian.Weather.myMethod.setStatusBarHeight;

public class CityManagerActivity extends SingleFragmentActivity {
    private String id;
    @Override
    protected Fragment createFragment() {

        return new CityManagerFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        id = intent.getStringExtra("prosition_id");

        Intent intentyy = new Intent();
        intentyy.putExtra("prositon_id",id);
        setResult(RESULT_OK,intentyy);

    }
}