package com.cn.mnvideo.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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

import com.bumptech.glide.Glide;
import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.PayBean;
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
    private PayBean mpayBean;

    private ImageView imageView;

    private String[] url = {
            "chong/1j.jpg",
            "chong/2j.jpg",
            "chong/3j.jpg",
            "chong/4j.jpg",
            "chong/5j.jpg",
            "chong/6j.jpg",
            "chong/7j.jpg",
            "chong/7j.jpg"};

    private String[] money = {"39", "20", "28", "25", "20", "22", "66"};


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
        mpayBean = new PayBean();
        //以view的方式引入，然后回调activity方法，setContentView，实现自定义布局
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_common, null);
        setContentView(view);
        imageView = view.findViewById(R.id.common_dialog_iv);
        Window dialogWindow = getWindow();
        WindowManager manager = ((Activity) context).getWindowManager();
        WindowManager.LayoutParams params = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        dialogWindow.setGravity(Gravity.CENTER);//设置对话框位置
        Display d = manager.getDefaultDisplay(); // 获取屏幕宽、高度
        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.65，根据实际情况调整
        dialogWindow.setAttributes(params);
        Glide.with(context)
                .load(Constant.INMAGE_IP + url[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()])
                .override(params.width, 200) // resizes the image to these dimensions (in pixel). does not respect aspect ratio
                .into(imageView);
        view.findViewById(R.id.wx_pay_btn).setOnClickListener(this);
        view.findViewById(R.id.albb_pay_btn).setOnClickListener(this);
        view.findViewById(R.id.wx_sm_pay_btn).setOnClickListener(this);
        //radiobutton的初始化
        RadioGroup groupBroadcast = (RadioGroup) view.findViewById(R.id.radio_group);
        rbtn1 = (RadioButton) groupBroadcast.getChildAt(0);
        rbtn2 = (RadioButton) groupBroadcast.getChildAt(1);
        rbtn3 = (RadioButton) groupBroadcast.getChildAt(2);

        if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() > 0) {
            rbtn1.setVisibility(View.VISIBLE);
            rbtn2.setVisibility(View.GONE);
            rbtn3.setVisibility(View.GONE);
        } else {
            rbtn1.setVisibility(View.GONE);
            rbtn2.setVisibility(View.VISIBLE);
            rbtn3.setVisibility(View.VISIBLE);
        }
        rbtn1.setText(Html.fromHtml("<font>全站观看</font>    <font color='red'>           ￥" + money[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()] + "元</font>"));
        rbtn2.setText(Html.fromHtml("<font>全站</font>    <font color='red'>包月观看      ￥" + AppApplication.getInstance().getBaseUserInfo().getScbn() + "元</font>"));
        rbtn3.setText(Html.fromHtml("<font>全站</font>    <font color='red'>包年观看      ￥" + AppApplication.getInstance().getBaseUserInfo().getScby() + "元</font>"));
        rbtn2.performClick();
        mpayBean.setMoney(money[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()]);
        rbtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mpayBean.setMoney(AppApplication.getInstance().getBaseUserInfo().getScbn());
            }
        });

        rbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                mpayBean.setMoney(AppApplication.getInstance().getBaseUserInfo().getScby());
            }
        });
//        groupBroadcast.setOnCheckedChangeListener(listener);
        //设置dialog大小，这里是一个小赠送，模块好的控件大小设置
    }

//    //监听接口
//    private RadioGroup.OnCheckedChangeListener listener = new RadioGroup.OnCheckedChangeListener() {
//        @Override
//        public void onCheckedChanged(RadioGroup group, int checkedId) {
//            RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
//
//            mPayInfo.setMoney(Long.parseLong(radioButton.getText().toString()));
//        }
//    };

    @Override
    public void onClick(View view) {

        mpayBean.setQudaoname(Constant.TUIGUANGMA);
        mpayBean.setUserid(AppApplication.getInstance().getBaseUserInfo().getId());
        mpayBean.setUserid("1");
//        mPayInfo.setTerminalIp(NetUtil.getIPAddress(getContext()));
//        mPayInfo.setOutTradeNo("HP" + System.currentTimeMillis());
        if (null == mpayBean.getMoney()) {
            ToastUtils.getInstance().showShortToast("套餐不能为空");
            return;
        }
        switch (view.getId()) {
            case R.id.wx_pay_btn:
                mpayBean.setPaytype("1");
                break;
            case R.id.albb_pay_btn:
                mpayBean.setPaytype("2");
                break;
            case R.id.wx_sm_pay_btn:
//                mPayInfo.setPayWay("WX_SCAN");
                break;
        }
        Intent intent = new Intent(getContext(), PayWebViewActivity.class);
        intent.putExtra("json", Constant.PAY + GsonUtil.BeanToencode(mpayBean));
        getContext().startActivity(intent);
        dismiss();
    }
}