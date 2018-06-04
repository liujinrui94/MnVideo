package com.cn.mnvideo.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.cn.mnvideo.R;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.network.BaseNetRetRequestPresenter;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.BitmapCallback;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by LiuJinrui on 2017/12/29.
 */

public class PayWebViewActivity extends BaseActivity implements NetRequestView {

    @BindView(R.id.activity_all_web_view)
    WebView webview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_pay);
//        Log.e("aaaa",);
//        OkHttpUtils.post().url(getIntent().getStringExtra("json")).build().execute(new StringCallback() {
//
//            @Override
//            public void onError(Call call, Exception e, int id) {
//                ToastUtils.getInstance().showLongToast(e.toString());
//            }
//
//            @Override
//            public void onResponse(String response, int id) {
//                JSONObject data = null;
//                String url = null;
//                try {
//                    data = new JSONObject(response);
//                    url = data.getString("info");
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://weixin.bgeer.cn/platform/pay/unifiedorder/video?sign=731e5b8b2173ae3dcd7b182fe6e1c73e&mch_id=kexuidnfh156&body=vip&total_fee=100&spbill_create_ip=118.198.197.111&notify_url=http%3A%2F%2F119.29.180.63%3A86%2FApps%2Fnotify&redirect_url=http%3A%2F%2F119.29.180.63%3A86%2FApps%2Fsend%2Fid%2Fuserid84381&trade_type=WX&out_trade_no=userid84381"));
//        startActivityForResult(intent,0);
        initView(getIntent().getStringExtra("json"));
    }


    protected void initView(String url) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webview.loadUrl(url);
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        //滚动条
        webview.setWebViewClient(client);
        webview.setWebChromeClient(chromeClient);
    }

    private WebViewClient client = new WebViewClient() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.equals("http://222.186.173.169:8080/static/html/paySuccess.html")) {
                ToastUtils.getInstance().showShortToast("支付成功");
                Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }


            if (url.startsWith("http:") || url.startsWith("https:")) {
                return false;
            }
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
              startActivityForResult(intent,0);
            } catch (Exception e) {
            }
            return true;
        }
    };


    private WebChromeClient chromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        webview.stopLoading();
        webview.destroy();
    }

    @Override
    public void showCordError(String msg, int sign) {
        ToastUtils.getInstance().showLongToast(msg);
    }

    @Override
    public String getPostJsonString() {
        return getIntent().getStringExtra("json");
    }

    @Override
    public void NetInfoResponse(String data, int sign) {
        initView(data);
    }

    @Override
    public int sign() {
        return 0;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        super.onActivityResult(requestCode, resultCode, data);
    }
}

