package com.bronet.blockchain.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class StringUtil {

//    public static Gson getGson(){
//        return  new Gson();
//    }

    /**
     *
     * @param escap
     * @return
     */
    public static Gson getGson(boolean escap){
        if(escap){
            return  new GsonBuilder().disableHtmlEscaping().create();
        }else {
            return new Gson();
        }
    }
    public static int count2Length(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        int count = 0;
        char[] chs = str.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            count += (chs[i] > 0xff) ? 2 : 1;
        }
        return count;
    }

}
