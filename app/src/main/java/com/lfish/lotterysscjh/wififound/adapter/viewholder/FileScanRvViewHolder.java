package com.lfish.lotterysscjh.wififound.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lfish.lotterysscjh.R;


/**
 * Created by shenmegui on 2017/6/19.
 */
public class FileScanRvViewHolder extends RecyclerView.ViewHolder {
    public FileScanRvViewHolder(View itemView) {
        super(itemView);
        this.view = itemView;
        imagedes = (ImageView) itemView.findViewById(R.id.iv_filescan_ico);
        name = (TextView) itemView.findViewById(R.id.tv_filescan_name);
        imageDownLoad = (ImageView) itemView.findViewById(R.id.iv_filescan_download);
    }

    public ImageView imagedes,imageDownLoad;
    public TextView name;
    public View view;

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ImageView getImagedes() {
        return imagedes;
    }

    public void setImagedes(ImageView imagedes) {
        this.imagedes = imagedes;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
