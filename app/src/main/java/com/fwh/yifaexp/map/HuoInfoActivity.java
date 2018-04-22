package com.fwh.yifaexp.map;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.fwh.commons.time.SlideDateTimeListener;
import com.fwh.commons.time.SlideDateTimePicker;
import com.fwh.utils.Constants;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class HuoInfoActivity extends FragmentActivity implements OnClickListener{
	//private String huoType = "";//货物类型
	private SimpleDateFormat mFormatter = new SimpleDateFormat(
			"yyyy年MMMMdd日hh:mm aa");
	private String cheType = "";//车辆类型
	private  Dialog dialogHuoLeiXing,dialogZhongliang ,dialogCheLeiXing,dialogZhengSan= null;//货物类型Dialog
	private ImageView ivMapIndexTongCheng,ivMapIndexWuLiu,ivMapIndexKuaidi = null;
	private TextView tvMapIndexKuaidi,tvMapIndexWuliu,tvMapIndexTongCheng,tvHuoInfoConfirm = null;
	private TextView tvMapIndexYuYue,tvMapHuoChakanPayInfo = null;
	private TextView tvHuoInfoCheXingXuanze,tvHuoInfoZhongliangXuanze,tvHuoInfoZhuanSanXuanze;
	private RelativeLayout rlHuoInfoLeixing,rlHuoInfoZhongliang,rlHuoInfoCheXing,rlHuoInfoZhuansan=null;
	private TextView tv_Huo_confirm,tv_huo_ZhongPaoHuo,tv_huo_ZhongZhonghuo,tvHuoInfoZhuanSan,tv_huo_info_sanhuo,tv_huo_info_zhengche,tv_Huo_danwei;//重量dialog 控件
	private EditText ed_Huo_Zhong,tvHuoInfoLeixingXuanze;
	SoundPool soundPool;
	//private int  expWay  = IndexActivity.SELECT_EXP_WAY_TONGCHENG;
	private boolean bYuYue = false;
	
	//@ViewInject(R.id.tvXianShiDa1)//当日达
	TextView tvXianShiDa1;
	//@ViewInject(R.id.tvXianShiDa2)//一小时
	TextView tvXianShiDa2;
	//@ViewInject(R.id.tvXianShiDa3)//两小时
	TextView tvXianShiDa3;
	//@ViewInject(R.id.rlHuoInfoaWuLiuChe)//物流车
	RelativeLayout rlHuoInfoaWuLiuChe;
	//@ViewInject(R.id.rlHuoInfoWuLiuGongSi)//物流公司
	RelativeLayout rlHuoInfoWuLiuGongSi;
	//@ViewInject(R.id.rlXianShiDa1)//限时达
	RelativeLayout rlXianShiDa1;
	
	
	//@ViewInject(R.id.tvHuoInfoHuoInfoWuLiuGongSi)//物流公司
	TextView tvHuoInfoHuoInfoWuLiuGongSi;
	//@ViewInject(R.id.tvHuoInfoWuLiuChe)//物流车
	EditText tvHuoInfoWuLiuChe;
	EditText tvHuoInfoTiJi;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huo_info);
		//ViewUtils.inject(this);
		initView();
	}

	private void initView() {
		tvXianShiDa1 = (TextView)findViewById(R.id.tvXianShiDa1);
		tvXianShiDa2= (TextView)findViewById(R.id.tvXianShiDa2);
		tvXianShiDa3= (TextView)findViewById(R.id.tvXianShiDa3);
		tvHuoInfoHuoInfoWuLiuGongSi= (TextView)findViewById(R.id.tvHuoInfoHuoInfoWuLiuGongSi);
		rlHuoInfoaWuLiuChe= (RelativeLayout)findViewById(R.id.rlHuoInfoaWuLiuChe);
		rlHuoInfoWuLiuGongSi= (RelativeLayout)findViewById(R.id.rlHuoInfoWuLiuGongSi);
		rlXianShiDa1= (RelativeLayout)findViewById(R.id.rlXianShiDa1);
		tvHuoInfoWuLiuChe		= (EditText)findViewById(R.id.tvHuoInfoWuLiuChe);

		tvHuoInfoTiJi = (EditText)findViewById(R.id.tvHuoInfoTiJi);

	System.out.println(Config.getInstance().ExpWay+"Config.getInstance().ExpWay" + Constants.EXP_WAY_BY_TONGCHENG);
		if (Config.getInstance().ExpWay ==Constants.EXP_WAY_BY_TONGCHENG ) {//同城 不 显示 物流车 和物流公司
			rlHuoInfoaWuLiuChe.setVisibility(View.GONE);
			rlHuoInfoWuLiuGongSi.setVisibility(View.GONE);
		}else{//显示 物流车 和物流公司 同城以外
			rlHuoInfoaWuLiuChe.setVisibility(View.VISIBLE);
			rlHuoInfoWuLiuGongSi.setVisibility(View.VISIBLE);
			rlXianShiDa1.setVisibility(View.GONE);
		}
		
		// TODO Auto-generated method stub
		//Config.getInstance().ExpHuoInfoType="重货";
		Config.getInstance().isJingZhun = false;//取消精准发货;
		((TextView)findViewById(R.id.title)).setText("输入货物信息");
		tvMapIndexYuYue = (TextView)findViewById(R.id.tvMapIndexYuYue);
		tvMapIndexYuYue.setOnClickListener(this); 
		tvHuoInfoLeixingXuanze =(EditText) findViewById(R.id.tvHuoInfoLeixingXuanze);
		tvHuoInfoLeixingXuanze.setOnClickListener(this);      
		tvHuoInfoCheXingXuanze =(TextView) findViewById(R.id.tvHuoInfoCheXingXuanze);
		tvHuoInfoCheXingXuanze.setOnClickListener(this);
		tvHuoInfoZhongliangXuanze =(TextView) findViewById(R.id.tvHuoInfoZhongliangXuanze);
		tvHuoInfoZhongliangXuanze	.setOnClickListener(this);
		tvHuoInfoZhuanSanXuanze =(TextView) findViewById(R.id.tvHuoInfoZhuanSanXuanze);
		tvHuoInfoZhuanSanXuanze	.setOnClickListener(this);
		soundPool= new SoundPool(10,AudioManager.STREAM_SYSTEM,5);
		soundPool.load(this,R.raw.voice_booking,1);
		soundPool.load(this,R.raw.voice_now,2);
		//soundPool.play(1,1, 1, 0, 0, 1);
		tvHuoInfoConfirm  = (TextView)findViewById(R.id.tvHuoInfoConfirm);
		tvHuoInfoConfirm.setOnClickListener(this);
		
		ivMapIndexTongCheng = (ImageView) findViewById(R.id.ivMapIndexTongCheng);
		ivMapIndexTongCheng.setOnClickListener(this);
		ivMapIndexWuLiu = (ImageView) findViewById(R.id.ivMapIndexWuLiu);
		ivMapIndexWuLiu.setOnClickListener(this);
		ivMapIndexKuaidi = (ImageView) findViewById(R.id.ivMapIndexKuaidi);
		ivMapIndexKuaidi.setOnClickListener(this);
		tvMapIndexKuaidi = (TextView) findViewById(R.id.tvMapIndexKuaidi);
		tvMapIndexKuaidi.setOnClickListener(this);
		tvMapIndexWuliu = (TextView) findViewById(R.id.tvMapIndexWuliu);
		tvMapIndexWuliu.setOnClickListener(this);
		tvMapIndexTongCheng = (TextView) findViewById(R.id.tvMapIndexTongCheng);
		tvMapIndexTongCheng.setOnClickListener(this);
		
		rlHuoInfoLeixing = (RelativeLayout)findViewById(R.id.rlHuoInfoLeixing);
		rlHuoInfoZhongliang = (RelativeLayout)findViewById(R.id.rlHuoInfoZhongliang);
		rlHuoInfoCheXing = (RelativeLayout)findViewById(R.id.rlHuoInfoCheXing);
		rlHuoInfoZhuansan = (RelativeLayout)findViewById(R.id.rlHuoInfoZhuansan);
		//返回按钮
		 findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		 tvMapHuoChakanPayInfo = (TextView)findViewById(R.id.tvMapHuoChakanPayInfo);
		 tvMapHuoChakanPayInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(getApplicationContext(), PayInfoActivity.class));
			}
		});
		TextView tv_user_findcar = (TextView)findViewById(R.id.tv_user_findcar);
		tv_user_findcar.setVisibility(View.VISIBLE);
		 findViewById(R.id.tv_user_findcar).setOnClickListener(
					this);
		 //选择物流公司
		 tvHuoInfoHuoInfoWuLiuGongSi.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent().setClass(getApplicationContext(), SelectWuLiuActivity.class));
			}
		});
		 //物流车
		 tvHuoInfoCheXingXuanze.setOnClickListener(new OnClickListener() {
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					startActivity(new Intent().setClass(getApplicationContext(), SelectCheXingActivity.class));
				}
			});
	}

	
	/*
	 * 显示货物类型Dialog
	 * */
	   private void showHuoLeixingDialog() {
	        // TODO Auto-generated method stub
		   //1、自定义Dialog
		   dialogHuoLeiXing= new Dialog(this,R.style.dialog_white);//s, R.style.Theme_dialog
		 //　2、窗口布局
		 View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_huo_info,null);
		//　3、把设定好的窗口布局放到dialog中
		 dialogHuoLeiXing.setContentView(contentView);
		//　4、设定点击窗口空白处取消会话
		 dialogHuoLeiXing.setCanceledOnTouchOutside(true);
		 //　5、具体的操作
		 //　ListView msgView = (ListView)contentView.findViewById(R.id.listview_flow_list);
		 //　6、展示窗口
		 dialogHuoLeiXing.show();
		 contentView.findViewById(R.id.tv_huo_info_shebei).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_kuangchan).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_jiancai).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_shipin).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_shucai).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_shengxian).setOnClickListener(this);
		 
		 contentView.findViewById(R.id.tv_huo_info_yaopin).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_huagong).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_mucai).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_jiachu).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_fangzhipin).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_riyongpin).setOnClickListener(this);
		 
		 contentView.findViewById(R.id.tv_huo_info_dianzidianqi).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_nongfuchanpin).setOnClickListener(this);
		 contentView.findViewById(R.id.tv_huo_info_qita).setOnClickListener(this);
		 
	   }
	   /**
	 * 车类型dialog
	 */
	private void showCheLeixingDialog() {
		dialogCheLeiXing= new Dialog(this,R.style.dialog_white);//s, R.style.Theme_dialog
		 View contentViewCheLeiXing = LayoutInflater.from(this).inflate(R.layout.dialog_che_info,null);
		 dialogCheLeiXing.setContentView(contentViewCheLeiXing);
		 dialogCheLeiXing.setCanceledOnTouchOutside(true);
		 dialogCheLeiXing.show();
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_baowenche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_dilanche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_gaodiban).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_gaolanche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_jizhuangxiang).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_lengcangche).setOnClickListener(this);
		 
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_pingbanche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_qita).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_tielongche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_weixinpin).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_xiangshiche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_zixiehuoche).setOnClickListener(this);
		 
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_zhonglanche).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info_guanshiche).setOnClickListener(this);
	}
	   /**
	 * 车类型dialog
	 */
	private void showCheLeixingDialog2() {
		dialogCheLeiXing= new Dialog(this,R.style.dialog_white);//s, R.style.Theme_dialog
		 View contentViewCheLeiXing = LayoutInflater.from(this).inflate(R.layout.dialog_che_info2,null);
		 dialogCheLeiXing.setContentView(contentViewCheLeiXing);
		 dialogCheLeiXing.setCanceledOnTouchOutside(true);
		 dialogCheLeiXing.show();
		
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info2_sanlun).setOnClickListener(this);
		 contentViewCheLeiXing.findViewById(R.id.tv_che_info2_dahuoche).setOnClickListener(this);
	}
	/*
	 * 货物重量dialog
	 * */
	private void showZhongliangDialog() {
		Config.getInstance().ExpHuoInfoType="重货";//恢复 货物类型
		dialogZhongliang= new Dialog(this,R.style.dialog_white);//s, R.style.Theme_dialog
		 View contentViewCheLeiXing = LayoutInflater.from(this).inflate(R.layout.dialog_huo_zhong,null);
		 
		 tv_Huo_confirm = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_Huo_confirm);
		 tv_Huo_confirm.setOnClickListener(this);
		 tv_huo_ZhongPaoHuo = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_huo_ZhongPaoHuo);
		 tv_huo_ZhongPaoHuo.setOnClickListener(this);
		 tv_huo_ZhongZhonghuo  = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_huo_ZhongZhonghuo);
		 tv_huo_ZhongZhonghuo.setOnClickListener(this);
		 ed_Huo_Zhong  = (EditText) contentViewCheLeiXing.findViewById(R.id.ed_Huo_Zhong);
		 tv_Huo_danwei  = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_Huo_danwei);
		 dialogZhongliang.setContentView(contentViewCheLeiXing);
		 dialogZhongliang.setCanceledOnTouchOutside(true);
		 dialogZhongliang.show();
	
	}
	private void showZhengSanDialog() {
		dialogZhengSan= new Dialog(this,R.style.dialog_white);//s, R.style.Theme_dialog
		 View contentViewCheLeiXing = LayoutInflater.from(this).inflate(R.layout.dialog_huo_info_zheng_san,null);
		 
		 tv_huo_info_zhengche = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_huo_info_zhengche);
		 tv_huo_info_zhengche.setOnClickListener(this);
		 tv_huo_info_sanhuo = (TextView) contentViewCheLeiXing.findViewById(R.id.tv_huo_info_sanhuo);
		 tv_huo_info_sanhuo.setOnClickListener(this);

		 
		 dialogZhengSan.setContentView(contentViewCheLeiXing);
		 dialogZhengSan.setCanceledOnTouchOutside(true);
		 dialogZhengSan.show();
	
	}
	
	
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.tvHuoInfoLeixingXuanze://货物类型
			//showHuoLeixingDialog();
			//startActivity(new Intent().setClass(getApplicationContext(), ));
			break;
		case R.id.tvHuoInfoCheXingXuanze://车型选择型
