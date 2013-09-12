package com.schoolkonw.app;


import com.index.schoolkonw.R;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class ecjtu_news_content extends BaseActivity{ 
	
	private EditText edit=null;
	private Button btn=null;
	private CheckBox cb=null;
	WebSettings webSettings=null;
	private TextView tip=null;
	private RelativeLayout relative=null;
	private int inputLen=0;
	private int comment_num=345;
	
	@SuppressLint("SetJavaScriptEnabled")
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.ecjtu_news_content);
		SetTitle("交大新闻");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		this.btn=(Button) super.findViewById(R.id.ecjtu_news_comment_btn);
		btn.setText(String.valueOf(comment_num));
		this.edit=(EditText) super.findViewById(R.id.ecjtu_news_comment_edit);
		this.cb=(CheckBox) super.findViewById(R.id.ecjtu_news_comment_checkbox);
		this.tip=(TextView) super.findViewById(R.id.ecjtu_news_comment_tip);
		this.edit.clearFocus();
		this.relative=(RelativeLayout) super.findViewById(R.id.ecjtu_news_comment_layout);
		
		WebView web=(WebView) super.findViewById(R.id.webview);
		webSettings = web.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBlockNetworkImage(false);
        webSettings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
        web.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		web.loadUrl("http://192.168.1.100:8082/schoolkonw/news.php");
		
		this.edit.setOnFocusChangeListener(new OnFocusChangeListener() {
			public void onFocusChange(View v, boolean hasFocus) {
				if(hasFocus){
					relative.setVisibility(View.VISIBLE);
				}else{
					relative.setVisibility(View.GONE);
				}
			}
		});
		
		this.edit.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				inputLen=140-count-start;
				tip.setText("还可以输入 "+inputLen+" 个字");
				if(inputLen==140){
					btn.setText(String.valueOf(comment_num));
				}else{
					btn.setText("发布");
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		this.btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(btn.getText().toString().equals("发布")){
					if(inputLen<0){
						showShortToast("字体超过了限制哟");
					}else{
						showShortToast("可以发表了哟！");
						if(cb.hasSelection()){
							System.out.println("1");
						}else{
							System.out.println("2");
						}
					}
				}else{
					showShortToast("去看看评论吧!");
				}
			}
		});
				    
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
			edit.clearFocus();
			finish();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	@Override
	protected void HandleTitleBarEvent(int buttonId,View view) {
		switch (buttonId) {
		case 0:
			finish();
			break;
		default:
			break;
		}

	}

	
	
	
}
