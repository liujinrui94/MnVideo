package com.cn.mnvideo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.Login;
import com.cn.mnvideo.bean.UserInfo;
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.utils.GsonUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/21 22:18
 * @description:
 */
public class SplashActivity extends BaseActivity implements NetRequestView {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new BaseNetRetRequestPresenter(this).GetNetRetRequest();
    }

    @Override
    public void showCordError(String msg, int sign) {

    }

    @Override
    public String getPostJsonString() {
        Login login = new Login();
        login.setUserId(JPushInterface.getRegistrationID(this));
        login.setTuiguangma(Constant.TUIGUANGMA);
        return Constant.IP + Constant.LOGIN + GsonUtil.BeanToencode(login);
    }

    @Override
    public void NetInfoResponse(String data, int sign) {
        AppApplication.getInstance().setBaseUserInfo(GsonUtil.getInstance().fromJson(data, UserInfo.class));
        startActivity(new Intent(this, TabMainActivity.class));
        finish();
    }

    @Override
    public int sign() {
        return 0;
    }
}
