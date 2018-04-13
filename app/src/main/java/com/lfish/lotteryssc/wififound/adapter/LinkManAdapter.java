package com.lfish.lotteryssc.wififound.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.wififound.core.CoreFinder;
import com.lfish.lotteryssc.wififound.core.cmd.LinkMan;

import java.util.List;

/**
 * Created by shenmegui on 2017/6/5.
 */
public class LinkManAdapter extends BaseAdapter {

    private List<LinkMan> linkMens;
    private LayoutInflater inflater;

    public LinkManAdapter(List<LinkMan> linkMens, Context context) {
        this.linkMens = linkMens;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return linkMens.size()+1;
    }

    @Override
    public Object getItem(int position) {
        return linkMens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_linkman,null);
        }

        TextView name = (TextView) convertView.findViewById(R.id.tv_linkman_name);

        if(position==0){
            if(CoreFinder.getInstance().isFind()){
                name.setText("正在查找局域网设备...");
            }else {
                name.setText("发现Wifi 设备（" + linkMens.size() + "个）");
            }
        }else {
            LinkMan linkMan = linkMens.get(position-1);
            name.setText(linkMan.getIp());
        }

        return convertView;
    }
}
