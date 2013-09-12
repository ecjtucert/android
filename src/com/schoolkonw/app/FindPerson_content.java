package com.schoolkonw.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.Util.getServerResUtil;
import com.schoolkonw.helper.JsonHelper;
import com.schoolkonw.widgets.MyAlertDialog;
import com.schoolkonw.widgets.MyAlertDialog.MyDialogInt;
import com.schoolkonw.widgets.MyProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class FindPerson_content extends BaseActivity {
	private ListView listview=null;
	private List<Map<String,Object>> list=null;
	private SimpleAdapter adapter=null;
	private MyProgressBar pb=null;
	private MyAlertDialog mad=null;
	
	String name=null;
	String id=null;
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setView(R.layout.find_person_content);
		SetTitle("找人");
		setTitleBar(R.drawable.titlebar_reback_selector,"返回", 0,"");
		
		this.listview=(ListView) super.findViewById(R.id.find_person_listview);
		this.list=new ArrayList<Map<String,Object>>();
		Intent it=getIntent();
		
		 id=it.getStringExtra("id");
		try {
			name=URLEncoder.encode(it.getStringExtra("name"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		pb=new MyProgressBar(this);
		pb.setMessage("正在查询中...");
		new Thread(){
			public void run(){
				Message msg = new Message();
				msg.what = 102;
				msg.obj=new getServerResUtil("http://192.168.1.100:8082"+
						"/schoolkonw/who.php?name="+name+"&id="+id).getRes();
				hander.sendMessage(msg);
				System.out.println(msg.obj);
			}
		}.start();
		
		
		
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==102){
				try {
					list=new JsonHelper(msg.obj.toString()).parseJson(
							new String[]{"stuId","classId","name","sex","birth","major",
									"nation","politics","address","status"},
							new String[]{"学号:","在班编号:","姓名:","性别:","出生年月:",
									"班级名称:","民族:","政治面貌:","籍贯:","学习状态:"});
					if(list.size()==0){
						listview.setVisibility(View.GONE);
						mad=new MyAlertDialog(FindPerson_content.this);
						mad.setTitle("信息提示");
						mad.setMessage("据考察交大查无此人,请确定你输入的信息是否正确!");
						mad.setLeftButton("确定",new MyDialogInt() {
							public void onClick(View view) {
								mad.dismiss();
								finish();
							}
						});
					}else{
						adapter=new SimpleAdapter(FindPerson_content.this,list,R.layout.find_person_content_list_style,
								new String[]{"stuId","classId","name","sex","birth","major",
								"nation","politics","address","status"},
								new int[]{R.id.find_person_list_stuId,R.id.find_person_list_classId,
								R.id.find_person_list_name,R.id.find_person_list_sex,
								R.id.find_person_list_birth,R.id.find_person_list_major,
								R.id.find_person_list_nation,R.id.find_person_list_politics,
								R.id.find_person_list_address,R.id.find_person_list_status});
						listview.setAdapter(adapter);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pb.dismiss();
		}
	};

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
