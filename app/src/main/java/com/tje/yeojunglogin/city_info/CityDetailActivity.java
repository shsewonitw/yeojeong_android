package com.tje.yeojunglogin.city_info;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

public class CityDetailActivity extends AppCompatActivity {

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;
    private static final String CITY_IMG_FOLDER = "/resources/cityimg/";

    private ImageView iv_image_src;
    private TextView tv_local_time;
    private TextView tv_flight_time;
    private TextView tv_local_voltage;
    private TextView tv_visa;
    private TextView tv_danger_level;
    private TextView tv_detail_country;
    private TextView tv_detail_city;
    private LinearLayout city_detail_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        iv_image_src = findViewById(R.id.iv_image_src);
        tv_local_time = findViewById(R.id.tv_local_time);
        tv_flight_time = findViewById(R.id.tv_flight_time);
        tv_local_voltage = findViewById(R.id.tv_local_voltage);
        tv_visa = findViewById(R.id.tv_visa);
        tv_danger_level = findViewById(R.id.tv_danger_level);
        city_detail_layout = findViewById(R.id.city_detail_layout);
        tv_detail_country = findViewById(R.id.tv_detail_country);
        tv_detail_city = findViewById(R.id.tv_detail_city);

        Intent intent = getIntent();
        String country = intent.getExtras().getString("country");
        String city = intent.getExtras().getString("city");
        String local_time = intent.getExtras().getString("local_time");
        String flight_time = intent.getExtras().getString("flight_time");
        String local_voltage = intent.getExtras().getString("local_voltage");
        String visa = intent.getExtras().getString("visa");
        String latitude = intent.getExtras().getString("latitude");
        String longitude = intent.getExtras().getString("longitude");
        String danger_level = String.valueOf(intent.getExtras().getInt("danger_level"));
        String image_src = intent.getExtras().getString("image_src");
        final String image_src2 = intent.getExtras().getString("image_src2");
        final String image_src3 = intent.getExtras().getString("image_src3");

        tv_local_time.setText("시차 : "+ local_time);
        tv_flight_time.setText("비행시간 : "+flight_time);
        tv_local_voltage.setText("현지 전압 : "+local_voltage);
        tv_visa.setText("비자 : "+visa);
        tv_danger_level.setText("유의지역 : "+danger_level);
        tv_detail_country.setText(country);
        tv_detail_city.setText(city);


        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                try {

                    URL url = new
                            URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + CITY_IMG_FOLDER + image_src2);
                    URLConnection conn = url.openConnection();
                    conn.connect();
                    BufferedInputStream bis = new
                            BufferedInputStream(conn.getInputStream());
                    Bitmap bm = BitmapFactory.decodeStream(bis);
                    bis.close();

                    final Drawable drawable = new BitmapDrawable(bm);





                    URL url3 = new
                            URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + CITY_IMG_FOLDER + image_src3);
                    URLConnection conn3 = url3.openConnection();
                    conn3.connect();
                    BufferedInputStream bis3 = new
                            BufferedInputStream(conn3.getInputStream());
                    final Bitmap bm3 = BitmapFactory.decodeStream(bis3);
                    bis3.close();


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            city_detail_layout.setBackground(drawable);
                            iv_image_src.setImageBitmap(bm3);
                        }
                    });



                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("이미지 출력 실패!");
                }


            }
        });


    }

}
