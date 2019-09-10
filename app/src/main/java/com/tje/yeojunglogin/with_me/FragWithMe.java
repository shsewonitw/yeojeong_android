package com.tje.yeojunglogin.with_me;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.city_info.Cityinfo_adapter;
import com.tje.yeojunglogin.model.City_Data;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class FragWithMe extends Fragment {

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;
    ArrayList<City_Data> list = new ArrayList<>();

    private Button btnFind;


    public FragWithMe() {
        System.out.println("Frag With Me 생성자 호출!!!");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        System.out.println("Frag WIth Me onCreate 호출!!!");

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


    System.out.println("Frag WIth Me onCreate View 호출!!!");


        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_withme, container, false);

        btnFind = layout.findViewById(R.id.btn_find);
        btnFind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "zzzzzz", Toast.LENGTH_SHORT).show();
            }
        });




//
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                String targetURL = "/android_traveldata";
//
//                try {
//                    URL endPoint = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + targetURL);
//                    HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
//                    connection.setRequestMethod("GET");
//
//                    if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
//                        BufferedReader in = new BufferedReader(
//                                new InputStreamReader(connection.getInputStream(),"UTF-8")
//                        );
//
//                        Gson gson = new Gson();
//
//                        ArrayList<City_Data> city_dataList = gson.fromJson(in, new TypeToken<ArrayList<City_Data>>(){}.getType());
//                        list = city_dataList;
//
//
//                    }
//
//                    connection.disconnect();
//                } catch(Exception e) {
//                    e.printStackTrace();
//                }
//
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        RecyclerView recyclerView = layout.findViewById(R.id.rv_cityinfo);
//                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//                        Cityinfo_adapter adapter = new Cityinfo_adapter(list);
//                        recyclerView.setAdapter(adapter);
//                    }
//                });
//
//
//
//            }
//        });
//
//
//


        return layout;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        System.out.println("Frag City info onActivityCreated 호출!");
    }
}
