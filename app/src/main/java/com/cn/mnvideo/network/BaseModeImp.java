package com.cn.mnvideo.network;

import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.BaseResponseParams;
import com.cn.mnvideo.mode.BaseNetRequestModel;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public abstract class BaseModeImp implements BaseNetRequestModel {


    public static class PostBaseModeImp implements BaseNetRequestModel {

        @Override
        public void postBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            OkHttpUtils.post().url( requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    BaseResponseParams info = new Gson().fromJson(response,
                            BaseResponseParams.class);
                    if (info.getRespondeCode().equals(Constant.RESPONSE_SUCCESS)) {
                        callBack.SucceedCallBack(info.getInfo());
                    } else {
                        callBack.CodeError(info.getResponseMsg());
                    }

                }
            });

        }
    }

}