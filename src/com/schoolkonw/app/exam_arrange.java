package com.schoolkonw.app;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import com.index.schoolkonw.R;
import com.schoolkonw.Util.getServerResUtil;
import com.schoolkonw.helper.JsonHelper;
import com.schoolkonw.widgets.MyProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class exam_arrange extends BaseActivity implements OnItemSelectedListener{
	private Spinner major=null;
	private Spinner term=null;
	private Button btn=null;
	
	private List<String> classname=null;
	private List<String> classid=null; 	
	private List<String> term_data=null;	

	
	private ArrayAdapter<String> collegeAdapter=null ;
	private ArrayAdapter<String> termAdapter=null ;
	
	private String searchId=null;
	
	private MyProgressBar pd=null;
	private final int QUERY_MSG = 102;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.exam_arrange);
		SetTitle("考试安排");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回",0, "");
		
		this.major=(Spinner) super.findViewById(R.id.exam_arrange_college_select);
		this.term=(Spinner) super.findViewById(R.id.exam_arrange_term_select);
		
		
		
		this.btn=(Button) super.findViewById(R.id.exam_arrange_btn);
		this.classid=new ArrayList<String>();
		classname=new ArrayList<String>();
		this.major.setPrompt("请选择你所在的班级");
		this.term.setPrompt("请选择你的年级");
		
		term_data=new ArrayList<String>();
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+ 1;
		int startYear=month>=8?year:year-1;
		for(int i=startYear; i>startYear-5; i--){
			term_data.add(String.valueOf(i));
		}
		
		termAdapter=new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item,term_data);
		termAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		term.setAdapter(termAdapter);
		
		btn.setOnClickListener(new OnClickListenerimp());
		major.setOnItemSelectedListener(this);
		term.setOnItemSelectedListener(this);
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case QUERY_MSG:
				try {
					try {
						
						List<Map<String,Object>> all=new JsonHelper(msg.obj.toString()).parseJson(
								new String[]{"id","name"},
								new String[]{"",""});
						Iterator<Map<String,Object>> iter=all.iterator();
						classname.clear();
						classid.clear();
						while(iter.hasNext()){
							Map<String,Object> map=iter.next();
							classname.add(map.get("name").toString());
							classid.add(map.get("id").toString());
						}
					} catch (Exception e1){
						e1.printStackTrace();
					}
					collegeAdapter=new ArrayAdapter<String>(exam_arrange.this,
							android.R.layout.simple_spinner_item,classname);
					collegeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);     
					major.setAdapter(collegeAdapter);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
			pd.dismiss();
		}
	};
	
	
	public class OnClickListenerimp implements OnClickListener{

		public void onClick(View arg0) {
			Intent it=new Intent(exam_arrange.this,exam_arrange_content.class);
			it.putExtra("classId",searchId);
			startActivity(it);
		}
		
	}
	
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


	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long arg3) {
		if(parent==term){
			final String temp_term=parent.getItemAtPosition(position).toString();
			pd=new MyProgressBar(this);
			pd.setMessage("正在加载班级数据...");
			new Thread(){
				public void run(){
					Message msg = new Message();
					msg.what = QUERY_MSG;
					msg.obj=new getServerResUtil("http://192.168.1.100:8082"+
					"/schoolkonw/examarrange.php?getClassInfo=1&term="+temp_term).getRes();
					hander.sendMessage(msg);
				}
			}.start();
		}else if(parent==major){
			searchId=classid.get(position);
		}
		
	}


	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		
		
	}
	
	
	

}
