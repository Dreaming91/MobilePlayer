package com.itheima.player.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.player.interfaces.UI;
import com.itheima.player.util.Util;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public abstract class BaseFragment extends Fragment implements UI {

    public Activity mactivity;
    private View mRootview;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mactivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        if (mRootview == null) {
            mRootview = LayoutInflater.from(mactivity).inflate(getLayout(), container, false);

            if (mRootview instanceof ViewGroup) {
                Util.findButtonClick((ViewGroup) mRootview, this);
            }
            initView();
            initListener();
            initData();
        }
        return mRootview;
    }

    public <T> T findView(int id) {
        T view = (T) mRootview.findViewById(id);
        return view;
    }


}
