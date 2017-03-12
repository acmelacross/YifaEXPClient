/*
 * 官网地站:http://www.mob.com
 * 技术支持QQ: 4006852216
 * 官方微信:ShareSDK   （如果发布新版本的话，我们将会第一时间通过微信将版本更新内容推送给您。如果使用过程中有任何问题，也可以通过微信与我们取得联系，我们将会在24小时内给予回复）
 *
 * Copyright (c) 2014年 mob.com. All rights reserved.
 */
package com.fwh.yifaexp;
import java.util.HashMap;
import java.util.Random;


import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.map.IndexActivity;
import com.fwh.yifaexp.model.UserForYifa;



import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;
import cn.bmob.v3.update.UpdateResponse;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.gui.CommonDialog;
import cn.smssdk.gui.RegisterPage;

/**
 * @author WHFeng
 * 入口类
 */


public class MainActivity extends Activity implements OnClickListener,Callback {
    // 填写从短信SDK应用后台注册得到的APPKEY
    private static String APPKEY = "1bde49514e720";
    // 填写从短信SDK应用后台注册得到的APPSECRET
    private static String APPSECRET = "27a2128def6de8e8de95602f8a574777";
    // 短信注册，随机产生头像
    public static final String[] AVATARS = {
            "http://tupian.qqjay.com/u/2011/0729/e755c434c91fed9f6f73152731788cb3.jpg",
            "http://99touxiang.com/public/upload/nvsheng/125/27-011820_433.jpg",
            "http://img1.touxiang.cn/uploads/allimg/111029/2330264224-36.png",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339485237265.jpg",
            "http://diy.qqjay.com/u/files/2012/0523/f466c38e1c6c99ee2d6cd7746207a97a.jpg",
            "http://img1.touxiang.cn/uploads/20121224/24-054837_708.jpg",
            "http://img1.touxiang.cn/uploads/20121212/12-060125_658.jpg",
            "http://img1.touxiang.cn/uploads/20130608/08-054059_703.jpg",
            "http://diy.qqjay.com/u2/2013/0422/fadc08459b1ef5fc1ea6b5b8d22e44b4.jpg",
            "http://img1.2345.com/duoteimg/qqTxImg/2012/04/09/13339510584349.jpg",
            "http://img1.touxiang.cn/uploads/20130515/15-080722_514.jpg",
            "http://diy.qqjay.com/u2/2013/0401/4355c29b30d295b26da6f242a65bcaad.jpg"
    };
    private boolean ready;
    private Dialog pd;
    private TextView tvNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Bmob.initialize(this, "2fac321bf36ec6b6b9174ad8a47d3eb5");//秉德便利店


