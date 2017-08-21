package com.itheima.player.ui.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.format.DateFormat;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.itheima.player.R;
import com.itheima.player.base.Globle;
import com.itheima.player.bean.VideoBean;
import com.itheima.player.ui.view.VideoView;

import java.util.ArrayList;

import static com.itheima.player.R.id.btn_prev_video;
import static com.itheima.player.R.id.btn_toggle_fullscreen;
import static com.itheima.player.R.id.ll_loading;
import static com.itheima.player.R.id.tv_current_position;

/**
 * Created by 杂兵 on 2017/8/8.
 */
public class VideoPlay extends BaseActivity {

    private ArrayList<VideoBean> data;
    private int position;
    private VideoView videoview;
    private TextView videoname;
    private VideoBean mCurrentData;
    private ImageView ivbattery;
    private TextView tvbattery;
    private TextView tvsystemtime;
    private SeekBar sbvolume;
    private AudioManager mAudioManager;
    private int maxVolume;
    private int currentVolume;
    private GestureDetector gestureDetector;
    private float brightness;
    private static final int WHAT_UPDATE_TIME = 0;
    private static final int WHAT_UPDATE_CURRENTPOSITION = 1;
    private static final int WHAT_HIDE_CTRL = 2;
    private SeekBar sbcurrentposition;
    private TextView tvduration;
    private TextView tvcurrentposition;
    private Button btntoggleplay;
    private Button btnnextvideo;
    private Button btnprevvideo;
    private Button btntogglefullscreen;
    private LinearLayout lltopctrllayout;
    private LinearLayout llbottomctrllayout;
    private LinearLayout llloading;
    private int mTopHeight;
    private int mBottomHeight;

