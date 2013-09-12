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
    
    //�ײ�С��ͼƬ
    private ImageView[] dots ;
    
    private String[] newTitle=new String[]{"ͥ�������:������ժ�۾�¶ƣ̬",
    		"����쭳�����ҹ���������о���",
    		"�ӽ�:���֡�CEO����������ֻ�����",
    		"����Ͱ�͡��ϰ��꾻��1293��,����7.1��",
    		"��!��ʵ�������װ������չ��"};
    private String[][] news=new String[][]{
    {"11","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"22","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"33","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"4","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"5","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"6","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"7","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"},
    {"8","ֱ��:��������������ͥ�����","���������ۻ���,�ط�:�����м������ؾ�����Ӧ�ϳ�.","124"}
    };
    
    //��¼��ǰѡ��λ��
    private int currentIndex;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setView(R.layout.ecjtu_news);
		SetTitle("��������");
		setTitleBar(R.drawable.titlebar_reback_selector, "����", 0, "");
		
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
		
		//��ʼ������ͼƬ�б�
        for(int i=0; i<pics.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setLayoutParams(mParams);
            iv.setImageResource(pics[i]);
            views.add(iv);
        }
        
        vp = (ViewPager) findViewById(R.id.news_img_viewpager);
        //��ʼ��Adapter
        vpAdapter = new ViewPagerAdapter(views);
        vp.setAdapter(vpAdapter);
        //�󶨻ص�
        vp.setOnPageChangeListener(this);
        
        //��ʼ���ײ�С��
        initDots();
	}
	
	private void initDots(){
		 LinearLayout layout = (LinearLayout) findViewById(R.id.news_dot_loyout);
		 dots = new ImageView[pics.length];
		 
		//ѭ������С��ͼƬ
	    for (int i = 0; i < pics.length; i++) {
	         dots[i] = new ImageView(this);
	         dots[i].setImageResource(R.drawable.dot);
	         dots[i].setEnabled(true);//����Ϊ��ɫ
	         dots[i].setOnClickListener(new OnclickListenerImp());
	         dots[i].setTag(i);//����λ��tag������ȡ���뵱ǰλ�ö�Ӧ
	         LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(15,15);
	         param.setMargins(5, 0, 5,0);
	         layout.addView(dots[i],param);
	    }
	    currentIndex = 0;
        dots[currentIndex].setEnabled(false);//����Ϊ��ɫ����ѡ��״̬
        topic.setText(newTitle[currentIndex]);
	}
	
	 /**
     *���õ�ǰ������ҳ 
     */
    private void setCurView(int position)
    {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }
    
    /**
     *��ֻ��ǰ����С���ѡ�� 
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

	//������״̬�ı�ʱ����
	public void onPageScrollStateChanged(int arg0) {
		
	}

	 //����ǰҳ�汻����ʱ����
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	//���µ�ҳ�汻ѡ��ʱ����
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
