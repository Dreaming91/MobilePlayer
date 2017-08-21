package com.itheima.player.interfaces;

/**
 * Created by 杂兵 on 2017/8/10.
 */

public interface AudioMethod {

    void play();

    void pause();

    void next();

    void prev();

    boolean isplaying();

    void open();

    void seekto(int position);

    int getcurrenttime();

    int getduration();


}
