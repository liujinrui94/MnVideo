package com.cn.mnvideo.ui.fragmeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.BaseRecyclerAdapter;
import com.cn.mnvideo.adapter.SmartViewHolder;
import com.cn.mnvideo.base.BaseFragment;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.InfoBean;
import com.cn.mnvideo.bean.Mnvideo;
import com.cn.mnvideo.ui.activity.MVideoPlayActivity;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.GsonUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/27 20:18
 * @description:
 */
public class ArtistFragment extends BaseFragment {


    private RecyclerView recyclerView;


    private SmartRefreshLayout smartRefreshLayout;

    private View rootView;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;

    private InfoBean mInfoBean;

    private int pageNum = 0;

    private ArrayList<Mnvideo> mnVideoArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_fist_member, container, false);
        initEvent();
        smartRefreshLayout.autoRefresh();
        return rootView;
    }

    private void initEvent() {
        recyclerView = rootView.findViewById(R.id.fragment_rlv);
        smartRefreshLayout = rootView.findViewById(R.id.fragment_smartRefreshLayout);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 0;
                getData(Constant.IP + Constant.VIDEO_RUL + "?type=2&pageNum=" + pageNum, 0);
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getData(Constant.IP + Constant.VIDEO_RUL + "?type=2&pageNum=" + pageNum, 0);
            }
        });


    }

    private void initNetView(ArrayList<Mnvideo> mnvideoArrayList) {

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);
        mBaseRecyclerAdapter = new BaseRecyclerAdapter<Mnvideo>(mnvideoArrayList, R.layout.rlv_item) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, final Mnvideo model, int position) {
                holder.text(R.id.rlv_item_tv, model.getTitle());
                GlideUtils.getInstance().loadNetImage(model.getFileUrl(), (ImageView) holder.itemView.findViewById(R.id.rlv_item_iv));

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent mIntent=new Intent(getContext(), MVideoPlayActivity.class);
                        mIntent.putExtra("videoUrl",model.getWaibuUrl());
                        startActivity(mIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mBaseRecyclerAdapter);

    }


    @Override
    public void NetInfoResponse(String data, int sign) {
        progressCancel();
        switch (sign) {
            case 0:
                mInfoBean = GsonUtil.getInstance().fromJson(data, InfoBean.class);

                if (smartRefreshLayout.isRefreshing() && mBaseRecyclerAdapter != null) {
                    mBaseRecyclerAdapter.refresh(mInfoBean.getRecords());
                    smartRefreshLayout.finishRefresh();

                } else if (smartRefreshLayout.isLoading()) {
                    mBaseRecyclerAdapter.loadmore(mInfoBean.getRecords());
                    smartRefreshLayout.finishLoadmore();
                } else {
                    if (mBaseRecyclerAdapter == null) {
                        initNetView(mInfoBean.getRecords());
                        smartRefreshLayout.finishRefresh();

                    } else {
                        mnVideoArrayList.addAll(mInfoBean.getRecords());
                        initNetView(mnVideoArrayList);
                    }

                }
                if (mInfoBean.getRecords().size() < 20) {
                    smartRefreshLayout.setLoadmoreFinished(true);
                }
                break;
            case 1:
                break;

        }

    }

}
