package com.itheima.player.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateFormat;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.player.R;
import com.itheima.player.bean.VideoBean;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class VideoAdapter extends CursorAdapter {

    private ViewHolder viewHolder;

    public VideoAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);

        viewHolder = new ViewHolder();

        viewHolder.tvname = (TextView) view.findViewById(R.id.tv_name);
        viewHolder.tvsize = (TextView) view.findViewById(R.id.tv_size);
        viewHolder.tvduration = (TextView) view.findViewById(R.id.tv_duration);

        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        VideoBean videoBean = VideoBean.fromCursor(cursor);

        viewHolder = (ViewHolder) view.getTag();
        viewHolder.tvduration.setText(DateFormat.format("kk:mm:ss", videoBean.duration));
        viewHolder.tvname.setText(videoBean.name);
        viewHolder.tvsize.setText(Formatter.formatFileSize(mContext, videoBean.size));


    }

    static class ViewHolder {
        private TextView tvname;
        private TextView tvsize;
        private TextView tvduration;
    }
}
