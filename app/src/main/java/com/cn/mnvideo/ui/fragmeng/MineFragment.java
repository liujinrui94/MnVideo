package com.cn.mnvideo.ui.fragmeng;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseFragment;
import com.cn.mnvideo.utils.GlideUtils;

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

    private final String[] needPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this,rootView);
        initView();
        return rootView;
    }


    private void initView() {

        GlideUtils.getInstance().loadNetImage(AppApplication.getInstance().getBaseUserInfo().getUserHead(),iv_header);
        tv_account.setText(AppApplication.getInstance().getBaseUserInfo().getId());
        tv_password.setText(AppApplication.getInstance().getBaseUserInfo().getPassword());

        if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel()==5){
            upgrade_tv.setVisibility(View.GONE);
        }


    }
}
