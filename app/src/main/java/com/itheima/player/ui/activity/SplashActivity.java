package com.itheima.player.ui.activity;

import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;

import com.itheima.player.R;
import com.itheima.player.base.Globle;

public class SplashActivity extends BaseActivity {


    @Override
    public void initListener() {

    }

    @Override
    public void initView() {
        Globle.mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterMain();
            }
        }, 1500);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Globle.mhandler.removeCallbacksAndMessages(null);
            enterMain();
        }


        return super.onTouchEvent(event);
    }

    private void enterMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    @Override
    public void initData() {

    }

    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void onClick(View v) {

    }
}
