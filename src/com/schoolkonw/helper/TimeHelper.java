package com.schoolkonw.helper;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
	
	public TimeHelper(){
		
	}
	
	/**
	 *�������� ��ȡ��������
	 * @param week
	 * @return
	 */
	public String weekday(int week){
		String weekday = null;
		switch(week){
		case 0:weekday="����һ";break;
		case 1:weekday="���ڶ�";break;
		case 2:weekday="������";break;
		case 3:weekday="������";break;
		case 4:weekday="������";break;
		case 5:weekday="������";break;
		case 6:weekday="������";break;
		}
		return weekday;
	}
	
	/**
	 * ���ؽ��������ڼ�
	 * @return
	 */
	public int getDayOfWeek(){
		 Calendar calendar = Calendar.getInstance();
	     Date date = new Date();
	     calendar.setTime(date);
	     if(calendar.get(Calendar.DAY_OF_WEEK)==1){
	    	 return 7;
	     }else{
	    	 return calendar.get(Calendar.DAY_OF_WEEK)-1;
	     }	
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public static String geTimeNoS(){
		Date date=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm");   
		String time=df.format(date);
		return time;
	}
	
	
	@SuppressLint("SimpleDateFormat")
	public static String geTime(){
		Date date=new Date();   
		SimpleDateFormat df=new SimpleDateFormat("MM-dd HH:mm:ss");   
		String time=df.format(date);
		return time;
	}
	
}
