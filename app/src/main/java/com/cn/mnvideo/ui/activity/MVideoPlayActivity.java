package com.cn.mnvideo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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

    private List<PingLunInfo> lunInfoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = getIntent().getStringExtra("videoUrl");
        Log.e("AAA",url);
        url = url1;
        tid = getIntent().getStringExtra("videotId");

        initViews();
        initPlayer();
        smartRefreshLayout.autoRefresh();


    }

    private void initViews() {
        mnViderPlayer = (MNViderPlayer) findViewById(R.id.mn_videoplayer);
        if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 1) {
            mnViderPlayer.setCanSpeed(false);
        } else {
            mnViderPlayer.setCanSpeed(true);
        }
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                pageNum = 1;
                qingqiu = Constant.PINGLUN + "pageNum=" + pageNum + "&tid=" + tid;
                new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
            }
        });

        smartRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                pageNum++;
                qingqiu = Constant.PINGLUN + "pageNum=" + pageNum + "&tid=" + tid;
                new BaseNetRetRequestPresenter(MVideoPlayActivity.this).GetNetRetRequest();
            }
        });
    }

    private void initRlv(List<PingLunInfo> mlunInfoList) {

        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

        Log.e("aaa",mlunInfoList.toString());


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
                Log.i(TAG, "播放完成----");
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
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public String getPostJsonString() {

        return qingqiu;
    }

    @Override
    public void NetInfoResponse(String data, int sign) {

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(data);
            String data1 = jsonObject.getString("records");

            lunInfoList = (List<PingLunInfo>) GsonUtil.getInstance().fromJson(data1, new TypeToken<List<PingLunInfo>>() {
            }.getRawType());
            initRlv(lunInfoList);

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int sign() {
        return 0;
    }
}
