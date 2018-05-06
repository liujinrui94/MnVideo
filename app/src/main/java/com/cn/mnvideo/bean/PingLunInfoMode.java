package com.cn.mnvideo.bean;

import java.util.List;

/**
 * Created by ljr on 2018/5/6.
 */

public class PingLunInfoMode {
    private String total;

    private String current;
    private String size;
    private List<PingLunInfo> records;

    public List<PingLunInfo> getInfot() {
        return records;
    }

    public void setInfot(List<PingLunInfo> infot) {
        this.records = infot;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
