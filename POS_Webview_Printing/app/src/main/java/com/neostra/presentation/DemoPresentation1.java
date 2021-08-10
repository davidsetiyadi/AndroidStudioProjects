package com.neostra.presentation;

import android.app.Presentation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.webkit.WebView;
import android.widget.ViewFlipper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author xghsir
 * Created by 肖根华 on 2019/6/27.
 */

/**
 * The presentation to show on the secondary display.
 * <p>
 * Note that the presentation display may have different metrics from the display on which
 * the main activity is showing so we must be careful to use the presentation's own context
 * whenever we load resources.
 */
public class DemoPresentation1 extends Presentation {

    private ViewFlipper vflp_help;
    private Context mContext;
    private int[] resId = {R.mipmap.ic_help_view_1, R.mipmap.ic_help_view_2,
            R.mipmap.ic_help_view_3, R.mipmap.ic_help_view_4};
    private final static int MIN_MOVE = 200;   //最小距离
    private MyGestureListener mgListener;
    private GestureDetector mDetector;
    private Button jump_bt1;
    private Button jump_bt2;
    private DemoPresentation2 presentation2 = null;
    private DisplayManager mDisplayManager;
    private Display[] displays;
    private CheckBox mCheckBox;
    private TextView mTextView;
    private String mString;
    private static ViewPager mViewPager;
    private static ArrayList<String> paths;
    private static int index;

    private boolean isRun;
    private MyHandler myHandler;
    WebView myWebView;


    public DemoPresentation1(Context context, Display display, CheckBox checkBox, String string) {
        super(context, display);
        mContext = context;
        mCheckBox = checkBox;
        mString = string;
    }

    TextView myAwesomeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.presentation_content1);
        Log.i("XGH","onCreate");
         myHandler =new MyHandler(mContext);
         
       /*ImageView image = new ImageView(mContext);
       image = (ImageView) findViewById(R.id.imageView);*/

        //WebView myWebView = new WebView(mContext);
        //setContentView(myWebView);
        //myWebView = (WebView) findViewById(R.id.os2_view);
        //myWebView.loadUrl("http://18.141.203.220:8069/");
        // TextView myAwesomeTextView = (TextView)findViewById(R.id.myAwesomeTextView);
        //myAwesomeTextView = findViewById(R.id.myAwesomeTextView);
        //myAwesomeTextView.setText(mString); //set text for text view

        getAllImagePath();
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mViewPager.setAdapter(new ViewPagerAdapter(mContext, paths));
        index=0;
        mViewPager.setCurrentItem(index);
        flip();





    }


    //关闭Presentation时释放资源
    @Override
    protected void onStop() {
        super.onStop();
        Log.i("XGH","onStop");
        isRun=false;
        myHandler.removeCallbacksAndMessages(null);

    }




    //重写onTouchEvent触发MyGestureListener里的方法
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }


    //自定义一个GestureListener,这个是View类下的，别写错哦！！！
    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
            if (e1.getX() - e2.getX() > MIN_MOVE) {
                vflp_help.setInAnimation(mContext, R.anim.right_in);
                vflp_help.setOutAnimation(mContext, R.anim.right_out);
                vflp_help.showNext();
            } else if (e2.getX() - e1.getX() > MIN_MOVE) {
                vflp_help.setInAnimation(mContext, R.anim.left_in);
                vflp_help.setOutAnimation(mContext, R.anim.left_out);
                vflp_help.showPrevious();

            }
            return true;
        }
    }

    private ImageView getImageView(int resId) {
        ImageView img = new ImageView(mContext);
        img.setBackgroundResource(resId);
        return img;
    }


    //获取本地相册
    private void getAllImagePath() {

        if (paths == null) {
            paths = new ArrayList<>();
            //            paths.add(getResourcesUri(R.drawable.imin001));
            //            paths.add(getResourcesUri(R.drawable.imin002));
//            paths.add(getResourcesUri(R.drawable.imin003));
            paths.add(getResourcesUri(R.drawable.sigma_screen));
//            paths.add(getResourcesUri(R.drawable.imin005));
//            paths.add(getResourcesUri(R.drawable.imin006));
//            paths.add(getResourcesUri(R.drawable.imin007));
//            paths.add(getResourcesUri(R.drawable.imin008));
//            paths.add(getResourcesUri(R.drawable.imin009));
//            paths.add(getResourcesUri(R.drawable.imin010));
//            paths.add(getResourcesUri(R.drawable.imin011));
//            paths.add(getResourcesUri(R.drawable.imin012));
//            paths.add(getResourcesUri(R.drawable.imin013));
//            paths.add(getResourcesUri(R.drawable.imin014));
//            paths.add(getResourcesUri(R.drawable.imin015));
//            paths.add(getResourcesUri(R.drawable.imin016));
//            paths.add(getResourcesUri(R.drawable.imin017));
//            paths.add(getResourcesUri(R.drawable.imin018));
//            paths.add(getResourcesUri(R.drawable.imin019));
//            paths.add(getResourcesUri(R.drawable.imin020));
//            paths.add(getResourcesUri(R.drawable.imin021));
//            paths.add(getResourcesUri(R.drawable.imin022));
//            paths.add(getResourcesUri(R.drawable.imin023));
//            paths.add(getResourcesUri(R.drawable.imin024));
//            paths.add(getResourcesUri(R.drawable.imin025));
        }

    }

    private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }

    private void flip() {
        isRun=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    myHandler.sendEmptyMessage(0x123);
                }
            }
        }).start();
    }



    private static class MyHandler extends Handler {

        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<Context> mContextWeakReference;

        private MyHandler(Context context) {
            mContextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mContextWeakReference.get();
            if (context != null) {

                if (msg.what == 0x123) {
                    index++;
                    if (index == paths.size()) {
                        index = 0;
                    }
                    mViewPager.setCurrentItem(index);
                }

            }
            super.handleMessage(msg);
        }
    }
}