package com.schoolkonw.app;



import com.index.schoolkonw.R;
import com.schoolkonw.Util.UploadFileUtil;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class resource_upload extends BaseActivity{
	
	TextView tip=null;
	EditText edit=null;
	Button select=null;
	Button upload=null;
	
	public static String FILE_PATH_ACTION = "com.schoolkonw.app.filepath";
	private GetPathBroadcastReceiver mGetpathReceiver = null;
	
	String filePath=null;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.resource_upload);
		SetTitle("上传资料");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.tip=(TextView) super.findViewById(R.id.resource_uoload_file_tip);
		this.edit=(EditText) super.findViewById(R.id.resource_uoload_file_name);
		this.select=(Button) super.findViewById(R.id.resource_uoload_select_file);
		this.upload=(Button) super.findViewById(R.id.resource_uoload_btn);
		
		this.select.setOnClickListener(new selectClickListener());
		this.upload.setOnClickListener(new uploadClickListener());
		
		mGetpathReceiver = new GetPathBroadcastReceiver();
		IntentFilter filterPosition = new IntentFilter();
		filterPosition.addAction(FILE_PATH_ACTION);
		registerReceiver(mGetpathReceiver, filterPosition);
	}
	
	public class selectClickListener implements OnClickListener{
		public void onClick(View v) {
			Intent intent = new Intent(resource_upload.this,
					FileManagerActivity.class);
			startActivity(intent);
		}
		
	}
	
	public class uploadClickListener implements OnClickListener{
		public void onClick(View v) {
			if(filePath==null||filePath.equals("")){
				showLongToast("请先选择文件再上传！");
			}else if(edit.getText().toString().equals("")){
				showLongToast("上传的文件名不能为空!");
			}else{
				new UploadFileUtil(resource_upload.this,filePath).execute();
				showLongToast("上传成功!");
				tip.setText("");
				edit.setText("");
				filePath=null;
			}
		}
		
	}

	@SuppressLint("SdCardPath")
	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch (buttonId) {
		case 0:
			finish();
			break;
		case 1:
			break;
		default:
			break;
		}
		
	}
	
	private class GetPathBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(FILE_PATH_ACTION)) {
				String strFilePath = intent.getStringExtra("filepath");
				filePath=(String) strFilePath;			      
				String fileName = filePath.substring(filePath.lastIndexOf("/")+1,
						filePath.lastIndexOf("."));    
				tip.setText("你选择的是:"+filePath);
				edit.setText(fileName);
			}
		}

	}

}
