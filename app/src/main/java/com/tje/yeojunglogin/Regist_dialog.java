package com.tje.yeojunglogin;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Regist_dialog extends AppCompatActivity {

    private static final String HOST_NETWORK_PROTOCOL = "http://";
    private static final String HOST_ADDRESS = "192.168.0.87:8080";
    private static final String HOST__APP_NAME = "/yeojeong";




    EditText edit_regist_id;
    EditText edit_regist_pw;
    EditText edit_regist_name;
    EditText edit_regist_year;
    EditText edit_regist_month;
    EditText edit_regist_day;
    EditText edit_regist_email;
    EditText edit_regist_phoneNumber;
    Button btn_regist;

    RadioGroup ra_gr;
    RadioButton ra_regist_men;
    RadioButton ra_regist_women;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_dialog);

        edit_regist_id = findViewById(R.id.edit_regist_id);
        edit_regist_pw = findViewById(R.id.edit_regist_pw);
        edit_regist_name = findViewById(R.id.edit_regist_name);

        ra_gr = findViewById(R.id.ra_gr);
        ra_regist_men = findViewById(R.id.ra_regist_men);
        ra_regist_women = findViewById(R.id.ra_regist_women);

        edit_regist_year = findViewById(R.id.edit_regist_year);
        edit_regist_month = findViewById(R.id.edit_regist_month);
        edit_regist_day = findViewById(R.id.edit_regist_day);
        edit_regist_email = findViewById(R.id.edit_regist_email);
        edit_regist_phoneNumber = findViewById(R.id.edit_regist_phoneNumber);
        btn_regist = findViewById(R.id.btn_regist);

        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {

                        String member_id = edit_regist_id.getText().toString().trim();
                        String password = edit_regist_pw.getText().toString().trim();
                        String name = edit_regist_name.getText().toString().trim();
                        int gender = 1;
                        RadioButton btn = (RadioButton) findViewById(ra_gr.getCheckedRadioButtonId());
                        if (btn.getText().equals("여")) {
                            gender = 1;

                        } else {
                            gender = 0;
                        }


                        int year = Integer.parseInt(edit_regist_year.getText().toString().trim());
                        int month = Integer.parseInt(edit_regist_month.getText().toString().trim());
                        int day = Integer.parseInt(edit_regist_day.getText().toString().trim());
                        String email = edit_regist_email.getText().toString().trim();
                        String tel = edit_regist_phoneNumber.getText().toString().trim();


                        SimpleDateFormat sdf =
                                new SimpleDateFormat("yyyy-MM-dd");

                        String source = String.format("%04d-%02d-%02d", year, month, day);
                        Date birth = null;
                        try {
                            birth = sdf.parse(source);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        Member member = new Member();

//                        String param = String.format("member_id=%s&password=%s&name=%s&gender=%d&birth=%s&email=%s&tel=%s", member_id, password, name, gender, source, email, tel);

                        String targetURL = "/regist_Regular";

                        try {
                            URL url = new URL(Regist_dialog.HOST_NETWORK_PROTOCOL + Regist_dialog.HOST_ADDRESS + Regist_dialog.HOST__APP_NAME + targetURL);

                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setDoOutput(true);
                            connection.setDoInput(true);
//                            connection.setRequestProperty("Content-type", "application/json");
//                            connection.setRequestProperty("Accept", "application/json");


                            OutputStream writer = connection.getOutputStream();

                            member.setMember_id(member_id);
                            member.setPassword(password);
                            member.setName(name);
                            member.setGender(gender);
                            member.setBirth(year, month, day);
                            member.setEmail(email);
                            member.setTel(tel);

                            JSONObject jsonObject = new JSONObject();
                            jsonObject.put("member_id",member_id);
                            jsonObject.put("password",password);
                            jsonObject.put("name",name);
                            jsonObject.put("gender",gender);
                            jsonObject.put("birth",source);
                            jsonObject.put("email",email);
                            jsonObject.put("tel",tel);
                            writer.write(jsonObject.toString().getBytes());
                            writer.flush();

                            final Integer r_code = connection.getResponseCode();

//                            runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    try {
//                                        Toast.makeText(Regist_dialog.this, r_code.toString(), Toast.LENGTH_LONG).show();
//                                    } catch (Exception e) {
//                                        e.printStackTrace();
//                                    }
//                                }
//                            });
                            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

                                Gson gson = new Gson();
                                Type listType = new TypeToken<Boolean>() {
                                }.getType();
                                final Boolean result = gson.fromJson(in, listType);
                                Intent intent = new Intent();
                                intent.putExtra("result", result);
                                intent.putExtra("add_member", member);

                                setResult(result ? RESULT_OK : RESULT_CANCELED, intent);
                                in.close();


                            }

                            writer.close();
                            connection.disconnect();
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                });

            }
        });
    }
}
