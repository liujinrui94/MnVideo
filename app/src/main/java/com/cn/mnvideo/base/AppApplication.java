package com.cn.mnvideo.base;

import android.app.Application;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cn.mnvideo.utils.AppLogger;

import java.util.Stack;

import cn.jpush.android.api.JPushInterface;

public class AppApplication extends Application {

    public static AppApplication instance;

    public Stack<AppCompatActivity> allActivity = new Stack<>();


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
//            if (null != allActivitys.get(i) && !(allActivitys.get(i) instanceof LoginActivity)) {
//                allActivitys.get(i).finish();
//            }

            if (null != allActivity.get(i)) {
                allActivity.get(i).finish();
            }
        }
        allActivity.clear();
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

}