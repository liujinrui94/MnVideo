package com.cn.mnvideo.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/28 11:13
 * @description:
 */
public class PingLunInfo  {


    private String content;
    private String createTime;
    private String userName;
    private String headImg;
    private String uid;


    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
