package com.itheima.player.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/**
 * parcelable
 * Created by 杂兵 on 2017/8/7.
 */

public class AudioBean implements Serializable {
    public long id;
    public String path;
    public String atist;
    public String anthur;
    public String name;


    public static AudioBean fromCursor(Cursor cursor) {
        AudioBean audioBean = new AudioBean();
        audioBean.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
        audioBean.atist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        audioBean.anthur = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
        audioBean.name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
        audioBean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));


        return audioBean;
    }


}
