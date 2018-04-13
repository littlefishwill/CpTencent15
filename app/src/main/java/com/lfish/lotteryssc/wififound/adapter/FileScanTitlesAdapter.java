package com.lfish.lotteryssc.wififound.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.wififound.dao.FileWifiWebDes;

import java.util.List;

/**
 * Created by shenmegui on 2017/6/7.
 */
public class FileScanTitlesAdapter extends RecyclerView.Adapter<FileScanTitlesAdapter.FileScanTitlesViewHolder> {

    private List<FileWifiWebDes.FileWifiWeb> fileWifiWebList;
    private LayoutInflater inflater;
    private OnItemClickListener onItemClickListener;

    public FileScanTitlesAdapter(List<FileWifiWebDes.FileWifiWeb> fileWifiWebList, Context context) {
        this.fileWifiWebList = fileWifiWebList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public FileScanTitlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        FileScanTitlesViewHolder fileScanTitlesViewHolder = new FileScanTitlesViewHolder(inflater.inflate(R.layout.item_filescan_title, null));
        return fileScanTitlesViewHolder;
    }

    @Override
    public void onBindViewHolder(FileScanTitlesViewHolder holder, final int position) {
        FileWifiWebDes.FileWifiWeb fileWifiWeb = fileWifiWebList.get(position);
        holder.dirName.setText(fileWifiWeb.getName());
        holder.dirName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fileWifiWebList.size();
    }

    public class  FileScanTitlesViewHolder extends RecyclerView.ViewHolder {
        public Button dirName;
        public View view;

        public FileScanTitlesViewHolder(View itemView) {
            super(itemView);
            dirName = (Button) itemView.findViewById(R.id.bt_filescan_tilte_des);
            view = itemView;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(int posation);
    }

    public void setFileWifiWebList(List<FileWifiWebDes.FileWifiWeb> fileWifiWebList) {
        this.fileWifiWebList = fileWifiWebList;
    }
}
