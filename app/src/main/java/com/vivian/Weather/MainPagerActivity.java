package com.vivian.Weather;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.vivian.Weather.GSON.IdAndName;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

import java.io.IOException;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import me.relex.circleindicator.CircleIndicator;

import static com.vivian.Weather.myMethod.setStatusBarHeight;

public class MainPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    private List<IdAndName> idAndNames;
    private PagerAdapter mPagerAdapter;
    private StringBuilder currentPrositonId;
    private static int REQUEST_CODE_SEARCH = 1;
    private String returndata;
    private TextView open_search_city;
    private TextView main_city;
    public static final int LOCATION_CODE = 301;
    private LocationManager locationManager;
    private String locationProvider = null;
    private double Longitude = 0d;
    private double Latitude = 0d;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);
        Log.i("tt","onCreate");
        //设置状态栏透明效果
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //利用反射获取状态栏高度
        int result = 0;
        //获取状态栏高度的资源id
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        RelativeLayout titleLayout = (RelativeLayout)findViewById(R.id.title);
        //增加状态栏下移
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(titleLayout.getLayoutParams());
        lp.setMargins(0, result, 0, 0);
        titleLayout.setLayoutParams(lp);

        main_city = (TextView) findViewById(R.id.main_city);
        open_search_city = (TextView) findViewById(R.id.open_search_city);
        mViewPager = (ViewPager) findViewById(R.id.main_view_pager);
        RequestGps();
        //通过判断数据库是否有数据决定是否初始化
        LitePal.initialize(this);
        LitePal.getDatabase();
        //为空进行初始化
        //if (LitePal.findAll(IdAndName.class).size() == 0) {
        //}
        //不为空取数据库的值
        //else {
        //    init();
        //}
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void init() {

        currentPrositonId = new StringBuilder("");
        idAndNames = LitePal.findAll(IdAndName.class);
        idAndNames.sort(new Comparator<IdAndName>() {
            @Override
            public int compare(IdAndName o1, IdAndName o2) {
                return o1.getDisplay_sort() - o2.getDisplay_sort();
            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();

        mViewPager.setAdapter(mPagerAdapter = new FragmentStatePagerAdapter(fragmentManager) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                IdAndName idAndName = idAndNames.get(position);
                return MainFragment.newInstance(idAndName.getCity_id());
            }

            @Override
            public int getCount() {
                return idAndNames.size();
            }
        });
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mViewPager);
        //
        if (currentPrositonId.toString().equals("")) {
            main_city.setText(idAndNames.get(0).getShow_name());
        }
        for (int i = 0; i < idAndNames.size(); i++) {
            if (idAndNames.get(i).getCity_id().equals(returndata)) {
                mViewPager.setCurrentItem(i);
                main_city.setText(idAndNames.get(i).getShow_name());
            }
        }
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                IdAndName idAndName = idAndNames.get(position);
                // currentPrositonId.delete(0,currentPrositonId.length());
                // currentPrositonId.append(idAndName.getCity_id());
                currentPrositonId = new StringBuilder(idAndName.getCity_id());

                main_city.setText(idAndName.getShow_name());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        open_search_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CityManagerActivity.class);
                intent.putExtra("prosition_id", currentPrositonId.toString());
                startActivityForResult(intent, REQUEST_CODE_SEARCH);
            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onRestart() {
        super.onRestart();
        if (idAndNames != null) {
            idAndNames.clear();
        }
        init();
        Log.i("tt","onrestart");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == RESULT_OK) {
                    returndata = data.getStringExtra("prositon_id");
                }
                break;
            default:
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void RequestGps() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //获取权限（如果没有开启权限，会弹出对话框，询问是否开启权限）
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //请求权限
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_CODE);
            } else {
                locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                //获取所有可用的位置提供器
                List<String> providers = locationManager.getProviders(true);
                if (providers.size() == 0) {
                    init();
                } else {
                    if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                        //如果是Network
                        locationProvider = LocationManager.NETWORK_PROVIDER;
                    }
                    //监视地理位置变化
                    locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
                    Location location = locationManager.getLastKnownLocation(locationProvider);
                    if (location != null) {
                        //输入经纬度
                        //Toast.makeText(this, location.getLongitude() + " " + location.getLatitude() + "", Toast.LENGTH_SHORT).show();
                        //Log.i("ttt", getAddress(location.getLatitude(), location.getLongitude()));
                        //先判断数据库是否有地理位置
                        List<IdAndName> mlist = LitePal.where("city_id like '%,%'")
                                .find(IdAndName.class);
                        if (!mlist.isEmpty()) {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            String city_id = Longitude + "," + Latitude;
                            ContentValues values = new ContentValues();
                            values.put("city_id", city_id);
                            LitePal.updateAll(IdAndName.class, values, "city_id like '%,%'");
                        } else {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                            String show_name = getAddress(Latitude, Longitude);
                            IdAndName idAndName = new IdAndName();
                            idAndName.setCity_id(Longitude + "," + Latitude);
                            idAndName.setName(show_name);
                            idAndName.setShow_name(show_name);
                            idAndName.setDisplay_sort(0);
                            idAndName.save();
                        }
                        init();
                    }
                }

            }
        }
    }

    public LocationListener locationListener = new LocationListener() {
        // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        // Provider被enable时触发此函数，比如GPS被打开
        @Override
        public void onProviderEnabled(String provider) {
        }

        // Provider被disable时触发此函数，比如GPS被关闭
        @Override
        public void onProviderDisabled(String provider) {
        }

        //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                //不为空,显示地理位置经纬度
                //Toast.makeText(MainActivity.this, location.getLongitude() + " " + location.getLatitude() + "", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_CODE:
                if (grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //Toast.makeText(this, "申请权限", Toast.LENGTH_LONG).show();
                    try {
                        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        //获取所有可用的位置提供器
                        List<String> providers = locationManager.getProviders(true);
                        //判断有没有打开gps
                        if (providers.size() != 0) {
                            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                                //如果是Network
                                locationProvider = LocationManager.NETWORK_PROVIDER;
                            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                                //如果是GPS
                                locationProvider = LocationManager.GPS_PROVIDER;
                            }
                            //监视地理位置变化
                            locationManager.requestLocationUpdates(locationProvider, 3000, 1, locationListener);
                            Location location = locationManager.getLastKnownLocation(locationProvider);
                            if (location != null) {
                                //不为空,显示地理位置经纬度
                                getAddress(location.getLatitude(), location.getLongitude());
                                Latitude = location.getLatitude();
                                Longitude = location.getLongitude();
                                String show_name = getAddress(Latitude, Longitude);
                                IdAndName idAndName = new IdAndName();
                                idAndName.setCity_id(Longitude + "," + Latitude);
                                idAndName.setName(show_name);
                                idAndName.setShow_name(show_name);
                                idAndName.setDisplay_sort(0);
                                idAndName.save();
                                init();
                                //Toast.makeText(MainActivity.this, location.getLongitude() + " " + location.getLatitude() + "", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Intent intent = new Intent(this, SearchCityActivity.class);
                            startActivity(intent);
                        }
                    } catch (SecurityException e) {
                        e.printStackTrace();
                    }
                } else {
                    Intent intent = new Intent(this, SearchCityActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    public String getAddress(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            List<Address> addresses = geocoder.getFromLocation(latitude,
                    longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getLocality() + " " + address.getThoroughfare();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取失败";
    }

    @Override
    public void onStop() {
        super.onStop();
        returndata = currentPrositonId.toString();
        Log.i("tt", "onstop");
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("tt", "ondestroy");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("tt", "onpause");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i("tt", "onstart");
    }
}
