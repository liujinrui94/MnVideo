package com.cn.mnvideo.ui.fragmeng;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 22:53
 * @description:
 */
public class HomeFragment extends BaseFragment {

    @BindView(R.id.fragment_home_rlv)
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_home, container, false);
    }


    private void initView() {


    }
}
