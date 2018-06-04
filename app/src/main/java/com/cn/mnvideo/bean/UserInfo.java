package com.cn.mnvideo.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/26 22:57
 * @description:
 */
public class UserInfo  {

    private String name;//用户名称
    private String password;
    private int memberlevel;//当前等级
    private String headImg;//头像地址

    private String id;//用户ID

    private String endTime;//会员到期时间

    private long scbn;//包年价格
    private long scby;//包月价格

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
