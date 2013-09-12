package com.schoolkonw.app;


import com.index.schoolkonw.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class library_login extends BaseActivity {
		EditText edit_id=null;
		EditText edit_pwd=null;
		Button btn=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.library_login);
		SetTitle("图书馆");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.edit_id=(EditText) super.findViewById(R.id.library_edit_id);
		this.edit_pwd=(EditText) super.findViewById(R.id.library_edit_pwd);
		this.btn=(Button) super.findViewById(R.id.library_login_btn);
		this.btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String uid=edit_id.getText().toString();
				String pwd=edit_pwd.getText().toString();
				if(uid.equals("")){
					showLongToast("请输入您的学号或者借书证号!");
				}else{
					Intent it=new Intent(library_login.this,library_data.class);
					it.putExtra("uid", uid);
					it.putExtra("pwd", pwd);
					library_login.this.startActivity(it);
				}
			}
		});
	}

	@Override
	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch (buttonId) {
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
