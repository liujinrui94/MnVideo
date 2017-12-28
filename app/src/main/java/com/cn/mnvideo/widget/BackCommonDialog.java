package com.cn.mnvideo.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.cn.mnvideo.R;

public class BackCommonDialog extends Dialog {
    private String title;
    private String detail;
    private boolean isCenter;
    private View.OnClickListener wxPay;
    private View.OnClickListener aliPay;
    private View.OnClickListener saomaliPay;

    private String positive;
    private String negate;

    public BackCommonDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.back_dialog_common);

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


    public void setWxPay(View.OnClickListener wxPay) {
        this.wxPay = wxPay;
    }

    public void setAliPay(View.OnClickListener aliPay) {
        this.aliPay = aliPay;
    }

    public void setSaomaliPay(View.OnClickListener saomaliPay) {
        this.saomaliPay = saomaliPay;
    }

    public void setNegate(String negate) {
        this.negate = negate;
    }

    public void setPositive(String positive) {
        this.positive = positive;
    }
}