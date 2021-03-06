package com.cdkj.link_community.module.maintab;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cdkj.baselibrary.adapters.ViewPagerAdapter;
import com.cdkj.baselibrary.base.BaseLazyFragment;
import com.cdkj.link_community.R;
import com.cdkj.link_community.databinding.FragmentFirstPageBinding;
import com.cdkj.link_community.module.message.FastMessageFragment;
import com.cdkj.link_community.module.message.MessageFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 首页 资讯
 * Created by cdkj on 2018/3/13.
 */

public class FirstPageFragment extends BaseLazyFragment {

    private FragmentFirstPageBinding mBinding;


    public static FirstPageFragment getInstanse() {
        FirstPageFragment fragment = new FirstPageFragment();
        Bundle bundle = new Bundle();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_first_page, null, false);

        initTopTitle();

        initViewPager();

        return mBinding.getRoot();
    }

    private void initTopTitle() {

        mBinding.topLayout.radiogroup.setOnCheckedChangeListener((radioGroup, i) -> {

            switch (i) {
                case R.id.radio_left:
                    mBinding.viewpager.setCurrentItem(0, true);
                    break;
                case R.id.radio_right:
                    mBinding.viewpager.setCurrentItem(1, true);
                    break;
            }

        });

    }

    private void initViewPager() {
        //设置fragment数据
        ArrayList fragments = new ArrayList<>();

        fragments.add(FastMessageFragment.getInstanse());
        fragments.add(MessageFragment.getInstanse());

        mBinding.viewpager.setAdapter(new ViewPagerAdapter(getChildFragmentManager(), fragments));
        mBinding.viewpager.setOffscreenPageLimit(fragments.size());


    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    protected void onInvisible() {

    }
}
