package com.schoolkonw.app;

import com.index.schoolkonw.R;
import com.schoolkonw.Util.getServerResUtil;
import com.schoolkonw.widgets.MyProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class online_course_login extends BaseActivity {
	
	private TextView uid=null;
	private TextView pwd=null;
	private Button login=null;
	private Button reg=null;
	private final int QUERY_MSG = 102;
	MyProgressBar pb=null;
	String stuid=null;
	String password=null;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.online_course_login);
		SetTitle("网上选修");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0,"");
		
		this.uid=(TextView) super.findViewById(R.id.online_course_login_uid);
		this.pwd=(TextView) super.findViewById(R.id.online_course_login_pwd);
		
		this.login=(Button) super.findViewById(R.id.online_course_login_loginbtn);
		this.login.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				stuid=uid.getText().toString();
				password=pwd.getText().toString();
				if(stuid.equals("")||stuid==null){
					Toast.makeText(online_course_login.this,
							"请输入你的学号!", Toast.LENGTH_SHORT).show();
				}else if(password.equals("")||password==null){
					Toast.makeText(online_course_login.this,
							"请输入密码!", Toast.LENGTH_SHORT).show();
				}else{
					pb=new MyProgressBar(online_course_login.this);
					pb.setMessage("正在登陆中...");
					new Thread(){
						public void run(){
							Message msg = new Message();
							msg.what = QUERY_MSG;
							msg.obj=new getServerResUtil("http://192.168.1.100:8082/schoolkonw/"+
							"onlineCourse.php?action=checkPwd&uid="+stuid+"&pwd="+password).getRes();
							hander.sendMessage(msg);
						}
					}.start();
				}
			}
		});
		
		this.reg=(Button) super.findViewById(R.id.online_course_login_regbtn);
		this.reg.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Intent it=new Intent(online_course_login.this,online_course_reg.class);
				startActivity(it);				
			}
		});
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case QUERY_MSG:
				if(Boolean.parseBoolean(msg.obj.toString())){
					Toast.makeText(online_course_login.this,
							"登录成功!", Toast.LENGTH_SHORT).show();
					Intent it=new Intent(online_course_login.this,online_course_menu.class);
					it.putExtra("uid", stuid);
					it.putExtra("pwd", password);
					startActivity(it);
				}else{
					showLongToast("用户名或密码错误，登陆失败，请重新登陆!");
				}
				break;
			}
			if(pb.isShowing()){
				pb.dismiss();
			}
		}
	};

	@Override
	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			break;
		default:
			break;
		}
		
	}

}
