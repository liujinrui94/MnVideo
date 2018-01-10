package com.cn.mnvideo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.BaseRecyclerAdapter;
import com.cn.mnvideo.adapter.SmartViewHolder;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.Mnvideo;
import com.cn.mnvideo.bean.PingLunInfo;
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.player.MNViderPlayer;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.PlayerUtils;
import com.cn.mnvideo.utils.ToastUtils;
import com.cn.mnvideo.widget.RadioButtonDialog;
import com.google.gson.reflect.TypeToken;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/21 0:24
 * @description:
 */
public class MVideoPlayActivity extends BaseActivity implements NetRequestView {


    private static final String TAG = "MNViderPlayer";


    private MNViderPlayer mnViderPlayer;
    private String url;
    private String tid;

    private int pageNum = 1;

    @BindView(R.id.activity_mvideo_pinglun_smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;

    @BindView(R.id.activity_mvideo_pinglun_rlv)
    RecyclerView recyclerView;
    private final String url1 = "http://bvideo.spriteapp.cn/video/2016/0704/577a4c29e1f14_wpd.mp4";
    private String qingqiu;

    private BaseRecyclerAdapter baseRecyclerAdapter;

    private List<PingLunInfo> lunInfoList;

    @BindView(R.id.my_header)
    ImageView head;

    @BindView(R.id.m_videoplayer_et)
    EditText editText;

    @BindView(R.id.pinglun_fabiao)
    TextView textView;

    private int code = 0;

    @BindView(R.id.ic_nocomment)
    ImageView ic_nocomment;

    private String fileUrlXq, fileUrlJt1, fileUrlJt2, fileUrlJt3, fileUrlJt4, fileUrlJt5, fileUrlJt6;

    @BindView(R.id.imageView1)
    ImageView imageView1;
    @BindView(R.id.imageView2)
    ImageView imageView2;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.imageView4)
    ImageView imageView4;
    @BindView(R.id.imageView5)
    ImageView imageView5;
    @BindView(R.id.imageView6)
    ImageView imageView6;

    @BindView(R.id.edit_ll)
    LinearLayout edit_ll;
    List<PingLunInfo> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = getIntent().getStringExtra("videoUrl");
        tid = getIntent().getStringExtra("videotId");
        fileUrlXq = getIntent().getStringExtra("fileUrlXq");
        fileUrlJt1 = getIntent().getStringExtra("fileUrlJt1");
        fileUrlJt2 = getIntent().getStringExtra("fileUrlJt2");
        fileUrlJt3 = getIntent().getStringExtra("fileUrlJt3");
        fileUrlJt4 = getIntent().getStringExtra("fileUrlJt4");
        fileUrlJt5 = getIntent().getStringExtra("fileUrlJt5");
        fileUrlJt6 = getIntent().getStringExtra("fileUrlJt6");
        initViews();
        initPlayer();
        smartRefreshLayout.autoRefresh();


    }

    private void initViews() {
        GlideUtils.getInstance().loadNetCircleDefaultImage(AppApplication.getInstance().getBaseUserInfo().getHeadImg(), head);
        GlideUtils.getInstance().loadNetImage(fileUrlXq, imageView1);
        GlideUtils.getInstance().loadNetImage(fileUrlJt1, imageView2);
        GlideUtils.getInstance().loadNetImage(fileUrlJt2, imageView3);
        GlideUtils.getInstance().loadNetImage(fileUrlJt3, imageView4);
        GlideUtils.getInstance().loadNetImage(fileUrlJt4, imageView5);
        GlideUtils.getInstance().loadNetImage(fileUrlJt5, imageView6);
        mnViderPlayer = (MNViderPlayer) findViewById(R.id.mn_videoplayer);
        if (null != getIntent().getStringExtra("shikan") && AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 5) {
            mnViderPlayer.setCanSpeed(false);
        } else {
            mnViderPlayer.setCanSpeed(true);
        }

//        if (null != getIntent().getStringExtra("bofang") && AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 5) {
//            mnViderPlayer.setBoFang(false);
//        }

        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                qingqiu = Constant.PINGLUN + "pageNum=" + pageNum + "&tid=" + tid;
                code = 0;
                new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                qingqiu = Constant.PINGLUN + "pageNum=" + pageNum + "&tid=" + tid;
                code = 0;
                new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
            }
        });

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(editText.getText().toString())) {
                    ToastUtils.getInstance().showShortToast("请输入评论");
                    return;
                }
                code = 1;
