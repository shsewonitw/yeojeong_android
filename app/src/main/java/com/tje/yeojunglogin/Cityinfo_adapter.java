package com.tje.yeojunglogin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tje.yeojunglogin.model.City_Data;

import java.util.ArrayList;
import java.util.List;

public class Cityinfo_adapter extends RecyclerView.Adapter<Cityinfo_adapter.ViewHolder>{

    private ArrayList<City_Data> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cityinfo_country;
        TextView tv_cityinfo_city;
        ImageView iv_cityinfo_img;

        ViewHolder(View itemView){
            super(itemView);

            tv_cityinfo_country = itemView.findViewById(R.id.tv_cityinfo_country);
            tv_cityinfo_city = itemView.findViewById(R.id.tv_cityinfo_city);
            iv_cityinfo_img = itemView.findViewById(R.id.iv_cityinfo_img);
        }
    }

    public Cityinfo_adapter(ArrayList<City_Data> list){
        mData = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.cityinfo_item_view, parent, false);
        Cityinfo_adapter.ViewHolder vh = new Cityinfo_adapter.ViewHolder(view);

        return vh;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        City_Data city_data = mData.get(position);
        holder.tv_cityinfo_country.setText(city_data.getCountry());
        holder.tv_cityinfo_city.setText(city_data.getCity());

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
