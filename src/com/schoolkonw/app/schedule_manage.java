package com.schoolkonw.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.helper.DatabaseHelper;
import com.schoolkonw.helper.ScheduleHelper;
import com.schoolkonw.widgets.MyAlertDialog;
import com.schoolkonw.widgets.MyAlertDialog.MyDialogInt;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class schedule_manage extends BaseActivity {
	
	private ListView listview=null;
	private List<Map<String,Object>> list=null;
	private SimpleAdapter adapter=null;
	private MyAlertDialog mad=null;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.schedule_manage);
		SetTitle("课程表管理");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		listview=(ListView) super.findViewById(R.id.schedule_manage_listview);
		listview.setOnItemClickListener(new OnItemClickListenerimp());
		
		list=new ArrayList<Map<String,Object>>();
		
		
		//从数据库读出本地课程表信息
		DatabaseHelper dbHelper = new DatabaseHelper(schedule_manage.this,
				"schedule",null,2);
		Cursor cur=dbHelper.select("schedule", null,"", null, null, null, null);
		while(cur.moveToNext()){ 
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("classname",cur.getString(cur.getColumnIndex("classname")));
			map.put("term",cur.getString(cur.getColumnIndex("term")));
			map.put("classid",cur.getString(cur.getColumnIndex("classid")));
			list.add(map);

		} 
		dbHelper.close();

		adapter=new SimpleAdapter(this, list, R.layout.schedule_manage_listview_item,
				new String[]{"classname","term","classid"},
				new int[]{R.id.schedule_manage_listview_item_className,
				R.id.schedule_manage_listview_item_term,R.id.schedule_manage_listview_item_classid});
		listview.setAdapter(adapter);
		
	}
	
	//切换课表
	public class OnItemClickListenerimp implements OnItemClickListener{
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			@SuppressWarnings("unchecked")
			HashMap<String,Object> map=(HashMap<String,Object>)listview.
					getItemAtPosition(position);
					String className=map.get("classname").toString();
					String classTerm=map.get("term").toString();
					final String temp_file_name=map.get("classid").toString()+"-"+
							classTerm+".json";
					mad=new MyAlertDialog(schedule_manage.this);
					mad.setTitle("消息提示");
					mad.setMessage("你确定要切换到\n\t\t\t\""+className+" \"\n\t\t\t\t("+classTerm+") \n课程表吗?");
					mad.setLeftButton("确定",new MyDialogInt() {
						public void onClick(View view) {
							new ScheduleHelper(schedule_manage.this).setCurrentSchedule(temp_file_name);
							mad.dismiss();
							Intent it=new Intent(schedule_manage.this,schedule.class);
							startActivity(it);
							finish();
						}
					});
					mad.setRightButton("取消",new MyDialogInt() {
						public void onClick(View view) {
							mad.dismiss();
						}
					});
					
		}
		
	}
	
	
	

	@Override
	protected void HandleTitleBarEvent(int buttonId, View v) {
		switch (buttonId) {
		case 0:
			finish();
			break;
		default:
			break;
		}
	}

}
