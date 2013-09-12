package com.schoolkonw.app;


import java.util.ArrayList;
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
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class library_data extends BaseActivity {
	private ListView datalist=null;
	private String stuid=null;
	private String pwd=null;
	private final int QUERY_MSG = 102;
	private MyProgressBar pBar=null;
	private List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
	private SimpleAdapter simpleadapter=null;
	private TextView tip=null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.library_data);
		SetTitle("借书记录");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.datalist=(ListView) super.findViewById(R.id.library_data_listview);
		tip=(TextView) super.findViewById(R.id.library_data_tip);
		Intent it=super.getIntent();
		stuid=it.getStringExtra("uid");
		pwd=it.getStringExtra("pwd");
		
		pBar = new MyProgressBar(this);
		pBar.setMessage("正在加载数据中...");
		new Thread() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = QUERY_MSG;
				msg.obj=new getServerResUtil("http://121.199.50.123:80/"+
				"schoolkonw/library.php?uid="+stuid+"&pwd="+pwd).getRes();
				hander.sendMessage(msg);
			}
		}.start();
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case QUERY_MSG:
				try {
					list=new JsonHelper(msg.obj.toString()).parseJson(
							new String[]{"book","borrow","return","num"}, 
							new String[]{"","外借时间:","应还时间:","续借次数:"});
					if(list.size()==0){
						tip.setVisibility(View.VISIBLE);
						tip.setText("你目前没有借书记录,快去图书馆转转吧!");
					}else{
						simpleadapter=new SimpleAdapter(library_data.this,list,
								R.layout.library_data_list, new String[]{"book","borrow","return",
								"num"},
								new int[]{R.id.library_book,R.id.library_borrow,
								R.id.library_return,R.id.library_num});
						
						datalist.setAdapter(simpleadapter);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				break;
			default:
				break;
			}
			if (pBar.isShowing()) {
				pBar.dismiss();
			}
		}
	};
	
	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch (buttonId) {
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
