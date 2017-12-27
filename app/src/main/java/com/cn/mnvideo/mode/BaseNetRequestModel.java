package com.cn.mnvideo.mode;

import com.cn.mnvideo.network.BaseNetRequestCallBack;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/5 17:35
 * @description: 新增最新公共网络请求
 */
public interface BaseNetRequestModel {

    void postBaseNetRequestModel(String requestString, BaseNetRequestCallBack callBack);

    void getBaseNetRequestModel(String requestString, BaseNetRequestCallBack callBack);


}
