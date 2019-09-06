package com.tje.yeojunglogin.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tje.yeojunglogin.Cityinfo_adapter;
import com.tje.yeojunglogin.MainActivity;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.model.City_Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragCityInfo extends Fragment {

    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.87:8080";
    private static final String HOST_APP_NAME = "/yeojeong";
    ArrayList<City_Data> list = new ArrayList<>();

    public FragCityInfo() {
        System.out.println("Frag City info 생성자 호출!!!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Frag City info onCreate 호출!!!");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


    System.out.println("Frag City info onCreate View 호출!!!");


        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_cityinfo, container, false);



        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String targetURL = "/android_traveldata";

                try {
                    URL endPoint = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + targetURL);
                    HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                    connection.setRequestMethod("GET");

                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                        BufferedReader in = new BufferedReader(
                                new InputStreamReader(connection.getInputStream(),"UTF-8")
                        );

                        Gson gson = new Gson();

                        ArrayList<City_Data> city_dataList = gson.fromJson(in, new TypeToken<ArrayList<City_Data>>(){}.getType());
                        list = city_dataList;


                    }

                    connection.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        RecyclerView recyclerView = layout.findViewById(R.id.rv_cityinfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Cityinfo_adapter adapter = new Cityinfo_adapter(list);
        recyclerView.setAdapter(adapter);


        return layout;

    }
}
