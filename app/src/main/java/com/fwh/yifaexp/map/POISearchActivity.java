package com.fwh.yifaexp.map;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;

import cn.bmob.v3.datatype.BmobGeoPoint;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.core.SuggestionCity;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.Inputtips.InputtipsListener;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;
import com.fwh.utils.Constants;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.map.adapter.PoiSearchContentAdapter;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;

public class POISearchActivity extends Activity implements TextWatcher,
		OnPoiSearchListener {
	private int currentPage = 0;// 当前页面，从0开始计数
	private PoiSearch.Query query;// Poi查询条件类
	private PoiSearch poiSearch;// POI搜索
	private String keyWord = "";// 要输入的poi搜索关键字
	private AutoCompleteTextView searchText;// 输入搜索关键字
	private EditText etPOIInfo = null;
	private ListView listPoiContent = null;
	private List<String> listString = null;// 搜索出来的内容集合
	private PoiSearchContentAdapter adapter = null;
	public final static int TAGSELECTSTARTPLACE = 10; //
	public final static int TAGSELECTENDPLACE = 11; // 正常选择结束位置
	public final static int TAGYUYUESTARTPLACE = 12; // 预约选择起始位置
	public final static int TAGYUYUEENDPLACE = 13; // 预约结束起始位置
	int tag = 0;// 标识符Activity传进来的区别
	public List<Tip> placeList = null;
	// public final static int TAGSELECTENDPLACE = 10;
	public static ArrayList<String> infoList = new ArrayList<String>();
	// private CustomerSpinner spinner;
	private static String TAG = VoiceActivity.class.getSimpleName();
	// 用HashMap存储听写结果
	private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
	private EditText ETVoiceInput = null;
	SpeechRecognizer mIat;
	public static boolean isStart = true;// 是否为起点

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_poisearch);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		searchText = (AutoCompleteTextView) findViewById(R.id.etPOIInput);
		searchText.addTextChangedListener(this);
		listPoiContent = (ListView) findViewById(R.id.listPoiContent);
		listString = new ArrayList<String>();
		adapter = new PoiSearchContentAdapter(getApplicationContext(),
				listString);
		listPoiContent.setAdapter(adapter);
		// adapter = new PoiSearchContentAdapter();
		// listPoiContent.setAdapter(adapter);
		listPoiContent.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				String[] temp = adapter.getList().get(position).split(";");
				keyWord = temp[0];
				doSearchQuery();
			}
		});
		etPOIInfo = (EditText) findViewById(R.id.etPOIInfo);
		etPOIInfo.setText("");
		Intent intent = getIntent();
		tag = intent.getIntExtra("tag", 0);
		int expWay = intent.getIntExtra("expWay", 0);
		if (tag == TAGYUYUEENDPLACE) {
			etPOIInfo.setVisibility(View.GONE);
		}
		// 返回按钮
		findViewById(R.id.imgvPoiTitleBack).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						finish();
					}
				});


		findViewById(R.id.imgvMapIndexChangeTextVoice).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						// 3.开始听写
						//mIat.startListening(mRecoListener);
						getVoiceDialog();
					}
				});

		SpeechUtility.createUtility(POISearchActivity.this,
				SpeechConstant.APPID + "=5566a266");
		// 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
		mIat = SpeechRecognizer.createRecognizer(getApplicationContext(), null);
		// 2.设置听写参数，详见《科大讯飞MSC API手册(Android)》SpeechConstant类
		mIat.setParameter(SpeechConstant.DOMAIN, "iat");
		mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
		mIat.setParameter(SpeechConstant.ACCENT, "mandarin ");
		mIat.setParameter(SpeechConstant.ASR_PTT, "0");

	}

	/**
	 * 开始进行poi搜索
	 */
	protected void doSearchQuery() {
		currentPage = 0;
		query = new PoiSearch.Query(keyWord, "", "");// 第一个参数表示搜索字符串，第二个参数表示poi搜索类型，第三个参数表示poi搜索区域（空字符串代表全国）
		query.setPageSize(30);// 设置每页最多返回多少条poiitem
		query.setPageNum(currentPage);// 设置查第一页
		poiSearch = new PoiSearch(this, query);
		poiSearch.setOnPoiSearchListener(this);
		poiSearch.searchPOIAsyn();
	}



	@Override
	public void afterTextChanged(Editable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		String newText = arg0.toString().trim();
		Inputtips inputTips = new Inputtips(POISearchActivity.this,
				new InputtipsListener() {
					@Override
					public void onGetInputtips(List<Tip> tipList, int rCode) {
						System.out.println(tipList.size()+"rCode    "  +rCode);
						if (rCode == 1000) {// 正确返回
							listString = new ArrayList<String>();
							for (int i = 0; i < tipList.size(); i++) {

								if (tipList.get(i).getAdcode() != null
										&& !"".equals(tipList.get(i)
												.getAdcode())) {
									listString.add(tipList.get(i).getName()
											+ ";"
											+ tipList.get(i).getDistrict()
											+ "  邮编:"
											+ tipList.get(i).getAdcode());
								}

							}
							adapter.setList(listString);
							adapter.notifyDataSetChanged();
							placeList = tipList;
						}
					}
				});
		try {
			inputTips.requestInputtips(newText, "");// 第一个参数表示提示关键字，第二个参数默认代表全国，也可以为城市区号

		} catch (AMapException e) {
			e.printStackTrace();
		}
	}



	@Override
	public void onPoiSearched(PoiResult result, int rCode) {
		// TODO Auto-generated method stub
		if (rCode == 1000) {
			if (result != null && result.getQuery() != null) {// 搜索poi的结果
				if (result.getQuery().equals(query)) {// 是否是同一条
					// poiResult = result;
					// 取得搜索到的poiitems有多少页

					try {
						// List<PoiItem> poiItems = result.getPois();//
						// 取得第一页的poiitem数据，页数从数字0开始
						// List<SuggestionCity> suggestionCities = result
						// .getSearchSuggestionCitys();//
						// 当搜索不到poiitem数据时，会返回含有搜索关键字的城市信息
						// System.out.println(result.getPois()+"suggestionCities ");
						if (result.getPois().size() <= 0) {
							ToastUtil.show(POISearchActivity.this,
									"此关键字未在百度登记地理位置信息,请更换关键字");
							return;
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
					if (!isStart) {// 重点

						Config.getInstance().gpsAddFinish = new BmobGeoPoint(
								result.getPois().get(0).getLatLonPoint()
										.getLongitude(), result.getPois()
										.get(0).getLatLonPoint().getLatitude());
						Config.getInstance().gpsAddFinishStr = result.getPois()
								.get(0).getCityName()
								+ result.getPois().get(0).getAdName()
								+ result.getPois().get(0).toString();
						// 选择回调
						Intent intent2 = new Intent();
						intent2.putExtra("weidu", result.getPois().get(0)
								.getLatLonPoint().getLatitude());
						intent2.putExtra("jingdu", result.getPois().get(0)
								.getLatLonPoint().getLongitude());
						intent2.putExtra("miaoshu", result.getPois().get(0)
								.getCityName()
								+ result.getPois().get(0).getAdName()
								+ result.getPois().get(0).toString());
						intent2.putExtra("info", etPOIInfo.getText().toString());
						//System.out.println("----0 " +Config.getInstance().currentCity);
					//ToastUtil.show(getApplicationContext(),result.getPois().get(0).getAdName()+result.getPois().get(0).getAdCode());
						//if (Config.getInstance().currentCity.equals(result.getPois().get(0).getCityName())) {// 判断是否同城物流 判断同城或者物流  区分同城或者物流
																	// 当前同城
						if (!result.getPois().get(0).getAdName().endsWith("市")&&(Config.getInstance().currentCity.equals(result.getPois().get(0).getCityName()) ||

								result.getPois().get(0).getCityName().equals(	Config.getInstance().gpsAddStartCityStr))) {
							Config.getInstance().ExpWay = Constants.EXP_WAY_BY_TONGCHENG;
							Config.getInstance().ExpWayStr = Constants.EXP_WAY_STR_TONGCHENG;
							System.out.println("tongcheng   "
									+ Config.getInstance().gpsAddStartStr
									+ "---"

												+ result.getPois().get(0).getCityName());
						} else {
							Config.getInstance().ExpWay = Constants.EXP_WAY_BY_WULIU;
							Config.getInstance().ExpWayStr = Constants.EXP_WAY_STR_WULIU;
							System.out.println("wuliu   "
									+ Config.getInstance().gpsAddStartStr
									+ "---"
									+ result.getPois().get(0).getCityName());
						}



						switch (tag) {
						// 正常选择结束地点
						case TAGSELECTENDPLACE:
							// setResult(IndexActivity.SELECT_ADDRESS_RULE_END_PLACE,
							// intent2);
							// finish();
							startActivity(intent2.setClass(this,
									HuoInfoActivity.class));
							break;
						case TAGYUYUESTARTPLACE:
							// setResult(POISearchActivity.TAGYUYUESTARTPLACE,
							// intent2);
							// finish();
							startActivity(intent2.setClass(this,
									HuoInfoActivity.class));
							break;
						case TAGYUYUEENDPLACE:
							// setResult(POISearchActivity.TAGYUYUEENDPLACE,
							// intent2);
							// finish();
							startActivity(intent2.setClass(this,
									HuoInfoActivity.class));
							break;
						default:
							break;
						}
						finish();
					} else {
						Config.getInstance().gpsAddStart = new BmobGeoPoint(
								result.getPois().get(0).getLatLonPoint()
										.getLongitude(), result.getPois()
										.get(0).getLatLonPoint().getLatitude());
						Config.getInstance().gpsAddStartStr = result.getPois()
								.get(0).getCityName()
								+ result.getPois().get(0).getAdName()
								+ result.getPois().get(0).toString();

						Config.getInstance().gpsAddStartCityStr= result.getPois()
								.get(0).getCityName();
						Intent intent2 = new Intent();
						intent2.putExtra("weidu", result.getPois().get(0)
								.getLatLonPoint().getLatitude());
						intent2.putExtra("jingdu", result.getPois().get(0)
								.getLatLonPoint().getLongitude());
						intent2.putExtra("miaoshu", result.getPois().get(0)
								.getCityName()
								+ result.getPois().get(0).getAdName()
								+ result.getPois().get(0).toString());
						intent2.putExtra("info", etPOIInfo.getText().toString());
						setResult(IndexActivity.SELECT_ADDRESS_WITH_MAP,
								intent2);
						finish();
					}

					// if (poiItems != null && poiItems.size() > 0) {
					// aMap.clear();// 清理之前的图标
					// PoiOverlay poiOverlay = new PoiOverlay(aMap, poiItems);
					// poiOverlay.removeFromMap();
					// poiOverlay.addToMap();
					// poiOverlay.zoomToSpan();
					// } else if (suggestionCities != null
					// && suggestionCities.size() > 0) {
					// showSuggestCity(suggestionCities);
					// } else {
					// ToastUtil.show(POISearchActivity.this,
					// R.string.no_result);
					// }
				}
			} else {
				ToastUtil.show(POISearchActivity.this, R.string.no_result);
			}
		} else if (rCode == 27) {
			ToastUtil.show(POISearchActivity.this, R.string.error_network);
		} else if (rCode == 32) {
			ToastUtil.show(POISearchActivity.this, R.string.error_key);
		} else {
			ToastUtil.show(POISearchActivity.this,
					getString(R.string.error_other) + rCode);
		}
	}

	/**
	 * poi没有搜索到数据，返回一些推荐城市的信息
	 */
	private void showSuggestCity(List<SuggestionCity> cities) {
		String infomation = "推荐城市\n";
		for (int i = 0; i < cities.size(); i++) {
			infomation += "城市名称:" + cities.get(i).getCityName() + "城市区号:"
					+ cities.get(i).getCityCode() + "城市编码:"
					+ cities.get(i).getAdCode() + "\n";
		}
		ToastUtil.show(POISearchActivity.this, infomation);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			infoList.clear();
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 启动讯飞语音
	 */
	private void getVoiceDialog() {
		// TODO Auto-generated method stub
		// 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
		RecognizerDialog iatDialog = new RecognizerDialog(this, mInitListener);
		// 2.设置听写参数，同上节
		// 3.设置回调接口
		iatDialog.setListener(recognizerDialogListener);
		// 4.开始听写 Ô
		iatDialog.show();
	}

//	// 听写监听器
//	private RecognizerListener mRecoListener = new RecognizerListener() {
//		// 听写结果回调接口(返回Json格式结果，用户可参见附录12.1)；
//		// 一般情况下会通过onResults接口多次返回结果，完整的识别内容是多次结果的累加；
//		// 关于解析Json的代码可参见MscDemo中JsonParser类；
//		// isLast等于true时会话结束。
//		public void onResult(RecognizerResult results, boolean isLast) {
//			Log.d("Result:", results.getResultString());
//			System.out.println("-------" + results.getResultString());
//		}
//
//		// 会话发生错误回调接口
//		public void onError(SpeechError error) {
//			error.getPlainDescription(true); // 获取错误码描述
//		}
//
//		// 开始录音
//		public void onBeginOfSpeech() {
//		}
//
//		// 音量值0~30
//		public void onVolumeChanged(int volume) {
//		}
//
//		// 结束录音
//		public void onEndOfSpeech() {
//		}
//
//		// 扩展用接口
//		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
//		}
//
//	};

	/**
	 * 初始化监听器。
	 */
	public InitListener mInitListener = new InitListener() {

		@Override
		public void onInit(int code) {
			// Log.d(TAG, "SpeechRecognizer init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
				// ToastUtil.show(getApplicationContext(), "初始化失败，错误码：" + code);
			}
		}
	};
	/**
	 * 听写UI监听器
	 */
	private RecognizerDialogListener recognizerDialogListener = new RecognizerDialogListener() {
		public void onResult(RecognizerResult results, boolean isLast) {
			// printResult(results);
			System.out.println("------------" + results.getResultString());
			printResult(results);
		}

		@Override
		public void onError(SpeechError arg0) {
			// TODO Auto-generated method stub
			System.out
					.println("RecognizerDialogListener" + arg0.getErrorCode());
		}
	};

	private void printResult(RecognizerResult results) {
		String text = com.fwh.utils.JsonParser.parseIatResult(results
				.getResultString());

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

		// System.out.println("返回字符串" + resultBuffer);
		searchText.setText(resultBuffer.toString());
	}

	@Override
	public void onPoiItemSearched(PoiItem poiItem, int i) {

	}
}
