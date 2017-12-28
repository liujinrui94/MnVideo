package com.cn.mnvideo.ui.fragmeng;

import android.Manifest;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseFragment;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.utils.GlideUtils;
import com.cn.mnvideo.utils.PermissionsUtil;
import com.cn.mnvideo.utils.ToastUtils;
import com.cn.mnvideo.widget.CommonDialog;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/27 20:19
 * @description:
 */
public class MineFragment extends BaseFragment {

    private View rootView;

    @BindView(R.id.fragment_mine_header_iv)
    ImageView iv_header;
    @BindView(R.id.fragment_mine_tv_account)
    TextView tv_account;
    @BindView(R.id.fragment_mine_tv_password)
    TextView tv_password;
    @BindView(R.id.fragment_mine_save_pwd_tv)
    TextView save_pwd_tv;
    @BindView(R.id.fragment_mine_upgrade_tv)
    TextView upgrade_tv;
    protected static final int CHOOSE_PICTURE = 0;


    private final String[] needPermissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, rootView);
        initView();
        initEvent();
        return rootView;
    }

    private void initEvent() {

        save_pwd_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCurrentImage();
            }
        });
        upgrade_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonDialog commonDialog = new CommonDialog(getContext());
                commonDialog.show();
            }
        });
    }


    private void initView() {

        GlideUtils.getInstance().loadNetImage(AppApplication.getInstance().getBaseUserInfo().getUserHead(), iv_header);
        tv_account.setText(AppApplication.getInstance().getBaseUserInfo().getId());
        tv_password.setText(AppApplication.getInstance().getBaseUserInfo().getPassword());

        if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() == 5) {
            upgrade_tv.setVisibility(View.GONE);
        }


    }


    private void saveCurrentImage() {

        if (!PermissionsUtil.hasPermissions(getActivity(), needPermissions)) {
            PermissionsUtil.requestPermissions(getActivity(), "需要获取存储权限"
                    , CHOOSE_PICTURE, needPermissions);
            return;
        }
        //找到当前页面的跟布局
        View view = getActivity().getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        Bitmap temBitmap = view.getDrawingCache();

        //输出到sd卡
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//            File file = new File(String.valueOf(Uri.fromFile(fileCropUri)));
            File fileCropUri = new File(Environment.getExternalStorageDirectory().getPath() + "/mv_pwd_photo.png");
            try {
                if (!fileCropUri.exists()) {
                    fileCropUri.createNewFile();
                }
                FileOutputStream foStream = new FileOutputStream(fileCropUri);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
                ToastUtils.getInstance().showLongToast("保存成功" + Environment.getExternalStorageDirectory().getPath() + "/mv_pwd_photo.png");
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtils.getInstance().showLongToast("保存失败");
                AppLogger.e(e.toString());
            }
        }
    }
}
