package com.lfish.lotterysscjh.wififound.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lfish.lotterysscjh.R;
import com.lfish.lotterysscjh.wififound.adapter.viewholder.FileScanRvViewHolder;
import com.lfish.lotterysscjh.wififound.dao.FileWifiWebDes;
import com.lfish.lotterysscjh.wififound.wifishare.WiFiShareServerConfig;

/**
 * Created by shenmegui on 2017/6/6.
 */
public abstract class FileScanRvAdapter extends RecyclerView.Adapter<FileScanRvViewHolder> {

    private FileWifiWebDes fileWifiWebDes;
    private LayoutInflater layoutInflater;
    private Context context;
    public static final int DIR=0,FILE_IMAGE=1,FILE_VIDEO=2,FILE_MUSIC=3,FILE_OTHER=4,FILE_APK=5;

    public FileScanRvAdapter(FileWifiWebDes fileWifiWebDes, Context context) {
        this.fileWifiWebDes = fileWifiWebDes;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public void onBindViewHolder(final FileScanRvViewHolder holder, final int position) {

        FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.getWifiWebList().get(position);

        String filepath = getChooseIp() + fileWifiWeb.getPath();

        int itemViewType = getItemViewType(position);
        holder.imageDownLoad.setVisibility(View.VISIBLE);
        switch (itemViewType) {
            case FILE_IMAGE:
                Glide.with(context).load(filepath).into(holder.imagedes);
                break;
            case FILE_VIDEO:
                holder.imagedes.setImageResource(R.drawable.file_ico_video);
                break;
            case FILE_MUSIC:
                holder.imagedes.setImageResource(R.drawable.file_ico_music);
                break;
            case DIR:
                holder.imagedes.setImageResource(R.drawable.filescan_dir);
                holder.imageDownLoad.setVisibility(View.GONE);
                break;
            case FILE_OTHER:
                holder.imagedes.setImageResource(R.drawable.filescan_file);
                break;
            case FILE_APK:
                holder.imagedes.setImageResource(R.mipmap.logo8);
                break;
        }

        holder.imagedes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDesImageClick((ImageView) v,position);
            }
        });

        holder.imageDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDownloadClick(position);
            }
        });

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(position,holder);
            }
        });

        holder.name.setText(fileWifiWeb.getName());

    }

    @Override
    public FileScanRvViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new FileScanRvViewHolder(layoutInflater.inflate(R.layout.item_filescan,null));
    }

    @Override
    public int getItemViewType(int position) {
        FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebDes.getWifiWebList().get(position);
        if(fileWifiWeb.isFile()){
            return getFileViewType(fileWifiWeb);
        }else{
            return DIR;
        }
    }

    @Override
    public int getItemCount() {
        return fileWifiWebDes.getWifiWebList().size();
    }

    /**
     * 获取文件类型
     * @param fileWifiWeb
     * @return
     */
    public static int getFileViewType(FileWifiWebDes.FileWifiWeb fileWifiWeb) {
        String name = fileWifiWeb.getName().toLowerCase();

        for(String image: WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_IMAGE_ARRAY){
            if(name.endsWith(image)){
                return FILE_IMAGE;
            }
        }

        for(String video: WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_VIDEO_ARRAY){
            if(name.endsWith(video)){
                return FILE_VIDEO;
            }
        }

        for(String music: WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_MUSIC_ARRAY){
            if(name.endsWith(music)){
                return FILE_MUSIC;
            }
        }

        for(String music: WiFiShareServerConfig.HOST_PARAM_FILE_FILETYPE_APK_ARRAY){
            if(name.endsWith(music)){
                return FILE_APK;
            }
        }

        return FILE_OTHER;
    }

    public abstract  String getChooseIp();

    public abstract  void onDesImageClick(ImageView imageView,int posation);

    public abstract  void onDownloadClick(int posation);

    public abstract  void onItemClick(int posation,FileScanRvViewHolder fileScanRvViewHolder);

    public FileWifiWebDes getFileWifiWebDes() {
        return fileWifiWebDes;
    }

    public void setFileWifiWebDes(FileWifiWebDes fileWifiWebDes) {
        this.fileWifiWebDes = fileWifiWebDes;
    }
}
