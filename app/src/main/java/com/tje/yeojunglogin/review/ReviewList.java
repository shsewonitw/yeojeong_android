package com.tje.yeojunglogin.review;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReviewList extends Fragment {

    private static final String HOST_NETWORK_PROTOCOL = Preferences.HOST_NETWORK_PROTOCOL;
    private static final String HOST_ADDRESS = Preferences.HOST_ADDRESS;
    private static final String HOST_APP_NAME = Preferences.HOST_APP_NAME;
    private RecyclerView recycleReivew;
    private RecyclerView.LayoutManager layoutManager;
    private ReviewListRecyclerViewAdapter reviewlistAdapter;
    ArrayList<Review_view> list;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.reviewlist, container, false);

        recycleReivew = layout.findViewById(R.id.recycleReivew);
        recycleReivew.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewlistAdapter = new ReviewListRecyclerViewAdapter();
        recycleReivew.setAdapter(reviewlistAdapter);

        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recycleReivew.getContext(),new LinearLayoutManager(getContext()).getOrientation());
        recycleReivew.addItemDecoration(dividerItemDecoration);

        VerticalSpaceItemDecoration spaceDecoration = new VerticalSpaceItemDecoration(20,20);
        recycleReivew.addItemDecoration(spaceDecoration);



         AsyncTask.execute(new Runnable() {
            @Override
            public void run() {

                String targetURL = "/androidreviewlist";

                try {
                    URL url = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST_APP_NAME + targetURL);

                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    Gson gson = new Gson();

                    if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
                        list =  gson.fromJson(in, new TypeToken<ArrayList<Review_view>>() {
                        }.getType());

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                reviewlistAdapter.loadItems(list);
                                reviewlistAdapter.notifyDataSetChanged();

                            }
                        });

                        in.close();

                    } else {
                        Toast.makeText(getContext(),"연결실패",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return layout;
    }


}
