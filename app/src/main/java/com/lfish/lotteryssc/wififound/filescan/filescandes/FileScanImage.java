package com.lfish.lotteryssc.wififound.filescan.filescandes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.bm.library.Info;
import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.lfish.lotteryssc.R;

/**
 * Created by shenmegui on 2017/6/20.
 */
public class FileScanImage extends Activity {
    private static ImageView imageViewCache;
    private static String loadPath;
    private PhotoView photoView;
    private Info imageViewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filescan_img);
        photoView = (PhotoView) findViewById(R.id.pv_imagedes_show);
        photoView.enable();
        Log.i("...","in");
        if(imageViewCache!=null) {
            imageViewInfo = PhotoView.getImageViewInfo(imageViewCache);
            photoView.setImageDrawable(photoView.getDrawable());
//            photoView.animaFrom(imageViewInfo);
            Glide.with(this).load(loadPath).into(photoView);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    photoView.animaTo(imageViewInfo, new Runnable() {
                        @Override
                        public void run() {
                            finish();
                        }
                    });
                }
            });
        }else{
            Glide.with(this).load(loadPath).into(photoView);
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    public static void startFileScanImage(Context context,ImageView imageView, String loadPath){
        imageViewCache = imageView;
        FileScanImage.loadPath = loadPath;
        Intent intent = new Intent(context,FileScanImage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


}
