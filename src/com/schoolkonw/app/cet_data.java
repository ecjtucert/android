package com.schoolkonw.app;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.Util.getServerResUtil;
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

public class cet_data extends BaseActivity {
	
	private ListView listview=null;
	private List<Map<String,String>> list=null;
	private SimpleAdapter adapter=null;
	private MyProgressBar pb=null;
	String examid=null;
	String name=null;
	MyAlertDialog mad=null;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.cet_data);
		SetTitle("��������ѯ");
		setTitleBar(R.drawable.titlebar_reback_selector, "����", 0,"");
		
		listview=(ListView) super.findViewById(R.id.cet_data_listview);
		list=new ArrayList<Map<String,String>>();
		
		Intent it=getIntent();
		examid=it.getStringExtra("id");
		name=it.getStringExtra("name");
		try {
			name = URLEncoder.encode(name,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		 
		pb=new MyProgressBar(this);
		pb.setMessage("���ڲ�ѯ��...");
		new Thread(){
			public void run(){
				Message msg = new Message();
				msg.what = 102;
				msg.obj=new getServerResUtil("http://121.199.50.123:80"+
						"/schoolkonw/cet.php?cetId="+examid+"&name="+name).getRes();
				hander.sendMessage(msg);
			}
		}.start();
	}
	
	@SuppressLint("HandlerLeak")
	Handler hander=new Handler(){
		public void handleMessage(Message msg) {
			String temp=msg.obj.toString();
			if(msg.what==102){
				if(temp.contains("false")){
					mad=new MyAlertDialog(cet_data.this);
					mad.setTitle("��ʾ��Ϣ");
					mad.setMessage("�޷��ҵ���Ӧ�ķ���,��ȷ���������׼��֤�ż���������");
					mad.setLeftButton("ȷ��",new MyDialogInt() {
						public void onClick(View view) {
							mad.dismiss();
							finish();
						}
					});
				}else{
					String params[]=temp.split("\\|");
					String addition[]=new String[]{"����:","ѧУ:","��������:","׼��֤��:",
							"����ʱ��:","�ܷ�:","����:","�Ķ�:","�ۺ�:","д��:"};
					
					for(int i=0;i<params.length;i++){
						Map<String,String> map=new HashMap<String, String>();
						map.put("title",addition[i]+params[i]);
						list.add(map);
					}
					adapter=new SimpleAdapter(cet_data.this,list,R.layout.cet_data_list_style,
							new String[]{"title"},new int[]{R.id.cet_data_list_title});
					listview.setAdapter(adapter);
				}
			}
			pb.dismiss();
		}
	};
	
	

	@Override
	protected void HandleTitleBarEvent(int buttonId,View view) {
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
