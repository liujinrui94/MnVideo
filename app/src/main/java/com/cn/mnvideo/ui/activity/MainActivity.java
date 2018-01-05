package com.cn.mnvideo.ui.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cn.mnvideo.R;
import com.cn.mnvideo.player.MNViderPlayer;
import com.cn.mnvideo.utils.PlayerUtils;

import butterknife.BindView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MNViderPlayer";

    private final String url1 = "http://v.wxwwfx.org:8088/vip7/1.mp4";
    private final String url2 = "http://v.wxwwfx.org:8088/vip7/1.mp4";
    //这个地址是错误的
    private final String url3 = "http://v.wxwwfx.org:8088/vip7/1.mp4";
    //本地视频
    private final String url4 = "http://v.wxwwfx.org:8088/vip7/1.mp4";

    private MNViderPlayer mnViderPlayer;

    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initPlayer();
//        image = findViewById(R.id.image);
//        setVideoThumbnail();
        //请求权限:调节亮度
//        requestPermission();

    }


    private void setVideoThumbnail() {
        if (PlayerUtils.isNetworkConnected(this)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final Bitmap videoThumbnail = PlayerUtils.createVideoThumbnail(url3, 200, 200);
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (videoThumbnail != null) {
                                image.setVisibility(View.VISIBLE);
                                image.setImageBitmap(videoThumbnail);
                            } else {
                                image.setVisibility(View.GONE);
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.System.canWrite(this)) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("提示");
                builder.setMessage("视频播放调节亮度需要申请权限");
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                                Uri.parse("package:" + getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivityForResult(intent, 100);
                    }
                });
                builder.show();
            }
        }
    }

    private void initViews() {
        mnViderPlayer = (MNViderPlayer) findViewById(R.id.mn_videoplayer);
    }

    private void initPlayer() {

        //初始化相关参数(必须放在Play前面)
        mnViderPlayer.setWidthAndHeightProportion(16, 9);   //设置宽高比
        mnViderPlayer.setIsNeedBatteryListen(true);         //设置电量监听
        mnViderPlayer.setIsNeedNetChangeListen(true);       //设置网络监听
        //第一次进来先设置数据
        mnViderPlayer.setDataSource(url2, "标题");



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
                Toast.makeText(MainActivity.this, "请注意,当前网络状态切换为3G/4G网络", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNoAvailable(MediaPlayer mediaPlayer) {
                Toast.makeText(MainActivity.this, "当前网络不可用,检查网络设置", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void btn01(View view) {
        mnViderPlayer.playVideo(url1, "标题1");
    }

    public void btn02(View view) {
        //position表示需要跳转到的位置
        mnViderPlayer.playVideo(url2, "标题2", 30000);

    }

    public void btn03(View view) {
        mnViderPlayer.playVideo(url3, "标题3");
    }

    public void btn04(View view) {
        if (hasPermission(this, "android.permission.WRITE_EXTERNAL_STORAGE")) {
            mnViderPlayer.playVideo(url4, "标题4");
        } else {
            Toast.makeText(this, "没有存储权限", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //暂停
        mnViderPlayer.pauseVideo();
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

}
