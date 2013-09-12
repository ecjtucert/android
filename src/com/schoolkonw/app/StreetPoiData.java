/**
 * 
 */

package com.schoolkonw.app;

import android.graphics.Bitmap;

/**
 * 将创建poi overlay�?��数据封装 
 * 
 */
public class StreetPoiData {

    /**
     * 纬度�?0E6
     */
    public int latE6;

    /**
     * 经度�?0E6
     */
    public int lonE6;
    
    /**
     * poi点显示的图片
     */
    public Bitmap marker;
    
    /**
     * poi被点击时显示的图�?
     */
    public Bitmap markerPressed;
    
    /**
     * 高度偏移�?
     */
    public float heightOffset;
    
    public String uid;

    public StreetPoiData(int x, int y) {
        this(x, y, null, null, 0);
    }
    
    public StreetPoiData(int x, int y, Bitmap marker) {
    	this(x, y, marker, null, 0);
    }
    
	public StreetPoiData(int x, int y, Bitmap marker, Bitmap markerPressed, float offset) {
		this.latE6 = x;
		this.lonE6 = y;
		this.marker = marker;
		this.markerPressed = markerPressed;
		this.heightOffset = offset;
		this.uid = "";
	}
	
	public void updateMarker(Bitmap bitmap, String uid) {
		this.marker = bitmap;
		this.uid = uid;
	}

}
