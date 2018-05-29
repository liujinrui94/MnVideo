package com.cn.mnvideo.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/26 22:57
 * @description:
 */
public class UserInfo  {

    private String name;
    private String password;
    private int memberlevel;
    private String headImg;

    private String id;

    private String endTime;

    private long scbn;
    private long scby;

    public long getScbn() {
        return scbn;
    }

    public void setScbn(long scbn) {
        this.scbn = scbn;
    }

    public long getScby() {
        return scby;
    }

    public void setScby(long scby) {
        this.scby = scby;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMemberlevel() {
        return memberlevel;
    }

    public void setMemberlevel(int memberlevel) {
        this.memberlevel = memberlevel;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }


    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", memberlevel=" + memberlevel +
                ", headImg='" + headImg + '\'' +
                ", id='" + id + '\'' +
                ", endTime='" + endTime + '\'' +
                ", scbn=" + scbn +
                ", scby=" + scby +
                '}';
    }
}
