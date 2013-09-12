package com.schoolkonw.app;

import com.index.schoolkonw.R;
import com.schoolkonw.widgets.MyAlertDialog;
import com.schoolkonw.widgets.MyAlertDialog.MyDialogInt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class FindPerson extends BaseActivity {
	private EditText name,id;
	private Button btn;
	private MyAlertDialog mad;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.find_person);
		SetTitle("找人");
		setTitleBar(R.drawable.titlebar_reback_selector,"返回", 0,"");
		this.name=(EditText) super.findViewById(R.id.find_person_edit_name);
		this.id=(EditText) super.findViewById(R.id.find_person_edit_id);
		this.btn=(Button) super.findViewById(R.id.find_person_search_btn);
		
		this.btn.setOnClickListener(new OnClickListener() {			
			public void onClick(View arg0) {
				String p_name=name.getText().toString();
				String p_id=id.getText().toString();
				
				mad=new MyAlertDialog(FindPerson.this);
				mad.setTitle("提示信息");
				mad.setLeftButton("确定",new MyDialogInt() {
					public void onClick(View view) {
						mad.dismiss();						
					}
				});
				
				if(p_name.equals("")&&p_id.equals("")){					
					mad.setMessage("姓名和学号请至少填入一项进行查询");
				}else if(p_name.length()==1){
					mad.setMessage("姓名不能少于两个汉字");
				}else if(p_id.length()!=0&&p_id.length()!=14){
					mad.setMessage("学号输入有误");
				}else{
					mad.dismiss();
					Intent it=new Intent(FindPerson.this,FindPerson_content.class);
					it.putExtra("name",p_name);
					it.putExtra("id",p_id);
					startActivity(it);
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
		default:
			break;
		}
		
	}

}
