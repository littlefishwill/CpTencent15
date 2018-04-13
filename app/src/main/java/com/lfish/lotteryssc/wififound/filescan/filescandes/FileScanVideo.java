package com.lfish.lotteryssc.wififound.filescan.filescandes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.lfish.lotteryssc.R;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by shenmegui on 2017/6/20.
 */
public class FileScanVideo extends AppCompatActivity {
    private static String loadPath;
    private PhotoView photoView;
    private Info imageViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filescan_video);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(loadPath.length()<1){
            return;
        }
        String[] split = loadPath.split("/");
        JCVideoPlayerStandard jcVideoPlayerStandard = (JCVideoPlayerStandard) findViewById(R.id.videoplayer);
        jcVideoPlayerStandard.setUp(loadPath
                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL, split[split.length-1]);
        jcVideoPlayerStandard.startPlayLogic();
//        jcVideoPlayerStandard.thumbImageView.setImage("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");
    }

    public static void startVideoScanImage(Context context, String loadPath){
        FileScanVideo.loadPath = loadPath;
        Intent intent = new Intent(context,FileScanVideo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
