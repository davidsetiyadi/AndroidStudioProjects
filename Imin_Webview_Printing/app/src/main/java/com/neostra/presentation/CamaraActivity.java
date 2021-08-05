package com.neostra.presentation;

import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.hardware.camera2.*;

import android.media.Image;
import android.media.ImageReader;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.util.Size;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.nio.ByteBuffer;

public class CamaraActivity extends BaseActivity implements View.OnClickListener {

    private Camera camera = null;
    private Camera camera2 = null;
    private TextView cancelButton, okButton;
    private SurfaceView sfv_preview0;
    private SurfaceView sfv_preview1;
    private SurfaceView sfv_preview2;
    private Button button0, button1, button2;

    private boolean isCreated0, isCreated1, isCreated2;

    private SurfaceHolder.Callback cpHolderCallback0 = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isCreated0 = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopPreview();
        }
    };


    private SurfaceHolder.Callback cpHolderCallback1 = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isCreated1 = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopPreview();
        }
    };

    private SurfaceHolder.Callback cpHolderCallback2 = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            isCreated2 = true;
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            stopPreview();
        }
    };
    private CameraManager mCameraManager;


    @Override
    protected View getNavigation() {
        return findViewById(R.id.tb_back);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_camera;
    }

    @Override
    public void onActivityCreate() {
        bindViews();
    }

    private void bindViews() {
        sfv_preview0 = (SurfaceView) findViewById(R.id.sfv_preview0);
        sfv_preview0.getHolder().addCallback(cpHolderCallback0);

        sfv_preview1 = (SurfaceView) findViewById(R.id.sfv_preview1);
        sfv_preview1.getHolder().addCallback(cpHolderCallback1);

        sfv_preview2 = (SurfaceView) findViewById(R.id.sfv_preview2);
        sfv_preview2.getHolder().addCallback(cpHolderCallback2);


        okButton = (TextView) findViewById(R.id.tv_ok);
        cancelButton = (TextView) findViewById(R.id.tv_stop);
        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        cancelButton.setOnClickListener(this);
        okButton.setOnClickListener(this);
        button0.setOnClickListener(this);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);


    }

    //开始预览
    private void startPreview(int i, int rotation, SurfaceView sfv_preview) {

        //1代表打开后置摄像头,0代表打开前置摄像头.

        try {
            camera = Camera.open(i);
            camera.setPreviewDisplay(sfv_preview.getHolder());
            Camera.Parameters parameters = camera.getParameters();
//            Camera.Size bestSize = getBestPreviewSize(sfv_preview.getWidth(), sfv_preview.getHeight(), parameters);
//            parameters.setPreviewSize(bestSize.width, bestSize.height);
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
            camera.setParameters(parameters);
            camera.setDisplayOrientation(rotation);   //让相机旋转90度
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
            if (camera != null) {
                camera.stopPreview();
                camera.lock();
                camera.release();
                camera = null;
            }
            finish();
        }
    }


    //停止预览
    private void stopPreview() {
        if (camera != null) {
            camera.stopPreview();
            camera.lock();
            camera.release();
            camera = null;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.tv_ok:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra("android.intent.extras.CAMERA_FACING", 1);
                intent.putExtra("camerasensortype", 1); // 调用前置摄像头
                intent.putExtra("autofocus", true); // 自动对焦
                intent.putExtra("fullScreen", false); // 全屏
                intent.putExtra("showActionIcons", false);
                startActivityForResult(intent, 11);
                break;

            case R.id.tv_stop:
                finish();
                break;
            case R.id.button0:
                if (isCreated0) {
                    try {
                        startPreview(0, 0, sfv_preview0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }


                break;
            case R.id.button1:
                if (isCreated1) {
                    try {
                        startPreview(1, 0, sfv_preview1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.button2:
                if (isCreated2) {
                    try {
                        startPreview(2, 90, sfv_preview2);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    private Camera.Size getBestPreviewSize(int surfaceViewWidth, int surfaceViewHeight, Camera.Parameters parameters) {
        Camera.Size bestSize = null;
        //不同机器 尺寸大小排序方式不一样  有的从小到大有的从大到小
        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= surfaceViewWidth && size.height <= surfaceViewHeight) {
                if (bestSize == null) //初始化一个值
                    bestSize = size;
                else {
                    int tempArea = bestSize.width * bestSize.height;
                    int newArea = size.width * size.height;

                    if (newArea > tempArea) //取满足条件里面最大的
                        bestSize = size;
                }
            }
        }
        return bestSize;
    }



}
