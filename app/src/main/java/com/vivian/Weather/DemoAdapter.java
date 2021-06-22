package com.vivian.Weather;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vivian.Weather.GSON.IdAndName;

import org.litepal.LitePal;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.ViewHolder> {
    private List<IdAndName> mIdAndName;
    public DemoAdapter(List<IdAndName> list){
        mIdAndName = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        Button button;
        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.recyc_text_view);
            button = itemView.findViewById(R.id.recyc_button);
        }
    }
    @NonNull
    @Override
    public DemoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_text_button,parent,false);
        ViewHolder holder = new ViewHolder(view);

        //if(idAndName.getCity_id() == )
        //点击按钮添加到数据库，禁用按钮
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int positon = holder.getBindingAdapterPosition();
                IdAndName idAndName = mIdAndName.get(positon);
                List<IdAndName> list = LitePal.findAll(IdAndName.class);
                //判断数据库是否为空，为空跳转main页面
                if(list.size() ==0) {
                    idAndName.setDisplay_sort(0);
                    idAndName.save();
                    Intent intent = new Intent(view.getContext(), MainPagerActivity.class);
                    startActivity(view.getContext(), intent, null);
                    if (Activity.class.isInstance(view.getContext())) {
                        // 转化为activity，然后finish就行了
                        Activity activity = (Activity) view.getContext();
                        activity.finish();
                    }
                }else if(11-list.size()>=0){
                    idAndName.setDisplay_sort(list.size()+1);
                    if(idAndName.save()){
                        holder.button.setEnabled(false);
                        holder.button.setText("已添加");
                    }
                }else {
                    Toast.makeText(view.getContext(), "最多添加10个城市", Toast.LENGTH_SHORT);
                }


            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        IdAndName idAndName = mIdAndName.get(position);
        holder.textView.setText(idAndName.getName());
        //设置button显示的文字
        List<IdAndName> sql =LitePal.where("city_id = ?", String.valueOf(idAndName.getCity_id())).find(IdAndName.class);
        if (sql.size() == 0) {
            holder.button.setText("➕");
        }else {
            holder.button.setText("已添加");
        }
    }

    @Override
    public int getItemCount() {
        return mIdAndName.size();
    }


}
