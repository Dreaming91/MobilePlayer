package com.itheima.player.interfaces;

import android.view.View;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public interface UI extends View.OnClickListener {

    void initListener();

    void initView();

    void initData();

    int getLayout();
}
