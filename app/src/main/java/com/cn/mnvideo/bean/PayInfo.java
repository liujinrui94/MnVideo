package com.cn.mnvideo.bean;

/**
 * Created by LiuJinrui on 2017/12/29.
 */

public class PayInfo {

    private String userId;
    private String reId;
    private String tuiguangma;
    private String payWay;
    //商品订单号
    private String outTradeNo;
    private String terminalIp;
    //显示的是元，实际是分
    private Long money;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReId() {
        return reId;
    }

    public void setReId(String reId) {
        this.reId = reId;
    }

    public String getTuiguangma() {
        return tuiguangma;
    }

    public void setTuiguangma(String tuiguangma) {
        this.tuiguangma = tuiguangma;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTerminalIp() {
        return terminalIp;
    }

    public void setTerminalIp(String terminalIp) {
        this.terminalIp = terminalIp;
    }

    public Long getMoney() {
        return money;
    }

    public void setMoney(Long money) {
        this.money = money;
    }
}
