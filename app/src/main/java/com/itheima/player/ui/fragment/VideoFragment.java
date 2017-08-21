package com.itheima.player.ui.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itheima.player.R;
import com.itheima.player.adapter.VideoAdapter;
import com.itheima.player.bean.VideoBean;
import com.itheima.player.ui.activity.VideoPlay;

import java.util.ArrayList;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class VideoFragment extends BaseFragment {
    private ArrayList<VideoBean> list = new ArrayList<>();
    private ListView listView;

    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mactivity, VideoPlay.class);
                intent.putExtra("data", list);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initView() {
        listView = findView(R.id.list_view);
    }

    @Override
    public void initData() {
        new AsyncQueryHandler(mactivity.getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                listView.setAdapter(new VideoAdapter(mactivity, cursor));
                cursor.moveToFirst();
                do {
                    VideoBean videoBean = VideoBean.fromCursor(cursor);
                    list.add(videoBean);
                } while (cursor.moveToNext());


            }
        }.startQuery(0, null, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DATA,
                MediaStore.Video.Media.SIZE,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DURATION
        }, null, null, MediaStore.Video.Media.DISPLAY_NAME + " ASC ");


    }

    @Override
    public int getLayout() {
        return R.layout.fragment_media_list;
    }

    @Override
    public void onClick(View v) {

    }
}
