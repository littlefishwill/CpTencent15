package com.lfish.lotterysscjh.wififound.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.lfish.lotterysscjh.R;
import com.lfish.lotterysscjh.wififound.abroadcast.localbroadcast.BroadCastManager;
import com.lfish.lotterysscjh.wififound.adapter.LinkManAdapter;
import com.lfish.lotterysscjh.wififound.core.CoreFinder;
import com.lfish.lotterysscjh.wififound.core.cmd.FindLinkManFinish;
import com.lfish.lotterysscjh.wififound.core.cmd.FindLinkManStart;
import com.lfish.lotterysscjh.wififound.core.cmd.LinkMan;
import com.lfish.lotterysscjh.wififound.filescan.FileScanActivity;

/**
 * Created by shenmegui on 2017/6/5.
 */
public class WifiLinkmanFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ListView listView;
    private LinkManAdapter linkManAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_linkman, null);
        listView = (ListView) view.findViewById(R.id.ls_linkman_list);
        linkManAdapter = new LinkManAdapter(CoreFinder.getInstance().getLinkMenResult(),getActivity());
        listView.setAdapter(linkManAdapter);

        linkManLogic();

        return view;

    }

    private void linkManLogic() {

        BroadCastManager.getInstance().registerReceiver(getActivity(), new FindLinkManFinish(), new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                linkManAdapter = new LinkManAdapter(CoreFinder.getInstance().getLinkMenResult(),getActivity());
                listView.setAdapter(linkManAdapter);
            }
        });

        BroadCastManager.getInstance().registerReceiver(getActivity(), new FindLinkManStart(), new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(linkManAdapter!=null){
                    linkManAdapter.notifyDataSetChanged();
                }
            }
        });

        CoreFinder.getInstance().init(getActivity());

        CoreFinder.getInstance().broadCastOnLine();

        listView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if(position==0){

        }else{
            LinkMan linkMan = CoreFinder.getInstance().getLinkMenResult().get(position - 1);
            FileScanActivity.OpenFileScan("http://"+linkMan.getIp()+":8080",getActivity());
        }
    }
}
