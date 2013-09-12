package com.schoolkonw.helper;

import java.util.Calendar;

public class TermHelper {
	public String getNowTerm(){
		String temp=null;
		int year=Calendar.getInstance().get(Calendar.YEAR);
		int month=Calendar.getInstance().get(Calendar.MONTH)+ 1;
		if(month>8){
			temp=year+"-"+(year+1)+"-1";
		}else if(month<2){
			temp=(year-1)+"-"+year+"-1";
		}else{
			temp=year+"-"+(year+1)+"-2";
		}
		return temp;
	}
	
	public String getTermTip(String term){
		return "";
	}
	
	
}
