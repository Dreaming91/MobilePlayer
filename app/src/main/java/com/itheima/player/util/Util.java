package com.itheima.player.util;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;


/**
 * Created by 杂兵 on 2017/8/7.
 */

public class Util {

    public static final String UPDATEUI = "com.itheima.player102.ACTION_UPDATE_AUDIO_UI";

    public static void findButtonClick(ViewGroup parent, View.OnClickListener listener) {
        for (int i = 0; i < parent.getChildCount(); i++) {
            View child = parent.getChildAt(i);
            if (child instanceof Button || child instanceof ImageButton) {
                child.setOnClickListener(listener);
            } else if (child instanceof ViewGroup) {
                findButtonClick((ViewGroup) child, listener);
            }
        }


    }


}
