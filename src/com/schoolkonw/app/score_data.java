package com.schoolkonw.app;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.index.schoolkonw.R;
import com.schoolkonw.widgets.MyAlertDialog;
import com.schoolkonw.widgets.MyAlertDialog.MyDialogInt;
import com.schoolkonw.widgets.MyProgressBar;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class score_data extends BaseActivity {
	private ListView datalist=null;
	private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	private SimpleAdapter simpleadapter=null;
	private MyProgressBar pBar=null;
	private final int QUERY_MSG = 102;
	private String stuid;
	private MyAlertDialog mad=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.score_data);
		SetTitle("成绩查询");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.datalist=(ListView) super.findViewById(R.id.score_data_listview);
		Intent it=super.getIntent();
		stuid=it.getStringExtra("stuid");
		
		
		pBar = new MyProgressBar(this);
		pBar.setMessage("正在加载数据中...");
		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = QUERY_MSG;
				msg.obj=updateData(stuid);
				hander.sendMessage(msg);
			}
		}.start();
	}
	
	
	
	
	private String updateData(String stuid) {
		try {
			URL url=new URL("http","192.168.1.100",8082,
					"/schoolkonw/getScore.php?stuid="+stuid);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			byte data[]=new byte[512*100];
			int len=conn.getInputStream().read(data);
			if(len>0){
				String json=new String(data,0,len).trim();				
				return json;
			}
			conn.getInputStream().close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stuid;
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case QUERY_MSG:
				try {
					if(msg.obj.toString().equals("server error")){
						mad=new MyAlertDialog(score_data.this);
						mad.setTitle("消息提示");
						mad.setMessage("教务处服务器数据库出现异常,请稍后查询!");
						mad.setLeftButton("确定",new MyDialogInt() {
							public void onClick(View view) {
								mad.dismiss();
								finish();
							}
						});
					}else{
						List<Map<String,Object>> all=score_data.this.parseJson(msg.obj.toString());
						Iterator<Map<String,Object>> iter=all.iterator();
						int sp=1;
						
						while(iter.hasNext()){
							Map<String,Object> map=iter.next();
							Map<String,String> map2=new HashMap<String, String>();
							map2.put("subject",sp+". "+map.get("subject").toString());
							map2.put("score","成绩:"+map.get("score").toString());
							map2.put("extra_1","重考:"+map.get("extra_1").toString());
							map2.put("extra_2",map.get("extra_2").toString());
							map2.put("demand","要求:"+map.get("demand").toString());
							map2.put("credit","学分:"+map.get("credit").toString());
							list.add(map2);
							sp++;
						}
						simpleadapter=new SimpleAdapter(score_data.this,list,
								R.layout.score_data_list, new String[]{"subject","score","extra_1",
								"extra_2","demand","credit"},
								new int[]{R.id.subject,R.id.score,R.id.extra_1,R.id.extra_2,
								R.id.demand,R.id.credit});
						
						datalist.setAdapter(simpleadapter);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
			if(pBar.isShowing()){
				pBar.dismiss();
			}
		}
	};
	
	
	private List<Map<String,Object>>  parseJson(String data) throws Exception{
		 List<Map<String,Object>> all= new ArrayList<Map<String,Object>>();
		 JSONArray jsonarr=new JSONArray(data);
		 for(int i=0;i<jsonarr.length();i++){
			 Map<String,Object> map=new HashMap<String, Object>();
			 JSONObject jsonobj=jsonarr.getJSONObject(i);
			 map.put("subject", jsonobj.getString("subject"));
			 map.put("score", jsonobj.getString("score"));
			 map.put("extra_1",jsonobj.getString("extra_1"));
			 map.put("extra_2", jsonobj.getString("extra_2"));
			 map.put("demand", jsonobj.getString("demand"));
			 map.put("credit", jsonobj.getString("credit"));
			 all.add(map);
		 }
		return all;
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
