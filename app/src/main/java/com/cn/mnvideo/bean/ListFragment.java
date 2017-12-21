package com.cn.mnvideo.bean;

import android.support.v4.app.Fragment;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/15 22:04
 * @description:
 */
public class ListFragment {


    private int index;
    private Fragment fragment;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
