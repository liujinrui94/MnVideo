package com.cn.mnvideo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.cn.mnvideo.R;

public class CommonDialog extends Dialog {
    private String title;
    private String detail;
    private boolean isCenter;
    private View.OnClickListener OnNegateClickListener;
    private View.OnClickListener OnPositiveClickListener;
    private String positive;
    private String negate;

    public CommonDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_common);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);


        setCanceledOnTouchOutside(false);
        setCancelable(true);

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setDetailCenter(boolean isCenter) {
        this.isCenter = isCenter;
    }


    /**
     * 确定按钮
     */
    public void setOnPositiveListener(View.OnClickListener onPositiveClickListener) {
        this.OnPositiveClickListener = onPositiveClickListener;
    }

    /**
     * 取消按钮
     */
    public void setOnNegateListener(View.OnClickListener onNegateClickListener) {
        this.OnNegateClickListener = onNegateClickListener;
    }

    public void setNegate(String negate) {
        this.negate = negate;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }
}