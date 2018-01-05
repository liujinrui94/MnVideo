package com.cn.mnvideo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.PayInfo;
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.ui.activity.PayWebViewActivity;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.NetUtil;
import com.cn.mnvideo.utils.ToastUtils;

public class BackCommonDialog extends Dialog implements View.OnClickListener {
    private String title;
    private String detail;
    private boolean isCenter;
    private View.OnClickListener wxPay;
    private View.OnClickListener aliPay;
    private View.OnClickListener saomaliPay;

    private String positive;
    private String negate;
    private SharedPreferences preferences;
    private String REID;
    private PayInfo mPayInfo;

    public BackCommonDialog(Context context) {
        super(context, R.style.CommonDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.back_dialog_common);
        preferences = AppApplication.getInstance().getSharedPreferences(Constant.REID, Context.MODE_PRIVATE);
        REID = preferences.getString(Constant.REID, Constant.REID);
        mPayInfo = new PayInfo();
        mPayInfo.setReId(REID);
        mPayInfo.setTuiguangma(Constant.TUIGUANGMA);
        mPayInfo.setUserId(AppApplication.getInstance().getBaseUserInfo().getId());
        mPayInfo.setTerminalIp(NetUtil.getIPAddress(getContext()));
        mPayInfo.setOutTradeNo("HP" + System.currentTimeMillis());
        mPayInfo.setMoney((long) 18);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);
        setCanceledOnTouchOutside(false);
        setCancelable(true);
        findViewById(R.id.back_dialog_wx_pay_btn).setOnClickListener(this);
        findViewById(R.id.back_dialog_wx_sm_pay_btn).setOnClickListener(this);
        findViewById(R.id.back_dialog_albb_pay_btn).setOnClickListener(this);
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

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.back_dialog_wx_pay_btn:
                mPayInfo.setPayWay("WX");

                break;
            case R.id.back_dialog_wx_sm_pay_btn:
                mPayInfo.setPayWay("WX_SCAN");
                break;
            case R.id.back_dialog_albb_pay_btn:
                mPayInfo.setPayWay("ALI");

                break;


        }
        Intent intent = new Intent(getContext(), PayWebViewActivity.class);
        intent.putExtra("json", Constant.PAY+GsonUtil.BeanToencode(mPayInfo));
        getContext().startActivity(intent);
        dismiss();
    }


}