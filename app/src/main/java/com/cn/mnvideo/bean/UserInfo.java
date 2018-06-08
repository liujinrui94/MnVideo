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

    private String userId;//用户ID

    private String endTime;//会员到期时间

    private String snbc;//包年价格
    private String sybc;//包月价格


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getSnbc() {
        return snbc;
    }

    public void setSnbc(String snbc) {
        this.snbc = snbc;
    }

    public String getSybc() {
        return sybc;
    }

    public void setSybc(String sybc) {
        this.sybc = sybc;
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
                ", userId='" + userId + '\'' +
                ", endTime='" + endTime + '\'' +
                ", snbc='" + snbc + '\'' +
                ", sybc='" + sybc + '\'' +
                '}';
    }
}
