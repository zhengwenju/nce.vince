package com.bronet.blockchain.model.api.cookie;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.bronet.blockchain.base.MyApplication;

public class SharePreData {
    private static final String COOKIE_PREFS = "Cookies_Prefs";
    private final SharedPreferences cookiePrefs;
    public SharePreData() {
        cookiePrefs = MyApplication.getInstance().getSharedPreferences(COOKIE_PREFS, 0);
    }
    public void add(String key ,String value){
        SharedPreferences.Editor prefsWriter = cookiePrefs.edit();
        prefsWriter.putString(key, value);
        prefsWriter.apply();
    }
    public String getString(String key){
        return cookiePrefs.getString(key,"");
    }
    public String getString(String key,String def){
        return cookiePrefs.getString(key,def);
    }
}
