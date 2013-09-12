package com.schoolkonw.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class resource_sharing extends BaseActivity {
	ListView listview=null;
	private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	private SimpleAdapter simpleadapter=null;
	
	private String data[][]=new String[][]{
			{"2013-2014高等数学期末考试试题A卷","舞影凌风","2013-08-21"},
			{"计算机网络PPt","风流三月","2013-05-20"},
			{"2012英语四级考试模拟题","@HFlyer","2013-09-07"},
			{"华东交大2013大学物理试题A卷","╰風嘯︶ㄣ軒轅╮","2012-07-05"},
			{"2013-2014高等数学期末考试试题A卷","舞影凌风","2013-08-21"},
			{"计算机网络PPt","风流三月","2013-05-20"},
			{"2012英语四级考试模拟题","@HFlyer","2013-09-07"},
			{"华东交大2013大学物理试题A卷","╰風嘯︶ㄣ軒轅╮","2012-07-05"},
			{"2013-2014高等数学期末考试试题A卷","舞影凌风","2013-08-21"},
			{"计算机网络PPt","风流三月","2013-05-20"},
			{"2012英语四级考试模拟题","@HFlyer","2013-09-07"},
			{"华东交大2013大学物理试题A卷","╰風嘯︶ㄣ軒轅╮","2012-07-05"}};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.resource_sharing);
		SetTitle("学习资料");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 
				R.drawable.title_btn_right, "上传");
		
		this.listview=(ListView) super.findViewById(R.id.resource_data_listview);
		listview.setDividerHeight(10); 
		for(int i=0;i<this.data.length;i++){
			Map<String,String> map=new HashMap<String, String>();
			map.put("title", this.data[i][0]);
			map.put("author", "上传作者:"+this.data[i][1]);
			map.put("date", "上传日期:"+this.data[i][2]);
			this.list.add(map);
		}
		this.simpleadapter=new SimpleAdapter(this,this.list,
				R.layout.resource_sharing_list, new String[]{"title","author","date"},
				new int[]{R.id.resource_sharing_tip,R.id.resource_sharing_author,
				R.id.resource_sharing_date});
		
		this.listview.setAdapter(this.simpleadapter);
	}

	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch (buttonId) {
		case 0:
			finish();
			break;
		case 1:
			Intent it=new Intent(resource_sharing.this,resource_upload.class);
			startActivity(it);
			break;
		default:
			break;
		}
		
	}

}