    @Override
    public void initListener() {
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                llloading.setVisibility(View.GONE);
                videoview.start();
                updateDuration();
                updateCurrentPosition();
            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoview.seekTo(0);
            }
        });
        videoview.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    llloading.setVisibility(View.VISIBLE);
                }
                if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    llloading.setVisibility(View.GONE);
                }
                return true;
            }
        });
        sbvolume.setOnSeekBarChangeListener(onSeekBarChangeListener);
        gestureDetector = new GestureDetector(this, gestureListener);
        sbcurrentposition.setOnSeekBarChangeListener(onSeekBarChangeListener);


    }

    private void updateCurrentPosition() {
        int currentPosition = videoview.getCurrentPosition();
        String currentPositionstr = DateFormat.format("kk:mm:ss", currentPosition).toString();
        tvcurrentposition.setText(currentPositionstr);

        sbcurrentposition.setProgress(currentPosition);

        handler.sendEmptyMessageDelayed(WHAT_UPDATE_CURRENTPOSITION, 100);

    }

    private void updateDuration() {
        int duration = videoview.getDuration();
        String durationstr = DateFormat.format("kk:mm:ss", duration).toString();
        tvduration.setText(durationstr);
        sbcurrentposition.setMax(duration);
    }

    SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {

                if (seekBar == sbvolume) {
                    changeVolume(progress);
                }
                if (seekBar == sbcurrentposition) {
                    videoview.seekTo(progress);
                }
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            removeHideMsg();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sendHideMsg();
        }
    };
    GestureDetector.SimpleOnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float movedistance = e1.getY() - e2.getY();

            if (e1.getX() > Globle.heightPixels / 2) {
                touchToChangeVolume(movedistance);
            } else {
                touchToChangeBrige(movedistance);
            }

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            brightness = getWindow().getAttributes().screenBrightness;
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            videoview.toggleFullscreen();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            showOrHideCtrlBroad();

            return super.onSingleTapConfirmed(e);
        }
    };

    private void showOrHideCtrlBroad() {
        float translationY = lltopctrllayout.getTranslationY();
        if (translationY == 0) {
            hideCtrlBroad();
        } else {
            llbottomctrllayout.animate().translationY(0);
            lltopctrllayout.animate().translationY(0);
        }

    }

    private void sendHideMsg() {
        handler.sendEmptyMessageDelayed(WHAT_HIDE_CTRL, 3000);
    }

    private void removeHideMsg() {
        handler.removeMessages(WHAT_HIDE_CTRL);
    }

    private void hideCtrlBroad() {
        llbottomctrllayout.animate().translationY(mBottomHeight);
        lltopctrllayout.animate().translationY(-mTopHeight);
    }

    private void touchToChangeBrige(float movedistance) {
        WindowManager.LayoutParams attributes = getWindow().getAttributes();

        float changeBrige = movedistance / Globle.widthPixels * 1f;

        float newBroghtness = brightness + changeBrige;

        if (newBroghtness < 0) {
            newBroghtness = 0;
        } else if (newBroghtness > 1) {
            newBroghtness = 1;
        }
        attributes.screenBrightness = newBroghtness;
        getWindow().setAttributes(attributes);
    }

    private void touchToChangeVolume(float movedistance) {
        float setVolume = movedistance / Globle.widthPixels * maxVolume;
        float newVolume = currentVolume + setVolume;
        if (newVolume < 0) {
            newVolume = 0;
        } else if (newVolume > maxVolume) {
            newVolume = maxVolume;
        }
        changeVolume((int) newVolume);
    }

    private void changeVolume(int progress) {
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
        sbvolume.setProgress(progress);
    }

    @Override
    public void initView() {
        videoview = findView(R.id.video_view);
        videoname = findView(R.id.tv_video_name);
        ivbattery = findView(R.id.iv_battery);
        tvbattery = findView(R.id.tv_battery);
        tvsystemtime = findView(R.id.tv_system_time);
        sbvolume = findView(R.id.sb_volume);
        sbcurrentposition = findView(R.id.sb_current_position);
        tvduration = findView(R.id.tv_duration);
        tvcurrentposition = findView(tv_current_position);
        btntoggleplay = findView(R.id.btn_toggle_play);
        btnnextvideo = findView(R.id.btn_next_video);
        btnprevvideo = findView(btn_prev_video);
        btntogglefullscreen = findView(btn_toggle_fullscreen);
        llloading = findView(ll_loading);


        updateSystemTime();
        initVolume();
        initCtrlBroad();
    }

    private void initCtrlBroad() {
        lltopctrllayout = findView(R.id.ll_top_ctrl_layout);
        llbottomctrllayout = findView(R.id.ll_bottom_ctrl_layout);

        lltopctrllayout.measure(0, 0);
        mTopHeight = lltopctrllayout.getMeasuredHeight();
        lltopctrllayout.setTranslationY(-mTopHeight);

        llbottomctrllayout.measure(0, 0);
        mBottomHeight = llbottomctrllayout.getMeasuredHeight();
        llbottomctrllayout.setTranslationY(mBottomHeight);

    }

    private void initVolume() {
        mAudioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        sbvolume.setMax(maxVolume);
        sbvolume.setProgress(currentVolume);
    }

    private void updateSystemTime() {
        long l = System.currentTimeMillis();
        String time = DateFormat.format("kk:mm:ss", l).toString();
        tvsystemtime.setText(time);

        handler.sendEmptyMessageDelayed(WHAT_UPDATE_TIME, 900);


    }

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case WHAT_UPDATE_TIME:
                    updateSystemTime();
                    break;
                case WHAT_UPDATE_CURRENTPOSITION:
                    updateCurrentPosition();
                    break;
                case WHAT_HIDE_CTRL:
                    showOrHideCtrlBroad();
                    break;
            }
        }
    };

    @Override
    public void initData() {
        Uri uri = getIntent().getData();
        if (uri == null) {
            position = getIntent().getIntExtra("position", -1);
            this.data = (ArrayList<VideoBean>) getIntent().getSerializableExtra("data");
            openVideo();
        } else {
            videoview.setVideoURI(uri);
            btnnextvideo.setEnabled(false);
            btnprevvideo.setEnabled(false);

            videoname.setText(uri.toString());

            llloading.setVisibility(View.VISIBLE);

        }


    }

    private void openVideo() {
        if (position == -1 || data.size() < 1) {
        }

        mCurrentData = data.get(position);
        videoview.setVideoPath(mCurrentData.path);
        videoname.setText(mCurrentData.name);
    }

    @Override
    public int getLayout() {
        return R.layout.activity_video_play;
    }

    @Override
    public void onClick(View v) {
        removeHideMsg();
        switch (v.getId()) {
            case R.id.btn_toggle_mute:
                mute();
                break;
            case R.id.btn_toggle_play:
                pauseorplay();
                break;
            case R.id.btn_next_video:
                next();
                break;
            case R.id.btn_prev_video:
                prev();
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_toggle_fullscreen:
                videoview.toggleFullscreen();
                if (videoview.isFullscreen()) {
                    btntogglefullscreen.setBackgroundResource(R.drawable.btn_default_screen);
                } else {
                    btntogglefullscreen.setBackgroundResource(R.drawable.btn_full_screen);
                }
                break;
        }
        sendHideMsg();
    }

    private void prev() {
        if (position > 0) {
            position--;
            openVideo();
        }
        if (position == 0) {
            showToast("这是第一首");
        }
    }

    private void next() {
        if (position < data.size() - 1) {
            position++;
            openVideo();
        }
        if (position == data.size() - 1) {
            showToast("这是最后一首");
        }


    }

    private void pauseorplay() {
        if (videoview.isPlaying()) {
            videoview.pause();
            btntoggleplay.setBackgroundResource(R.drawable.btn_play);
        } else {
            videoview.start();
            btntoggleplay.setBackgroundResource(R.drawable.btn_pause);

        }
    }

    private void mute() {
        int volumeMemery = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        if (volumeMemery > 0) {
            currentVolume = volumeMemery;
            changeVolume(0);
            sbvolume.setProgress(0);
        } else {
            changeVolume(volumeMemery);
            sbvolume.setProgress(volumeMemery);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra("level", 0);
            updateBattery(level);
        }
    };

    private void updateBattery(int level) {
        if (level == 0) {
            ivbattery.setImageResource(R.drawable.ic_battery_0);
        } else if (level < 10) {
            ivbattery.setImageResource(R.drawable.ic_battery_10);
        } else if (level < 20) {
            ivbattery.setImageResource(R.drawable.ic_battery_20);
        } else if (level < 40) {
            ivbattery.setImageResource(R.drawable.ic_battery_40);
        } else if (level < 60) {
            ivbattery.setImageResource(R.drawable.ic_battery_60);
        } else if (level < 80) {
            ivbattery.setImageResource(R.drawable.ic_battery_80);
        } else if (level <= 100) {
            ivbattery.setImageResource(R.drawable.ic_battery_100);
        }
        tvbattery.setText(level + "%");

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                removeHideMsg();
                break;
            case MotionEvent.ACTION_UP:
                sendHideMsg();
                break;
        }

        return super.onTouchEvent(event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
        handler.removeCallbacksAndMessages(null);
    }


}
