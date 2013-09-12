package com.schoolkonw.app;

import com.index.schoolkonw.R;

import android.os.Bundle;
import android.view.View;

public class online_course_reg extends BaseActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.online_course_reg);
		SetTitle("Õ¯…œ—°–ﬁ");
		setTitleBar(R.drawable.titlebar_reback_selector, "∑µªÿ", 0,"");
	}

	@Override
	protected void HandleTitleBarEvent(int buttonId,View v) {
		switch(buttonId){
		case 0:
			finish();
			break;
		case 1:
			break;
		default:
			break;
		}
		
	}

}
