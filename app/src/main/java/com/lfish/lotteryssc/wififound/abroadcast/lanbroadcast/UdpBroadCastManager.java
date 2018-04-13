package com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.util.Log;
import com.google.gson.Gson;
import com.lfish.lotteryssc.wififound.abroadcast.localbroadcast.BroadCast;
import com.lfish.lotteryssc.wififound.abroadcast.localbroadcast.BroadCastManager;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * 局域网 广播，此广播 发送后，局域网内，其他注册此广播的机器，可以接受到广播与参数
 */
public class UdpBroadCastManager {
    /**
     * 局域网广播版本（此版本号，需要在广播中包含，否则不同版本apk广播可能会导致机器奔溃）
     */
    public static final int  LANBROAD_VERSION = 1;
    public static String TAG = "UdpBroadCastManager";
    // udp 监听的端口
    public static final int PORT = 5219;
    private byte[] msg = new byte[1028*4];
    private boolean isReceive = true;
    private DatagramSocket dSocket;
    private Context context;

    private static UdpBroadCastManager multiPlayManager;
    public static UdpBroadCastManager getInstance(){
        if(multiPlayManager==null){
            multiPlayManager = new UdpBroadCastManager();
        }
        return multiPlayManager;
    }

    private UdpBroadCastManager() {

    }


    public Context getContext() {
        return context;
    }

    /**
     * 开始接受广播
     */
    private void startReceive() {
            DatagramPacket dPacket = new DatagramPacket(msg, msg.length);
                while (isReceive) {
                    try {
                        if(dSocket==null){
                            Log.w(TAG,"检测到局域网广播接收器异常，或许端口被占用，请重启机器.");
                            return;
                        }
                        dSocket.receive(dPacket);
                        String str = new String(dPacket.getData(),dPacket.getOffset(),dPacket.getLength());
                        UdpBroadCast udpBroadCast = new Gson().fromJson(str, UdpBroadCast.class);
                        Log.i(TAG,"recevice:"+ udpBroadCast.getBroadVersion()+":" +str);
                        if(udpBroadCast.getBroadVersion()!= UdpBroadCastManager.LANBROAD_VERSION){
                            Log.i(TAG,"广播版本不一致");
                            continue;
                        }
                        //为了得到T的Class，采用如下方法
                        //1得到该泛型类的子类对象的Class对象
                        Class<?> aClass = Class.forName(udpBroadCast.getCmdClass());
                        BroadCastManager.getInstance().sendBroadCast((BroadCast) new Gson().fromJson(str,aClass));
                    } catch (Exception e) {
                        e.printStackTrace();
//                        Log.i("广播错误:"+e.getMessage());
                        return;
                    }
                }
    }

    public void sendBroadCast(UdpBroadCast udpBroadCast){
        udpBroadCast.setBroadVersion(UdpBroadCastManager.LANBROAD_VERSION);
        new Thread(udpBroadCast).start();
    }

    public void sendBroadCast(UdpBroadCast udpBroadCast, String toIp){
        udpBroadCast.setBroadVersion(UdpBroadCastManager.LANBROAD_VERSION);
        udpBroadCast.setToIp(toIp);
        new Thread(udpBroadCast).start();
    }


    public DatagramSocket getdSocket() {
        return dSocket;
    }

    public void init(Context context) {
        this.context = context;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dSocket = new DatagramSocket(PORT);
                    dSocket.setBroadcast(true);
                } catch (SocketException e) {
                    e.printStackTrace();
                }
                startReceive();
            }
        }).start();
    }

    public void resetBroadCast(){
        if(dSocket!=null){
            dSocket.disconnect();
            dSocket.close();
        }
    }

    /**
     * 注册广播
     * @param context
     * @param broadCast
     * @param broadcastReceiver
     */
    public void registerReceiver(Context context, BroadCast broadCast, BroadcastReceiver broadcastReceiver){
        IntentFilter filter = new IntentFilter();
        filter.addAction(broadCast.getClass().toString());
        filter.setPriority(Integer.MAX_VALUE);
        if(broadcastReceiver!=null) {
            try {
                context.registerReceiver(broadcastReceiver, filter);
            }catch (Exception e) {

            }
        }
    }

    /**
     * 取消注册广播
     * @param context
     * @param broadcastReceiver
     */
    public void unRegisterReceiver(Context context, BroadcastReceiver broadcastReceiver){
        context.unregisterReceiver(broadcastReceiver);
    }

}
