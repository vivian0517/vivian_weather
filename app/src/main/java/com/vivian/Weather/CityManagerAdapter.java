package com.vivian.Weather;

import android.content.ContentValues;
import android.net.sip.SipSession;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.CityManagerList;
import com.vivian.Weather.GSON.IdAndName;

import org.litepal.LitePal;
import org.litepal.exceptions.DataSupportException;

import java.text.CharacterIterator;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CityManagerAdapter extends RecyclerView.Adapter<CityManagerAdapter.ViewHolder> implements ItemTouchHelperAdapter {
    private List<CityManagerList> managerLists;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public CityManagerAdapter(List<CityManagerList> list) {
        //list.sort(new Comparator<CityManagerList>(){
        //    @Override
        //    public int compare(CityManagerList o1, CityManagerList o2) {
        //        return o1.getDisplay_sort() - o2.getDisplay_sort();
        //    }
        //});
        managerLists = list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        //交换位置
        Collections.swap(managerLists, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        //更新数据库里的display_sort
        List<IdAndName> list = LitePal.findAll(IdAndName.class);
        list.sort(new Comparator<IdAndName>(){
            @Override
            public int compare(IdAndName o1, IdAndName o2) {
                return o1.getDisplay_sort() - o2.getDisplay_sort();
            }
        });
        Collections.swap(list,fromPosition,toPosition);
        for (int i = 0; i < list.size(); i++) {
            list.get(i).setDisplay_sort(i + 1);
            try {
                list.get(i).saveThrows();
            } catch (DataSupportException e){
                //Log.i("tt", "hh"+String.valueOf(e));
            }

        }}

        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public void onItemDelete (int position){
            //数据库删除
            String city_id = managerLists.get(position).getCity_id();
            LitePal.deleteAll(IdAndName.class, "city_id = ?", city_id);
            //update数据库
            List<IdAndName> list = LitePal.findAll(IdAndName.class);
            list.sort(new Comparator<IdAndName>() {
                @Override
                public int compare(IdAndName o1, IdAndName o2) {
                    return o1.getDisplay_sort() - o2.getDisplay_sort();
                }
            });


            //for (int i = 0;i<list.size();i++){
            //    ContentValues values = new ContentValues();
            //    values.put("display_sort", i+1);
            //    LitePal.update(IdAndName.class, values, i+1);
            //}
            LitePal.deleteAll(IdAndName.class);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).clearSavedState();
                list.get(i).setDisplay_sort(i + 1);
                list.get(i).save();
            }
            //移除数据
            managerLists.remove(position);
            notifyItemRemoved(position);
        }




    public class ViewHolder extends RecyclerView.ViewHolder {
            TextView city_name;
            TextView weather_quaility;
            TextView weather_tempe;
            TextView weather_now_tempe;
            ImageView iv_img;

            public ViewHolder(View itemView) {
                super(itemView);
                city_name = itemView.findViewById(R.id.city_name);
                weather_quaility = itemView.findViewById(R.id.weather_quaility);
                weather_tempe = itemView.findViewById(R.id.weather_tempe);
                weather_now_tempe = itemView.findViewById(R.id.weather_now_tempe);
                iv_img = itemView.findViewById(R.id.iv_img);
            }
        }
        @NonNull
        @Override
        public CityManagerAdapter.ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,
        int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_city_list_layout, parent, false);
            ViewHolder holder = new ViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder (@NonNull ViewHolder holder,int position){
            CityManagerList item = managerLists.get(position);
            holder.city_name.setText(item.getShow_name());
            holder.weather_quaility.setText(item.getWeather_quaility());
            holder.weather_tempe.setText(item.getWeather_tempe());
            holder.weather_now_tempe.setText(item.getWeather_now_tempe());
            holder.iv_img.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {
                    onItemDelete(holder.getLayoutPosition());

                }
            });
        }

        @Override
        public int getItemCount () {
            return managerLists.size();
        }


    }
