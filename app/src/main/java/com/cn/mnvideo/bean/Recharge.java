package com.cn.mnvideo.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/28 14:21
 * @description:
 */
public class Recharge {

    String userId;

    String reId;
    String memberlevel;
    String tuiguangma;

    public Recharge(String userId, String reId, String memberlevel, String tuiguangma) {
        this.userId = userId;
        this.reId = reId;
        this.memberlevel = memberlevel;
        this.tuiguangma = tuiguangma;
    }

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

    public String getMemberlevel() {
        return memberlevel;
    }

    public void setMemberlevel(String memberlevel) {
        this.memberlevel = memberlevel;
    }

    public String getTuiguangma() {
        return tuiguangma;
    }

    public void setTuiguangma(String tuiguangma) {
        this.tuiguangma = tuiguangma;
    }
}
