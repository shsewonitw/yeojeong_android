package com.tje.yeojunglogin.tab;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import android.widget.Toolbar;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.tje.yeojunglogin.Login_dialog;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.chatting.FragChatting;
import com.tje.yeojunglogin.city_info.FragCityInfo;
import com.tje.yeojunglogin.fragment.FragLogin;
import com.tje.yeojunglogin.review.FragReview;
import com.tje.yeojunglogin.review.ReviewList;
import com.tje.yeojunglogin.with_me.FragWithMe;

public class TabApplication extends FragmentActivity {

    ViewPager vp_tab;

    private ImageView btn_1;
    private ImageView btn_2;
    private ImageView btn_3;
    private ImageView btn_4;
    private Drawable planetearth1;
    Drawable planetearth2;
    Drawable handshake1;
    Drawable handshake2;
    Drawable review1;
    Drawable review2;
    Drawable message1;
    Drawable message2;
    private Toolbar toolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        planetearth1 = getResources().getDrawable(R.drawable.planetearth1);
        planetearth2 = getResources().getDrawable(R.drawable.planetearth2);
        handshake1 = getResources().getDrawable(R.drawable.handshake1);
        handshake2 = getResources().getDrawable(R.drawable.handshake2);
        review1 = getResources().getDrawable(R.drawable.review1);
        review2 = getResources().getDrawable(R.drawable.review2);
        message1 = getResources().getDrawable(R.drawable.message1);
        message2 = getResources().getDrawable(R.drawable.message2);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setTitle("여행자들의 정보");
        setActionBar(toolbar);



        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher_foreground);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);

        vp_tab = findViewById(R.id.vp_tab);


        pagerAdapter pager = new pagerAdapter(getSupportFragmentManager());
        vp_tab.setAdapter(pager);
        vp_tab.setCurrentItem(0);


        btn_1.setOnClickListener(movePageListener);
        btn_1.setTag(0);
        btn_2.setOnClickListener(movePageListener);
        btn_2.setTag(1);
        btn_3.setOnClickListener(movePageListener);
        btn_3.setTag(2);
        btn_4.setOnClickListener(movePageListener);
        btn_4.setTag(3);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.toolbar_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.home:
                Toast.makeText(this, "햄버거!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.logout:

                Intent logoutIntent = new Intent(this, Login_dialog.class);
                startActivity(logoutIntent);
                finish();

                return true;
            case R.id.item2:
                Toast.makeText(this, "22222", Toast.LENGTH_SHORT).show();

        }

        return super.onOptionsItemSelected(item);
    }


    View.OnClickListener movePageListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int tag = (int) v.getTag();
            vp_tab.setCurrentItem(tag);

            btn_1.setForeground(handshake1);
            btn_2.setForeground(planetearth1);
            btn_3.setForeground(message1);
            btn_4.setForeground(review1);

            if(tag == 0) {
                btn_1.setForeground(handshake2);
            } else if(tag == 1) {
                btn_2.setForeground(planetearth2);
            } else if(tag == 2) {
                btn_3.setForeground(message2);
            } else if(tag == 3) {
                btn_4.setForeground(review2);
            }


        }
    };

    private class pagerAdapter extends FragmentStatePagerAdapter {


        public pagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position)
            {
                case 0:
                    return new FragWithMe();
                case 1:
                    return new FragCityInfo();
                case 2:
                    return new FragChatting();
                case 3:
                    return new ReviewList();


                default:
                    return null;
            }


        }

        @Override
        public int getCount() {
            return 4;
        }

    }


}
