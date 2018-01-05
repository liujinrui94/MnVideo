package com.cn.mnvideo.network;

import java.text.ParseException;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/6 9:06
 * @description:
 */
public interface NetRequestView {
    /**
     * @param msg 错误码信息
     */
    void showCordError(String msg,int sign);

    String getPostJsonString();

    void NetInfoResponse(String data,int sign) throws ParseException;
    int sign();

}
