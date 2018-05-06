package com.cn.mnvideo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljr on 2018/5/6.
 */

public class MnvideoModel {

    private String responseCode;
    private String responseMsg;

    private ArrayList<Mnvideo> info;

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    public ArrayList<Mnvideo> getMnvideoList() {
        return info;
    }

    public void setInfo(ArrayList<Mnvideo> info) {
        this.info = info;
    }
}
