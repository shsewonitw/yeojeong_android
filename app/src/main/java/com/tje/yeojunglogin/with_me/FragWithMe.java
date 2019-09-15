package com.tje.yeojunglogin.with_me;

import android.content.SharedPreferences;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sdsmdg.harjot.rotatingtext.RotatingTextWrapper;
import com.sdsmdg.harjot.rotatingtext.models.Rotatable;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.city_info.Cityinfo_adapter;
import com.tje.yeojunglogin.model.City_Data;
import com.tje.yeojunglogin.model.Withme_view;
import com.tje.yeojunglogin.tab.TabApplication;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class FragWithMe extends Fragment {

    private SharedPreferences appData;

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;
    ArrayList<Withme_view> list = new ArrayList<>();

    private BootstrapButton btnFind;
    private RecyclerView rv_withme;

    final LocationListener gpsLocationListener = new LocationListener() {
        public void onLocationChanged(Location location) {

            String provider = location.getProvider();
            double longitude = location.getLongitude();
            double latitude = location.getLatitude();
            double altitude = location.getAltitude();
//
//            Toast.makeText(getContext(), "위치정보 : " + provider + "\n" +
//                    "위도 : " + longitude + "\n" +
//                    "경도 : " + latitude + "\n" +
//                    "고도  : " + altitude, Toast.LENGTH_SHORT).show();

        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

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

        appData = Preferences.getSp(getActivity());

    System.out.println("Frag WIth Me onCreate View 호출!!!");

        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_withme, container, false);
        final Geocoder geocoder = new Geocoder(getContext());
        final LocationManager lm  = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);


        btnFind = layout.findViewById(R.id.btn_find);
        rv_withme = layout.findViewById(R.id.rv_withme);
        btnFind.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
//                btnFind.setVisibility(View.GONE);
                String currentCity = null;

                List<Address> addressList = null;

                if ( Build.VERSION.SDK_INT >= 23 &&
                        ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
                    ActivityCompat.requestPermissions(getActivity() , new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
                            0 );
                }
                else{
                    Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                    String provider = location.getProvider();
                    double longitude = location.getLongitude();
                    double latitude = location.getLatitude();
                    double altitude = location.getAltitude();

                    try {
                        addressList=geocoder.getFromLocation(latitude, longitude, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null) {
                        if (addressList.size()==0) {
                            Toast.makeText(getContext(), "현재 위치를 알 수 없습니다", Toast.LENGTH_SHORT).show();
                        } else {
                            currentCity = addressList.get(0).getLocality() == null ? addressList.get(0).getAdminArea() : addressList.get(0).getLocality();

                            Toast.makeText(getContext(), currentCity , Toast.LENGTH_LONG).show();
                            System.out.println(" 로그인 아이디 : "+appData.getString("ID",""));
                        }
                    }

                    lm.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);
                    lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                            1000,
                            1,
                            gpsLocationListener);


                    final String finalCurrentCity = currentCity;
                    AsyncTask.execute(new Runnable() {
                        @Override
                        public void run() {
                            String member_id = appData.getString("ID","");
                            String targetURL = "/android_withme_article";
                            String param = String.format("?member_id=%s",member_id);

                            try {
                                URL endPoint = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + targetURL + param);
                                HttpURLConnection connection = (HttpURLConnection)endPoint.openConnection();
                                connection.setRequestMethod("GET");

                                if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
                                    BufferedReader in = new BufferedReader(
                                            new InputStreamReader(connection.getInputStream(),"UTF-8")
                                    );

                                    Gson gson = new Gson();

                                    ArrayList<Withme_view> withme_viewList = gson.fromJson(in, new TypeToken<ArrayList<Withme_view>>(){}.getType());
                                    ArrayList<Withme_view> targetPersonList = new ArrayList<>();
                                    list = withme_viewList;

                                    for(Withme_view withme_view : list){
                                        try {
                                            List<Address> tempList = geocoder.getFromLocationName(withme_view.getCity(),1);
                                            String searchedCity = tempList.get(0).getAdminArea() == null ? tempList.get(0).getLocality() : tempList.get(0).getAdminArea();


                                            if(searchedCity.equals(finalCurrentCity)) {
                                                // 현재 모바일 기기가 위치한 도시와 같은 곳을 여행중인 동행요청한 유저
                                                targetPersonList.add(withme_view);
                                            }

                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    list = targetPersonList;

                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            btnFind.setVisibility(View.GONE);
                                            rv_withme.setVisibility(View.VISIBLE);
                                            RecyclerView recyclerView = layout.findViewById(R.id.rv_withme);
                                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                                            Withme_adapter adapter = new Withme_adapter(list);
                                            recyclerView.setAdapter(adapter);
                                        }
                                    });


                                }

                                connection.disconnect();
                            } catch(Exception e) {
                                e.printStackTrace();
                            }


                        }
                    });
                }
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