        //FailedlWrite.writeCrashInfoToFile("未能匹配到司机,请稍后再试"+163+"aaa");
        //初始化MOB
        loadSharePrefrence();
        showAppkeyDialog();

//		Bundle params = new Bundle();
//		//设置开放平台 API Key
//		params.putString(BaiduASRDigitalDialog.PARAM_API_KEY, "MnUbizu0boGx5utrZwACEGYm");
//		//设置开放平台 Secret Key
//		params.putString(BaiduASRDigitalDialog.PARAM_SECRET_KEY, "8a7f6b1e6305054d052b2beba5c5b918");
//		//设置识别领域：搜索、输入、地图、音乐……，可选。默认为输入。
//		params.putInt( BaiduASRDigitalDialog.PARAM_PROP, VoiceRecognitionConfig.PROP_INPUT);
//		//设置语种类型：中文普通话，中文粤语，英文，可选。默认为中文普通话
//		params.putString( BaiduASRDigitalDialog.PARAM_LANGUAGE, VoiceRecognitionConfig.LANGUAGE_CHINESE);
//		//如果需要语义解析，设置下方参数。领域为输入不支持
//		params.putBoolean(BaiduASRDigitalDialog.PARAM_NLU_ENABLE, true);
//		// 设置对话框主题，可选。BaiduASRDigitalDialog 提供了蓝、暗、红、绿、橙四中颜色，每种颜色又分亮、暗两种色调。共 8 种主题，开发者可以按需选择，取值参考 BaiduASRDigitalDialog 中前缀为 THEME_的常量。默认为亮蓝色
//		params.putInt(BaiduASRDigitalDialog.PARAM_DIALOG_THEME, BaiduASRDigitalDialog.THEME_ORANGE_DEEPBG);
//		mDialog = new BaiduASRDigitalDialog(this,params);
//	    mDialog.setDialogRecognitionListener(mRecognitionListener);
////      }
////      mDialog.getParams().putInt(BaiduASRDigitalDialog.PARAM_PROP, Config.CURRENT_PROP);
////      mDialog.getParams().putString(BaiduASRDigitalDialog.PARAM_LANGUAGE,
////              Config.getCurrentLanguage());
////      Log.e("DEBUG", "Config.PLAY_START_SOUND = "+Config.PLAY_START_SOUND);
////      mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_START_TONE_ENABLE, Config.PLAY_START_SOUND);
////      mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_END_TONE_ENABLE, Config.PLAY_END_SOUND);
////      mDialog.getParams().putBoolean(BaiduASRDigitalDialog.PARAM_TIPS_TONE_ENABLE, Config.DIALOG_TIPS_SOUND);
//      mDialog.show();
//
//
//		  mRecognitionListener = new DialogRecognitionListener() {
//
//	            @Override
//	            public void onResults(Bundle results) {
//
//	                ArrayList<String> rs = results != null ? results
//	                        .getStringArrayList(RESULTS_RECOGNITION) : null;
//	                        System.out.println("aaaaaaaaa"+rs.get(0));
//	                if (rs != null && rs.size() > 0) {
//	                    //mResult.setText(rs.get(0));
//	                	System.out.println("aaaaaaaaa"+rs.get(0));
//	                }
//
//	            }
//	        };
//

        Button btnRegist = (Button) findViewById(R.id.btn_bind_phone);
        View btnContact = findViewById(R.id.rl_contact);
        tvNum = (TextView) findViewById(R.id.tv_num);
        tvNum.setVisibility(View.GONE);
        btnRegist.setOnClickListener(this);
        btnContact.setOnClickListener(this);

        //int a = 2/0;

//		UserForYifa user =  UserForYifa.getCurrentUser(this,UserForYifa.class);
//		if(user != null){
//		    // 允许用户使用应用
//			System.out.println("you   "  + user.getUsername());
//			startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
//			finish();
//			BmobQuery<UserForYifa> bmobQuery = new BmobQuery<UserForYifa>();
//			bmobQuery.addWhereNear("gpsAdd", new BmobGeoPoint(118.183812,35.766126));
//			bmobQuery.setLimit(10);    //获取最接近用户地点的10条数据
//			bmobQuery.findObjects(this, new FindListener<UserForYifa>() {
//			    @Override
//			    public void onSuccess(List<UserForYifa> object) {
//			        // TODO Auto-generated method stub
//			        System.out.println("查询成功：共" + object.size() + "条数据。");
//			    }
//			    @Override
//			    public void onError(int code, String msg) {
//			        // TODO Auto-generated method stub
//			    	  System.out.println("查询失败：" + msg);
//			    }
//			});
//
//		}else{
//		    //缓存用户对象为空时， 可打开用户注册界面…
//			System.out.println("null   没登陆");
////			RegisterPage registerPage = new RegisterPage();
////			registerPage.setRegisterCallback(new EventHandler() {
////				public void afterEvent(int event, int result, Object data) {
////					// 解析注册结果
////					if (result == SMSSDK.RESULT_COMPLETE) {
////						@SuppressWarnings("unchecked")
////						HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
////						String country = (String) phoneMap.get("country");
////						String phone = (String) phoneMap.get("phone");
////						// 提交用户信息
////						registerUser(country, phone);
////					}
////				}
////			});
////			registerPage.show(this);
//		}
        //延迟跳转到主页
        handler.sendEmptyMessageDelayed(0, 200);


