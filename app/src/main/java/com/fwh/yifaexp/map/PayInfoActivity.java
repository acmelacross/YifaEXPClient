package com.fwh.yifaexp.map;

import com.fwh.yifaexp.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.TextView;


public class PayInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_payinfo);
		((TextView)findViewById(R.id.title)).setText("查看收费标准");
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		WebView webView = (WebView) findViewById(R.id.webView);
		//webView.loadUrl("http://www.wenyuwangdai.com");
		//webView.loadUrl("http://www.weather.com.cn/textFC/hb.shtml");

		//        if(b){
//            webView.loadUrl("http://www.baidu.com");//u.eqxiu.com/s/e7Cygpmq
//
//        }else{
//            webView.loadUrl("http://yifaexpuser.bmob.site");
//        }
//
		webView.loadUrl("file:///android_asset/myhtml.html");
		WebSettings webSettings =   webView .getSettings();       
		//webSettings.setJavaScriptEnabled(true);  
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setUseWideViewPort(false);  //将图片调整到适合webview的大小 
		//webView.setInitialScale(60);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局  
		webSettings.setAllowFileAccess(true);  //设置可以访问文件 
		webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
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
