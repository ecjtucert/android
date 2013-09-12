package com.schoolkonw.app;


import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.index.schoolkonw.R;
import com.tencent.street.StreetThumbListener;
import com.tencent.street.StreetViewListener;
import com.tencent.street.StreetViewShow;
import com.tencent.street.map.basemap.GeoPoint;
import com.tencent.street.overlay.ItemizedOverlay;

public class ecjtuStreeMap extends Activity implements StreetViewListener {
    private ViewGroup mView;

    private ImageView mImage;

    private Handler mHandler;
    
    private View mStreetView;

    @SuppressLint("HandlerLeak")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseLayout baselay=new BaseLayout(this,R.layout.ecjtu_stree_map);
		baselay.setTitle("交大街景");
		baselay.leftButton.setBackgroundResource(R.drawable.title_btn_right);
		baselay.leftButton.setText("定位");
		baselay.rightButton.setBackgroundResource(R.drawable.title_btn_right);
		baselay.rightButton.setText("切换");
		baselay.leftButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		baselay.rightButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StreetViewShow.getInstance().destory();  
				Intent it=new Intent(ecjtuStreeMap.this,ecjtuMap.class);
				startActivity(it);
			}
		});
		setContentView(baselay);
        
        
        
        mView = (LinearLayout)findViewById(R.id.ecjtu_stree_map_layout);
        mImage = (ImageView)findViewById(R.id.ecjtu_stree_map);

        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                mImage.setImageBitmap((Bitmap)msg.obj);
            }
        };
        
        GeoPoint center = new GeoPoint((int)(28.743166 * 1E6), (int)(115.867749 * 1E6));
        String key = "ffd045196eea7f1c20963e4690d6d84e";       
        StreetViewShow.getInstance().showStreetView(this, center, 100, this, -170, 0, key);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StreetViewShow.getInstance().requestStreetThumb("10041002111120153536407",//"10011505120412110900000",
                new StreetThumbListener() {

                    @Override
                    public void onGetThumbFail() {
                       
                    }
                    public void onGetThumb(Bitmap bitmap) {
                        Message msg = new Message();
                        msg.obj = bitmap;
                        mHandler.sendMessage(msg);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onViewReturn(final View v) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView = v;
                mView.addView(mStreetView);
            }
        });
    }

    @Override
    public void onNetError() {
//        LogUtil.logStreet("onNetError");
    }

    @Override
    public void onDataError() {
//        LogUtil.logStreet("onDataError");
    }

    StreetOverlay overlay;
    
    @Override
    public ItemizedOverlay getOverlay() {
        if (overlay == null) {
            ArrayList<StreetPoiData> pois = new ArrayList<StreetPoiData>();
            pois.add(new StreetPoiData(39984066, 116307968, getBm(R.drawable.poi_center),
                    getBm(R.drawable.poi_center_pressed), 0));
            overlay = new StreetOverlay(pois);
            overlay.populate();
        }
        return overlay;
    }

    private Bitmap getBm(int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Config.ARGB_8888;
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inScaled = false;

        return BitmapFactory.decodeResource(getResources(), resId, options);
    }

	@Override
	public void onLoaded() {
		runOnUiThread(new Runnable() {
            @Override
            public void run() {
            	mStreetView.setVisibility(View.VISIBLE);
            }
        });
	}

    @Override
    public void onAuthFail() {
    }
}