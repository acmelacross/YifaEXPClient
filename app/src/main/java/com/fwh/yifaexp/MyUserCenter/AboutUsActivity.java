package com.fwh.yifaexp.MyUserCenter;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.fwh.yifaexp.R;

public class AboutUsActivity extends Activity {
    WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        init();
    }

    private void init(){
        ((TextView)findViewById(R.id.title)).setText("关于我们");
        boolean b =getIntent().getBooleanExtra("isSoft",false);//判断是否为  关于我们 或者关于易发
        webView = (WebView) findViewById(R.id.webViewHtpp);
        //webView.loadUrl("file:///android_asset/myhtml.html");
        if(b){
            webView.loadUrl("http://u.eqxiu.com/s/e7Cygpmq");//u.eqxiu.com/s/e7Cygpmq

        }else{
            webView.loadUrl("http://yifaexpuser.bmob.site");
        }

        WebSettings webSettings =   webView .getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setBuiltInZoomControls(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小
//        //webView.setInitialScale(60);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
//        webSettings.setAllowFileAccess(true);  //设置可以访问文件
//        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WebSettings webSettings =   webView .getSettings();
        webSettings.setJavaScriptEnabled(false);
        webView.loadUrl("http://u.eqxiu.com/s/e7Cygpmq");//u.eqxiu.com/s/e7Cygpmq
        webView.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });
    }
}
