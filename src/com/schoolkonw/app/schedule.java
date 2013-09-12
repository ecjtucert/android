package com.schoolkonw.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;
import com.schoolkonw.Util.FileUtil;
import com.schoolkonw.Util.getServerResUtil;
import com.schoolkonw.helper.ScheduleHelper;
import com.schoolkonw.helper.DatabaseHelper;
import com.schoolkonw.helper.JsonHelper;
import com.schoolkonw.helper.TermHelper;
import com.schoolkonw.helper.TimeHelper;
import com.schoolkonw.widgets.ClassSelect;
import com.schoolkonw.widgets.ClassSelect.ClassSelectInterface;
import com.schoolkonw.widgets.MyPopMenu;
import com.schoolkonw.widgets.MyPopMenu.MyPopMenuImp;
import com.schoolkonw.widgets.MyProgressBar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class schedule extends BaseActivity implements OnGestureListener {
	
	private MyPopMenu popmenu;
	private MyProgressBar mpb;
	
	String select_class_id="";
	String select_class_name="";
	String select_term="";
	String json_file_name="";
	
	
	
	private ViewFlipper viewFlipper;
	private GestureDetector detector; //手势检测
	
	Animation leftInAnimation;
	Animation leftOutAnimation;
	Animation rightInAnimation;
	Animation rightOutAnimation;
	

	@SuppressLint("SimpleDateFormat")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setView(R.layout.schedule);
		SetTitle("课程表");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回",
				R.drawable.title_btn_right,"载入");
		
		viewFlipper = (ViewFlipper)findViewById(R.id.schedule_viewFlipper);
        detector = new GestureDetector(this);
        
        //载入页面
        int current_week=new TimeHelper().getDayOfWeek();
        for(int i=current_week;i<current_week+7;i++){
        	viewFlipper.addView(scheduleView((i-1)%7));
        }
        
        
        //动画效果
    	leftInAnimation = AnimationUtils.loadAnimation(this, R.anim.left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(this, R.anim.left_out);
		rightInAnimation = AnimationUtils.loadAnimation(this, R.anim.right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(this, R.anim.right_out);
		
		
	}
	
	private View scheduleView(int weekday) {
		LayoutInflater inflater = LayoutInflater.from(this);
		View layout = inflater.inflate(R.layout.schedule_data_list, null);
		TextView week = (TextView) layout.findViewById(R.id.schedule_week_day_tip);
		week.setText(new TimeHelper().weekday(weekday));
		
		ListView listv=(ListView) layout.findViewById(R.id.schedule_listview);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		
		 try {
			 //从配置文件中获取当前课程表
			 String current_schedule=new ScheduleHelper(schedule.this).getCurrentSchedule();
			 String JsonDa=new FileUtil(schedule.this, current_schedule).readFileData();
			 List<Map<String,Object>> temp_list=new JsonHelper(JsonDa).parseJson(
						new String[]{"s1","s2","s3","s4","s5"},
						new String[]{"","","","",""});
			Map<String,Object> map=temp_list.get(weekday);
			for(int k=1;k<6;k++){
				Map<String,Object> map2=new HashMap<String, Object>();
				String[] temp=map.get("s"+k).toString().split("\\|");
				switch(temp.length){
				case 1:
					map2.put("p1","");
					map2.put("p2","");
					map2.put("p3","");
					map2.put("p4","");
					map2.put("p5","");
					map2.put("p6","");
					break;
				case 3:
					map2.put("p1",temp[0]);
					map2.put("p2",temp[1]);
					map2.put("p3",temp[2]);
					map2.put("p4","");
					map2.put("p5","");
					map2.put("p6","");
					break;
				case 6:
					map2.put("p1",temp[0]);
					map2.put("p2",temp[1]);
					map2.put("p3",temp[2]);
					map2.put("p4",temp[3]);
					map2.put("p5",temp[4]);
					map2.put("p6",temp[5]);
					break;
				}
				map2.put("p7",k*2-1+"-"+(k*2));
				list.add(map2);
			}
			SimpleAdapter adap=new SimpleAdapter(schedule.this, list,R.layout.schedule_listview_item,
					new String[]{"p1","p2","p3","p4","p5","p6","p7"},
					new int []{R.id.schedule_oneclass_subject,R.id.schedule_oneclass_teacher,
					R.id.schedule_oneclass_address,R.id.schedule_oneclass_subject_2,
					R.id.schedule_oneclass_teacher_2,R.id.schedule_oneclass_address_2,
					R.id.schedule_oneclass_time});
			listv.setAdapter(adap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return layout;
    }
	
	
	
	public boolean onTouchEvent(MotionEvent event) {
	     
    	return this.detector.onTouchEvent(event);  //touch事件交给手势处理。
    }
		

	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			popmenu=new MyPopMenu(schedule.this);
			popmenu.addItems(new String[]{"载入", "管理"});
			popmenu.showAsDropDown(v);	
			popmenu.setOnItemClickListener(new MyPopMenuImp() {
				@SuppressLint("WorldReadableFiles")
				public void onItemClick(int index) {
					switch(index){
					//载入最新的课程表到本地
					case 0:
						final ClassSelect newClass=new ClassSelect(schedule.this);
						newClass.setOnclickListener(new ClassSelectInterface() {
							public void onClick(View view) {
								newClass.dismiss();							
					
								//获取选择班级的班级号和名称
								select_class_id=newClass.getClassId();
								select_class_name=newClass.getClassN();
								select_term=new TermHelper().getNowTerm();
								if(new ScheduleHelper(schedule.this).HasTheSchedule(select_class_id, select_term)){
									showLongToast("该课表已经下载到本地,请勿重复添加!");
									return;
								}
								json_file_name=select_class_id+"-"+select_term+".json";
								mpb=new MyProgressBar(schedule.this);
								mpb.setMessage("正在导入课表中...");
								new Thread(){
									public void run(){
										Message msg=new Message();
										msg.what=102;
										String jsonData=new getServerResUtil(
												"http://192.168.1.100:8082/schoolkonw/schedule.php?classid="+select_class_id).getRes();
										msg.obj=new FileUtil(schedule.this,json_file_name).writeFileData(jsonData);
										hander.sendMessage(msg);
										
									}
								}.start();
							}
						});
						break;
				    //管理本地课程表
					case 1:
						Intent it=new Intent(schedule.this,schedule_manage.class);
						startActivity(it);
						break;
					}
				}
			});
			break;
		default:
			break;
		}
		
	}
	@SuppressLint("HandlerLeak")
	Handler hander=new Handler(){
		public void handleMessage(Message msg) {
			if(msg.what==102){
				if((Boolean) msg.obj){
					showLongToast("导入课表成功!");
					//设置配置文件中的当前课程表
					new ScheduleHelper(schedule.this).setCurrentSchedule(json_file_name);					
					//向sqlite插入数据
					DatabaseHelper dbHelper = new DatabaseHelper(schedule.this,
							"schedule",null,2);					
					dbHelper.insert("schedule",new String[]{"classid","classname","term"},
							new String[]{select_class_id,select_class_name,select_term});
					dbHelper.close();
					
					//刷新页面显示最新课表
					startActivity(new Intent(schedule.this,schedule.class));
					finish();
				}else{
					showLongToast("导入课表失败，请重新导入!%>_<%");
				}
			}
			mpb.dismiss();
		}
	};


	public boolean onDown(MotionEvent arg0) {
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		//Log.i("tip", "e1="+e1.getX()+" e2="+e2.getX()+" e1-e2="+(e1.getX()-e2.getX()));
		
	
		
		if(e1.getX()-e2.getX()>120){
			viewFlipper.setInAnimation(leftInAnimation);
			viewFlipper.setOutAnimation(leftOutAnimation);
		    viewFlipper.showNext();//向右滑动
		    return true;
		}else if(e1.getX()-e2.getY()<-120){
			viewFlipper.setInAnimation(rightInAnimation);
			viewFlipper.setOutAnimation(rightOutAnimation);
			viewFlipper.showPrevious();//向左滑动
			return true;
		}
		return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
