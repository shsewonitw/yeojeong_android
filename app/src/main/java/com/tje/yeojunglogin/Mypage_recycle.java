package com.tje.yeojunglogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Mypage_recycle extends AppCompatActivity {

    RecyclerView mypage_recycle;
    RecyclerView.LayoutManager layoutManager;
    MypageRecyclerViewAdaptor mypageRecyclerViewAdaptor;

    TextView tv_no_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage_recycle);

        tv_no_content = findViewById(R.id.tv_no_content);

        mypage_recycle = findViewById(R.id.mypage_recycle);
        layoutManager = new LinearLayoutManager(this);
        mypage_recycle.setLayoutManager(layoutManager);
        mypageRecyclerViewAdaptor = new MypageRecyclerViewAdaptor( Mypage_recycle.this);
        mypage_recycle.setAdapter(mypageRecyclerViewAdaptor);


    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mypageRecyclerViewAdaptor.getItemCount() == 0) {
            tv_no_content.setVisibility(View.VISIBLE);
            mypage_recycle.setVisibility(View.GONE);
        } else {
            mypageRecyclerViewAdaptor.loadItems();
            tv_no_content.setVisibility(View.GONE);
            mypage_recycle.setVisibility(View.VISIBLE);

        }
    }

}
