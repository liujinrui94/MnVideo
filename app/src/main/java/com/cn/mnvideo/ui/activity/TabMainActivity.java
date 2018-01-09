package com.cn.mnvideo.ui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.TabFragmentAdapter;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.base.Constant;
import com.cn.mnvideo.bean.Login;
import com.cn.mnvideo.bean.UserInfo;
import com.cn.mnvideo.mode.UpgradeInterface;
import com.cn.mnvideo.network.NetRequestView;
import com.cn.mnvideo.ui.fragmeng.ArtistFragment;
import com.cn.mnvideo.ui.fragmeng.ClassifyFragment;
import com.cn.mnvideo.ui.fragmeng.ExperienceFragment;
import com.cn.mnvideo.ui.fragmeng.FirstMemberFragment;
import com.cn.mnvideo.ui.fragmeng.MineFragment;
import com.cn.mnvideo.ui.fragmeng.ShikanRragment;
import com.cn.mnvideo.utils.GsonUtil;
import com.cn.mnvideo.utils.ToastUtils;
import com.cn.mnvideo.widget.BackCommonDialog;
import com.cn.mnvideo.widget.RadioButtonDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 17:59
 * @description:
 */
public class TabMainActivity extends BaseActivity implements UpgradeInterface, NetRequestView {

    private TabFragmentAdapter tabFragmentAdapter;
    @BindView(R.id.activity_lottery_rg)
    RadioGroup radioGroup;

    @BindView(R.id.top_title_tv)
    TextView textView;

    private List<Fragment> list = new ArrayList<>();

    private int[] drawback = {
            R.drawable.tab_selected_home_rb,
            R.drawable.tab_selected_member_rb,
            R.drawable.tab_selsected_baijin_rb,
            R.drawable.tab_select_bojin_rb,
            R.drawable.tab_select_chaoji_rb,
            R.drawable.tab_select_bojin_rb};

    private String[] strings = { "钻石会员", "黑金会员", "紫钻会员", "蓝钻会员", "皇冠会员","蓝钻会员"
    };
    private RadioButtonDialog commonDialog;

    private BackCommonDialog backCommonDialog;
    private long exitTime = 0;//标记退出时间

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        initView();
        initCommonDialog();
    }

    private void initView() {
        if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() == 0) {
            list.add(new ExperienceFragment());
        } else if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() <= 5) {
            list.add(new FirstMemberFragment());
            Drawable rightDrawable = getResources().getDrawable(drawback[AppApplication.getInstance().getBaseUserInfo().getMemberlevel() - 1]);
            RadioButton mrb1 = findViewById(radioGroup.getChildAt(0).getId());
            rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            mrb1.setCompoundDrawables(null, rightDrawable, null, null);
            mrb1.setText(strings[AppApplication.getInstance().getBaseUserInfo().getMemberlevel() - 1]);
            Drawable rightDrawable1 = getResources().getDrawable(drawback[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()]);
            rightDrawable1.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
            RadioButton mrb2 = findViewById(radioGroup.getChildAt(1).getId());
            mrb2.setCompoundDrawables(null, rightDrawable1, null, null);
            mrb2.setText(strings[AppApplication.getInstance().getBaseUserInfo().getMemberlevel()]);
        }
        list.add(new ShikanRragment());
        list.add(new ClassifyFragment());
        list.add(new ArtistFragment());
        list.add(new MineFragment());
        tabFragmentAdapter = new TabFragmentAdapter(this, list, R.id.fragment_these_month_profit_fl, radioGroup);
        tabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new TabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                textView.setText(radioButton.getText().toString());
//                if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 5 && index != 4&&index!=0&&index!=1) {
//                    commonDialog.show();
//                    RadioButton mrb = findViewById(radioGroup.getChildAt(0).getId());
//                    mrb.performClick();
//                }
            }
        });


    }

    @Override
    public void onUpgradeInterface(int type) {

    }


    private void initCommonDialog() {
        commonDialog = new RadioButtonDialog(getContext(), R.style.Dialog);
        backCommonDialog = new BackCommonDialog(getContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && AppApplication.getInstance().getBaseUserInfo().getMemberlevel() < 1) {
            backCommonDialog.show();
        } else {
            exitBy2Click();
        }
        return false;
    }

    //双击退出方法
    public void exitBy2Click() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            ToastUtils.getInstance().showShortToast("再按一次退出");
            exitTime = System.currentTimeMillis();
        } else {
            AppApplication.getInstance().AppExit();
        }
    }

    @Override
    public void showCordError(String msg, int sign) {

    }

    @Override
    public String getPostJsonString() {
        Login login = new Login();
        login.setUserId(JPushInterface.getRegistrationID(this));
        login.setTuiguangma(Constant.TUIGUANGMA);
        return Constant.IP + Constant.LOGIN + GsonUtil.BeanToencode(login);
    }

    @Override
    public void NetInfoResponse(String data, int sign) {
        AppApplication.getInstance().setBaseUserInfo(GsonUtil.getInstance().fromJson(data, UserInfo.class));
        initView();
        initCommonDialog();
    }

    @Override
    public int sign() {
        return 0;
    }
}
