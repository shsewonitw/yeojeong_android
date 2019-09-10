package com.tje.yeojunglogin.city_info;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.model.City_Data;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Cityinfo_adapter extends RecyclerView.Adapter<Cityinfo_adapter.ViewHolder>{

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;
    private static final String CITY_IMG_FOLDER = "/resources/cityimg/";


    private ArrayList<City_Data> mData = null;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_cityinfo_country;
        TextView tv_cityinfo_city;
        ImageView iv_cityinfo_img;
        LinearLayout cityinfo_item;
        ViewHolder(View itemView){
            super(itemView);

            tv_cityinfo_country = itemView.findViewById(R.id.tv_cityinfo_country);
            tv_cityinfo_city = itemView.findViewById(R.id.tv_cityinfo_city);
            iv_cityinfo_img = itemView.findViewById(R.id.iv_cityinfo_img);
            cityinfo_item = itemView.findViewById(R.id.cityinfo_item);
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
        final City_Data city_data = mData.get(position);
        holder.tv_cityinfo_country.setText(city_data.getCountry());
        holder.tv_cityinfo_city.setText(city_data.getCity());
        final ViewHolder tmpHolder = holder;






        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url = new
                            URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + CITY_IMG_FOLDER + city_data.getImage_src());
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new
                            BufferedInputStream(conn.getInputStream());
                    final Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            tmpHolder.iv_cityinfo_img.setImageBitmap(bm);
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("이미지 출력 실패!");
                }


            }
        });


        holder.cityinfo_item.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (view.getContext(), CityDetailActivity.class);
                intent.putExtra("country",city_data.getCountry());
                intent.putExtra("city",city_data.getCity());
                intent.putExtra("local_time",city_data.getLocal_time());
                intent.putExtra("flight_time",city_data.getFlight_time());
                intent.putExtra("local_voltage",city_data.getLocal_voltage());
                intent.putExtra("visa",city_data.getVisa());
                intent.putExtra("latitude",city_data.getLatitude());
                intent.putExtra("longitude",city_data.getLongitude());
                intent.putExtra("danger_level",city_data.getDanger_level());
                intent.putExtra("image_src",city_data.getImage_src());
                intent.putExtra("image_src2",city_data.getImage_src2());
                intent.putExtra("image_src3",city_data.getImage_src3());
                view.getContext().startActivity(intent);

            }
        });



    }


    @Override
    public int getItemCount() {
        return mData.size();
    }
}
