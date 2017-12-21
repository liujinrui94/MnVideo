package com.cn.mnvideo.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/20 21:53
 * @description:
 */
public class InfoBean extends BaseResponseParams{


    private String condition;
    private String current;
    private String limit;
    private String offset;
    private String offsetCurrent;
    private String orderByField;
    private int pages;
    private ArrayList<Mnvideo> records;



    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getOffsetCurrent() {
        return offsetCurrent;
    }

    public void setOffsetCurrent(String offsetCurrent) {
        this.offsetCurrent = offsetCurrent;
    }


    public String getOrderByField() {
        return orderByField;
    }

    public void setOrderByField(String orderByField) {
        this.orderByField = orderByField;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public ArrayList<Mnvideo> getRecords() {
        return records;
    }

    public void setRecords(ArrayList<Mnvideo> records) {
        this.records = records;
    }


    @Override
    public String toString() {
        return "InfoBean{" +
                "condition='" + condition + '\'' +
                ", current='" + current + '\'' +
                ", limit='" + limit + '\'' +
                ", offset='" + offset + '\'' +
                ", offsetCurrent='" + offsetCurrent + '\'' +
                ", orderByField='" + orderByField + '\'' +
                ", pages=" + pages +
                ", records=" + records +
                '}';
    }
}