//                new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
                PingLunInfo pingLunInfo = new PingLunInfo();
                pingLunInfo.setContent(editText.getText().toString());
                pingLunInfo.setCreateTime("一分钟前");
                pingLunInfo.setHeadImg(AppApplication.getInstance().getBaseUserInfo().getHeadImg());
                pingLunInfo.setUserName(AppApplication.getInstance().getBaseUserInfo().getName());
                ic_nocomment.setVisibility(View.GONE);
                list.add(pingLunInfo);
                baseRecyclerAdapter.refresh(list);

            }
        });

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        int mCurrentOrientation = getResources().getConfiguration().orientation;
        if (mCurrentOrientation == Configuration.ORIENTATION_PORTRAIT) {
            edit_ll.setVisibility(View.VISIBLE);
        } else if (mCurrentOrientation == Configuration.ORIENTATION_LANDSCAPE) {

            edit_ll.setVisibility(View.GONE);

        }

    }


    private void initRlv(List<PingLunInfo> mlunInfoList) {

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        baseRecyclerAdapter = new BaseRecyclerAdapter<PingLunInfo>(mlunInfoList, R.layout.pinglun_info_item) {
            @Override
            protected void onBindViewHolder(SmartViewHolder holder, PingLunInfo model, int position) {
                holder.text(R.id.pinglun_item_userName, model.getUserName());
                holder.text(R.id.pinglun_item_content, model.getContent());
                holder.text(R.id.pinglun_item_createTime, model.getCreateTime());

            }
        };
        recyclerView.setAdapter(baseRecyclerAdapter);

    }


    private void initPlayer() {

        //初始化相关参数(必须放在Play前面)
        mnViderPlayer.setWidthAndHeightProportion(16, 9);   //设置宽高比
        mnViderPlayer.setIsNeedBatteryListen(true);         //设置电量监听
        mnViderPlayer.setIsNeedNetChangeListen(true);       //设置网络监听
        //第一次进来先设置数据
        mnViderPlayer.setDataSource(url, "标题");

        //播放完成监听
        mnViderPlayer.setOnCompletionListener(new MNViderPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 7) {
                    new RadioButtonDialog(getContext()).show();
                }
            }
        });

        //网络监听
        mnViderPlayer.setOnNetChangeListener(new MNViderPlayer.OnNetChangeListener() {
            @Override
            public void onWifi(MediaPlayer mediaPlayer) {
            }

            @Override
            public void onMobile(MediaPlayer mediaPlayer) {
                Toast.makeText(MVideoPlayActivity.this, "请注意,当前网络状态切换为3G/4G网络", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNoAvailable(MediaPlayer mediaPlayer) {
                Toast.makeText(MVideoPlayActivity.this, "当前网络不可用,检查网络设置", Toast.LENGTH_LONG).show();
            }
        });


    }


    @Override
    protected void onPause() {
        super.onPause();
        //暂停
        if (mnViderPlayer != null) {
            mnViderPlayer.pauseVideo();
        }

    }

    @Override
    public void onBackPressed() {
        if (mnViderPlayer.isFullScreen()) {
            mnViderPlayer.setOrientationPortrait();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        //一定要记得销毁View
        if (mnViderPlayer != null) {
            mnViderPlayer.destroyVideo();
            mnViderPlayer = null;
        }
        super.onDestroy();
    }


    public boolean hasPermission(Context context, String permission) {
        int perm = context.checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void showCordError(String msg, int sign) {
        if (smartRefreshLayout.isRefreshing()) {
            smartRefreshLayout.finishRefresh();
        }
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public String getPostJsonString() {

        if (code == 0) {
            return qingqiu;
        } else if (code == 1) {
            return Constant.FACHUPINGLUN + "tid=" + tid + "&content=" + editText.getText().toString() + "&uid=" + AppApplication.getInstance().getBaseUserInfo().getId();
        }
        return qingqiu;

    }

    @Override
    public void NetInfoResponse(String data, int sign) {

        if (sign == 0) {

            JSONObject jsonObject = null;

            try {
                jsonObject = new JSONObject(data);
                String data1 = jsonObject.getString("records");
                list = GsonUtil.getInstance().fromJson(data1, new TypeToken<List<PingLunInfo>>() {
                }.getType());


            } catch (JSONException e) {
                e.printStackTrace();
            }
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh();
                initRlv(list);
                baseRecyclerAdapter.refresh(list);

            } else if (smartRefreshLayout.isLoading() && list != null) {
                lunInfoList.addAll(list);
                smartRefreshLayout.finishLoadmore();
                baseRecyclerAdapter.loadmore(list);

            }
            if (list.size() == 0) {
                ic_nocomment.setVisibility(View.VISIBLE);
            }
            if (list.size() < 20) {
                smartRefreshLayout.setLoadmoreFinished(true);
            }
        } else {
            editText.setText("");
            ToastUtils.getInstance().showShortToast("评论成功");
            code = 0;
//            new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
//            smartRefreshLayout.autoRefresh();
        }

    }

    @Override
    public int sign() {
        return code;
    }
}
