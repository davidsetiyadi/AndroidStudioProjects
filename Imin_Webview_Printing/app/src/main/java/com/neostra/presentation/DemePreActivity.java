package com.neostra.presentation;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;
import android.util.Log;
import java.lang.ref.WeakReference;
import android.os.Message;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintJob;
import android.print.PrintManager;
import android.webkit.JsResult;



public class DemePreActivity extends Activity implements CompoundButton.OnCheckedChangeListener {

    private DisplayManager mDisplayManager;
    private Display[] displays;
    private CheckBox checkbox_presentation1;
    private CheckBox checkbox_presentation2;
    private DemoPresentation1 presentation1 = null;
    private DemoPresentation2 presentation2 = null;
    private static DemoPresentation1 presentation1s = null;
    private static Display[] displayss;
    private String mString = "Try Access Local Storage";
    private Button camara;
    private static String webview_url   = "https://cbfrozen.com.sg/";    // web address or local file location you want to open in webview
    /*-- MAIN VARIABLES --*/
    WebView webView;
    private boolean isRun;
    //private MHandler mHandler;
    private int index;
    private static String mStrings = "Try Access Local Storage";
    JSONArray array ;
    JSONObject m_jobj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deme_pre);
        webView = (WebView) findViewById(R.id.os_view);
        assert webView != null;
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setGeolocationEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSupportMultipleWindows(true);

        index=0;
        //String appCachePath = Context.getApplicationContext().getCacheDir().getAbsolutePath();
        //webSettings.setAppCachePath(appCachePath);
       // webSettings.setAllowFileAccess(true); // can read the file cache


        if(Build.VERSION.SDK_INT >= 21){
            webSettings.setMixedContentMode(0);
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
        //webView.setWebViewClient(new Callback());
        //webView.setWebViewClient(new WebViewClient());
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {

                //if page loaded successfully then show print button
                findViewById(R.id.fab).setVisibility(View.VISIBLE);
            }
        });
        webView.loadUrl(webview_url);
        //prepare your html content which will be show in webview


        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                //Required functionality here
                return super.onJsAlert(view, url, message, result);
            }
        });
        /*webView.evaluateJavascript("javascript:window.localStorage.getItem('openerp_pos_db_90bfa9b9-84b0-46a4-aa20-2ec5189796a4_unpaid_orders')", new ValueCallback<String>() {
                @Override public void onReceiveValue(String s) {
                    //Log.e("OnRecieve",s);
                    mString = s;
                    presentation1 = new DemoPresentation1(DemePreActivity.this, displays[1], checkbox_presentation1, mString);
                    presentation1.show();
                }
            }); 
        */





        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS}, 0x0099);
        }

        mDisplayManager = (DisplayManager) getSystemService(Context.DISPLAY_SERVICE);
        displays = mDisplayManager.getDisplays(null);
        displayss = mDisplayManager.getDisplays(null);

        checkbox_presentation1 = (CheckBox) findViewById(R.id.checkbox_presentation1);
        checkbox_presentation1.setOnCheckedChangeListener(this);
        checkbox_presentation2 = (CheckBox) findViewById(R.id.checkbox_presentation2);
        checkbox_presentation2.setOnCheckedChangeListener(this);
        /*if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {

            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0x001);
                return;
            }
        }*/
        presentation1 = new DemoPresentation1(this, displays[1], checkbox_presentation1, mString);

        presentation1.show();
        //mHandler =new MHandler(this);
        //flip();

    }
    

  /*  @Override
    protected void onStop() {
        super.onStop();
        Log.i("XGH","onStop");
        isRun=false;
        mHandler.removeCallbacksAndMessages(null);

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
                    mHandler.sendEmptyMessage(0x123);
                }
            }
        }).start();
    }



    private static class MHandler extends Handler {

        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<Context> mContextWeakReference;

        private MHandler(Context context) {
            mContextWeakReference = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            Context context = mContextWeakReference.get();

            mStrings = "Try Access local storage . . . .";
            presentation1s = new DemoPresentation1(context, displayss[1], checkbox_presentation1, mStrings);
            presentation1s.show();

            super.handleMessage(msg);
        }
    } */
