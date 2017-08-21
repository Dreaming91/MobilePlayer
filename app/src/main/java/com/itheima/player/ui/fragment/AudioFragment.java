package com.itheima.player.ui.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.itheima.player.R;
import com.itheima.player.adapter.AudioAdapter;
import com.itheima.player.bean.AudioBean;
import com.itheima.player.ui.activity.AudioPlay;

import java.util.ArrayList;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class AudioFragment extends BaseFragment {
    private ArrayList<AudioBean> list = new ArrayList<>();
    private ListView listView;


    @Override
    public void initListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(mactivity, AudioPlay.class);
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
                listView.setAdapter(new AudioAdapter(mactivity, cursor));
                cursor.moveToFirst();
                do {
                    AudioBean aideoBean = AudioBean.fromCursor(cursor);
                    list.add(aideoBean);
                } while (cursor.moveToNext());


            }
        }.startQuery(0, null, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, new String[]{
                MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.TITLE,
        }, null, null, MediaStore.Audio.Media.TITLE + " ASC ");
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_media_list;
    }

    @Override
    public void onClick(View v) {

    }
}
