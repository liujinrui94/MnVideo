package com.cn.mnvideo.network;

import android.util.Log;

import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.BaseResponseParams;
import com.cn.mnvideo.mode.BaseNetRequestModel;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;

public abstract class BaseModeImp implements BaseNetRequestModel {


    public static class PostBaseModeImp implements BaseNetRequestModel {

        @Override
        public void postBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            Log.e("Net—请求",requestString);
            OkHttpUtils.get().url( requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    Log.e("Net—返回",response);
                    BaseResponseParams baseResponseParams= GsonUtil.getInstance().fromJson(response,BaseResponseParams.class);
                    if (baseResponseParams.getResponseCode().equals(Constant.RESPONSE_SUCCESS)){
                        if (null!=baseResponseParams.getInfo()){
                            callBack.SucceedCallBack(response);
                        }else {
                            callBack.SucceedCallBack("");
                        }
                    }else {
                        callBack.CodeError(baseResponseParams.getResponseMsg());
                    }
                }
            });

        }

        @Override
        public void getBaseNetRequestModel(String requestString, final BaseNetRequestCallBack callBack) {
            AppLogger.e(requestString);
            OkHttpUtils.get().url(requestString).build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    callBack.OnNetError();
                }

                @Override
                public void onResponse(String response, int id) {
                    AppLogger.e(response);
                    JSONObject jsonObject = null;
                    JSONObject data=null;
                    try {
                        jsonObject = new JSONObject(response);
                        data = jsonObject.optJSONObject("info");
                        if (jsonObject.getString("responseCode").equals(Constant.RESPONSE_SUCCESS)) {
                            callBack.SucceedCallBack(response);
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