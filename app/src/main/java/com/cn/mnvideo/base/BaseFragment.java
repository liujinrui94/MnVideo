package com.cn.mnvideo.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cn.mnvideo.R;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.widget.BaseProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 18:23
 * @description:
 */
public class BaseFragment extends Fragment {

    private BaseProgressDialog progressDialog;
    private Context mContext;
    private View RootView;
    private LayoutInflater inflater;
    private ViewGroup container;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppLogger.i(getClass().getSimpleName() + " onCreate");
        mContext=getActivity();
        super.onCreate(savedInstanceState);
    }

    public Context getContext() {
        return mContext;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.inflater=inflater;
        this.container=container;
        if (RootView!=null){
            ButterKnife.bind(RootView);

        }
        return RootView;

    }

    public View getRootView() {
        return RootView;
    }

    public void setRootView(int resource ) {
        RootView = inflater.inflate(resource, container, false);
    }

    @Override
    public void onViewCreated(View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow(String dialogDetail) {
        if (progressDialog != null)
            progressDialog.cancel();
        progressDialog = new BaseProgressDialog(getActivity());
        progressDialog.setDialogDetail(dialogDetail);
        progressDialog.show();
        return progressDialog;
    }

    /**
     * 显示加载对话框
     */
    protected ProgressDialog progressShow() {
        return progressShow("");
    }

    /**
     * 取消加载对话框
     */
    protected void progressCancel() {
        if (progressDialog != null)
            progressDialog.cancel();
    }
}
