package com.cn.mnvideo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.TabFragmentAdapter;
import com.cn.mnvideo.base.AppApplication;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.mode.UpgradeInterface;
import com.cn.mnvideo.ui.fragmeng.ArtistFragment;
import com.cn.mnvideo.ui.fragmeng.ClassifyFragment;
import com.cn.mnvideo.ui.fragmeng.ExperienceFragment;
import com.cn.mnvideo.ui.fragmeng.FirstMemberFragment;
import com.cn.mnvideo.ui.fragmeng.HomeFragment;
import com.cn.mnvideo.ui.fragmeng.MineFragment;
import com.cn.mnvideo.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 17:59
 * @description:
 */
public class TabMainActivity extends BaseActivity implements UpgradeInterface {

    private TabFragmentAdapter tabFragmentAdapter;
    @BindView(R.id.activity_lottery_rg)
    RadioGroup radioGroup;

    @BindView(R.id.top_title_tv)
    TextView textView;

    private List<Fragment> list = new ArrayList<>();

    private Fragment[] fragments={new ExperienceFragment(),new FirstMemberFragment(),};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);

        list.add(new ExperienceFragment());
        list.add(new FirstMemberFragment());
        list.add(new ClassifyFragment());
        list.add(new ArtistFragment());
        list.add(new MineFragment());
        tabFragmentAdapter = new TabFragmentAdapter(this, list, R.id.fragment_these_month_profit_fl, radioGroup);
        tabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new TabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
                RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
                textView.setText(radioButton.getText().toString());
                if (AppApplication.getInstance().getBaseUserInfo().getMemberlevel()<5&&index!=4){

                }
            }
        });

    }

    @Override
    public void onUpgradeInterface(int type) {

    }
}
