package com.schoolkonw.app;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.helper.JsonHelper;
import com.schoolkonw.widgets.MyProgressBar;
import com.schoolkonw.Util.getServerResUtil;



import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class exam_arrange_content extends BaseActivity {
	private String classId=null;
	private ListView listview=null;
	private List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
	private SimpleAdapter simpleadapter=null;
	
	private TextView tip=null;
	MyProgressBar pb=null;
	
	
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.exam_arrange_content);
		SetTitle("考试安排");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		this.listview=(ListView) super.findViewById(R.id.exam_arrange_listview);
		this.tip=(TextView) super.findViewById(R.id.exam_arrange_tip);
		tip.setVisibility(View.GONE);
		
		Intent it=super.getIntent();
		classId=it.getStringExtra("classId");
		
		pb=new MyProgressBar(this);
		pb.setMessage("正在加载中...");
		new Thread(){
			public void run(){
				Message msg = new Message();
				msg.what = 102;
				msg.obj=new getServerResUtil("http://192.168.1.100:8082/schoolkonw/"+
				"examarrange.php?classId="+classId).getRes();
				hander.sendMessage(msg);
			}
		}.start();
	}
	
	Handler hander = new Handler() {
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 102:
				try {
					list=new JsonHelper(msg.obj.toString()).parseJson(
							new String[]{"subject","class","term","week","time",
									"classroom","o_t","i_t"},
							new String[]{"","班级:","学期:","考试周:","考试时间:","考试教室:","监考老师: ",","});
					simpleadapter=new SimpleAdapter(exam_arrange_content.this,list,
							R.layout.exam_arrange_content_data_list, 
							new String[]{"subject","class","term","week","time","classroom","o_t","i_t"},
							new int[]{R.id.exam_arrange_data_list_subject,R.id.exam_arrange_data_list_class,
							R.id.exam_arrange_data_list_term,R.id.exam_arrange_data_list_week,
							R.id.exam_arrange_data_list_time,R.id.exam_arrange_data_list_classroom,
							R.id.exam_arrange_data_list_o_t,R.id.exam_arrange_data_list_i_t});
					listview.setAdapter(simpleadapter);
					if(list.size()==0){
						tip.setVisibility(View.VISIBLE);
						listview.setVisibility(View.GONE);
						tip.setText("你所查的班级目前没有考试安排,考试安排一般16周以后才会陆续发布,"+
					"本软件与教务处同步更新，敬请等待！");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			pb.dismiss();
		}
	};
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

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
