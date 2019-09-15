package com.tje.yeojunglogin.chatting;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.city_info.Cityinfo_adapter;
import com.tje.yeojunglogin.model.City_Data;

import java.util.ArrayList;


public class FragChatting extends Fragment {

    private SharedPreferences appData;

   ArrayList<String> list = new ArrayList<>();

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        System.out.println("fragChatting 실행!");

        appData = Preferences.getSp(getActivity());
        final String user_id = appData.getString("ID","");


        final LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_chatting, container, false);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    String chat = ds.getKey();
                    System.out.println("으악::: "+chat);
                    if ( chat.contains(user_id) ){
                        System.out.println("이프문 들어옴");
                        list.add(chat);
                    }
                }

                System.out.println("리스트사이즈: "+list.size());
                RecyclerView recyclerView = layout.findViewById(R.id.rv_fragChatting);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                ChattingAdapter adapter = new ChattingAdapter(list);
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });






        return layout;
    }
}
