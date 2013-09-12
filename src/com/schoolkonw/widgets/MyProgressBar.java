package com.schoolkonw.widgets;

import com.index.schoolkonw.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class MyProgressBar {
	
	private Dialog mDialog;
	private TextView tip=null;

	public MyProgressBar(Context context) {
		mDialog = new AlertDialog.Builder(context).create();
		mDialog.show();
        
        View layout = LayoutInflater.from(context).inflate(R.layout.my_pregress_bar_style, null);
        tip=(TextView) layout.findViewById(R.id.my_progress_bar_tip);

        
        mDialog.setContentView(layout);
	}
	
	public void dismiss(){
		mDialog.dismiss();
	}
	
	public void setMessage(String str){
		tip.setText(str);
	}
	
	public boolean isShowing(){
		return mDialog.isShowing();
	}

}
