package com.lfish.lotteryssc.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.lfish.lotteryssc.R;

/**
 * 创建自定义的dialog，主要学习其实现原理
 * Created by chengguo on 2016/3/22.
 */
public class ShowRadomDialog extends Dialog {

    private Button yes;//确定按钮
    private TextView titleTv;//消息标题文本
    private TextView qsView;//消息提示文本

    //确定文本和取消文本的显示内容
    private String yesStr, noStr,coStr,numberStr;

    private onNoOnclickListener noOnclickListener;//取消按钮被点击了的监听器
    private onYesOnclickListener yesOnclickListener;//确定按钮被点击了的监听器


    public ShowRadomDialog(Context context, String conStr,String numberStr) {
        super(context, R.style.MyDialog);
        this.coStr = conStr;
        this.numberStr = numberStr;
    }

    /**
     * 设置取消按钮的显示内容和监听
     *
     * @param str
     * @param onNoOnclickListener
     */
    public void setNoOnclickListener(String str, onNoOnclickListener onNoOnclickListener) {
        if (str != null) {
            noStr = str;
        }
        this.noOnclickListener = onNoOnclickListener;
    }

    /**
     * 设置确定按钮的显示内容和监听
     *
     * @param str
     * @param onYesOnclickListener
     */
    public void setYesOnclickListener(String str, onYesOnclickListener onYesOnclickListener) {
        if (str != null) {
            yesStr = str;
        }
        this.yesOnclickListener = onYesOnclickListener;
    }

    public ShowRadomDialog(Context context) {
        super(context, R.style.MyDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_showradom);
        //按空白处不能取消动画
        setCanceledOnTouchOutside(false);

        //初始化界面控件
        initView();
        //初始化界面数据
        initData();
        //初始化界面控件的事件
        initEvent();

    }

    /**
     * 初始化界面的确定和取消监听器
     */
    private void initEvent() {
        //设置确定按钮被点击后，向外界提供监听
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (yesOnclickListener != null) {
                    yesOnclickListener.onYesClick();
                }
            }
        });
        //设置取消按钮被点击后，向外界提供监听

    }

    /**
     * 初始化界面控件的显示数据
     */
    private void initData() {
        //如果用户自定了title和message

        //如果设置按钮的文字
        if (yesStr != null) {
            yes.setText(yesStr);
        }

        if(coStr!=null){
            titleTv.setText(coStr);
            qsView.setText(numberStr);

        }

    }

    /**
     * 初始化界面控件
     */
    private void initView() {
        yes = (Button) findViewById(R.id.yes);
        titleTv = (TextView) findViewById(R.id.title);
        qsView = (TextView) findViewById(R.id.qs);

    }



    /**
     * 设置确定按钮和取消被点击的接口
     */
    public interface onYesOnclickListener {
        public void onYesClick();
    }

    public interface onNoOnclickListener {
        public void onNoClick();
    }
}
