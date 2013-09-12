package com.schoolkonw.app;


import com.index.schoolkonw.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class cet_login extends BaseActivity {
		EditText exam_id=null;
		EditText name=null;
		Button btn=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.cet_login);
		SetTitle("四六级查询");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.exam_id=(EditText) super.findViewById(R.id.cet_edit_id);
		this.name=(EditText) super.findViewById(R.id.cet_edit_name);
		this.btn=(Button) super.findViewById(R.id.cet_login_btn);
		this.btn.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String id=exam_id.getText().toString();
				String na=name.getText().toString();
				if(id.equals("")){
					showLongToast("请输入您的四六级考号!");
				}else if(na.equals("")){
					showLongToast("请输入您的名字!");
				}else{
					Intent it=new Intent(cet_login.this,cet_data.class);
					it.putExtra("id", id);
					it.putExtra("name", na);
					cet_login.this.startActivity(it);
				}
			}
		});
	}

	@Override
	protected void HandleTitleBarEvent(int buttonId,View view) {
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
