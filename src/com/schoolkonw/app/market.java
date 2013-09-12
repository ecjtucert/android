package com.schoolkonw.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class market extends BaseActivity {
	
	private ListView listview=null;
	private List<Map<String,Object>> list=null;
	private SimpleAdapter adapter=null;
	
	private int imgRes[]=new int[]{R.drawable.left_slide_menu_list_1,R.drawable.left_slide_menu_list_2,
			R.drawable.left_slide_menu_list_3,R.drawable.left_slide_menu_list_4};
	private String title[]=new String[]{"跳蚤市场","二手书屋","毕业大甩卖","我的市场"};
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.market);
		SetTitle("二手市场");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0,"");
		
		this.listview=(ListView) super.findViewById(R.id.market_listview);
		this.list=new ArrayList<Map<String,Object>>();
		
		for(int i=0;i<title.length;i++){
			Map<String, Object> map=new HashMap<String,Object>();
			map.put("img", String.valueOf(imgRes[i]));
			map.put("title",title[i]);
			this.list.add(map);
		}
		
		this.adapter=new SimpleAdapter(this, list, R.layout.market_item,
				new String[]{"img","title"}, new int[]{R.id.market_item_img,R.id.market_item_title});
		this.listview.setAdapter(adapter);
		this.listview.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				Intent it=null;
				switch(position){
				case 1:
					it=new Intent(market.this,market_bookstore.class);
					break;
				}
				startActivity(it);
			}
		});
	}

	@Override
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
