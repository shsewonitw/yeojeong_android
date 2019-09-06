package com.tje.yeojunglogin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_INSERT = 1;
    private static final int REQUEST_CODE_LOGIN = 2;
    private static final int REQUEST_CODE_DELETE = 3;
    private static final int REQUEST_CODE_MYPAGE = 4;
    private static final int REQUEST_CODE_KAKAO = 5;
    public static final int LOGIN_REGIST = 3;

    Button btn_login_ac;
    Button btn_regist_ac;
    Button btn_mypage_ac;
    Button btn_kakao_ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_login_ac = findViewById(R.id.btn_regist);
        btn_regist_ac = findViewById(R.id.btn_regist_ac);
        btn_mypage_ac = findViewById(R.id.btn_mypage_ac);
        btn_kakao_ac = findViewById(R.id.btn_kakao_ac);

        btn_mypage_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Mypage_recycle.class);
                startActivityForResult(intent, REQUEST_CODE_MYPAGE);
            }
        });

        btn_login_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Login_dialog.class);
                startActivityForResult(intent, REQUEST_CODE_LOGIN);

            }
        });
        btn_regist_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Regist_dialog.class);
                startActivityForResult(intent, REQUEST_CODE_INSERT);

            }
        });
        btn_kakao_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, KakaoActivity.class);
                startActivityForResult(intent, REQUEST_CODE_KAKAO);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Member member = null;
        String msg = null;
        switch (requestCode) {
            case REQUEST_CODE_INSERT:

                if (data == null) {
                    return;
                }

                member = (Member) data.getSerializableExtra("add_member");

                if (resultCode == RESULT_OK) {
                    msg = String.format("'%s' 아이디로 '%s' 님의 회원가입이 완료되었습니다.",
                            member.getMember_id(), member.getName());
                } else {
                    msg = String.format("'%s' 아이디로 '%s' 님의 회원가입이 실패하였습니다.",
                            member.getMember_id(), member.getName());
                }
                Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                break;
            case REQUEST_CODE_LOGIN:

                if (resultCode == RESULT_OK) {
                    msg = String.format("로그인이 완료되었습니다.");
                } else if (resultCode == LOGIN_REGIST) {

                    btn_regist_ac.performClick();
                } else {

                    msg = String.format("로그인이 실패하였습니다.");
                }
                if (msg != null) {
                    Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
                }

                break;
            case REQUEST_CODE_DELETE:

                break;
            case REQUEST_CODE_MYPAGE:
                break;
        }


    }
}