//			if (Config.getInstance().ExpWay == Constact.EXP_WAY_BY_TONGCHENG) {
//				showCheLeixingDialog2();
//			}else
//			showCheLeixingDialog();
			
			
			break;
		case R.id.tvHuoInfoZhongliangXuanze://车重量
			showZhongliangDialog();
			break;
		case R.id.tvHuoInfoZhuanSanXuanze://整车三车
			showZhengSanDialog();
			break;
		case R.id.tvMapIndexYuYue://实时预约
			//System.out.println("aaaaaaaaaaaaa");
			bYuYue = !bYuYue;
			if (bYuYue) {
				setBooking();
			}else{
				setBooking();
//				tvMapIndexYuYue.setText("实时");
//				tvMapIndexYuYue.setBackgroundResource(R.drawable.map_index_yuyue_btn_normal);
//				soundPool.play(2,1, 1, 0, 0, 1);
//				tvMapIndexYuYue.setTextColor(getResources().getColorStateList(Color.parseColor("#ff8903"));
			}
			
			break;
			
			
		 case R.id.ivMapIndexTongCheng: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_TONGCHENG);
	        	break;
	        case R.id.ivMapIndexWuLiu: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_WULIU);
	        	break;
	        case R.id.ivMapIndexKuaidi: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_KUAIDI);
	        	break;
	        case R.id.tvMapIndexKuaidi: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_KUAIDI);
	        	break;
	        case R.id.tvMapIndexWuliu: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_WULIU);
	        	break;
	        case R.id.tvMapIndexTongCheng: //选择同城物流快递
	        	changeExpWay(IndexActivity.SELECT_EXP_WAY_TONGCHENG);
	        	break;
			
		
		case R.id.tv_huo_info_shebei:
			Config.getInstance().ExpHuoType="设备";
			tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);
			dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_kuangchan:Config.getInstance().ExpHuoType="矿产";
		tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_jiancai:Config.getInstance().ExpHuoType="建材";dialogHuoLeiXing.dismiss();
		tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);
			break;
		case R.id.tv_huo_info_shipin:Config.getInstance().ExpHuoType="食品";dialogHuoLeiXing.dismiss();
		tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_shucai:Config.getInstance().ExpHuoType="蔬菜";dialogHuoLeiXing.dismiss();
		tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);
			break;
		case R.id.tv_huo_info_shengxian:Config.getInstance().ExpHuoType="生鲜";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
			
		case R.id.tv_huo_info_yaopin:Config.getInstance().ExpHuoType="药品";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_huagong:Config.getInstance().ExpHuoType="化工";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_mucai:Config.getInstance().ExpHuoType="木材";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_jiachu:Config.getInstance().ExpHuoType="家畜";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_fangzhipin:Config.getInstance().ExpHuoType="纺织品";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_riyongpin:Config.getInstance().ExpHuoType="日用品";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_dianzidianqi:Config.getInstance().ExpHuoType="电子电器";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_nongfuchanpin:Config.getInstance().ExpHuoType="农副产品";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
		case R.id.tv_huo_info_qita:Config.getInstance().ExpHuoType="其他类型";tvHuoInfoLeixingXuanze.setText(Config.getInstance().ExpHuoType);dialogHuoLeiXing.dismiss();
			break;
			
		
		case R.id.tv_che_info_baowenche:cheType="保温车";
		Config.getInstance().ExpCheType="保温车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_dilanche:cheType="低栏车";
		Config.getInstance().ExpCheType="低栏车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_gaodiban:cheType="高低板车";
		Config.getInstance().ExpCheType="高低板车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_gaolanche:cheType="高栏车";
		Config.getInstance().ExpCheType="高栏车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_guanshiche:cheType="罐式车";
		Config.getInstance().ExpCheType="罐式车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_jizhuangxiang:cheType="集装箱车";
		Config.getInstance().ExpCheType="集装箱车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);dialogCheLeiXing.dismiss();
		break;
		
		case R.id.tv_che_info_lengcangche:cheType="冷藏车";Config.getInstance().ExpCheType="冷藏车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();break;
		case R.id.tv_che_info_pingbanche:cheType="平板车";Config.getInstance().ExpCheType="平板车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();break;
		case R.id.tv_che_info_qita:cheType="其他类型";Config.getInstance().ExpCheType="其他类型";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();break;
		case R.id.tv_che_info_tielongche:cheType="铁笼车";Config.getInstance().ExpCheType="铁笼车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();break;
		case R.id.tv_che_info_weixinpin:cheType="危险品车";Config.getInstance().ExpCheType="危险品车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();break;
		case R.id.tv_che_info_xiangshiche:cheType="厢式车";Config.getInstance().ExpCheType="厢式车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();;break;
		
		case R.id.tv_che_info_zhonglanche:cheType="中拦车";
		Config.getInstance().ExpCheType="保温车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info_zixiehuoche:cheType="自卸货车";
		Config.getInstance().ExpCheType="保温车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();
		break;
		
		
	//同城下选择	
		case R.id.tv_che_info2_dahuoche:
		Config.getInstance().ExpCheType="小货车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_che_info2_sanlun:
		Config.getInstance().ExpCheType="三轮车";tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		dialogCheLeiXing.dismiss();
		break;
		case R.id.tv_huo_info_sanhuo:
			Config.getInstance().ExpHuoZSInfo=Constants.EXP_GoodZSSanStr;tvHuoInfoZhuanSanXuanze.setText(Config.getInstance().ExpHuoZSInfo);
			dialogZhengSan.dismiss();
			break;
		case R.id.tv_huo_info_zhengche:
				Config.getInstance().ExpHuoZSInfo=Constants.EXP_GoodZSZhengStr;
				tvHuoInfoZhuanSanXuanze.setText(Config.getInstance().ExpHuoZSInfo);
				dialogZhengSan.dismiss();
			break;
		
		
		//货物重量监听
		
		case R.id.tv_huo_ZhongPaoHuo:
		Config.getInstance().ExpHuoInfoType="轻货";
		tv_huo_ZhongPaoHuo.setBackgroundColor(Color.parseColor("#ff8903"));
		tv_huo_ZhongPaoHuo.setTextColor(Color.parseColor("#ffffff"));
		tv_huo_ZhongZhonghuo.setBackgroundColor(Color.parseColor("#ffffff"));
		tv_huo_ZhongZhonghuo.setTextColor(Color.parseColor("#000000"));
		tv_Huo_danwei.setText("立方");
		break;
		case R.id.tv_huo_ZhongZhonghuo:
		Config.getInstance().ExpHuoInfoType="重货";
		tv_huo_ZhongZhonghuo.setBackgroundColor(Color.parseColor("#ff8903"));
		tv_huo_ZhongZhonghuo.setTextColor(Color.parseColor("#ffffff"));
		tv_huo_ZhongPaoHuo.setBackgroundColor(Color.parseColor("#ffffff"));
		tv_huo_ZhongPaoHuo.setTextColor(Color.parseColor("#000000"));
		tv_Huo_danwei.setText("吨");
		break;
		case R.id.tv_Huo_confirm:
			if (!"".equals(Config.getInstance().ExpHuoInfoType)  && !"".equals(ed_Huo_Zhong.getText().toString())) {
				if (Config.getInstance().ExpHuoInfoType.contains("重货")) 
					Config.getInstance().ExpHuoInfoType=Config.getInstance().ExpHuoInfoType+",货重"+ed_Huo_Zhong.getText().toString()+"吨";
				else
					Config.getInstance().ExpHuoInfoType=Config.getInstance().ExpHuoInfoType+",货占"+ed_Huo_Zhong.getText().toString()+"立方";
				
				
				tvHuoInfoZhongliangXuanze.setText(Config.getInstance().ExpHuoInfoType);
				Config.getInstance().ExpHuoWeight=Double.parseDouble(ed_Huo_Zhong.getText().toString());
				
				dialogZhongliang.dismiss();
			}else
				ToastUtil.show(getApplicationContext(), "请将信息输入完整");
		
		break;
		case R.id.tv_user_findcar: // 精准找车
			startActivity(new Intent().setClass(getApplicationContext(),
					FindCarActivity.class));
			break;
		case R.id.tvHuoInfoConfirm://提交
			confirmAll();
		break;
		default:
			break;
		}
	}
	/**
	 * 全部提交
	 */
	private void  confirmAll(){
//		if(Config.getInstance().ExpWay !=Constact.EXP_WAY_BY_KUAIDI &&("".equals(Config.getInstance().ExpHuoType)||"".equals(Config.getInstance().ExpCheType)
//				||Config.getInstance().ExpHuoWeight ==0.0))
		if ("".equals(tvHuoInfoTiJi.getText().toString() ) ) {//tvHuoInfoLeixingXuanze.getText()==null||"".equals(tvHuoInfoLeixingXuanze.getText().toString()) ||

			ToastUtil.show(getApplicationContext(), "请将您的信息输入完整");//+(Config.getInstance().ExpWay !=Constact.EXP_WAY_BY_KUAIDI ) +"".equals(Config.getInstance().ExpHuoType)+
		}
		else{
			//ToastUtil.show(getApplicationContext(), "OK");
			//提交信息
			//commitGoodsInfo();
			try{
				Config.getInstance().ExpHuoTiji = Double.parseDouble(tvHuoInfoTiJi.getText().toString());
			}catch (Exception e){
				ToastUtil.show(getApplicationContext(),"货物体积请输入整数或者小数");
				return;
			}

			startActivity(new Intent().setClass(HuoInfoActivity.this, HuoConfirmSendActivity.class));
			finish();
			//getDrivres();
		}
	}
	
	
	
	/**
	 * 更换快递方式 1快递 2物流 3 同城
	 */
	private void changeExpWay( int way) {
		// TODO Auto-generated method stub
		switch (way) {
		case IndexActivity.SELECT_EXP_WAY_KUAIDI://快递
			Config.getInstance().ExpWay = Constants.EXP_WAY_BY_KUAIDI;
			Config.getInstance().ExpWayStr = Constants.EXP_WAY_STR_KUAIDI;
			//expWay = IndexActivity.SELECT_EXP_WAY_KUAIDI;
			ivMapIndexKuaidi.setBackgroundResource(R.drawable.ic_kuaidi);
			ivMapIndexTongCheng.setBackgroundResource(R.drawable.ic_kwt);
			ivMapIndexWuLiu.setBackgroundResource(R.drawable.ic_kwt);
			setRLVisible(false);
			
			break;
		case IndexActivity.SELECT_EXP_WAY_TONGCHENG://同城
			Config.getInstance().ExpWay = Constants.EXP_WAY_BY_TONGCHENG;
			Config.getInstance().ExpWayStr = Constants.EXP_WAY_STR_TONGCHENG;
			
			//expWay = IndexActivity.SELECT_EXP_WAY_TONGCHENG;
			ivMapIndexKuaidi.setBackgroundResource(R.drawable.ic_kwt);
			ivMapIndexTongCheng.setBackgroundResource(R.drawable.ic_tongcheng);
			ivMapIndexWuLiu.setBackgroundResource(R.drawable.ic_kwt);
			rlHuoInfoZhuansan.setVisibility(View.VISIBLE);
			setRLVisible(true);
			break;
		case IndexActivity.SELECT_EXP_WAY_WULIU:
			Config.getInstance().ExpWay = Constants.EXP_WAY_BY_WULIU;
			Config.getInstance().ExpWayStr = Constants.EXP_WAY_STR_WULIU;
			//expWay = IndexActivity.SELECT_EXP_WAY_WULIU;//物流
			ivMapIndexKuaidi.setBackgroundResource(R.drawable.ic_kwt);
			ivMapIndexTongCheng.setBackgroundResource(R.drawable.ic_kwt);
			ivMapIndexWuLiu.setBackgroundResource(R.drawable.ic_wuliu);
			rlHuoInfoZhuansan.setVisibility(View.GONE);
			setRLVisible(true);
			break;
		default:
			break;
		}
    }  
	/**
	 * 设置 货物信息显示与否
	 */
	private void setRLVisible( boolean bState){
		if (bState) {
			rlHuoInfoLeixing.setVisibility(View.VISIBLE);
			rlHuoInfoCheXing.setVisibility(View.VISIBLE);
			rlHuoInfoZhongliang.setVisibility(View.VISIBLE);
			//复原选项
//			Config.getInstance().ExpHuoType= "";
//			Config.getInstance().ExpCheType= "";
//			Config.getInstance().ExpHuoWeight= 0.0;
//			Config.getInstance().ExpHuoInfoType= "";
		
		}else{
			rlHuoInfoLeixing.setVisibility(View.GONE);
			rlHuoInfoCheXing.setVisibility(View.GONE);
			rlHuoInfoZhongliang.setVisibility(View.GONE);
		}
	}
	/*
	 * 预约时间选择
	 */
	private SlideDateTimeListener listener = new SlideDateTimeListener() {
		@Override
		public void onDateTimeSet(Date date) {
			Toast.makeText(getApplicationContext(),
					"您的预约时间为" + mFormatter.format(date), 5000)
					.show();
			Config.getInstance().EXPOrderTime = mFormatter.format(date);
		}

		// Optional cancel listener
		@Override
		public void onDateTimeCancel() {
			Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT)
					.show();
			bYuYue=false;
			setBooking();
		}
	};
	/**
	 * 设置实时预约
	 */
	private void setBooking(){
		if (bYuYue) {//预约
			tvMapIndexYuYue.setText("预约");
			tvMapIndexYuYue.setBackgroundResource(R.drawable.map_index_yuyue_btn_pressed);
			soundPool.play(1,1, 1, 0, 0, 1);
			new SlideDateTimePicker.Builder(getSupportFragmentManager())
			.setListener(listener).setInitialDate(new Date())
			// .setMinDate(minDate)
			// .setMaxDate(maxDate)
			// .setIs24HourTime(true)
			// .setTheme(SlideDateTimePicker.HOLO_DARK)
			.setIndicatorColor(Color.parseColor("#ff8903")).build().show();
			tvMapIndexYuYue.setTextColor(Color.parseColor("#ffffff"));
		}else{//实时
			tvMapIndexYuYue.setTextColor(Color.parseColor("#ff8903"));
			tvMapIndexYuYue.setText("实时");
			tvMapIndexYuYue.setBackgroundResource(R.drawable.map_index_yuyue_btn_normal);
			soundPool.play(2,1, 1, 0, 0, 1);
			Config.getInstance().EXPOrderTime ="";
		}
	}
	
	@SuppressLint("ResourceAsColor")
	//@OnClick(R.id.tvXianShiDa1)
	public void dangRiClick(View v) { // 当日达
		tvXianShiDa1.setTextColor(getResources().getColorStateList(R.color.white));
		tvXianShiDa1.setBackgroundColor(getResources().getColor(R.color.orange_main));
		
		tvXianShiDa2.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa2.setBackgroundColor(getResources().getColor(R.color.white));
		tvXianShiDa3.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa3.setBackgroundColor(getResources().getColor(R.color.white));
		
		Config.getInstance().XianShiDa = "当日达";
	}
	
	@SuppressLint("ResourceAsColor")
	//@OnClick(R.id.tvXianShiDa2)
	public void yiXiaoShiClick(View v) { // 1小时
		
		tvXianShiDa2.setTextColor(getResources().getColorStateList(R.color.white));
		tvXianShiDa2.setBackgroundColor(getResources().getColor(R.color.orange_main));
		
		tvXianShiDa1.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa1.setBackgroundColor(getResources().getColor(R.color.white));
		tvXianShiDa3.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa3.setBackgroundColor(getResources().getColor(R.color.white));
		Config.getInstance().XianShiDa = "1小时";
	}
	@SuppressLint("ResourceAsColor")
	//@OnClick(R.id.tvXianShiDa3)
	public void liangXiaoShiClick(View v) { // 2小时
		tvXianShiDa3.setTextColor(getResources().getColorStateList(R.color.white));
		tvXianShiDa3.setBackgroundColor(getResources().getColor(R.color.orange_main));
		
		tvXianShiDa2.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa2.setBackgroundColor(getResources().getColor(R.color.white));
		tvXianShiDa1.setTextColor(getResources().getColorStateList(R.color.black));
		tvXianShiDa1.setBackgroundColor(getResources().getColor(R.color.white));
		Config.getInstance().XianShiDa = "2小时";
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (!Config.getInstance().EXPCompany.equals("默认")) {
			tvHuoInfoHuoInfoWuLiuGongSi.setText("已选择物流公司");
		}
		if (!"".equals(Config.getInstance().ExpCheType)) {
			tvHuoInfoCheXingXuanze.setText(Config.getInstance().ExpCheType);
		}
		
	}
}
