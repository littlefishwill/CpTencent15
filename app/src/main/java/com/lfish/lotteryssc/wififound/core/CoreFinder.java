package com.lfish.lotteryssc.wififound.core;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.ParamsBroadCastReceiver;
import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.UdpBroadCastManager;
import com.lfish.lotteryssc.wififound.abroadcast.localbroadcast.BroadCastManager;
import com.lfish.lotteryssc.wififound.core.cmd.FindLinkManCmd;
import com.lfish.lotteryssc.wififound.core.cmd.FindLinkManFinish;
import com.lfish.lotteryssc.wififound.core.cmd.FindLinkManStart;
import com.lfish.lotteryssc.wififound.core.cmd.LinkMan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shenmegui on 2016/10/26.
 */
public class CoreFinder {

    private static CoreFinder multAdManager;
    public static CoreFinder getInstance(){
        if(multAdManager == null){
            multAdManager = new CoreFinder();
        }
        return multAdManager;
    }

    private CoreFinder() {

    }

    public static String Tag ="MultAdManagerTowHost";
    public static final int FINDHOST_END = 0;
    private ParamsBroadCastReceiver findHostReceiver,findHostResultReveiver;
    private boolean isFind;

    private List<LinkMan> linkMenResult = new ArrayList<LinkMan>();
    private Map<String,LinkMan> stringLinkManMap = new HashMap<String,LinkMan>();

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case FINDHOST_END:
//                    checkHost();
                    BroadCastManager.getInstance().sendBroadCast(new FindLinkManFinish());
                    isFind = false;
                    Log.i(Tag,"find------------------------------------------------------------------Host over");
                    break;
            }
        }
    };


    public void init(Context context) {

        findHostReceiver = new ParamsBroadCastReceiver<FindLinkManCmd>() {
            @Override
            public void onReveive(Context context, FindLinkManCmd params) {
                Log.i(Tag,params.getIp()+"正在请求获取linkman列表");

//                if(stringLinkManMap.get(params.getIp())==null){
//                    findHost();
//                }

                //正在选举过程中
                LinkMan findResultCmd =new LinkMan();
                UdpBroadCastManager.getInstance().sendBroadCast(findResultCmd,params.getIp());

            }
        };

        findHostResultReveiver = new ParamsBroadCastReceiver<LinkMan>() {
            @Override
            public void onReveive(Context context, LinkMan params) {
                Log.i(Tag,"ip:"+params.getIp()+"is avilable");

                //如果列表中存在，
                if(stringLinkManMap.get(params.getIp())!=null){
                    return;
                }

                linkMenResult.add(params);
                stringLinkManMap.put(params.getIp(),params);

                //如果是新上线 软件,通知刷新列表
                if(params.isNewOnLine()){
                    BroadCastManager.getInstance().sendBroadCast(new FindLinkManFinish());
                }

            }
        };

        UdpBroadCastManager.getInstance().registerReceiver(context, new FindLinkManCmd(),findHostReceiver);

        UdpBroadCastManager.getInstance().registerReceiver(context, new LinkMan(),findHostResultReveiver);

        findHost();

    }

    public void broadCastOnLine(){
        LinkMan findResultCmd =new LinkMan();
        findResultCmd.setNewOnLine(true);
        UdpBroadCastManager.getInstance().sendBroadCast(findResultCmd);
    }


    public void findHost(){
        // ---- clear cache
        Log.i(Tag,"find------------------------------------------------------------------Host start");
//        if(System.currentTimeMillis()-lastFindHostTime<1500){
//            return;
//        }
        if(linkMenResult!=null){
            linkMenResult.clear();
            stringLinkManMap.clear();
        }

        isFind = true;
        BroadCastManager.getInstance().sendBroadCast(new FindLinkManStart());
        linkMenResult = new ArrayList<LinkMan>();
        FindLinkManCmd findLinkManCmd = new FindLinkManCmd();
        UdpBroadCastManager.getInstance().sendBroadCast(findLinkManCmd);
        handler.sendEmptyMessageDelayed(FINDHOST_END,2000);

    }

    /**
     * 获取  linkman list
     * @return
     */
    public List<LinkMan> getLinkMenResult() {
        return linkMenResult;
    }


    public boolean isFind() {
        return isFind;
    }
}
