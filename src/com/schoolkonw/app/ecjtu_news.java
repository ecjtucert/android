package com.schoolkonw.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.index.schoolkonw.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ecjtu_news extends BaseActivity implements OnPageChangeListener {
	
	private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private TextView topic=null;
    private ListView newslist=null;
    
    private List<Map<String,String>> list=new ArrayList<Map<String,String>>();
	private SimpleAdapter simpleadapter=null;
    
    private static final int[] pics = { R.drawable.news1,
        R.drawable.news2, R.drawable.news3,
        R.drawable.news4,R.drawable.news5 };
    
    //底部小店图片
    private ImageView[] dots ;
    
    private String[] newTitle=new String[]{"庭审第五天:薄熙来摘眼镜露疲态",
    		"北京飙车党深夜开豪车逆行竞速",
    		"视界:少林‘CEO’开光天价手机号码",
    		"‘三桶油’上半年净利1293亿,日挣7.1亿",
    		"酷!现实版钢铁侠装备下周展出"};
    private String[][] news=new String[][]{
    {"11","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"22","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"33","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"4","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"5","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"6","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"7","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"},
    {"8","直播:薄熙来案第五日庭审过程","今天进入辩论环节,控方:薄罪行及其严重拒认罪应严惩.","124"}
    };
    
    //记录当前选中位置
    private int currentIndex;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.ecjtu_news);
		SetTitle("交大新闻");
		setTitleBar(R.drawable.titlebar_reback_selector, "返回", 0, "");
		
		topic=(TextView) super.findViewById(R.id.news_topic_title);
		newslist=(ListView) super.findViewById(R.id.news_listview);
		for(int i=0;i<this.news.length;i++){
			Map<String,String> map=new HashMap<String, String>();
			map.put("id", this.news[i][0]);
			map.put("title", this.news[i][1]);
			map.put("dep", this.news[i][2]);
			map.put("reply", this.news[i][3]);
			this.list.add(map);
		}
		this.simpleadapter=new SimpleAdapter(this,this.list,
				R.layout.ecjtu_news_list_style, new String[]{"id","title","dep","reply"},
				new int[]{R.id.news_list_id,R.id.news_list_title,R.id.news_list_dep,R.id.news_list_reply_num});
		this.newslist.setAdapter(this.simpleadapter);
		
		this.newslist.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				@SuppressWarnings("unchecked")
				HashMap<String,String> map=(HashMap<String,String>)newslist.
				getItemAtPosition(position); 
				Intent it=new Intent(ecjtu_news.this,ecjtu_news_content.class);
				it.putExtra("id",map.get("id"));
				startActivity(it);
				
			}});
		
		views = new ArrayList<View>();
		
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
		
		//初始化引导图片列表
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        
        vp = (ViewPager) findViewById(R.id.news_img_viewpager);
        //初始化Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //绑定回调
        vp.setOnPageChangeListener(this);
        
        //初始化底部小点
        initDots();
	}
	
	private void initDots(){
		 LinearLayout layout = (LinearLayout) findViewById(R.id.news_dot_loyout);
		 dots = new ImageView[pics.length];
		 
		//循环设置小点图片
	    for (int i = 0; i < pics.length; i++) {
	         dots[i] = new ImageView(this);
	         dots[i].setImageResource(R.drawable.dot);
	         dots[i].setEnabled(true);//都设为灰色
	         dots[i].setOnClickListener(new OnclickListenerImp());
	         dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
	         LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(15,15);
	         param.setMargins(5, 0, 5,0);
	         layout.addView(dots[i],param);
	    }
	    currentIndex = 0;
        dots[currentIndex].setEnabled(false);//设置为白色，即选中状态
        topic.setText(newTitle[currentIndex]);
	}
	
	 /**
     *设置当前的引导页 
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }
    
    /**
     *这只当前引导小点的选中 
     */
    private void setCurDot(int positon)
    {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setEnabled(false);
        dots[currentIndex].setEnabled(true);
        topic.setText(newTitle[currentIndex]);

        currentIndex = positon;
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

	//当滑动状态改变时调用
	public void onPageScrollStateChanged(int arg0) {
		
	}

	 //当当前页面被滑动时调用
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	//当新的页面被选中时调用
	public void onPageSelected(int position) {
		//Log.e("tip", "onPageSelected:"+arg0);
		setCurView(position);
        setCurDot(position);
        topic.setText(newTitle[position]);
		
	}
	
	public class OnclickListenerImp implements OnClickListener{

		public void onClick(View v) {
			int position = (Integer)v.getTag();
	        setCurView(position);
	        setCurDot(position);
	        topic.setText(newTitle[position]);
		}
		
	}

}
