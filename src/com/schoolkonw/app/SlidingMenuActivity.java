package com.schoolkonw.app;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.SimpleAdapter;

public abstract class SlidingMenuActivity extends Activity implements OnClickListener {
	
	private SlidingMenuLayout slideMenu;
	private int screen_width=0;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		screen_width = getWindowManager().getDefaultDisplay().getWidth();
	}
	
	public SlidingMenuLayout getslideMenu(){
		return this.slideMenu;
	}
	
	private LinearLayout createLeftView() {
		String menulist[]=new String[]{"新鲜事","消息","聊天","好友","找人"};
		int menulistImg[]=new int[]{R.drawable.left_slide_menu_list_6,
				R.drawable.left_slide_menu_list_5,R.drawable.left_slide_menu_list_2,
				R.drawable.left_slide_menu_list_3,R.drawable.left_slide_menu_list_8};
		
		LinearLayout slideMenu=(LinearLayout) getLayoutInflater().
				inflate(R.layout.left_slide_menu, null);
		
		/**
		 * 点击左菜单的头像进入登录页面
		 */
		RelativeLayout relative=(RelativeLayout) slideMenu.
				findViewById(R.id.left_slide_menu_layout_userinfo);
		relative.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent it=new Intent(SlidingMenuActivity.this,login.class);
				startActivity(it);
			}
		});
		
		ListView lv=(ListView) slideMenu.findViewById(R.id.left_slide_menu_usual_listview);
		
		List<Map<String,String>> list=new ArrayList<Map<String,String>>();		
		
		SimpleAdapter simpleadapter=null;
		
		for(int i=0;i<menulist.length;i++){
			Map<String,String> map=new HashMap<String, String>();
			map.put("title", menulist[i]);
			map.put("src", String.valueOf(menulistImg[i]));
			list.add(map);
		}
		simpleadapter=new SimpleAdapter(SlidingMenuActivity.this,list,
				R.layout.left_slide_menu_data_list, new String[]{"title","src"},
				new int[]{R.id.left_slide_menu_data_list_title,
				R.id.left_slide_menu_data_list_img});
		lv.setAdapter(simpleadapter);
		return slideMenu;
	}
	private LinearLayout createRightView() {
		String Data[][]=new String[][]{
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"},
				{"彭炜","舞影凌风","没有伞的孩子注定要努力向前奔跑"}
		};
		LinearLayout slideMenu=(LinearLayout) getLayoutInflater().
				inflate(R.layout.right_slide_menu, null);
		ListView listview=(ListView) slideMenu.findViewById(R.id.right_slide_menu_listview);
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(int i=0;i<Data.length;i++){
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("img", String.valueOf(R.drawable.userimage));
			map.put("name", Data[i][0]);
			map.put("nickname", "("+Data[i][1]+")");
			map.put("mood", Data[i][2]);
			list.add(map);
		}
		SimpleAdapter adapter=new SimpleAdapter(SlidingMenuActivity.this, list,
				R.layout.right_slide_menu_data_list,
				new String[]{"img","name","nickname","mood"}, 
				new int[]{R.id.right_slide_menu_friend_img,R.id.right_slide_menu_friend_name,
				R.id.right_slide_menu_friend_nickname,R.id.right_slide_menu_friend_mood});
		listview.setAdapter(adapter);
		return slideMenu;
	}


	public void setView(int layoutId) {
		slideMenu=new SlidingMenuLayout(this,layoutId);
		setContentView(slideMenu);
		slideMenu.GetBaseLayout().leftButton.setOnClickListener(this);
		slideMenu.GetBaseLayout().rightButton.setOnClickListener(this);
	}
	
	protected void SetTitle(String title) {
		slideMenu.SetTitle(title);
	}
	
	protected void setTitleBar(int leftBtnDrawId, String leftBtnText,
			int rightBtnDrawId, String rightBtnText){
		slideMenu.setTitleBar(leftBtnDrawId, leftBtnText, rightBtnDrawId, rightBtnText);
	}
	
	
	public void onClick(View view) {
		Button leftButton = slideMenu.GetBaseLayout().leftButton;
		Button rightButton = slideMenu.GetBaseLayout().rightButton;
		if (view == leftButton) {
			HandleTitleBarEvent(0,view);
		} else if (view == rightButton) {
			HandleTitleBarEvent(1,view);
		}
		
	}
	protected abstract void HandleTitleBarEvent(int buttonId,View v);
	
	
	
	
	
	/**
	 * SlidingMenuLayout类
	 * @author wei8888go
	 * 将侧滑菜单和主页面整合到一个线性布局
	 *
	 */
	
	public  class SlidingMenuLayout extends FrameLayout{
		
		private BaseLayout basely=null;
		private View leftMenu=null;
		private View rightMenu=null;
		
		private int LeftMenuWidth=screen_width*4/5;
		private int RightMenuWidth=screen_width*2/3;
		
		private int startX;
		private Scroller mScroller;
		private static final int MOVE_DISTANCE = 50;
		private int flingDirect = 0;
		public static final int MOVE_LEFT = 1;
		public static final int MOVE_RIGHT = 2;
		private int currOffset;
		private int mainState;
		public static final int STATE_MIDDLE=0;
		
		//右滑动菜单是否隐藏
		private Boolean isHiden=false;

		public SlidingMenuLayout(Context context,int layoutId) {
			super(context);
			
			//设置左侧滑菜单
			FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(
					LeftMenuWidth, LayoutParams.FILL_PARENT);
			leftMenu=createLeftView();
			addView(leftMenu,p);
			
			//设置右侧滑菜单
			FrameLayout.LayoutParams p1 = new FrameLayout.LayoutParams(
					RightMenuWidth, LayoutParams.FILL_PARENT);
			p1.gravity=Gravity.RIGHT;
			rightMenu=createRightView();
			addView(rightMenu,p1);
			
			basely=new BaseLayout(context,layoutId);
			FrameLayout.LayoutParams p2 = new FrameLayout.LayoutParams(
					LayoutParams.FILL_PARENT , LayoutParams.FILL_PARENT);
			addView(basely,p2);
			
			mScroller = new Scroller(context);
			mainState = 0;
			
		}
		
		public boolean onInterceptTouchEvent(MotionEvent ev) {
			switch (ev.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				startX = (int) ev.getRawX();
				flingDirect = 0;
				break;
			}
			case MotionEvent.ACTION_MOVE: {
				final int dx = (int) (ev.getRawX() - startX);
				if (Math.abs(dx) > 10) {
					return true;
				}
				break;
			}

			}
			return super.onInterceptTouchEvent(ev);
		}
		
		public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_MOVE: {
				int dx = (int) (event.getRawX() - startX);				
				if (dx > MOVE_DISTANCE) {
					flingDirect = MOVE_LEFT;
					
					//显示左菜单时隐藏右菜单
					if(!isHiden&&currOffset==0){
						rightMenu.setVisibility(View.GONE);
						isHiden=true;
					}
				} else if (dx < -MOVE_DISTANCE) {
					flingDirect = MOVE_RIGHT;
					
					//恢复右菜单显示
					if(isHiden&&currOffset==0){
						rightMenu.setVisibility(View.VISIBLE);
						isHiden=false;
					}
				} else {
					flingDirect = 0;
				}
				if(currOffset - dx <- LeftMenuWidth) {
					basely.scrollTo(-LeftMenuWidth, 0);
				}else if (currOffset - dx > RightMenuWidth) {
					basely.scrollTo(RightMenuWidth, 0);
				}else{
					basely.scrollTo(currOffset - dx, 0);
				}

				break;
			}
			case MotionEvent.ACTION_UP: {				
				judgeDirection();				
				invalidate();
				break;
			}
			}
			return true;
		}
		
		public void judgeDirection(){
			
			switch(mainState){
			case MOVE_LEFT:{
				if(flingDirect == MOVE_RIGHT){
					mScroller.startScroll(basely.getScrollX(), 0, -basely.getScrollX(), 0);
					mainState= STATE_MIDDLE;
					currOffset = 0;
				}
				else {
					mScroller.startScroll(basely.getScrollX(), 0,
							-LeftMenuWidth-basely.getScrollX(), 0);
					currOffset = -LeftMenuWidth;
				}
				break;
			}
			case MOVE_RIGHT:{
				if(flingDirect == MOVE_LEFT){
					mScroller.startScroll(basely.getScrollX(), 0, -basely.getScrollX(), 0);
					mainState= STATE_MIDDLE;
					currOffset = 0;
				}
				else {
					mScroller.startScroll(basely.getScrollX(), 0,
							RightMenuWidth-basely.getScrollX(), 0);
					currOffset = RightMenuWidth;
				}
				break;
			}
			case STATE_MIDDLE:{
				if (flingDirect == MOVE_LEFT) {
					mScroller.startScroll(basely.getScrollX(), 0, -LeftMenuWidth-basely.getScrollX(), 0);
					mainState= MOVE_LEFT;
					currOffset = -LeftMenuWidth;
				} else if (flingDirect == MOVE_RIGHT) {
					mScroller.startScroll(basely.getScrollX(), 0, RightMenuWidth-basely.getScrollX(), 0);
					mainState = MOVE_RIGHT;
					currOffset = RightMenuWidth;
				} else {
					mScroller.startScroll(basely.getScrollX(), 0,
							-basely.getScrollX(), 0);
					mainState=STATE_MIDDLE;
					currOffset = 0;
				}
				break;
			}
			}
			
			
		}
		public void moveToLeft(){
			if(mainState==STATE_MIDDLE){
				if(!isHiden&&currOffset==0){
					rightMenu.setVisibility(View.GONE);
					isHiden=true;
				}
				mScroller.startScroll(basely.getScrollX(), 0, -LeftMenuWidth-basely.getScrollX(), 0);
				mainState= MOVE_LEFT;
				currOffset = -LeftMenuWidth;
			}
		}
		
		public void moveToRight(){
			if(mainState==STATE_MIDDLE){
				if(isHiden&&currOffset==0){
					rightMenu.setVisibility(View.VISIBLE);
					isHiden=false;
				}
				mScroller.startScroll(basely.getScrollX(), 0, RightMenuWidth-basely.getScrollX(), 0);
				mainState = MOVE_RIGHT;
				currOffset = RightMenuWidth;
			}
		}
		
		
		public void moveToMain(){
			mScroller.startScroll(basely.getScrollX(), 0, -basely.getScrollX(), 0);
			mainState= STATE_MIDDLE;
			currOffset = 0;
		}
			
		public int getMoveState(){
			return mainState;
		}
		
		public void computeScroll() {
			if (mScroller.computeScrollOffset()) {
				basely.scrollTo(mScroller.getCurrX(), 0);
				postInvalidate();
			}
			super.computeScroll();
		}
		
		protected BaseLayout GetBaseLayout(){
			return this.basely;
		}
		
		protected View GetMenu(){
			return this.leftMenu;
		}
		
		protected void SetTitle(String title) {
			if (basely != null) {
				basely.setTitle(title);
			}
		}
		
		protected void setTitleBar(int leftBtnDrawId, String leftBtnText,
				int rightBtnDrawId, String rightBtnText) {
			if (leftBtnDrawId > 0 || leftBtnText.length() > 0) {
				basely.leftButton.setVisibility(View.VISIBLE);
				if (leftBtnDrawId > 0) {
					basely.leftButton.setBackgroundResource(leftBtnDrawId);
				}
				if (leftBtnText.length() > 0) {
					basely.leftButton.setText(leftBtnText);
				}
			} else {
				basely.leftButton.setVisibility(View.INVISIBLE);
			}
			if (rightBtnDrawId > 0 || rightBtnText.length() > 0) {
				basely.rightButton.setVisibility(View.VISIBLE);
				if (rightBtnDrawId > 0) {
					basely.rightButton.setBackgroundResource(rightBtnDrawId);
				}
				if (rightBtnText.length() > 0) {
					basely.rightButton.setText(rightBtnText);
				}
			} else {
				basely.rightButton.setVisibility(View.INVISIBLE);
			}
		}
		
		
		
		
	}



}
