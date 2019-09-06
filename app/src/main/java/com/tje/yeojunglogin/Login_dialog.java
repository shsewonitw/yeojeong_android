package com.tje.yeojunglogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.CookieManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tje.yeojunglogin.tab.TabApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Login_dialog extends AppCompatActivity {
    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.87:8080";
    private static final String HOST__APP_NAME = "/yeojeong";

    EditText edit_login_id;
    EditText edit_login_pw;
    CheckBox cb_saveId;
    Button btn_login;
    Button btn_regist;
    Button btn_custom_login;


    private boolean saveLoginData;
    private String id;
    private String pwd;


    private SharedPreferences appData;

    private void save() {
        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", cb_saveId.isChecked());
        editor.putString("ID", edit_login_id.getText().toString().trim());
        editor.putString("PWD", edit_login_pw.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();
    }

    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        id = appData.getString("ID", "");
        pwd = appData.getString("PWD", "");
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        appData = getSharedPreferences("appData", MODE_PRIVATE);
        load();

        edit_login_id = findViewById(R.id.edit_login_id);
        edit_login_pw = findViewById(R.id.edit_login_pw);
        cb_saveId = findViewById(R.id.cb_saveId);
        btn_login = findViewById(R.id.btn_login);
        btn_regist = findViewById(R.id.btn_regist);
        btn_custom_login = findViewById(R.id.btn_custom_login);

        if (saveLoginData) {
            edit_login_id.setText(id);
            edit_login_pw.setText(pwd);
            cb_saveId.setChecked(saveLoginData);
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTask.execute(new Runnable() {

                    @Override
                    public void run() {
                        System.out.println("로그인 버튼!!!");

                        String target = "/android_login";
                        try {
                            URL url = new URL(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME + target);

                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            String member_id = edit_login_id.getText().toString().trim();
                            String password = edit_login_pw.getText().toString().trim();
                            String param = String.format("member_id=%s&password=%s", member_id, password);


                            String cookieString = CookieManager.getInstance().getCookie(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME);
                            System.out.println(cookieString);

                            if (cookieString != null) {
                                connection.setRequestProperty("Cookie", cookieString);
                            }

                            connection.setRequestMethod("POST");

                            // 보내는 쪽
                            if(param != null){
                                connection.getOutputStream().write(param.getBytes());
                            } else {
                                System.out.println("param == null");
                            }


                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                Map<String, List<String>> headerFields = connection.getHeaderFields();
                                String COOKIES_HEADER = "Set-Cookie";
                                List<String> cookiesHeader = headerFields.get(COOKIES_HEADER);

                                if (cookiesHeader != null) {
                                    for (String cookie : cookiesHeader) {
                                        String cookieName = HttpCookie.parse(cookie).get(0).getName();
                                        String cookieValue = HttpCookie.parse(cookie).get(0).getValue();

                                        cookieString = cookieName + "=" + cookieValue;
                                        CookieManager.getInstance().setCookie(HOST_NETWORK_PROTOCOL + HOST_ADDRESS + HOST__APP_NAME, cookieString);
                                    }
                                }


                                // 받는 쪽
                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                                Gson gson = new Gson();
                                Type listType = new TypeToken<HashMap<String, Object>>() {
                                }.getType();
                                HashMap<String, Object> result = gson.fromJson(in, listType);
                                String login_msg = (String) result.get("login_message");
                                boolean login_result = (Boolean) result.get("result");
                                System.out.println(login_msg);
                                System.out.println(login_result);
                                if (login_result == true) {
                                    save();
                                }
                                in.close();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(Login_dialog.this, "통신에러...", Toast.LENGTH_SHORT);
                                    }
                                });

                            }

                            Intent intent = new Intent(Login_dialog.this, TabApplication.class);
                            startActivity(intent);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("가입버튼 클릭!!!");
                Intent intent = new Intent(Login_dialog.this, Regist_dialog.class);
                startActivity(intent);
                finish();
//                setResult(MainActivity.LOGIN_REGIST, intent);
//                finish();

            }
        });

        btn_custom_login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                System.out.println("카카오 로그인 클릭");
                Intent intent = new Intent(Login_dialog.this, KakaoActivity.class);
                startActivity(intent);

            }
        });
    }
}
