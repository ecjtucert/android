package com.schoolkonw.helper;

import android.annotation.SuppressLint;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeHelper {
	
	public TimeHelper(){
		
	}
	
	/**
	 *根据数字 获取星期文字
	 * @param week
	 * @return
	 */
	public String weekday(int week){
		String weekday = null;
		switch(week){
		case 0:weekday="星期一";break;
		case 1:weekday="星期二";break;
		case 2:weekday="星期三";break;
		case 3:weekday="星期四";break;
		case 4:weekday="星期五";break;
		case 5:weekday="星期六";break;
		case 6:weekday="星期日";break;
		}
		return weekday;
	}
	
	/**
	 * 返回今天是星期几
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
