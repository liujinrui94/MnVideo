package com.cn.mnvideo.network;

import android.util.Log;

import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.BaseResponseParams;
import com.cn.mnvideo.mode.BaseNetRequestModel;
import com.cn.mnvideo.utils.AppLogger;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public abstract class BaseModeImp implements BaseNetRequestModel {


    public static class PostBaseModeImp implements BaseNetRequestModel {

        @Override
        public void postBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            AppLogger.d(requestString);
            OkHttpUtils.post().url( requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    AppLogger.d(response);
                    JSONObject jsonObject = null;
                    JSONObject data=null;
                    try {
                        jsonObject = new JSONObject(response);
                        data = jsonObject.optJSONObject("info");
                        if (jsonObject.getString("responseCode").equals(Constant.RESPONSE_SUCCESS)) {
                            callBack.SucceedCallBack(data.toString());
                        } else {
                            callBack.CodeError(jsonObject.getString("responseMsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void getBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            OkHttpUtils.get().url(requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    AppLogger.d(response);
                    JSONObject jsonObject = null;
                    JSONObject data=null;
                    try {
                        jsonObject = new JSONObject(response);
                        data = jsonObject.optJSONObject("info");
                        if (jsonObject.getString("responseCode").equals(Constant.RESPONSE_SUCCESS)) {
                            callBack.SucceedCallBack(data.toString());
                        } else {
                            callBack.CodeError(jsonObject.getString("responseMsg"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

}