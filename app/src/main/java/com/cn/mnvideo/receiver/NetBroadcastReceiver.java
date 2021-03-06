package com.cn.mnvideo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.cn.mnvideo.mode.NetEventInterface;
import com.cn.mnvideo.ui.activity.MainActivity;
import com.cn.mnvideo.utils.NetUtil;

import cn.jpush.android.api.JPushInterface;

public class NetBroadcastReceiver extends BroadcastReceiver {
    private NetEventInterface netEvent;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            //检查网络状态的类型
            int netWorkState = NetUtil.getNetWorkState(context);
            if (netEvent != null)
                // 接口回传网络状态的类型
                netEvent.onNetChange(netWorkState);
        }
    }

    public void setNetEvent(NetEventInterface netEvent) {
        this.netEvent = netEvent;
    }



}