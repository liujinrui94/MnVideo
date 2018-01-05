//package com.cn.mnvideo.widget;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v7.widget.AppCompatRadioButton;
//import android.view.Display;
//import android.view.View;
//import android.view.WindowManager;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//
//import com.cn.mnvideo.R;
//import com.cn.mnvideo.base.AppApplication;
//import com.cn.mnvideo.base.Constant;
//import com.cn.mnvideo.bean.PayInfo;
//import com.cn.mnvideo.ui.activity.PayWebViewActivity;
//import com.cn.mnvideo.utils.GsonUtil;
//import com.cn.mnvideo.utils.NetUtil;
//
//public class CommonDialog extends Dialog implements View.OnClickListener {
//    private SharedPreferences preferences;
//    private String REID;
//    private PayInfo mPayInfo;
//
//    private RadioGroup radio_group;
//
//    public CommonDialog(Context context) {
//        super(context, R.style.CommonDialog);
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        this.setContentView(R.layout.dialog_common);
//        mPayInfo = new PayInfo();
//        mPayInfo.setTuiguangma(Constant.TUIGUANGMA);
//        mPayInfo.setUserId(AppApplication.getInstance().getBaseUserInfo().getId());
//        mPayInfo.setTerminalIp(NetUtil.getIPAddress(getContext()));
//        mPayInfo.setOutTradeNo("HP" + System.currentTimeMillis());
//        radio_group = findViewById(R.id.radio_group);
////        RadioButton radioButton1 = (RadioButton) radio_group.getChildAt(0);
////        RadioButton radioButton2 = (RadioButton) radio_group.getChildAt(1);
////        RadioButton radioButton3 = (RadioButton) radio_group.getChildAt(2);
////        radioButton1.setText((22 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
////        radioButton2.setText((39 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
////        radioButton3.setText((72 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
//        int[] titles = new int[3];
//        titles[0] = 22;
//        titles[1] = 39;
//        titles[2] = 72;
//        for(int i=0; i<titles.length; i++)
//        {
//            RadioButton btn = new RadioButton(getContext());
//            btn.setTextSize(20);
//            RadioGroup.LayoutParams btnParam = new RadioGroup.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//            btnParam.leftMargin = 10;
//            btnParam.topMargin = 2;
//            btn.setText((titles[i]- AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2)+"");
//            radio_group.addView(btn, btnParam);
//        }
//
//
//
//        WindowManager m = getWindow().getWindowManager();
//        Display d = m.getDefaultDisplay();
//        WindowManager.LayoutParams p = getWindow().getAttributes();
//        p.width = d.getWidth() - 120; //设置dialog的宽度为当前手机屏幕的宽度-100
//        getWindow().setAttributes(p);
//
//        setCanceledOnTouchOutside(false);
//        setCancelable(true);
//        findViewById(R.id.wx_pay_btn).setOnClickListener(this);
//        findViewById(R.id.albb_pay_btn).setOnClickListener(this);
//        findViewById(R.id.wx_sm_pay_btn).setOnClickListener(this);
//
//
//
//        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup radioGroup, int i) {
//                AppCompatRadioButton radioButton = (AppCompatRadioButton) radioGroup.getChildAt(i);
//                mPayInfo.setMoney(Long.parseLong(radioButton.getText().toString()));
//            }
//        });
//
//
//
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.wx_pay_btn:
//                mPayInfo.setPayWay("WX");
//                break;
//            case R.id.albb_pay_btn:
//                mPayInfo.setPayWay("ALI");
//                break;
//            case R.id.wx_sm_pay_btn:
//                mPayInfo.setPayWay("WX_SCAN");
//                break;
//        }
//        Intent intent = new Intent(getContext(), PayWebViewActivity.class);
//        intent.putExtra("json", Constant.PAY + GsonUtil.BeanToencode(mPayInfo));
//        getContext().startActivity(intent);
//        dismiss();
//    }
//
//}