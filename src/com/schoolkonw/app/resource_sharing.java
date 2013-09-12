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
			{"2013-2014�ߵ���ѧ��ĩ��������A��","��Ӱ���","2013-08-21"},
			{"���������PPt","��������","2013-05-20"},
			{"2012Ӣ���ļ�����ģ����","@HFlyer","2013-09-07"},
			{"��������2013��ѧ��������A��","�t�L�[���܎�@�r","2012-07-05"},
			{"2013-2014�ߵ���ѧ��ĩ��������A��","��Ӱ���","2013-08-21"},
			{"���������PPt","��������","2013-05-20"},
			{"2012Ӣ���ļ�����ģ����","@HFlyer","2013-09-07"},
			{"��������2013��ѧ��������A��","�t�L�[���܎�@�r","2012-07-05"},
			{"2013-2014�ߵ���ѧ��ĩ��������A��","��Ӱ���","2013-08-21"},
			{"���������PPt","��������","2013-05-20"},
			{"2012Ӣ���ļ�����ģ����","@HFlyer","2013-09-07"},
			{"��������2013��ѧ��������A��","�t�L�[���܎�@�r","2012-07-05"}};
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.resource_sharing);
		SetTitle("ѧϰ����");
		setTitleBar(R.drawable.titlebar_reback_selector, "����", 
				R.drawable.title_btn_right, "�ϴ�");
		
		this.listview=(ListView) super.findViewById(R.id.resource_data_listview);
		listview.setDividerHeight(10); 
		for(int i=0;i<this.data.length;i++){
			Map<String,String> map=new HashMap<String, String>();
			map.put("title", this.data[i][0]);
			map.put("author", "�ϴ�����:"+this.data[i][1]);
			map.put("date", "�ϴ�����:"+this.data[i][2]);
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
