package com.cn.mnvideo.ui.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.Login;
import com.cn.mnvideo.bean.UserInfo;
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

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
        Constant.TUIGUANGMA = getResources().getString(R.string.TGM);
        if (TextUtils.isEmpty(JPushInterface.getRegistrationID(this))) {
            Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
            return;
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                new BaseNetRetRequestPresenter(SplashActivity.this).GetNetRetRequest();
            }
        }, 1000);

    }


    @Override
    public void showCordError(String msg, int sign) {
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public String getPostJsonString() {
        Login login = new Login();
//        login.setUserId(JPushInterface.getRegistrationID(this));
        login.setName(JPushInterface.getRegistrationID(this));
        login.setTuiguangma(Constant.TUIGUANGMA);
        Log.e("AAAA",Constant.IP + Constant.LOGIN + GsonUtil.BeanToencode(login));
        return Constant.IP + Constant.LOGIN + GsonUtil.BeanToencode(login);
    }

    @Override
    public void NetInfoResponse(String data, int sign) {
        AppLogger.e(data);
        UserInfo userInfo = GsonUtil.getInstance().fromJson(data, UserInfo.class);
//        if (System.currentTimeMillis() > stringToLong(userInfo.getEndTime(), "yyyy-MM-dd HH:mm:ss")) {
//            userInfo.setMemberlevel(0);
//        }
        AppLogger.e(userInfo.toString());
        AppApplication.getInstance().setBaseUserInfo(userInfo);
        startActivity(new Intent(this, TabMainActivity.class));
        finish();
    }

    @Override
    public int sign() {
        return 0;
    }


    public static long stringToLong(String strTime, String formatType) {
        Date date = null; // String类型转成date类型
        date = stringToDate(strTime, formatType);
        if (date == null) {
            return 0;
        } else {
            long currentTime = dateToLong(date); // date类型转成long类型
            return currentTime;
        }
    }

    public static Date stringToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        Date date = null;
        try {
            date = formatter.parse(strTime);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static long dateToLong(Date date) {
        return date.getTime();
    }
}
