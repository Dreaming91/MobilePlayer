package com.itheima.player.ui.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.itheima.player.base.Globle;
import com.itheima.player.bean.LyricsBean;

import java.util.ArrayList;

/**
 * Created by 杂兵 on 2017/8/11.
 */

public class LyricsView extends View {

    private Paint mpaint;
    private ArrayList<LyricsBean> lyricslist;
    private int mCurrentLyrics;
    private int mRowsHeight;

    public LyricsView(Context context) {
        this(context, null);
    }

    public LyricsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mpaint = new Paint();
        mpaint.setTextSize(Globle.dp2dx(16));
        mpaint.setAntiAlias(true);
        mpaint.setColor(Color.WHITE);
        lyricslist = new ArrayList<>();

        for (int i = 0; i < 20; i++) {
            LyricsBean lyricsBean = new LyricsBean();
            lyricsBean.text = "蛤" + i;
            lyricsBean.time = i * 10;
            lyricslist.add(lyricsBean);
        }
        mCurrentLyrics = 5;
        mRowsHeight = getTextHeight("哈") + Globle.dp2dx(12);


    }

    private int getTextHeight(String s) {
        Rect rect = new Rect();
        mpaint.getTextBounds(s, 0, s.length(), rect);
        return rect.height();
    }
    private int getTextwidth(String s) {
        Rect rect = new Rect();
        mpaint.getTextBounds(s, 0, s.length(), rect);
        return rect.width();
    }

    @Override
    protected void onDraw(Canvas canvas) {




    }

    private void drowCenterText(Canvas canvas,String s) {

    }


}
