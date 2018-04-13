package com.lfish.lotteryssc.wififound;

import android.app.Application;
import android.content.Context;

import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.ParamsBroadCastReceiver;
import com.lfish.lotteryssc.wififound.abroadcast.lanbroadcast.UdpBroadCastManager;
import com.lfish.lotteryssc.wififound.abroadcast.localbroadcast.BroadCastManager;
import com.lfish.lotteryssc.wififound.adapter.FileScanRvAdapter;
import com.lfish.lotteryssc.wififound.core.cmd.BroadCastFilePlay;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanImage;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanMusic;
import com.lfish.lotteryssc.wififound.filescan.filescandes.FileScanVideo;
import com.lfish.lotteryssc.wififound.wifishare.WiFiShareServer;
import com.liulishuo.filedownloader.FileDownloader;

import java.io.IOException;

/**
 * Created by shenmegui on 2017/6/5.
 */
public class WififoundAppliaction extends Application {

    private WiFiShareServer wiFiShareServer;
    public static WififoundAppliaction wififoundAppliaction;

    @Override
    public void onCreate() {
        super.onCreate();
        wififoundAppliaction = this;
        FileDownloader.init(this);
        BroadCastManager.getInstance().init(this);
        UdpBroadCastManager.getInstance().init(this);
        startWiFiShareServer();

        regiserBroadCastShowFile();
    }

    private void regiserBroadCastShowFile() {
        UdpBroadCastManager.getInstance().registerReceiver(this, new BroadCastFilePlay(), new ParamsBroadCastReceiver<BroadCastFilePlay>() {
            @Override
            public void onReveive(Context context, BroadCastFilePlay params) {
                int itemViewType = FileScanRvAdapter.getFileViewType(params.getFileWifiWeb());
                String fileAddress = params.getIpAddress();
                switch (itemViewType){
                    case FileScanRvAdapter.FILE_IMAGE:
                        String name = params.getFileWifiWeb().getPath();
                        String filepath = fileAddress + name;
                        FileScanImage.startFileScanImage(WififoundAppliaction.this,null,filepath);
                        break;
                    case FileScanRvAdapter.FILE_VIDEO:
                        String nameVideo = params.getFileWifiWeb().getPath();
                        String filepathVideo = fileAddress + nameVideo;
                        FileScanVideo.startVideoScanImage(WififoundAppliaction.this,filepathVideo);
                        break;
                    case FileScanRvAdapter.FILE_MUSIC:
                        String nameMusic = params.getFileWifiWeb().getPath();
                        String filepathMusic = fileAddress + nameMusic;
                        FileScanMusic.startFileScanMusic(WififoundAppliaction.this,filepathMusic);
                        break;
                }
            }
        });
    }

    public void startWiFiShareServer(){
        if(wiFiShareServer==null) {
            wiFiShareServer = new WiFiShareServer(8080);
        }

        try {
            if(wiFiShareServer.isAlive()){
                //服务器已经开启
            }else {
                wiFiShareServer.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
