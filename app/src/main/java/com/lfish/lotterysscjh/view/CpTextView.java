package com.lfish.lotterysscjh.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by shenmegui on 2018/3/21.
 */
public class CpTextView extends TextView {
    public CpTextView(Context context) {
        super(context);
    }

    public CpTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CpTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CpTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
