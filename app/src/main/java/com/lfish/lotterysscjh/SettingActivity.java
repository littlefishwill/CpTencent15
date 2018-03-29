package com.lfish.lotterysscjh;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.lfish.lotterysscjh.adapter.HistoryAdapter;
import com.lfish.lotterysscjh.dao.YYEnter;
import com.lfish.lotterysscjh.wififound.WifiMainActivity;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private Spinner spinner;
    private List<YYEnter> yyEnters;
    private Switch aSwitch;
    private SharedPreferences sp;

    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ativity_setting);
        spinner = (Spinner) findViewById(R.id.sh_jhdy);
        aSwitch = (Switch) findViewById(R.id.sh_ts);
        yyEnters = GoodsResultActivity.getYyEnters();
        sp = getSharedPreferences("sp", Context.MODE_PRIVATE);

        spinner.setAdapter(new HistoryAdapter(this,yyEnters));

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
                Toast.makeText(SettingActivity.this, "已经为您订阅了"+yyEnters.get(position).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        aSwitch.setChecked(sp.getBoolean("ts",true));
        spinner.setSelection(sp.getInt("dy",0));
        findViewById(R.id.rl_wifi).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, WifiMainActivity.class);
                startActivity(intent);
            }
        });

    }
}
