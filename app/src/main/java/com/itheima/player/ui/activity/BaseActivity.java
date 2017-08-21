package com.itheima.player.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import com.itheima.player.base.Globle;
import com.itheima.player.interfaces.UI;
import com.itheima.player.util.Util;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public abstract class BaseActivity extends AppCompatActivity implements UI{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());

        ViewGroup view = (ViewGroup) findViewById(android.R.id.content);
        Util.findButtonClick(view,this);

        initView();
        initData();
        initListener();
    }



    public void showToast(String s) {
        Globle.showToast(s);
    }

    public <T> T findView(int id) {
        T view = (T) findViewById(id);
        return view;
    }


}
