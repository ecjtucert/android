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
	
	// 屏幕的宽度
	public int getScreenWidth(){
		WindowManager manage =((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		return display.getWidth();    
	}
	
	// 屏幕的高度
	public int getScreenHeight(){
		WindowManager manage =((Activity) context).getWindowManager();
		Display display = manage.getDefaultDisplay();
		return display.getHeight();   
	}
	
	
	//网络是否连接
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
