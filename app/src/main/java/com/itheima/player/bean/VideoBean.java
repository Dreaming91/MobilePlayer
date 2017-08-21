package com.itheima.player.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

/** parcelable
 * Created by 杂兵 on 2017/8/7.
 */

public class VideoBean implements Serializable {
    public long id;
    public long size;
    public long duration;
    public String path;
    public String name;


    public static VideoBean fromCursor(Cursor cursor) {
        VideoBean videoBean = new VideoBean();
        videoBean.id = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media._ID));
        videoBean.size = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE));
        videoBean.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION));
        videoBean.name = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DISPLAY_NAME));
        videoBean.path = cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));


        return videoBean;
    }


}
