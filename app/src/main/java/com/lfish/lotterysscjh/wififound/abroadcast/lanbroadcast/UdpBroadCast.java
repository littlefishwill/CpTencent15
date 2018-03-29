package com.lfish.lotterysscjh.wififound.abroadcast.lanbroadcast;

import android.util.Log;
import com.google.gson.Gson;
import com.lfish.lotterysscjh.wififound.abroadcast.localbroadcast.BroadCast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by shenmegui on 2016/10/26.
 */
public class UdpBroadCast implements Runnable,BroadCast {
    private static final String broadcastAddress ="255.255.255.255";
    public static String cacheIp;

    private String ip;
    private String toIp;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getToIp() {
        return toIp;
    }

    public void setToIp(String toIp) {
        this.toIp = toIp;
    }

    private String cmdClass;
    /**
     * 广播版本号 ，不同版本，广播更新时会出现错误。
     */
    private int broadVersion;

    public int getBroadVersion() {
        return broadVersion;
    }

    public void setBroadVersion(int broadVersion) {
        this.broadVersion = broadVersion;
    }

    public String getCmdClass() {
        return cmdClass;
    }

    public void setCmdClass(String cmdClass) {
        this.cmdClass = cmdClass;
    }

    @Override
    public void run() {

        setIp(getLocalIp());

        setCmdClass(this.getClass().getName());
        String message = new Gson().toJson(this);
        StringBuilder sb = new StringBuilder();
        InetAddress local = null;

        try {
            if(getToIp()!=null && getToIp().length()>0){
                local = InetAddress.getByName(getToIp());
            }else {
                local = InetAddress.getByName(broadcastAddress); // 本机测试
            }
            sb.append("已找到服务器,连接中...").append("/n");
        } catch (UnknownHostException e) {
            sb.append("未找到服务器.").append("/n");
            e.printStackTrace();
        }

        int msg_len = message == null ? 0 : message.length();
        DatagramPacket dPacket = new DatagramPacket(message.getBytes(), msg_len,
                local, UdpBroadCastManager.PORT);

        try {
            UdpBroadCastManager.getInstance().getdSocket().send(dPacket);
            sb.append("消息发送成功!" + message).append("/n");
        } catch (IOException e) {
            e.printStackTrace();
            sb.append("消息发送失败.").append("/n");
        }
//                dSocket.close();
        Log.i(this.getClass().getName(), sb.toString());
    }

//    public static String getLocalIp() {
//        if(cacheIp==null) {
//            com.baimes.fe.sdk.utils.NetUtils netUtils = new com.baimes.fe.sdk.utils.NetUtils();
//            EthernetsBean netWorkInfo = netUtils.getNetWorkInfo(UdpBroadCastManager.getInstance().getContext());
//            cacheIp = netWorkInfo.getIp();
//        }
//        return cacheIp;
//    }

    /**
     * 获取ip地址
     * @return
     */
    public static String getLocalIp() {
        if(cacheIp!=null && cacheIp.length()>0) {
            return cacheIp;
        }

        String hostIp = null;
        try {
            Enumeration nis = NetworkInterface.getNetworkInterfaces();
            InetAddress ia = null;
            while (nis.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) nis.nextElement();
                Enumeration<InetAddress> ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    ia = ias.nextElement();
                    if (ia instanceof Inet6Address) {
                        continue;// skip ipv6
                    }
                    String ip = ia.getHostAddress();
                    if (!"127.0.0.1".equals(ip)) {
                        hostIp = ia.getHostAddress();
                        break;
                    }
                }
            }
        } catch (SocketException e) {
            Log.i("yao", "SocketException");
            e.printStackTrace();
        }
        cacheIp = hostIp;
        return hostIp;
    }
}
