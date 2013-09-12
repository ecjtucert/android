package com.schoolkonw.app;





import com.index.schoolkonw.R;

import android.os.Bundle;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

public class MainActivity extends SlidingMenuActivity{
	
	public SlidingMenuLayout slideMenu;

	private GridView gridview=null;
	
	
	private int picRes[]=new int[]{R.drawable.menu_1,R.drawable.menu_2,R.drawable.menu_9,
			R.drawable.menu_5,R.drawable.menu_6,R.drawable.menu_3,
			R.drawable.menu_11,R.drawable.menu_8,R.drawable.menu_14,R.drawable.menu_10,
			R.drawable.menu_12,R.drawable.menu_13,R.drawable.menu_7,R.drawable.menu_15};
	private String title[]=new String[]{"成绩查询","图书馆","四六级","课程表","交大新闻","二手市场",
			"花椒问答","交大地图","网上选修","资料共享","考试安排","空教室","找人","交大影院"};
	

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    setView(R.layout.activity_main);
	    SetTitle(getString(R.string.app_name));
	    setTitleBar(R.drawable.title_btn_right, "登陆",R.drawable.title_btn_right, "好友");
	    
	    slideMenu=getslideMenu();
		
		
		this.gridview=(GridView) super.findViewById(R.id.gallery);
		this.gridview.setAdapter(new MenuListAdapter(this,this.picRes,this.title));		
		this.gridview.setOnItemClickListener(new itemclicklistener());
		
		
	}
	
	
	
	
	
	private class itemclicklistener implements OnItemClickListener{

		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			Intent it=null;
			switch(position){
			case 0:
				it=new Intent(MainActivity.this,get_score.class);
				break;
			case 1:
				it=new Intent(MainActivity.this,library_login.class);
				break;
			case 2:
				it=new Intent(MainActivity.this,cet_login.class);
				break;
			case 3:
				it=new Intent(MainActivity.this,schedule.class);
				break;
			case 4:
				it=new Intent(MainActivity.this,ecjtu_news.class);
				break;
			case 5:
				it=new Intent(MainActivity.this,market.class);
				break;
			case 6:
				it=new Intent(MainActivity.this,get_score.class);
				break;
			case 7:
				it=new Intent(MainActivity.this,ecjtuMap.class);
				break;
			case 8:
				it=new Intent(MainActivity.this,online_course_login.class);
				break;
			case 9:
				it=new Intent(MainActivity.this,resource_sharing.class);
				break;
			case 10:
				it=new Intent(MainActivity.this,exam_arrange.class);
				break;
			case 12:
				it=new Intent(MainActivity.this,FindPerson.class);	
                break;
			case 13:
				it=new Intent(MainActivity.this,Cinema.class);	
                break;
			default:
				it=new Intent(MainActivity.this,get_score.class);					
			}
			MainActivity.this.startActivity(it);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * 
	 * 连续按2次返回键退出系统
	 */
	private long exitTime = 0;
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(MainActivity.this,"再按一次退出系统",Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
				System.exit(0);
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void HandleTitleBarEvent(int buttonId,final View view) {
		switch(buttonId){
			case 0:
				if (slideMenu.getMoveState() == SlidingMenuLayout.STATE_MIDDLE)
					slideMenu.moveToLeft();
				else {
					slideMenu.moveToMain();
				}
				break;
			case 1:
				if (slideMenu.getMoveState() == SlidingMenuLayout.STATE_MIDDLE)
					slideMenu.moveToRight();
				else {
					slideMenu.moveToMain();
				}
				break;				
		}
		
	}

	
	

}
