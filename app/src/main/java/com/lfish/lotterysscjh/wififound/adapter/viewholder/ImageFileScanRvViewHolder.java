package com.lfish.lotterysscjh.wififound.adapter.viewholder;

import android.view.View;
import com.bm.library.PhotoView;
import com.lfish.lotterysscjh.R;

/**
 * Created by shenmegui on 2017/6/19.
 */
public class ImageFileScanRvViewHolder extends FileScanRvViewHolder {
    public ImageFileScanRvViewHolder(View itemView) {
        super(itemView);
        PhotoView photoView = (PhotoView) itemView.findViewById(R.id.iv_filescan_ico);
        photoView.enable();
        imagedes =  photoView;

    }
}
