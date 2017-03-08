package com.fwh.yifaexp;

import android.app.Application;

import com.tendcloud.tenddata.TCAgent;

import cn.bmob.v3.Bmob;
import cn.smssdk.SMSSDK;

//import org.xutils.x;

//import com.uuzuche.lib_zxing.activity.ZXingLibrary;

public class CrashApplication extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		//initBmob();
		//initZx();
		initAllSDKS();
	}

	private void initAllSDKS() {
//		x.Ext.init(this);
//		x.Ext.setDebug(false); // 是否输出debug日志, 开启debug会影响性能.

		SMSSDK.initSDK(this, "1bde49514e720", "27a2128def6de8e8de95602f8a574777");
		initBmob();
		initTalkingdata();
	}

	private void initTalkingdata() {
		TCAgent.LOG_ON=true;
		// App ID: 在TalkingData创建应用后，进入数据报表页中，在“系统设置”-“编辑应用”页面里查看App ID。
		// 渠道 ID: 是渠道标识符，可通过不同渠道单独追踪数据。
		TCAgent.init(this);
		// 如果已经在AndroidManifest.xml配置了App ID和渠道ID，调用TCAgent.init(this)即可；或与AndroidManifest.xml中的对应参数保持一致。
		TCAgent.setReportUncaughtExceptions(true);
	}

//	private void initZx() {
//		ZXingLibrary.initDisplayOpinion(this);
//	}

	private  void initBmob(){
		//第一：默认初始化
		Bmob.initialize(this, "0230f4953d4ec60eb7f701dda12e0359");

		//第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
		//BmobConfig config =new BmobConfig.Builder(this)
		////设置appkey
		//.setApplicationId("Your Application ID")
		////请求超时时间（单位为秒）：默认15s
		//.setConnectTimeout(30)
		////文件分片上传时每片的大小（单位字节），默认512*1024
		//.setUploadBlockSize(1024*1024)
		////文件的过期时间(单位为秒)：默认1800s
		//.setFileExpiration(2500)
		//.build();
		//Bmob.initialize(config);



//        Person p2 = new Person();
//        p2.setName("lucky");
//        p2.setAddress("北京海淀");
//        p2.save(new SaveListener<String>() {
//            @Override
//            public void done(String objectId,BmobException e) {
//                if(e==null){
//                    System.out.print("添加数据成功，返回objectId为："+objectId);
//                   // toast("添加数据成功，返回objectId为："+objectId);
//                }else{
//                    System.out.print("创建数据失败：" + e.getMessage());
//                  //  toast("创建数据失败：" + e.getMessage());
//                }
//            }
//        });
		System.out.println("initBMOB");
	}
}
