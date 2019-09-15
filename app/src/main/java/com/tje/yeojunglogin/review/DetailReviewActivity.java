package com.tje.yeojunglogin.review;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DetailReviewActivity extends AppCompatActivity {


    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;

    private TextView tv_title;
    private TextView tv_memberId;
    private TextView tv_date;
    private TextView tv_con;
    private TextView tv_city;

    private ImageView tv_reviewstar;
    private ImageView reviewImg;

    private TextView tv_content;

    private ImageButton btn_like;

    private TextView tv_likecount;
    private TextView tv_comcount;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_review);

        tv_title = findViewById(R.id.tv_title);
        tv_memberId = findViewById(R.id.tv_memberId);
        tv_date = findViewById(R.id.tv_date);
        tv_con = findViewById(R.id.tv_con);
        tv_city = findViewById(R.id.tv_city);

        tv_reviewstar = findViewById(R.id.tv_reviewstar);
        reviewImg = findViewById(R.id.reviewImg);

        tv_content = findViewById(R.id.tv_content);
        btn_like = findViewById(R.id.btn_like);
        tv_likecount = findViewById(R.id.tv_likecount);
        tv_comcount = findViewById(R.id.tv_comcount);

        final String article_id = getIntent().getStringExtra("article_id");

        Toast.makeText(getApplicationContext(),article_id,Toast.LENGTH_LONG).show();

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                intent = getIntent();

                String targetURL = "/androidreviewdetatil?" + article_id;

                try {
                    URL url = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + targetURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        final BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    tv_title.setText(in.readLine());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });


                    } else {
                        Toast.makeText(getApplicationContext(),"연결실패",Toast.LENGTH_LONG).show();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

    }
}
