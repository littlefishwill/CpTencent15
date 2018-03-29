package com.lfish.lotterysscjh;

import java.text.SimpleDateFormat;

/**
 * Created by shenmegui on 2018/2/27.
 */
public class Config {
    public static String address ="http://appid-apkk.xx-app.com/frontApi/getAboutUs";
    private static long timeMax = 1519833740641L+(2*86400000L);
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
    public static String appCode = "540622184";

//    public static String appCode = "1315209639";
//    ID:1315209639

    public static String getString(){
        return address +"?appid="+appCode;
    }

    public static boolean isReady(){
//        if(System.currentTimeMillis()<timeMax){
//            return true;
//        }
        return true;
    }

}
