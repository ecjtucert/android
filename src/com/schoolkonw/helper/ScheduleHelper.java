package com.schoolkonw.helper;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


/**
 * �α�������
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
	 * �γ̱�����
	 * ���õ�ǰ�γ̱�
	 */
	public void setCurrentSchedule(String str){
		edit.putString("schedule",str);
		edit.commit();
	}
	
	/**
	 * �γ̱�����
	 * ��ȡ��ǰ�γ̱�
	 */
	
	public String getCurrentSchedule(){
		return share.getString("schedule","");
	}
	
	/**
	 * �γ̱�����
	 * �жϵ�ǰ�Ƿ���ڿγ̱�
	 */
	
	public boolean hasSchedule(){
		return share.contains("schedule");
	}
	
	/**
	 * ���ݰ༶�ź�ѧ���жϿα��Ƿ����
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
