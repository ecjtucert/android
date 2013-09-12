package com.schoolkonw.helper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * 课表配置类
 * @author wei8888go
 *
 */
public class ScheduleHelper {
	public SharedPreferences share;
	public SharedPreferences.Editor edit=null;
	private Context context;
	private static final String FILENAME="schedule";
	
	@SuppressLint("CommitPrefEdits")
	public ScheduleHelper(Context context){
		this.context=context;
		this.share=this.context.getSharedPreferences(FILENAME,
				Activity.MODE_PRIVATE);
		this.edit=share.edit();
	}
	
	/**
	 * 课程表配置
	 * 设置当前课程表
	 */
	public void setCurrentSchedule(String str){
		edit.putString("schedule",str);
		edit.commit();
	}
	
	/**
	 * 课程表配置
	 * 获取当前课程表
	 */
	
	public String getCurrentSchedule(){
		return share.getString("schedule","");
	}
	
	/**
	 * 课程表配置
	 * 判断当前是否存在课程表
	 */
	
	public boolean hasSchedule(){
		return share.contains("schedule");
	}
	
	/**
	 * 根据班级号和学期判断课表是否存在
	 * @return
	 */
	public boolean HasTheSchedule(String classid,String term){
		DatabaseHelper dbHelper = new DatabaseHelper(context,
				"schedule",null,2);
		Boolean has=dbHelper.isExist("select * from schedule where classid= '"+classid+"' and term='"+term+"'");
		dbHelper.close();
		return has;
	}
	
	
}
