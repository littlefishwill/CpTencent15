package com.lfish.lotteryssc.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import com.lfish.lotteryssc.GoodsResultActivity;
import com.lfish.lotteryssc.R;
import com.lfish.lotteryssc.adapter.HistoryAdapter;
import com.lfish.lotteryssc.dao.YYEnter;
import com.lfish.lotteryssc.wififound.WifiMainActivity;
import java.util.List;

/**
 * Created by shenmegui on 2018/4/12.
 */
public class UsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.ativity_setting,null);

        spinner = (Spinner) view.findViewById(R.id.sh_jhdy);
        aSwitch = (Switch) view.findViewById(R.id.sh_ts);
        yyEnters = GoodsResultActivity.getYyEnters();
        sp = getActivity().getSharedPreferences("sp", Context.MODE_PRIVATE);

        spinner.setAdapter(new HistoryAdapter(getActivity(),yyEnters));

        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sp.edit().putBoolean("ts",isChecked).commit();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp.edit().putInt("dy",position).commit();
                Toast.makeText(getActivity(), "已经为您订阅了"+yyEnters.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aSwitch.setChecked(sp.getBoolean("ts",true));
        spinner.setSelection(sp.getInt("dy",0));
        view.findViewById(R.id.rl_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WifiMainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    private Spinner spinner;
    private List<YYEnter> yyEnters;
    private Switch aSwitch;
    private SharedPreferences sp;


}
