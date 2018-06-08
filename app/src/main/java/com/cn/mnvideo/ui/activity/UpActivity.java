package com.cn.mnvideo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.PayInfo;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.NetUtil;

/**
 * Created by LiuJinrui on 2017/12/30.
 */

public class UpActivity extends AppCompatActivity implements View.OnClickListener {


    private SharedPreferences preferences;
    private String REID;
    private PayInfo mPayInfo;

    private RadioGroup radio_group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_common);
        preferences = AppApplication.getInstance().getSharedPreferences(Constant.REID, Context.MODE_PRIVATE);
        REID = preferences.getString(Constant.REID, Constant.REID);

        radio_group = findViewById(R.id.radio_group);
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
        getWindow().setAttributes(p);

        findViewById(R.id.wx_pay_btn).setOnClickListener(this);
        findViewById(R.id.albb_pay_btn).setOnClickListener(this);
        findViewById(R.id.wx_sm_pay_btn).setOnClickListener(this);



        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
               RadioButton radioButton = (RadioButton) radioGroup.getChildAt(i);
                mPayInfo.setMoney(Long.parseLong(radioButton.getText().toString()));
            }
        });

        RadioButton radioButton1 = (RadioButton) radio_group.getChildAt(0);
        RadioButton radioButton2 = (RadioButton) radio_group.getChildAt(1);
        RadioButton radioButton3 = (RadioButton) radio_group.getChildAt(2);
        radioButton1.setText((22 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "元");
        radioButton2.setText((39 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "元");
        radioButton3.setText((72 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "元");

    }


    @Override
    public void onClick(View view) {

        mPayInfo = new PayInfo();
        mPayInfo.setReId(REID);
        mPayInfo.setTuiguangma(Constant.TUIGUANGMA);
        mPayInfo.setUserId(AppApplication.getInstance().getBaseUserInfo().getUserId());
        mPayInfo.setTerminalIp(NetUtil.getIPAddress(this));
        mPayInfo.setOutTradeNo("HP" + System.currentTimeMillis());

        switch (view.getId()) {
            case R.id.wx_pay_btn:
                mPayInfo.setPayWay("WX");
                break;
            case R.id.albb_pay_btn:
                mPayInfo.setPayWay("ALI");
                break;
            case R.id.wx_sm_pay_btn:
                mPayInfo.setPayWay("WX_SCAN");
                break;
        }
        Intent intent = new Intent(this, PayWebViewActivity.class);
        intent.putExtra("json", Constant.PAY + GsonUtil.BeanToencode(mPayInfo));
       startActivity(intent);
    }
}
