package com.itheima.player.Service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.itheima.player.bean.AudioBean;
import com.itheima.player.interfaces.AudioMethod;
import com.itheima.player.util.Util;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 杂兵 on 2017/8/10.
 */

public class AudioService extends Service implements AudioMethod {

    private int position;
    private ArrayList<AudioBean> audiodatas;
    private MediaPlayer mediaPlayer;
    private AudioBean currentaudio;


    public class MyBinder extends Binder {
        public AudioService mservice = AudioService.this;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        position = intent.getIntExtra("position", -1);
        audiodatas = (ArrayList<AudioBean>) intent.getSerializableExtra("data");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {


        super.onCreate();
    }

    @Override
    public void onDestroy() {


        super.onDestroy();
    }


    @Override
    public void play() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void next() {
        if (position == audiodatas.size() - 1) {
            position = 0;
        } else {
            position++;
        }

        open();
    }

    @Override
    public void prev() {
        if (position == 0) {
            position = audiodatas.size() - 1;
        } else {
            position--;
        }
        open();
    }

    @Override
    public boolean isplaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public void open() {
        if (audiodatas == null || audiodatas.size() < 1 || position == -1) {
            return;   // 没有合法数据
        }

        currentaudio = audiodatas.get(position);

        try {
            releseMediaplayer();
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(currentaudio.path);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    play();
                    sendToUpdateUI();
                }
            });

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    next();
                }
            });
            mediaPlayer.prepareAsync();


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendToUpdateUI() {
        Intent intent = new Intent(Util.UPDATEUI);
        intent.putExtra("audio", currentaudio);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void releseMediaplayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }


    @Override
    public void seekto(int position) {
        mediaPlayer.seekTo(position);
    }

    @Override
    public int getcurrenttime() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getduration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }
}
