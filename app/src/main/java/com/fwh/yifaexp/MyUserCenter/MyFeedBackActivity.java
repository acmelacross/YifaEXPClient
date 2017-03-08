package com.fwh.yifaexp.MyUserCenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

import com.fwh.utils.FailedlWrite;
import com.fwh.utils.ToastUtil;
import com.fwh.yifaexp.R;
import com.fwh.yifaexp.model.MessageFBModle;
import com.fwh.yifaexp.model.UserForYifa;

public class MyFeedBackActivity extends Activity  implements OnClickListener{
	  private EditText edit_txt_message = null;
	  private Button messageSubmit = null;
	  protected void onCreate(Bundle paramBundle)
	  {
	    super.onCreate(paramBundle);
	    setContentView(R.layout.activity_my_feedback);
	    ((TextView)findViewById(R.id.title)).setText("意见反馈");
	    this.messageSubmit = ((Button)findViewById(R.id.btn_message_submit));
	    this.messageSubmit.setOnClickListener(this);
	    this.edit_txt_message = ((EditText)findViewById(R.id.edit_txt_message));
	    findViewById(R.id.imgvPoiTitleBack).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	  }
	
	@Override
	  public void onClick(View paramView)
	  {
	    switch (paramView.getId())
	    {
	    default:
	    case R.id.btn_message_submit:
		    String str = this.edit_txt_message.getText().toString().trim();
		    if ((str != null) && (str.length() > 0))
		    {
		      MessageFBModle localMessageFBModle = new MessageFBModle();
		      localMessageFBModle.setMessage(str);
		      localMessageFBModle.setUserInfo(BmobUser.getCurrentUser(getApplicationContext(), UserForYifa.class).getUsername());
		      localMessageFBModle.save(this, new SaveListener()
		      {
		        public void onFailure(int paramAnonymousInt, String paramAnonymousString)
		        {
		        	ToastUtil.show(getApplicationContext(), "反馈失败,错误码"+paramAnonymousInt+paramAnonymousString);
		        	FailedlWrite.writeCrashInfoToFile("反馈意见失败,错误码"+paramAnonymousInt+paramAnonymousString);
		        }
		        public void onSuccess()
		        {
		          //System.out.println("!!!!!!!!!");
		        	ToastUtil.show(getApplicationContext(), "反馈成功");
				      finish();
		        }
		      });
		     
		      return;
		    }
		    ToastUtil.show(getApplicationContext(), "请输入有意义的内容");
		    break;
	    }
	    

	  }
	
	

}
