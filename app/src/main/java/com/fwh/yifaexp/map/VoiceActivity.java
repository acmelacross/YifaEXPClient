package com.fwh.yifaexp.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.fwh.yifaexp.R;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;


public class VoiceActivity extends Activity {
	ImageView animationIV ;
	private static String TAG = VoiceActivity.class.getSimpleName();
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	private EditText ETVoiceInput = null;
	private TextView title = null;
//	private BaiduASRDigitalDialog mDialog = null;
//	 private DialogRecognitionListener mRecognitionListener;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voice);
		initView();
		
		SpeechUtility.createUtility(VoiceActivity.this, SpeechConstant.APPID +"=5566a266");   
		//1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener    
		SpeechRecognizer mIat= SpeechRecognizer.createRecognizer(getApplicationContext(), null);    
		//2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类    
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");    
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");    
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");    
		//3.开始听写   
		mIat.startListening(mRecoListener);  
		getVoiceDialog();
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
//		
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
//	            	System.out.println("onResultsonResults");
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
	}
	private void initView() {
		// TODO Auto-generated method stub
		ETVoiceInput = (EditText) findViewById(R.id.ETVoiceInput);
		animationIV = (ImageView)findViewById(R.id.animationIV);
		animationIV.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				getVoiceDialog();
			}
		});
		title = (TextView)findViewById(R.id.title);
		title.setText("大声说出您的需求");
	}
	/**
	 * 启动讯飞语音
	 */
	private void getVoiceDialog() {
		// TODO Auto-generated method stub
		//1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener  
		RecognizerDialog    iatDialog = new RecognizerDialog(this,mInitListener);  
		//2.设置听写参数，同上节  
		//3.设置回调接口  
		iatDialog.setListener(recognizerDialogListener);  
		//4.开始听写  Ô
		iatDialog.show();  
	}
	
	
	
	//听写监听器    
			private RecognizerListener mRecoListener = new RecognizerListener(){    
			//听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；    
			//一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；    
			//关于解析Json的代码可参见MscDemo中JsonParser类；    
			//isLast等于true时会话结束。    
			public void onResult(RecognizerResult results, boolean isLast) {    
			            Log.d("Result:",results.getResultString ());
			System.out.println("-------" + results.getResultString ());            
			}    
			//会话发生错误回调接口    
			    public void onError(SpeechError error) {    
			error.getPlainDescription(true); //获取错误码描述
			}

		@Override
		public void onVolumeChanged(int i, byte[] bytes) {

		}

		//开始录音
			    public void onBeginOfSpeech() {}    
			    //音量值0~30    
			    public void onVolumeChanged(int volume){}    
			    //结束录音    
			    public void onEndOfSpeech() {}    
			    //扩展用接口    
			    public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {}
				 
			};    

			/**
			 * 初始化监听器。
			 */
			public InitListener mInitListener = new InitListener() {

				@Override
				public void onInit(int code) {
					//Log.d(TAG, "SpeechRecognizer init() code = " + code);
					if (code != ErrorCode.SUCCESS) {
						//ToastUtil.show(getApplicationContext(), "初始化失败，错误码：" + code);
					}
				}
			};
			/**
			 * 听写UI监听器
			 */
			private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
				public void onResult(RecognizerResult results, boolean isLast) {
				//printResult(results);
				System.out.println("------------" + results.getResultString());
				printResult(results);
				}

				@Override
				public void onError(SpeechError arg0) {
					// TODO Auto-generated method stub
					System.out.println("RecognizerDialogListener" + arg0.getErrorCode());
				}
			};
	
			private void printResult(RecognizerResult results) {
				String text = com.fwh.utils.JsonParser.parseIatResult(results.getResultString());

				String sn = null;
				// 读取json结果中的sn字段
				try {
					JSONObject resultJson = new JSONObject(results.getResultString());
					sn = resultJson.optString("sn");
				} catch (JSONException e) {
					e.printStackTrace();
				}

				mIatResults.put(sn, text);

				StringBuffer resultBuffer = new StringBuffer();
				for (String key : mIatResults.keySet()) {
					resultBuffer.append(mIatResults.get(key));
				}
				
				//System.out.println("返回字符串"  + resultBuffer.toString());
				ETVoiceInput.setText(resultBuffer.toString());
			}
}
