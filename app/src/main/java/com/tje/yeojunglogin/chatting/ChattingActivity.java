package com.tje.yeojunglogin.chatting;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tje.yeojunglogin.Preferences;
import com.tje.yeojunglogin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChattingActivity extends AppCompatActivity {

    private SharedPreferences appData;

    private ListView lv_chatting;
    private Button btn_chatting;
    private EditText et_chatting;
    private ArrayList<String> list = new ArrayList<>();
    private ArrayAdapter<String> arrayAdapter;

    private String userID, chat_msg, chat_user;
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("message1");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        appData = Preferences.getSp(this);

        Intent intent = getIntent();
        String receiver_id = intent.getExtras().getString("member_id");


        System.out.println("Frag Chatting onCreate View 호출!!!");


        lv_chatting = findViewById(R.id.lv_chatting);
        btn_chatting = findViewById(R.id.btn_chatting);
        et_chatting = findViewById(R.id.et_chatting);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,list);
        lv_chatting.setAdapter(arrayAdapter);

        userID = appData.getString("ID","");

        setDBReference(userID, receiver_id);

        System.out.println("으악: "+ userID+ " , "+receiver_id);
        btn_chatting.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Map<String, Object> map = new HashMap<String,Object>();


                String key = reference.push().getKey();

                reference.updateChildren(map);

                DatabaseReference root = reference.child(key);

                Map<String, Object> objectMap = new HashMap<String,Object>();

                objectMap.put("userID",userID);
                objectMap.put("text",et_chatting.getText().toString().trim());

                root.updateChildren(objectMap);
                et_chatting.setText("");
            }
        });

        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatConversation(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                chatConversation(dataSnapshot);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void chatConversation(DataSnapshot dataSnapshot) {
        Iterator i = dataSnapshot.getChildren().iterator();

        while (i.hasNext()) {
            chat_msg = (String) ((DataSnapshot) i.next()).getValue();
            chat_user = (String) ((DataSnapshot) i.next()).getValue();


            arrayAdapter.add(chat_user + " : " + chat_msg);
        }

        arrayAdapter.notifyDataSetChanged();
    }

    private void setDBReference(String str1, String str2) {
        String tableName = "";

        if( str1.compareTo(str2) == 0){
            ;
        } else if ( str1.compareTo(str2) > 0 ) {
            tableName = str1 +"|"+ str2;
        } else if ( str1.compareTo(str2) < 0 ) {
            tableName = str2 +"|"+ str1;
        }

        reference = FirebaseDatabase.getInstance().getReference().child(tableName);
    }
}
