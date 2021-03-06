package com.neostra.presentation;

import android.app.Presentation;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;

import java.io.IOException;

public class DemoPresentation2 extends Presentation implements View.OnClickListener {

    private Context mContext;
    private MediaPlayer mPlayer = null;
    private SurfaceView sfv_show;
    private SurfaceHolder surfaceHolder;
    private Button btn_start;
    private Button btn_pause;
    private Button btn_stop;
    private Button btn_finish;
    private CheckBox mCheckBox;



    public DemoPresentation2(Context context, Display display, CheckBox checkBox) {
        super(context, display);
        mContext = context;
        mCheckBox=checkBox;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_content_imin);

        WebView myWebView = new WebView(mContext);
        setContentView(myWebView);
        myWebView.loadUrl("https://www.sgeede.com/");
//        bindViews();
//        Log.i("XGH2","onCreate");
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        },2000);

    }
    private void bindViews() {
        sfv_show = (SurfaceView) findViewById(R.id.sfv_show);
        btn_start = (Button) findViewById(R.id.btn_start);
        btn_pause = (Button) findViewById(R.id.btn_pause);
        btn_stop = (Button) findViewById(R.id.btn_stop);
        btn_finish = (Button) findViewById(R.id.btn_finish);


        btn_start.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_stop.setOnClickListener(this);
        btn_finish.setOnClickListener(this);


        //?????????SurfaceHolder??????SurfaceView????????????
        surfaceHolder = sfv_show.getHolder();
//        surfaceHolder.setFixedSize(320, 220);   //??????????????????,????????????????????????
        surfaceHolder.addCallback(new SurfaceCallback());
        mPlayer = new MediaPlayer();
        mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mPlayer.setDataSource(mContext, Uri.parse("android.resource://com.neostra.presentation/" + R.raw.videoplayback));
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //???????????????????????????????????????
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mPlayer.reset();
                try {
                    mPlayer.setDataSource(mContext, Uri.parse("android.resource://com.neostra.presentation/" + R.raw.videoplayback));
                    mPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mPlayer.start();
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                mPlayer.setDisplay(surfaceHolder);    //???????????????????????????SurfaceView???
                mPlayer.start();
                break;
            case R.id.btn_pause:
                mPlayer.pause();
                break;
            case R.id.btn_stop:
                mPlayer.stop();
                break;
            case R.id.btn_finish:
                dismiss();            //????????????Presentation
                mCheckBox.setChecked(false);
                break;
        }
    }

    //??????Presentation???????????????
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("XGH2","onStop");
        if (mPlayer.isPlaying()) {
            mPlayer.stop();
            mPlayer.release();
        }
    }

    private final class SurfaceCallback implements SurfaceHolder.Callback {
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }
        public void surfaceCreated(SurfaceHolder holder) {
            mPlayer.setDisplay(surfaceHolder);    //???????????????????????????SurfaceView???
            mPlayer.start();
        }
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }


}