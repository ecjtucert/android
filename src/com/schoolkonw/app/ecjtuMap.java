package com.schoolkonw.app;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Paint.Style;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.index.schoolkonw.R;
import com.tencent.mapapi.map.GeoPoint;
import com.tencent.mapapi.map.LocationListener;
import com.tencent.mapapi.map.LocationManager;
import com.tencent.mapapi.map.MapActivity;
import com.tencent.mapapi.map.MapController;
import com.tencent.mapapi.map.MapView;
import com.tencent.mapapi.map.Overlay;
import com.tencent.mapapi.map.Projection;

public class ecjtuMap extends MapActivity {

	MapView mMapView;
	MapController mMapController;
	LocationManager locManager=null;
	LocationListener locListener=null;
	LocationOverlay locMyOverlay=null; 
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		BaseLayout baselay=new BaseLayout(this,R.layout.ecjtu_map);
		baselay.setTitle("交大地图");
		baselay.leftButton.setBackgroundResource(R.drawable.title_btn_right);
		baselay.leftButton.setText("定位");
		baselay.rightButton.setBackgroundResource(R.drawable.title_btn_right);
		baselay.rightButton.setText("切换");
		
		
		baselay.leftButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if(locManager==null){
					locManager=LocationManager.getInstance();
					locManager.requestLocationUpdates(locListener);
					locManager.enableProvider(ecjtuMap.this);
				}
			}
		});
		baselay.rightButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent it=new Intent(ecjtuMap.this,ecjtuStreeMap.class);
				startActivity(it);
			}
		});
		setContentView(baselay);
		mMapView = (MapView) findViewById(R.id.ecjtu_map);		
		mMapView.setBuiltInZoomControls(true); 
		mMapController = mMapView.getController(); 
		GeoPoint point = new GeoPoint((int)(39.91 * 1E6),(int) (116.397 * 1E6)); 		
		mMapController.setCenter(point); 
		mMapController.setZoom(12);
		
	    locListener=new LocationListener(){
			public void onLocationChanged(Location location) {
				if(location==null){
					return;
				}
				if(locMyOverlay==null)
				{
					Bitmap bmpMarker=null;
					Resources res=ecjtuMap.this.getResources();
					bmpMarker=BitmapFactory.decodeResource(res, R.drawable.mark_location);
					locMyOverlay=new LocationOverlay(bmpMarker);
					mMapView.getOverlays().add(locMyOverlay);
				}
				double iLongti=location.getLongitude();
				double iLatitu=location.getLatitude();
				locMyOverlay.setGeoCoords(iLongti, iLatitu);
				locMyOverlay.setAccuracy(location.getAccuracy());
				mMapView.invalidate();				
				GeoPoint geoAnimationTo=new GeoPoint((int)(iLatitu*1e6),(int)(iLongti*1e6));
				mMapView.getController().animateTo(geoAnimationTo);
			}};
	}
	
   protected void onDestroy() {
		// TODO Auto-generated method stub
	   if(locManager!=null){
		   locManager.removeUpdates(locListener);
		   locManager.disableProvider();
		   locManager.release();
		   locManager=null;
	   }
	   if(locMyOverlay!=null)
	   {
		   this.mMapView.getOverlays().remove(locMyOverlay);
	   }
	   super.onDestroy();
	}

   class LocationOverlay extends Overlay {
	
		GeoPoint geoPoint;
		Bitmap bmpMarker;
		float fAccuracy=0f;
		

		public LocationOverlay(Bitmap mMarker) {
		    bmpMarker = mMarker;
		}
		
		public void setGeoCoords(double dLongti,double dLatitu)
		{
			if(geoPoint==null)
			{
				geoPoint=new GeoPoint((int)(dLatitu*1E6),(int)(dLongti*1E6));
			}
			else
			{
				geoPoint.setLatitudeE6((int)(dLatitu*1E6));
				geoPoint.setLongitudeE6((int)(dLongti*1E6));
			}
		}
		
		public void setAccuracy(float fAccur)
		{
			fAccuracy=fAccur;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			if(geoPoint==null)
			{
				return;
			}
			Projection mapProjection = mapView.getProjection();
			Paint paint = new Paint();
			Point ptMap = mapProjection.toPixels(geoPoint, null);
			paint.setColor(Color.BLUE);
			paint.setAlpha(8);
			paint.setAntiAlias(true);

			float fRadius=mapProjection.metersToEquatorPixels(fAccuracy);
			canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
			paint.setStyle(Style.STROKE);
			paint.setAlpha(100);
			canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);

			if(bmpMarker!=null)
			{
				paint.setAlpha(255);
				canvas.drawBitmap(bmpMarker, ptMap.x - bmpMarker.getWidth() / 2, ptMap.y
						- bmpMarker.getHeight() / 2, paint);
			}
			
			super.draw(canvas, mapView, shadow);
		}
	}


	
}