        BmobUpdateAgent.update(this);
        BmobUpdateAgent.setUpdateOnlyWifi(false);
//        BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
//            @Override
//            public void onUpdateReturned(int updateStatus, UpdateResponse updateInfo) {
//                // TODO Auto-generated method stub
//                //根据updateStatus来判断更新是否成功
//                System.out.println("updateStatus   "+ updateStatus);
//            }
//        });
    }
    //原来显示对话框，现在直接载入
    private void showAppkeyDialog() {
        setSharePrefrence();
//        initSDK();
//		final Dialog dialog = new Dialog(this, R.style.CommonDialog);
//		dialog.setContentView(R.layout.smssdk_set_appkey_dialog);
//		final EditText etAppKey = (EditText) dialog.findViewById(R.id.et_appkey);
//		etAppKey.setText(APPKEY);
//		final EditText etAppSecret = (EditText) dialog.findViewById(R.id.et_appsecret);
//		etAppSecret.setText(APPSECRET);
//		OnClickListener ocl = new OnClickListener() {
//			public void onClick(View v) {
//				if (v.getId() == R.id.btn_dialog_ok) {
//					APPKEY = etAppKey.getText().toString().trim();
//					APPSECRET = etAppSecret.getText().toString().trim();
//					if (TextUtils.isEmpty(APPKEY) || TextUtils.isEmpty(APPSECRET)) {
//						Toast.makeText(v.getContext(), R.string.smssdk_appkey_dialog_title,
//								Toast.LENGTH_SHORT).show();
//					} else {
//						dialog.dismiss();
//						setSharePrefrence();
//						initSDK();
//					}
//				} else {
//					dialog.dismiss();
//					finish();
//				}
//			}
//		};
//		dialog.findViewById(R.id.btn_dialog_ok).setOnClickListener(ocl);
//		dialog.findViewById(R.id.btn_dialog_cancel).setOnClickListener(ocl);
//		dialog.setCancelable(false);
//		dialog.show();
    }

