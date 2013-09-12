package com.schoolkonw.app;



import com.index.schoolkonw.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuListAdapter extends BaseAdapter {
	
	private Context context=null;
	private View[] itemViews;  
	
	public MenuListAdapter(Context context,int[] picId,String[] str){
		this.context=context;
		itemViews = new View[picId.length];  		  
        for (int i = 0; i < itemViews.length; i++) {  
            itemViews[i] = makeItemView(picId[i],str[i]);  
        }  
	}
	
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return itemViews.length;
	}

	@Override
	public View getItem(int position) {
		// TODO Auto-generated method stub
		return this.itemViews[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	private View makeItemView(int picId,String str) {  
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);   
        View itemView = inflater.inflate(R.layout.menu_item, null);
        TextView title = (TextView) itemView.findViewById(R.id.txt_userAge);
        title.setText(str);  
		ImageView image=(ImageView) itemView.findViewById(R.id.imageView_ItemImage);;
		image.setImageResource(picId);
		image.setScaleType(ImageView.ScaleType.CENTER);
		return itemView;
    }  

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		 //if (view == null)  
             return itemViews[position];  
          //return view; 
	}

}
