package com.itheima.player.base;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.widget.Toast;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class Globle {
    public static Context mcontext;
    public static float density;
    public static int widthPixels;
    public static int heightPixels;

    public static void init(Context context) {
        mcontext = context;
        getScreenSize();
    }

    public static void getScreenSize() {
        DisplayMetrics metrics = mcontext.getResources().getDisplayMetrics();
        density = metrics.density;
        widthPixels = metrics.widthPixels;
        heightPixels = metrics.heightPixels;
    }

    public static int dp2dx(int dp) {
        return (int) (mcontext.getResources().getDisplayMetrics().density * dp + 0.5f);
    }

    public static Handler mhandler = new Handler(Looper.getMainLooper());

    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static void runOnMainThread(Runnable runnable) {
        if (isMainThread()) {
            runnable.run();
        } else {
            mhandler.post(runnable);
        }
    }


    public static Toast mtoast;

    public static void showToast(final String s) {
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                if (mtoast == null) {
                    mtoast = Toast.makeText(mcontext, s, Toast.LENGTH_SHORT);
                }
                mtoast.setText(s);
                mtoast.show();
            }
        });

    }


}
