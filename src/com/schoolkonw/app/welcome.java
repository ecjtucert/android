package com.schoolkonw.app;

import com.index.schoolkonw.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class welcome extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		new Handler().postDelayed(new Runnable() {

			public void run() {
				Intent it = new Intent(welcome.this, MainActivity.class);
				startActivity(it);
				finish();
			}
		}, 3000);
	}

}
