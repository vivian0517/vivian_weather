package com.vivian.Weather;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;

import com.vivian.Weather.GSON.AirNow;
import com.vivian.Weather.GSON.CityManagerList;
import com.vivian.Weather.GSON.DailyDTO;
import com.vivian.Weather.GSON.IdAndName;
import com.vivian.Weather.GSON.NowDTO;
import com.vivian.Weather.GSON.NowWeather;
import com.vivian.Weather.GSON.ThreeDayWeather;

import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.vivian.Weather.myMethod.setStatusBarHeight;

public class CityManagerFragment extends Fragment {
    private static int REQUEST_CODE_SEARCH = 0;
    private TextView city_name_textview;
    private TextView weather_tempe_textview;
    private List<CityManagerList> cityManagerLists;
    private RecyclerView recyclerView;
    private CityManagerAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cityManagerLists = new ArrayList<>();
        init();
        //状态栏黑色字符
        Window window =getActivity(). getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        View v = inflater.inflate(R.layout.activity_city_manager, container, false);
        //用asynctask实现
        //for (CityManagerList city :cityManagerLists) {
        //    new myTask().execute(city);
        //}
        Toolbar toolbar = v.findViewById(R.id.toolbar);

        TextView textView = v.findViewById(R.id.toolbar_textview);
        Activity mActivity = getActivity();;
        AppCompatActivity mAppCompatActivity = (AppCompatActivity) mActivity;
        mAppCompatActivity.setSupportActionBar(toolbar);
        mAppCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAppCompatActivity.getSupportActionBar().setHomeButtonEnabled(true);
        mAppCompatActivity.getSupportActionBar().setDisplayShowTitleEnabled(false);

        AppBarLayout appBarLayout =v.findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if( state == State.EXPANDED ) {
                    //展开状态
                    textView.setVisibility(View.GONE);

                }else if(state == State.COLLAPSED){
                    //折叠状态
                    textView.setVisibility(View.VISIBLE);
                }else {
                    //中间状态

                }
            }
        });
        //用recyclerview实现
        recyclerView = (RecyclerView) v.findViewById(R.id.recyc);
        LinearLayoutManager manager = new LinearLayoutManager(v.getContext());
        recyclerView.setLayoutManager(manager);
        adapter = new CityManagerAdapter(cityManagerLists);
        recyclerView.setAdapter(adapter);

       ItemTouchHelper.Callback callback = new myItemTouchHelperCallBack(adapter);
       ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
       touchHelper.attachToRecyclerView(recyclerView);

        EditText editText = (EditText) v.findViewById(R.id.search_edit_frame);
        editText.requestFocus();
        editText.setInputType(InputType.TYPE_NULL);
        editText.setOnClickListener(new View.OnClickListener() {
                                        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                        @Override
                                        public void onClick(View v) {
                                            EdittextAndImageviewClick();
                                        }
                                    }
        );
        ImageView imageView = (ImageView) v.findViewById(R.id.search_img);
        imageView.setOnClickListener(new View.OnClickListener() {
                                         @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                                         @Override
                                         public void onClick(View v) {
                                             EdittextAndImageviewClick();
                                         }
                                     }
        );
        return v;
    }

    class myTask extends AsyncTask<CityManagerList,Void,Void>{

        @Override
        protected Void doInBackground(CityManagerList... city) {
                request3DayWeather(city[0].getCity_id(), city[0]);
                requestAirNow(city[0].getCity_id(),city[0]);
                requestNowWeather(city[0].getCity_id(),city[0]);

            return null;

        }

        @Override
        protected void onPostExecute(Void aVoid) {
           // recyclerView.setAdapter(new CityManagerAdapter(cityManagerLists));
            adapter.notifyDataSetChanged();
            Log.i("TTT",cityManagerLists.get(0).getWeather_now_tempe()+" ");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void EdittextAndImageviewClick() {
        Boolean citymanager = true;
        Intent intent = new Intent(getActivity(), SearchCityActivity.class);
        intent.putExtra("witch_open", citymanager);
        startActivityForResult(intent, REQUEST_CODE_SEARCH, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());

    }

    private void requestAirNow(String id,CityManagerList city) {
        String address = "https://devapi.qweather.com/v7/air/now?" + "location=" + id + Key.getKey();

        Response response = HttpUtil.sendOKHttpRequest(address);
        try {
            String responseText = response.body().string();
            AirNow airNow = Utility.responseOfJson(responseText, AirNow.class);

            if (airNow.getCode().equals("200")) {
                city.setWeather_quaility(airNow.getNow().getCategory() + " ");

            }
        }
        catch (IOException exception)
        {

        }
    }

    private void request3DayWeather(String id, CityManagerList city) {
        String address = "https://devapi.qweather.com/v7/weather/3d?" + "location=" + id + Key.getKey();

        Response response = HttpUtil.sendOKHttpRequest(address);
        try {
            String responseText = response.body().string();
            ThreeDayWeather threeDayWeather = Utility.responseOfJson(responseText, ThreeDayWeather.class);

            if (threeDayWeather != null) {
                city.setWeather_tempe(threeDayWeather.getDaily().get(0).getTempMax()+
                        "°/"+threeDayWeather.getDaily().get(0).getTempMin()+"°");
            }
        }
        catch (IOException exception)
        {

        }
    }


    private void requestNowWeather(String id,CityManagerList city) {
        String address = "https://devapi.qweather.com/v7/weather/now?" + "location=" + id + Key.getKey();
        Response response = HttpUtil.sendOKHttpRequest(address);
        try {
            String responseText = response.body().string();
            NowWeather nowWeather = Utility.responseOfJson(responseText, NowWeather.class);
            if (nowWeather.getCode().equals("200")) {
                if (nowWeather != null) {
                    city.setWeather_now_tempe(nowWeather.getNow().getTemp()+"°");
                }
            }
        } catch (IOException exception)
        {

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();

        cityManagerLists.clear();
        init();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init(){

        List<IdAndName> idAndNames = LitePal.findAll(IdAndName.class);
        for (IdAndName item : idAndNames) {
            CityManagerList city = new CityManagerList();
            city.setCity_id(item.getCity_id());
            city.setShow_name(item.getShow_name());
            city.setDisplay_sort(item.getDisplay_sort());
            cityManagerLists.add(city);
        }
        cityManagerLists.sort(new Comparator<CityManagerList>(){

            @Override
            public int compare(CityManagerList o1, CityManagerList o2) {
                return o1.getDisplay_sort() - o2.getDisplay_sort();
            }
        });
        Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;
                if(what == 0){
                    //在主线程中需要执行的操作，一般是UI操作
                    adapter.notifyDataSetChanged();
                }
            }
        };
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                //for (CityManagerList city :cityManagerLists)
                //{
                //    request3DayWeather(city.getCity_id(), city);
                //    requestAirNow(city.getCity_id(),city);
                //    requestNowWeather(city.getCity_id(),city);
                //}
                for (int i=0;i<cityManagerLists.size();i++)
                {
                    request3DayWeather(cityManagerLists.get(i).getCity_id(), cityManagerLists.get(i));
                    requestAirNow(cityManagerLists.get(i).getCity_id(),cityManagerLists.get(i));
                    requestNowWeather(cityManagerLists.get(i).getCity_id(),cityManagerLists.get(i));
                }
                //在子线程中执行任务，执行完成或需要通知UI线程时调用以下方法
                mHandler.sendEmptyMessage(0);
            }
        });
        thread.start();
    }

}

