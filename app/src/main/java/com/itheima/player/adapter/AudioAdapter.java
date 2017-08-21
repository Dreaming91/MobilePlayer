package com.itheima.player.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.itheima.player.R;
import com.itheima.player.bean.AudioBean;

/**
 * Created by 杂兵 on 2017/8/7.
 */

public class AudioAdapter extends CursorAdapter {

    private ViewHolder viewHolder;

    public AudioAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_audio, parent, false);

        viewHolder = new ViewHolder();

        viewHolder.tvname = (TextView) view.findViewById(R.id.tv_title);
        viewHolder.tvartist = (TextView) view.findViewById(R.id.tv_artist);

        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        AudioBean audioBean = AudioBean.fromCursor(cursor);

        viewHolder = (ViewHolder) view.getTag();
        viewHolder.tvname.setText(audioBean.name);
        viewHolder.tvartist.setText(audioBean.atist+"|"+audioBean.anthur);

    }

    static class ViewHolder {
        private TextView tvname;
        private TextView tvartist ;
    }
}
