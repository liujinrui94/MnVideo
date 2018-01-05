package com.cn.mnvideo.bean;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/18 19:51
 * @description:
 */
public class Mnvideo {

    /*
    *  "content": "rrrr",
                "createTime": "2017-12-11 10:15:13",
                "fileUrl": "http://58.64.214.23:8080/pictures/pic/1_20171211101511182.png",
                "id": "940042254247026689",
                "imgFirst": 1,
                "modifyTime": null,
                "pid": null,
                "title": "rrrr",
                "top": 1,
                "type": 3,
                "waibuUrl": ""
    *
    * */

    private String content;
    private String type;
    private String createTime;
    private String id;
    private String imgFirst;
    private String fileUrl;
    private String title;
    private String waibuUrl;


    /**
     * 截图1
     */
    private String fileUrlJt1;
    /**
     * 截图2
     */
    private String fileUrlJt2;
    /**
     * 截图3
     */
    private String fileUrlJt3;
    /**
     * 截图4
     */
    private String fileUrlJt4;
    /**
     * 截图5
     */
    private String fileUrlJt5;
    /**
     * 详情图
     */
    private String fileUrlXq;


    public String getFileUrlJt1() {
        return fileUrlJt1;
    }

    public void setFileUrlJt1(String fileUrlJt1) {
        this.fileUrlJt1 = fileUrlJt1;
    }

    public String getFileUrlJt2() {
        return fileUrlJt2;
    }

    public void setFileUrlJt2(String fileUrlJt2) {
        this.fileUrlJt2 = fileUrlJt2;
    }

    public String getFileUrlJt3() {
        return fileUrlJt3;
    }

    public void setFileUrlJt3(String fileUrlJt3) {
        this.fileUrlJt3 = fileUrlJt3;
    }

    public String getFileUrlJt4() {
        return fileUrlJt4;
    }

    public void setFileUrlJt4(String fileUrlJt4) {
        this.fileUrlJt4 = fileUrlJt4;
    }

    public String getFileUrlJt5() {
        return fileUrlJt5;
    }

    public void setFileUrlJt5(String fileUrlJt5) {
        this.fileUrlJt5 = fileUrlJt5;
    }

    public String getFileUrlXq() {
        return fileUrlXq;
    }

    public void setFileUrlXq(String fileUrlXq) {
        this.fileUrlXq = fileUrlXq;
    }

    public String getWaibuUrl() {
        return waibuUrl;
    }

    public void setWaibuUrl(String waibuUrl) {
        this.waibuUrl = waibuUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImgFirst() {
        return imgFirst;
    }

    public void setImgFirst(String imgFirst) {
        this.imgFirst = imgFirst;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public String toString() {
        return "Mnvideo{" +
                "content='" + content + '\'' +
                ", type='" + type + '\'' +
                ", createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                ", imgFirst='" + imgFirst + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
