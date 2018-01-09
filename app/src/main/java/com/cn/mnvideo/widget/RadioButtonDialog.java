package com.cn.mnvideo.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.PayInfo;
import com.cn.mnvideo.ui.activity.PayWebViewActivity;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.NetUtil;
import com.cn.mnvideo.utils.ToastUtils;

public class RadioButtonDialog extends Dialog implements View.OnClickListener {

    private Context context;
    private String title;     //这里定义个title，一会可以看到是指向上面xml文件的控件title的，也就是我们可以通过这个进行动态修改title
    private AdapterView.OnItemClickListener onItemClickListener;      //这里定义了一个监听是为了实现内部的监听接口处理，从而实现代码分层管理
    private PayInfo mPayInfo;

    private ImageView imageView;

    private String[] url = {
            "chong/1j.jpg",
            "chong/2j.jpg",
            "chong/3j.jpg",
            "chong/4j.jpg",
            "chong/5j.jpg",
            "chong/6j.jpg",
            "chong/7j.jpg"};


    //可以看到两个构造器，想自定义样式的就用第二个啦
    public RadioButtonDialog(Context context) {
        super(context, R.style.Dialog);
        this.context = context;
    }

    public RadioButtonDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        if (onItemClickListener != null)
            this.onItemClickListener = onItemClickListener;
    }

    //控件的声明
    RadioButton rbtn2;
    RadioButton rbtn3;
    RadioButton rbtn1;

    private void init() {
        mPayInfo = new PayInfo();
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        setContentView(view);
        imageView = view.findViewById(R.id.common_dialog_iv);

        GlideUtils.getInstance().loadNetImage(Constant.INMAGE_IP+url[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()], imageView);

        view.findViewById(R.id.wx_pay_btn).setOnClickListener(this);
        view.findViewById(R.id.albb_pay_btn).setOnClickListener(this);
        view.findViewById(R.id.wx_sm_pay_btn).setOnClickListener(this);
        //radiobutton的初始化
        RadioGroup groupBroadcast = (RadioGroup) view.findViewById(R.id.radio_group);
        rbtn1 = (RadioButton) groupBroadcast.getChildAt(0);
        rbtn2 = (RadioButton) groupBroadcast.getChildAt(1);
        rbtn3 = (RadioButton) groupBroadcast.getChildAt(2);

        rbtn1.setText((22 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
        rbtn2.setText((39 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
        rbtn3.setText((72 - AppApplication.getInstance().getBaseUserInfo().getMemberlevel() * 2) + "");
        rbtn2.performClick();
        mPayInfo.setMoney(Long.parseLong(rbtn2.getText().toString()));
        groupBroadcast.setOnCheckedChangeListener(listener);
        //设置dialog大小，这里是一个小赠送，模块好的控件大小设置
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(params);

    }

    //监听接口
    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            RadioButton radioButton = (RadioButton) group.findViewById(checkedId);

            mPayInfo.setMoney(Long.parseLong(radioButton.getText().toString()));
        }
    };

    @Override
    public void onClick(View view) {

        mPayInfo.setTuiguangma(Constant.TUIGUANGMA);
        mPayInfo.setUserId(AppApplication.getInstance().getBaseUserInfo().getId());
        mPayInfo.setTerminalIp(NetUtil.getIPAddress(getContext()));
        mPayInfo.setOutTradeNo("HP" + System.currentTimeMillis());
        if (null == mPayInfo.getMoney()) {
           ToastUtils.getInstance().showShortToast("套餐不能为空");
           return;
        }
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
        Intent intent = new Intent(getContext(), PayWebViewActivity.class);
        intent.putExtra("json", Constant.PAY + GsonUtil.BeanToencode(mPayInfo));
        getContext().startActivity(intent);
        dismiss();
    }
}