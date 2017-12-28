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
import com.cn.mnvideo.ui.activity.MainActivity;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.GsonUtil;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/15 21:55
 * @description: 普通会员首页
 */
public class ExperienceFragment extends BaseFragment {


    private RecyclerView recyclerView;

    RollPagerView rollPagerView;

    private SmartRefreshLayout smartRefreshLayout;

    private View rootView;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;

    private InfoBean mInfoBean;
    private int [] imageUrls={R.mipmap.ic_huiyuangongnneg,R.mipmap.ic_huiyuangongnneg};//图片地址

    private int pageNum = 0;

    private ArrayList<Mnvideo> mnVideoArrayList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_experience, container, false);
        initEvent();
        smartRefreshLayout.autoRefresh();
        return rootView;
    }

    private void initEvent() {
        recyclerView = rootView.findViewById(R.id.fragment_experience_rlv);
        rollPagerView=rootView.findViewById(R.id.fragment_experience_roll_pager_view);
        rollPagerView.setAdapter(new LoopPagerAdapter(rollPagerView) {
            @Override
            public View getView(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                GlideUtils.getInstance().loadLocalImage(imageUrls[position],view);
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                return view;
            }

            @Override
            public int getRealCount() {
                return imageUrls.length;
            }
        });



        smartRefreshLayout = rootView.findViewById(R.id.fragment_experience_smartRefreshLayout);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 0;
                getData(Constant.IP + Constant.VIDEO_RUL + "?type=1&pageNum=" + pageNum, 0);
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getData(Constant.IP + Constant.VIDEO_RUL + "?type=1&pageNum=" + pageNum, 0);
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
                        Intent mIntent = new Intent(getContext(), MVideoPlayActivity.class);
//                        Intent mIntent = new Intent(getContext(), MainActivity.class);
                        mIntent.putExtra("videoUrl", model.getWaibuUrl());
                        mIntent.putExtra("videotId",model.getId());
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
