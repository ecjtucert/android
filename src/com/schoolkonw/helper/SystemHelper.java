package com.schoolkonw.helper;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Display;
import android.view.WindowManager;

public class SystemHelper{
	
	Context context=null;
	
	public SystemHelper(Context context){
		this.context=context;
	}
	
	// ��Ļ�Ŀ��
	public int getScreenWidth(){
		WindowManager manage =((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		return display.getWidth();    
	}
	
	// ��Ļ�ĸ߶�
	public int getScreenHeight(){
		WindowManager manage =((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		return display.getHeight();   
	}
	
	
	//�����Ƿ�����
	public boolean isNetConnecting() {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        } else {
            return true;
        }
	}
}
