package com.itheima.player.ui.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.player.R;
import com.itheima.player.Service.AudioService;
import com.itheima.player.bean.AudioBean;
import com.itheima.player.interfaces.AudioMethod;
import com.itheima.player.util.Util;

import java.util.ArrayList;

import static com.itheima.player.R.id.sb_current_position;
import static com.itheima.player.R.id.tv_artist;

/**
 * Created by 杂兵 on 2017/8/10.
 */
public class AudioPlay extends BaseActivity {

    private ArrayList<AudioBean> data;
    private int position;
    private TextView tvtitle;
    private TextView tvartist;
    private TextView tvcurrentposition;
    private SeekBar sbcurrentposition;
    private AudioMethod mservice;
    private Button btn_toggle_play;
    private String durition;
    private ImageView ivaudioanimation;
    private AnimationDrawable anime;

    @Override
    public void initListener() {
        sbcurrentposition.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mservice.seekto(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void initView() {
        tvtitle = findView(R.id.tv_title);
        tvartist = findView(tv_artist);
        tvcurrentposition = findView(R.id.tv_current_position);
        sbcurrentposition = findView(sb_current_position);
        btn_toggle_play = findView(R.id.btn_toggle_play);
        ivaudioanimation = findView(R.id.iv_audio_animation);

        initAnime();

    }

    private void initAnime() {
        anime = (AnimationDrawable) ivaudioanimation.getBackground();
        anime.start();
    }

    @Override
    public void initData() {
        Intent intent = new Intent(this, AudioService.class);
        intent.putExtras(getIntent());
        startService(intent);

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudioService.MyBinder binder = (AudioService.MyBinder) service;
            mservice = binder.mservice;
            mservice.open();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public int getLayout() {
        return R.layout.activity_audio_play;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_prev:
                mservice.prev();
                break;
            case R.id.btn_next:
                mservice.next();
                break;
            case R.id.btn_toggle_play:
                playorpause();
                btn_toggle_play.setBackgroundResource(
                        mservice.isplaying() ? R.drawable.btn_audio_pause : R.drawable.btn_audio_play);
                break;
        }
    }

    public void playorpause() {
        if (mservice.isplaying()) {
            mservice.pause();
            anime.stop();
        } else {
            mservice.play();
            anime.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(reciver);
        unbindService(conn);
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registreceiver();
    }

    private void registreceiver() {
        IntentFilter intentFilter = new IntentFilter(Util.UPDATEUI);
        LocalBroadcastManager.getInstance(this).registerReceiver(reciver, intentFilter);
    }

    BroadcastReceiver reciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            AudioBean audioBean = (AudioBean) intent.getSerializableExtra("audio");
            tvartist.setText(audioBean.atist);
            tvtitle.setText(audioBean.name);
            durition = DateFormat.format("mm:ss", mservice.getduration()).toString();
            sbcurrentposition.setMax(mservice.getduration());
            upDataCurrenttime();
        }
    };

    private void upDataCurrenttime() {
        String currenttime = DateFormat.format("mm:ss", mservice.getcurrenttime()).toString();
        tvcurrentposition.setText(currenttime + "/" + durition);
        sbcurrentposition.setProgress(mservice.getcurrenttime());
        handler.sendEmptyMessageDelayed(0, 60);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            upDataCurrenttime();
        }
    };

}
