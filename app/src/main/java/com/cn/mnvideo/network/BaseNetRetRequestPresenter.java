package com.cn.mnvideo.network;


import com.cn.mnvideo.utils.AppLogger;

import java.text.ParseException;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/6 8:49
 * @description:
 */
public class BaseNetRetRequestPresenter {


    private BaseModeImp.PostBaseModeImp baseModeImp;
    private NetRequestView netRequestView;

    public BaseNetRetRequestPresenter(NetRequestView netRequestView) {
        this.netRequestView = netRequestView;
        baseModeImp = new BaseModeImp.PostBaseModeImp();
    }

    public void PostNetRetRequest() {
        baseModeImp.postBaseNetRequestModel(netRequestView.getPostJsonString(), new BaseNetRequestCallBack() {
            @Override
            public void SucceedCallBack(String data) {
                try {
                    netRequestView.NetInfoResponse(data, netRequestView.sign());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnNetError() {
                AppLogger.e("访问异常"+netRequestView.getPostJsonString());
                netRequestView.showCordError("网络连接异常,请检查网络!",0);
            }

            @Override
            public void CodeError(String msg) {
                netRequestView.showCordError(msg,netRequestView.sign());
            }
        });
    }
    public void GetNetRetRequest() {
        baseModeImp.postBaseNetRequestModel(netRequestView.getPostJsonString(), new BaseNetRequestCallBack() {
            @Override
            public void SucceedCallBack(String data) {
                try {
                    netRequestView.NetInfoResponse(data, netRequestView.sign());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void OnNetError() {
                AppLogger.e("访问异常"+netRequestView.getPostJsonString());
                netRequestView.showCordError("网络连接异常,请检查网络!",0);
            }

            @Override
            public void CodeError(String msg) {
                netRequestView.showCordError(msg,netRequestView.sign());
            }
        });
    }

}
