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
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.utils.AppLogger;
import com.cn.mnvideo.utils.ToastUtils;
import com.cn.mnvideo.widget.BaseProgressDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 18:23
 * @description:
 */
public class BaseFragment extends Fragment implements NetRequestView {

    private BaseProgressDialog progressDialog;
    private Context mContext;
    private View RootView;
    private LayoutInflater inflater;
    private ViewGroup container;
    private String request;

    private int requestSign;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AppLogger.i(getClass().getSimpleName() + " onCreate");
        mContext = getActivity();
        super.onCreate(savedInstanceState);
    }

    public Context getContext() {
        return mContext;
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


    public void getData(String data, int requestSign) {
        progressShow();
        this.requestSign = requestSign;
        this.request = data;
        new BaseNetRetRequestPresenter(this).PostNetRetRequest();
    }

    protected void checkCordError(String msg) {
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public void showCordError(String msg, int sign) {
        progressCancel();
        checkCordError(msg);
    }

    @Override
    public String getPostJsonString() {
        return request;
    }

    @Override
    public void NetInfoResponse(String data, int sign) {


    }

    @Override
    public int sign() {
        return requestSign;
    }

}