//    di parse kan
  /*var x = localStorage.getItem("mytime");
    daftar_nama = JSON.parse(localStorage.getItem('mytime'));
  daftar_nama.forEach(function(nama){
        console.log(nama['data'].name);
        document.getElementById("demo").innerHTML = nama['data'].name;
    });*/

    /*-- callback reporting if error occurs --*/
   /* public  void createWebPagePrint(WebView webView) {
        PrintManager printManager = (PrintManager) getSystemService(Context.PRINT_SERVICE);
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();
        String jobName = getString(R.string.webapp_name) + " Document";
        PrintAttributes.Builder builder = new PrintAttributes.Builder();
        builder.setMediaSize(PrintAttributes.MediaSize.ISO_A5);
        PrintJob printJob = printManager.print(jobName, printAdapter, builder.build());

        if(printJob.isCompleted()){
            Toast.makeText(getApplicationContext(), R.string.print_complete, Toast.LENGTH_LONG).show();
        }
        else if(printJob.isFailed()){
            Toast.makeText(getApplicationContext(), R.string.print_failed, Toast.LENGTH_LONG).show();
        }
        // Save the job object for later status checking
    }*/
    //create a function to create the print job
    private void createWebPrintJob(WebView webView) {

        //create object of print manager in your device
        PrintManager printManager = (PrintManager) this.getSystemService(Context.PRINT_SERVICE);

        //create object of print adapter
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter();

        //provide name to your newly generated pdf file
        String jobName = getString(R.string.app_name) + " Print Test";

        //open print dialog
        printManager.print(jobName, printAdapter, new PrintAttributes.Builder().build());
    }
//
//    //perform click pdf creation operation on click of print button click
    public void printPDF(View view) {
        createWebPrintJob(webView);
    }
    public class Callback extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            mString = "Try Access local storage:::";
            presentation1 = new DemoPresentation1(DemePreActivity.this, displays[1], checkbox_presentation1, mString);
            presentation1.show();
            view.evaluateJavascript("javascript:window.localStorage.getItem('openerp_pos_db_90bfa9b9-84b0-46a4-aa20-2ec5189796a4_unpaid_orders')", new ValueCallback<String>() {
                @Override public void onReceiveValue(String s) {
                    //openerp_pos_db_90bfa9b9-84b0-46a4-aa20-2ec5189796a4_unpaid_orders
                    //Log.e("OnRecieve",s);
                    String json = "[{\"phonetype\":\"N95\",\"cat\":\"WP\"},{\"phonetype\":\"N95\",\"cat\":\"WP\"}]";
                    //s = s.substring(2, s.length() - 2);
                    //json = json.substring(1, json.length() - 1);
                    try {
//                        m_jobj = new JSONObject(json);
                        array = new JSONArray(json);
                        //JSONArray array = new JSONArray(json);
                        //array.toString();
                        mString = array.toString();
                    /*    JSONArray m_ja = m_jobj.getJSONArray("phonetype");
                    for(int i=0; i < m_ja.length(); i++)
                    {
                        JSONObject m_obj = m_ja.getJSONObject(i);
                        mString=m_obj.getString("phonetype");
                        //JSONObject object = array.getJSONObject(i);
                        //mString = object.getString("id");
                        //System.out.println(object.getString("No"));
                        //System.out.println(object.getString("Name"));
                    }*/
                    presentation1 = new DemoPresentation1(DemePreActivity.this, displays[1], checkbox_presentation1, mString);
                    presentation1.show();
                    }catch (JSONException err){
                        Log.d("Error", err.toString());
                        mString = err.toString();
                        //mString = s;
                        presentation1 = new DemoPresentation1(DemePreActivity.this, displays[1], checkbox_presentation1, mString);
                        presentation1.show();
                    }
                }
            });
            super.onPageFinished(view, url);
        }
        /*@Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            Toast.makeText(getApplicationContext(), "Failed loading app!", Toast.LENGTH_SHORT).show();
        }*/
    }

        @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (Settings.canDrawOverlays(this)) {

            } else {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 0x001);
                return;
            }
        }
        switch (buttonView.getId()) {
            case R.id.checkbox_presentation1:
                if (checkbox_presentation2.isChecked()) {
                    checkbox_presentation2.setChecked(false);
                    checkbox_presentation1.setChecked(isChecked);
                    if (presentation2 != null) {
                        presentation2.dismiss();
                        presentation2 = null;
                    }
                }
                if (isChecked) {
                   // presentation1 = new DemoPresentation1(this, displays[1], checkbox_presentation1);
                    //presentation1.show();

                } else {
                    if (presentation1 != null) {
                        presentation1.dismiss();
                        presentation1 = null;
                    }
                }
                break;

            case R.id.checkbox_presentation2:
                if (checkbox_presentation1.isChecked()) {
                    checkbox_presentation1.setChecked(false);
                    checkbox_presentation2.setChecked(isChecked);
                    if (presentation1 != null) {
                        presentation1.dismiss();
                        presentation1 = null;
                    }
                }

                if (isChecked) {
                    presentation2 = new DemoPresentation2(this, displays[1], checkbox_presentation2);
                    //                    presentation2.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    presentation2.show();
                } else {
                    if (presentation2 != null) {
                        presentation2.dismiss();
                        presentation2 = null;
                    }
                }
                break;

        }

    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0x001) {
            if (Settings.canDrawOverlays(DemePreActivity.this)) {
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}