package com.schoolkonw.app;



import com.index.schoolkonw.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class get_score extends BaseActivity{
	private EditText edit=null;
	private Button btn=null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.get_score);
		SetTitle("成绩查询");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回",0, "");
		
		this.edit=(EditText) super.findViewById(R.id.search_score_edit);
		this.btn=(Button) super.findViewById(R.id.search_score_btn);
		
		this.btn.setOnClickListener(new OnClickListenerimp());
	}
	
	public class OnClickListenerimp implements OnClickListener{

		public void onClick(View arg0) {
			String str=edit.getText().toString();
			if(str.equals("")){
				Toast.makeText(get_score.this,"请输入学号后再进行查询",Toast.LENGTH_SHORT).show();
			}else{
				Intent it=new Intent(get_score.this,score_data.class);
				it.putExtra("stuid", str);
				get_score.this.startActivity(it);
			}
		}
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
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
