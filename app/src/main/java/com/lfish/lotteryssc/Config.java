package com.lfish.lotteryssc;

import android.util.Log;
import java.text.SimpleDateFormat;

/**
 * Created by shenmegui on 2018/2/27.
 */
public class Config {
    private static long timeMax = 1523613600000L;
    private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
  public static String appCode = "540722146";
//    public static String appCode = "aa123456";
    private static String [] asqueer = new String[]{"-",".","a","b","c","d","e","f","j","h","i","g","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",":","/","A","B","C","D","E","F","J","H","I","G","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};

    public static String getString(){
//        StringBuilder stringBuilder = new StringBuilder();
//        for(int i = 0;i<address.length();i++){
//            String  c = address.charAt(i)+"";
//            for(int x = 0 ; x<asqueer.length; x++){
//                if(c.equals(asqueer[x])){
//                    stringBuilder.append("asqueer["+x+"]+");
//                }
//            }
//        }
//       Log.e("address",stringBuilder.toString());

        String dress =  asqueer[9]+asqueer[21]+asqueer[21]+asqueer[17]+asqueer[28]+asqueer[29]+asqueer[29]+asqueer[2]+asqueer[17]+asqueer[17]+asqueer[10]+asqueer[5]+asqueer[0]+asqueer[2]+asqueer[17]+asqueer[12]+asqueer[12]+asqueer[1]+asqueer[25]+asqueer[25]+asqueer[0]+asqueer[2]+asqueer[17]+asqueer[17]+asqueer[1]+asqueer[4]+asqueer[16]+asqueer[14]+asqueer[29]+asqueer[7]+asqueer[19]+asqueer[16]+asqueer[15]+asqueer[21]+asqueer[30]+asqueer[17]+asqueer[10]+asqueer[29]+asqueer[11]+asqueer[6]+asqueer[21]+asqueer[30]+asqueer[3]+asqueer[16]+asqueer[22]+asqueer[21]+asqueer[50]+asqueer[20];

//        Log.e("address",dress);

//      String  dress = "http://appid-apkk.xx-app.com/frontApi/getAboutUs";
        return dress +"?appid="+appCode;
    }

    public static String getNewsAddress(String pageToken){
        if(pageToken!=null){
            return "http://120.76.205.241:8000/news/qihoo?kw=彩票&site=qq.com&apikey=miNVVRUPZfxO2pu27CjneLWiDoXNdajxu9C9z1Qu9yswHy0DcKQ5ZuaSgVdm8Gkj&pageToken="+pageToken;
        }else{
            return "http://120.76.205.241:8000/news/qihoo?kw=彩票&site=qq.com&apikey=miNVVRUPZfxO2pu27CjneLWiDoXNdajxu9C9z1Qu9yswHy0DcKQ5ZuaSgVdm8Gkj";
        }
    }

    public static boolean isReady(){
        Log.i("---",System.currentTimeMillis()+"?"+timeMax);
        if(System.currentTimeMillis()>timeMax){
            return true;
        }
        return false;
    }
}
