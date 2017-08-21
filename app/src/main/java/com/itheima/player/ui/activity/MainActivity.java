package com.itheima.player.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.player.R;
import com.itheima.player.base.Globle;
import com.itheima.player.ui.fragment.AudioFragment;
import com.itheima.player.ui.fragment.VideoFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private TextView[] tvs = new TextView[2];
    private TextView mcuttent;
    private ViewPager viewPager;
    private View view;
    private int tabWidth;


    @Override
    public void initListener() {
        tvs[0].setOnClickListener(this);
        tvs[1].setOnClickListener(this);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                indicatorScorll(position, positionOffset);
            }


            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void indicatorScorll(int position, float positionOffset) {
        float translateOffset = tabWidth * position + tabWidth * positionOffset;
        view.setTranslationX(translateOffset);


    }

    @Override
    public void initView() {
        tvs[0] = findView(R.id.tv_tab_video);
        tvs[1] = findView(R.id.tv_tab_audeo);
        mcuttent = tvs[0];
        mcuttent.setSelected(true);
        mcuttent.setScaleX(1.2f);
        mcuttent.setScaleY(1.2f);

        initIndicator();
        initViewpager();

    }

    private void initViewpager() {

        viewPager = findView(R.id.view_pager);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new VideoFragment());
        fragments.add(new AudioFragment());

        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

    }

    private void initIndicator() {
        tabWidth = Globle.widthPixels / tvs.length;
        view = findView(R.id.view_tab_indicator);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = tabWidth;
        view.setLayoutParams(params);

    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab_video:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tv_tab_audeo:
                viewPager.setCurrentItem(1);
                break;
        }

    }

    private void changeTab(int i) {
        mcuttent.animate().scaleX(1f).scaleY(1f);
        mcuttent.setSelected(false);
        mcuttent = tvs[i];
        mcuttent.animate().scaleX(1.2f).scaleY(1.2f);
        mcuttent.setSelected(true);
    }
}
