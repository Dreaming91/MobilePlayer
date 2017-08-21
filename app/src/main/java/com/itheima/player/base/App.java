package com.itheima.player.base;

import android.app.Application;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Globle.init(this);
    }
}
