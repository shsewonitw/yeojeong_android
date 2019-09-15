package com.tje.yeojunglogin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;

import com.tje.yeojunglogin.Login_dialog;
import com.tje.yeojunglogin.R;
import com.tje.yeojunglogin.Regist_dialog;

public class FragLogin extends Fragment  {
    private Button btn_regist;
    public FragLogin()
    {
        System.out.println("FlagLogin constructor");
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        System.out.println("FlagLogin onCreate");


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        System.out.println("FlagLogin onCreateView");

        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_login, container, false);
        btn_regist = layout.findViewById(R.id.btn_regist);
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("가입버튼 클릭!!!");
                Intent intent = new Intent(getContext(), Regist_dialog.class);
                startActivityForResult(intent, 1);

//                setResult(FragReview.LOGIN_REGIST, intent);
//                finish();

            }
        });
        return layout;

    }



}
