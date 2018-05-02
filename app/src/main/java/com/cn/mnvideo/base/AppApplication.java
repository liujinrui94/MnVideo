package com.cn.mnvideo.base;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;

import com.cn.mnvideo.bean.UserInfo;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.utils.AppLogger;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

public class AppApplication extends Application implements NetRequestView {

    public static AppApplication instance;

    public Stack<AppCompatActivity> allActivity = new Stack<>();

    private UserInfo BaseUserInfo=new UserInfo();

    public AppApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        JPushInterface.init(this);
        AppLogger.d(JPushInterface.getRegistrationID(this));

    }

    public static AppApplication getInstance() {
        return instance;
    }

    public void addActivity(AppCompatActivity activity) {
        if (allActivity == null) {
            allActivity = new Stack<AppCompatActivity>();
        }
        allActivity.add(activity);
    }

    public void finishActivity(AppCompatActivity activity) {
        if (activity != null) {
            allActivity.remove(activity);
        }
    }

    public void finishAllActivity() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
    }

    public void finishActivitys() {
        for (int i = 0, size = allActivity.size(); i < size; i++) {
            if (allActivity.size() == i) {
                break;
            }
            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
    }

    public UserInfo getBaseUserInfo() {
        return BaseUserInfo;
    }

    public void setBaseUserInfo(UserInfo baseUserInfo) {
        BaseUserInfo = baseUserInfo;
    }

    public void AppExit() {
        try {
            finishAllActivity();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    @Override
    public void showCordError(String msg, int sign) {

    }

    @Override
    public String getPostJsonString() {
        return null;
    }

    @Override
    public void NetInfoResponse(String data, int sign) {

    }

    @Override
    public int sign() {
        return 0;
    }
}