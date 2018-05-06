package com.cn.mnvideo.ui.fragmeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.BaseRecyclerAdapter;
import com.cn.mnvideo.adapter.SmartViewHolder;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseFragment;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.GungGao;
import com.cn.mnvideo.bean.InfoBean;
import com.cn.mnvideo.bean.Login;
import com.cn.mnvideo.bean.Mnvideo;
import com.cn.mnvideo.bean.MnvideoModel;
import com.cn.mnvideo.ui.activity.MVideoPlayActivity;
import com.cn.mnvideo.ui.activity.MainActivity;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.ToastUtils;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import okhttp3.Call;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/15 21:55
 * @description: 普通会员首页
 */
public class ExperienceFragment extends BaseFragment {


    private RecyclerView recyclerView;

    private RollPagerView rollPagerView;

    private SmartRefreshLayout smartRefreshLayout;

    private View rootView;
    private BaseRecyclerAdapter mBaseRecyclerAdapter;

    private List<Mnvideo> gungGaoList;
//    private InfoBean mInfoBean;
    private int pageNum = 1;

    private ArrayList<Mnvideo> list;

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
        rollPagerView = rootView.findViewById(R.id.fragment_experience_roll_pager_view);
        Login login = new Login();
        login.setUserId(JPushInterface.getRegistrationID(getContext()));
        login.setTuiguangma(Constant.TUIGUANGMA);
        OkHttpUtils.post().url(Constant.IP + Constant.LOGIN + GsonUtil.BeanToencode(login)).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {


            }

            @Override
            public void onResponse(String response, int id) {
                JSONObject jsonObject = null;
                String data = null;
                String scby=null;
                String scbn=null;
                try {
                    jsonObject = new JSONObject(response);
                    data = jsonObject.getString("guanggao");
                    scby=jsonObject.getString("scby");
                    scbn=jsonObject.getString("scbn");
                    if (jsonObject.getString("responseCode").equals(Constant.RESPONSE_SUCCESS)) {

                        AppApplication.getInstance().getBaseUserInfo().setScbn(Long.parseLong(scby));
                        AppApplication.getInstance().getBaseUserInfo().setScby(Long.parseLong(scbn));

                        if (null != data) {
                            gungGaoList =GsonUtil.getInstance().fromJson(data, new TypeToken<ArrayList<Mnvideo>>() {
                                    }.getType());
                            rollPagerView.setAdapter(new LoopPagerAdapter(rollPagerView) {
                                @Override
                                public View getView(ViewGroup container, int position) {
                                    ImageView view = new ImageView(container.getContext());
                                    GlideUtils.getInstance().loadNetImage(gungGaoList.get(position).getFileUrl(), view);
                                    view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                                    view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                                    return view;
                                }

                                @Override
                                public int getRealCount() {
                                    return gungGaoList.size();
                                }
                            });
                        } else {
                        }
                    } else {
                        ToastUtils.getInstance().showShortToast(jsonObject.getString("responseMsg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });




        smartRefreshLayout = rootView.findViewById(R.id.fragment_experience_smartRefreshLayout);

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                getData(Constant.IP + Constant.VIDEO_RUL + "type=" + AppApplication.getInstance().getBaseUserInfo().getMemberlevel() + "&pageNum=" + pageNum, 0);
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                getData(Constant.IP + Constant.VIDEO_RUL + "type=" + AppApplication.getInstance().getBaseUserInfo().getMemberlevel() + "&pageNum=" + pageNum, 0);
            }
        });

        rollPagerView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent mIntent = new Intent(getContext(), MVideoPlayActivity.class);
                mIntent.putExtra("videoUrl", gungGaoList.get(position).getWaibuUrl());
                mIntent.putExtra("videotId", gungGaoList.get(position).getId());
                mIntent.putExtra("fileUrlXq", gungGaoList.get(position).getFileUrlXq());
                mIntent.putExtra("fileUrlJt1", gungGaoList.get(position).getFileUrlJt1());
                mIntent.putExtra("fileUrlJt2", gungGaoList.get(position).getFileUrlJt2());
                mIntent.putExtra("fileUrlJt3", gungGaoList.get(position).getFileUrlJt3());
                mIntent.putExtra("fileUrlJt4", gungGaoList.get(position).getFileUrlJt4());
                mIntent.putExtra("fileUrlJt5", gungGaoList.get(position).getFileUrlJt5());
                mIntent.putExtra("shikan", "1");
                startActivity(mIntent);
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
                        mIntent.putExtra("videoUrl", model.getWaibuUrl());
                        mIntent.putExtra("videotId", model.getId());
                        mIntent.putExtra("fileUrlXq", model.getFileUrlXq());
                        mIntent.putExtra("fileUrlJt1", model.getFileUrlJt1());
                        mIntent.putExtra("fileUrlJt2", model.getFileUrlJt2());
                        mIntent.putExtra("fileUrlJt3", model.getFileUrlJt3());
                        mIntent.putExtra("fileUrlJt4", model.getFileUrlJt4());
                        mIntent.putExtra("fileUrlJt5", model.getFileUrlJt5());
                        mIntent.putExtra("shikan", "1");
                        startActivity(mIntent);
                    }
                });
            }
        };
        recyclerView.setAdapter(mBaseRecyclerAdapter);

    }


    @Override
    public void NetInfoResponse(String data, int sign) {
        AppLogger.e("AAAAA"+data);
        progressCancel();
        switch (sign) {
            case 0:
//                mInfoBean = GsonUtil.getInstance().fromJson(data, InfoBean.class);

//                list=GsonUtil.getInstance().fromJson(data, new TypeToken<ArrayList<Mnvideo>>() {
//                }.getType());
                MnvideoModel mnvideoModel=GsonUtil.getInstance().fromJson(data,MnvideoModel.class);
                list=  mnvideoModel.getMnvideoList();
                if (smartRefreshLayout.isRefreshing() && mBaseRecyclerAdapter != null) {
                    mBaseRecyclerAdapter.refresh(list);
                    smartRefreshLayout.finishRefresh();

                } else if (smartRefreshLayout.isLoading()) {
                    mBaseRecyclerAdapter.loadmore(list);
                    smartRefreshLayout.finishLoadmore();
                } else {
                    if (mBaseRecyclerAdapter == null) {
                        initNetView(list);
                        smartRefreshLayout.finishRefresh();

                    } else {
                        mnVideoArrayList.addAll(list);
                        initNetView(mnVideoArrayList);
                    }

                }
                if (list.size() < 20) {
                    smartRefreshLayout.setLoadmoreFinished(true);
                }
                break;
            case 1:
                break;

        }

    }
}
