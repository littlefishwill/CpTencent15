package com.lfish.lotteryssc.wififound.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.wififound.dao.FileWifiWebDes;

/**
 * Created by shenmegui on 2017/6/6.
 */
public abstract class FileScanAdapter extends BaseAdapter {

    private FileWifiWebDes fileWifiWebDes;
    private LayoutInflater layoutInflater;
    private Context context;
    private int DIR=0,FILE_IMAGE=1,FILE_VIDEO=2,FILE_MUSIC=3,FILE_OTHER=4;

    public FileScanAdapter(FileWifiWebDes fileWifiWebDes, Context context) {
        this.fileWifiWebDes = fileWifiWebDes;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fileWifiWebDes.getWifiWebList().size();
    }

    @Override
    public Object getItem(int position) {
        return fileWifiWebDes.getWifiWebList().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.getWifiWebList().get(position);
        if(fileWifiWeb.isFile()){

        }else{
            return DIR;
        }


        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 5;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = layoutInflater.inflate(R.layout.item_filescan,null);
        }

        FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.getWifiWebList().get(position);

        ImageView ico = (ImageView) convertView.findViewById(R.id.iv_filescan_ico);
        TextView name = (TextView) convertView.findViewById(R.id.tv_filescan_name);

        if(fileWifiWeb.isFile()){
            ico.setImageResource(R.drawable.filescan_file);
        }else{
            ico.setImageResource(R.drawable.filescan_dir);
        }
        String nameWebFile = fileWifiWeb.getName();
        String filepath = getChooseIp() + "/" + nameWebFile;

        if(nameWebFile.toLowerCase().endsWith(".png") || nameWebFile.toLowerCase().endsWith(".jpg") || nameWebFile.toLowerCase().endsWith(".gif") || nameWebFile.toLowerCase().endsWith("jpeg")){
            Log.i("FileScanAdapter",filepath);
            Glide.with(context).load(filepath).into(ico);
        }

        name.setText(fileWifiWeb.getName());

        return convertView;
    }

    public abstract  String getChooseIp();
}
