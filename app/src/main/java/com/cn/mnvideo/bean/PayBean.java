package com.cn.mnvideo.bean;

/**
 * Created by ljr on 2018/6/2.
 */

public class PayBean {

    private String paytype;//支付方式  1 微信 2支付宝
    private String money;// 金额 元
    private String userid;// 用户id
    private String qudaoname="qudao1";//渠道名称


    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getQudaoname() {
        return qudaoname;
    }

    public void setQudaoname(String qudaoname) {
        this.qudaoname = qudaoname;
    }
}
