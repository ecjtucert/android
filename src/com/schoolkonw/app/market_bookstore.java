package com.schoolkonw.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.widgets.MyListView;
import com.schoolkonw.widgets.MyPopMenu;
import com.schoolkonw.widgets.MyListView.OnRefreshListener;
import com.schoolkonw.widgets.MyPopMenu.MyPopMenuImp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class market_bookstore extends BaseActivity {
	private String data[][]=new String[][]{
			{"001","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"002","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"003","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"004","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"005","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"006","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"},
			{"007","�����߼�������ϵͳ","���ڳ�����","��Ӱ���","2013-08-30 08:53"}};
	private MyListView listview;
	private List<Map<String,Object>> list=null;
	private SimpleAdapter adapter=null;
	private MyPopMenu popmenu;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.market_bookstore);
		SetTitle("��������");
		setTitleBar(R.drawable.titlebar_reback_selector, "����",
				R.drawable.title_btn_right, "����");
		
		this.listview=(MyListView) super.findViewById(R.id.market_bookstore_listview);
		list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<data.length;i++){
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("id",data[i][0]);
			map.put("bookname",data[i][1]);
			map.put("state", "״̬:"+data[i][2]);
			map.put("seller","����:"+data[i][3]);
			map.put("time","����ʱ��:"+data[i][4]);
			list.add(map);
		}
		adapter=new SimpleAdapter(this,list,R.layout.market_bookstore_item,
				new String[]{"id","bookname","state","seller","time"},
				new int[]{R.id.market_bookstore_item_bookid,R.id.market_bookstore_item_bookname,
				R.id.market_bookstore_item_state,R.id.market_bookstore_item_seller,
				R.id.market_bookstore_item_time});
		listview.setAdapter(adapter);
		
		listview.setonRefreshListener(new OnRefreshListener() {
			
			public void onRefresh() {
				new AsyncTask<Void, Void, Void>() {
					protected Void doInBackground(Void... params) {
						try {
							Thread.sleep(1000);
						} catch (Exception e) {
							e.printStackTrace();
						}
						//�˴���Ӵ�����Ϣ
						return null;
					}

					protected void onPostExecute(Void result) {
						adapter.notifyDataSetChanged();
						listview.onRefreshComplete();
					}

				}.execute();
				
			}
		});
	}

	protected void HandleTitleBarEvent(int buttonId, View view) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			popmenu=new MyPopMenu(market_bookstore.this);
			popmenu.addItems(new String[]{"����", "ɸѡ"});
			popmenu.showAsDropDown(view);	
			popmenu.setOnItemClickListener(new MyPopMenuImp() {
				public void onItemClick(int index) {
					Toast.makeText(market_bookstore.this, "item clicked " + index + "!", Toast.LENGTH_SHORT).show();				
				}
			});
			break;
		default:
			break;
		}

	}

}
