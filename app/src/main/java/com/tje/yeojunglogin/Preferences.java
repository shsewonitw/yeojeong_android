package com.tje.yeojunglogin;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String HOST_NETWORK_PROTOCOL = "http://";
    public static final String HOST_ADDRESS = "192.168.0.87:8080";
    public static final String HOST_APP_NAME = "/yeojeong";

    public static SharedPreferences getSp(Context context){
        return context.getSharedPreferences("appData",Context.MODE_PRIVATE);
    }
}
