package com.schoolkonw.widgets;


import com.index.schoolkonw.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public  class MyAlertDialog implements OnClickListener {
	
	private Dialog mDialog;
	private Button leftBtn,rightBtn;
	private TextView title,message;
	
	public MyAlertDialog(Context context){
		mDialog = new AlertDialog.Builder(context).create();
		mDialog.show();
		
		View layout = LayoutInflater.from(context).inflate(R.layout.my_alertdialog_style, null);
		leftBtn=(Button) layout.findViewById(R.id.MyAlertDialogLeftBtn);
		rightBtn=(Button) layout.findViewById(R.id.MyAlertDialogRightBtn);
		
		//Ϊ��ť�����¼�����
		leftBtn.setOnClickListener(this);
		rightBtn.setOnClickListener(this);
		
		//�����ð�ťĬ������
		leftBtn.setVisibility(View.GONE);
		rightBtn.setVisibility(View.GONE);
		
		title=(TextView) layout.findViewById(R.id.MyAlertDialogTitle);
		message=(TextView) layout.findViewById(R.id.MyAlertDialogMessage);
		
        mDialog.setContentView(layout);
	}
	
	//�¼����ݽӿ�
	public interface MyDialogInt{  
        public void onClick(View view);  
    } 
	
	MyDialogInt LeftcallBack = null; 
	MyDialogInt RightcallBack = null; 
	
	public void dismiss(){
		mDialog.dismiss();
	}
	
	public boolean isShowing(){
		return mDialog.isShowing();
	}
	
	//������ť
	public void setLeftButton(String value,MyDialogInt iBack){
		if(value==null||value.equals("")){
			return;
		}
		leftBtn.setVisibility(View.VISIBLE);
		leftBtn.setText(value);
		LeftcallBack=iBack;
	}
	
	//�����Ұ�ť
	public void setRightButton(String value,MyDialogInt iBack){
		if(value==null||value.equals("")){
			return;
		}
		rightBtn.setVisibility(View.VISIBLE);
		rightBtn.setText(value);
		RightcallBack=iBack;
	}
	
	public void setTitle(String title){
		this.title.setText(title);
	}
	
	public void setMessage(String message){
		this.message.setText(message);
	}
	

	public void onClick(View view) {
		if(view==leftBtn){
			LeftcallBack.onClick(view);
		}else if(view==rightBtn){
			RightcallBack.onClick(view);
		}
	}
	
}