//    private void initSDK() {
//        // 初始化短信SDK
//        SMSSDK.initSDK(this, APPKEY, APPSECRET);
//        final Handler handler = new Handler(this);
//        EventHandler eventHandler = new EventHandler() {
//            public void afterEvent(int event, int result, Object data) {
//                Message msg = new Message();
//                msg.arg1 = event;
//                msg.arg2 = result;
//                msg.obj = data;
//                handler.sendMessage(msg);
//            }
//        };
//        // 注册回调监听接口
//        SMSSDK.registerEventHandler(eventHandler);
//        ready = true;
//
//        // 获取新好友个数
//        showDialog();
//        SMSSDK.getNewFriendsCount();
//
//    }

    private void loadSharePrefrence() {
        SharedPreferences p = getSharedPreferences("SMSSDK_SAMPLE", Context.MODE_PRIVATE);
        APPKEY = p.getString("APPKEY", APPKEY);
        APPSECRET = p.getString("APPSECRET", APPSECRET);
    }

    private void setSharePrefrence() {
        SharedPreferences p = getSharedPreferences("SMSSDK_SAMPLE", Context.MODE_PRIVATE);
        Editor edit = p.edit();
        edit.putString("APPKEY", APPKEY);
        edit.putString("APPSECRET", APPSECRET);
        edit.commit();
    }

    protected void onDestroy() {
        if (ready) {
            // 销毁回调监听接口
            SMSSDK.unregisterAllEventHandler();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ready) {
            // 获取新好友个数
            showDialog();
            SMSSDK.getNewFriendsCount();
            System.out.println("加载数据完了吗");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_phone:
                // 打开注册页面
                RegisterPage registerPage = new RegisterPage();
                registerPage.setRegisterCallback(new EventHandler() {
                    public void afterEvent(int event, int result, Object data) {
                        // 解析注册结果
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            @SuppressWarnings("unchecked")
                            HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                            String country = (String) phoneMap.get("country");
                            String phone = (String) phoneMap.get("phone");
                            // 提交用户信息
                            registerUser(country, phone);
                        }
                    }
                });
                registerPage.show(this);
                break;
            case R.id.rl_contact:
//			tvNum.setVisibility(View.GONE);
//			// 打开通信录好友列表页面
//			ContactsPage contactsPage = new ContactsPage();
//			contactsPage.show(this);




                startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
                break;
        }
    }

    public boolean handleMessage(Message msg) {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        int event = msg.arg1;
        int result = msg.arg2;
        Object data = msg.obj;
        if (event == SMSSDK.EVENT_SUBMIT_USER_INFO) {
            // 短信注册成功后，返回MainActivity,然后提示新好友
            if (result == SMSSDK.RESULT_COMPLETE) {
                //Toast.makeText(this, R.string.smssdk_user_info_submited, Toast.LENGTH_SHORT).show();
            } else {
                ((Throwable) data).printStackTrace();
            }
        } else if (event == SMSSDK.EVENT_GET_NEW_FRIENDS_COUNT){
            if (result == SMSSDK.RESULT_COMPLETE) {
                refreshViewCount(data);
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
        return false;
    }
    // 更新，新好友个数
    private void refreshViewCount(Object data){
        int newFriendsCount = 0;
        try {
            newFriendsCount = Integer.parseInt(String.valueOf(data));
        } catch (Throwable t) {
            newFriendsCount = 0;
        }
        if(newFriendsCount > 0){
            tvNum.setVisibility(View.VISIBLE);
            tvNum.setText(String.valueOf(newFriendsCount));
        }else{
            tvNum.setVisibility(View.GONE);
        }
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
    }
    // 弹出加载框
    private void showDialog(){
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
        }
        pd = CommonDialog.ProgressDialog(this);
        pd.show();
    }
    // 提交用户信息
    private void registerUser(String country, String phone) {
        Random rnd = new Random();
        int id = Math.abs(rnd.nextInt());
        String uid = String.valueOf(id);
        String nickName = "易发快运安卓用户" + uid;
        String avatar = AVATARS[id % 12];
        SMSSDK.submitUserInfo(uid, nickName, avatar, country, phone);
        System.out.println("MOB短信验证成功");

        signUser(phone);


    }
    /**
     * 注册用户
     */
    private void signUser(final String phone){
        UserForYifa user = new UserForYifa();
        user.setUsername(phone);
        user.setPassword("123456");
        user.setIBanlance(0);
        user.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                //ToastUtil.show(getApplicationContext(), "注册成功:");
                startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
            }
            @Override
            public void onFailure(int code, String msg) {
                //ToastUtil.show(getApplicationContext(), code+"注册失败:原因 "+msg+code);
                System.out.println("code" + code + (code==202));
                if (code == 202) {
                    //登陆
                    UserForYifa bu2 = new UserForYifa();
                    bu2.setUsername(phone);
                    bu2.setPassword("123456");
                    bu2.login(getApplicationContext(), new SaveListener() {
                        @Override
                        public void onSuccess() {
                            // TODO Auto-generated method stub
                            startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
                        }
                        @Override
                        public void onFailure(int code, String msg) {
                            // TODO Auto-generated method stub
                            System.out.println();
                            ToastUtil.show(getApplicationContext(), "登录失败:原因 "+msg+code);
                        }
                    });

                }

            }
        });

    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            //System.out.println("啊啊啊啊啊啊啊啊啊");
            //startActivity(new Intent().setClass(getApplicationContext(), SelectCheXingActivity.class));//直接跳转
            startActivity(new Intent().setClass(getApplicationContext(), IndexActivity.class));//直接跳转
            //startActivity(new Intent().setClass(getApplicationContext(), SelectWuLiuActivity.class));//直接跳转
            finish();
        }
    };

}
