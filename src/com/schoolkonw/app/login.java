package com.schoolkonw.app;



import com.index.schoolkonw.R;
import com.schoolkonw.helper.LoginHelper;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class login extends BaseActivity{
	private TextView uid,pwd;
	private Button submit=null;
	private String userid,password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.login);
		SetTitle("用户登录");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.uid=(TextView) super.findViewById(R.id.login_uid);
		this.pwd=(TextView) super.findViewById(R.id.login_pwd);
		this.submit=(Button) super.findViewById(R.id.login_submit);
		this.submit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				userid=uid.getText().toString();
				password=pwd.getText().toString();
				if(userid.equals("1")&&password.equals("admin")){
					if(new LoginHelper(login.this).login(userid, password)){
						showShortToast("登陆成功");
					}
				}else{
					showShortToast("请输入正确的账号密码!");
				}
				
			}
		});
	}

	protected void HandleTitleBarEvent(int buttonId, View v) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			break;
		}

	}
	
	

}
