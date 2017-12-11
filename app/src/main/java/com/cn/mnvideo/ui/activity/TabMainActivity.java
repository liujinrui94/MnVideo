package com.cn.mnvideo.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.widget.RadioGroup;

import com.cn.mnvideo.R;
import com.cn.mnvideo.adapter.TabFragmentAdapter;
import com.cn.mnvideo.base.BaseActivity;
import com.cn.mnvideo.ui.fragmeng.HomeFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author: LiuJinrui
 * @email: liujinrui@qdcftx.com
 * @time: 2017/12/7 17:59
 * @description:
 */
public class TabMainActivity extends BaseActivity {

    private TabFragmentAdapter tabFragmentAdapter;
    @BindView(R.id.activity_lottery_rg)
    RadioGroup radioGroup;
    private List<Fragment> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_main);
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        list.add(new HomeFragment());
        tabFragmentAdapter = new TabFragmentAdapter(this, list, R.id.fragment_these_month_profit_fl, radioGroup);
        tabFragmentAdapter.setOnRgsExtraCheckedChangedListener(new TabFragmentAdapter.OnRgsExtraCheckedChangedListener() {
            @Override
            public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index) {
            }
        });

    }
}
