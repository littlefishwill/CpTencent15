package com.lfish.lotteryssc.wififound.filescan.filescandes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.lfish.lotteryssc.R;

import java.io.IOException;

import co.mobiwise.library.MusicPlayerView;

/**
 * Created by shenmegui on 2017/6/20.
 */
public class FileScanMusic extends Activity implements MediaPlayer.OnPreparedListener {
    private static String loadPath;
    private MusicPlayerView mpv;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filescan_music);
        mpv = (MusicPlayerView) findViewById(R.id.file_scan_mpv);

        mpv.setButtonColor(Color.parseColor("#70F5F6F7"));
        mpv.setCoverDrawable(R.drawable.file_ico_music);
        mpv.setProgressEmptyColor(Color.GRAY);
        mpv.setProgressLoadedColor(Color.parseColor("#105B2D"));
        mpv.setTimeColor(Color.WHITE);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(loadPath);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void startFileScanMusic(Context context, String loadPath){
        FileScanMusic.loadPath = loadPath;
        Intent intent = new Intent(context,FileScanMusic.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        int duration = mp.getDuration();
        mpv.setMax(duration/1000);
        mpv.start();
        mp.start();

        mpv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    mpv.stop();
                }else{
                    mediaPlayer.start();
                    mpv.start();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mpv.stop();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null){
            mediaPlayer.release();
        }
    }
}
