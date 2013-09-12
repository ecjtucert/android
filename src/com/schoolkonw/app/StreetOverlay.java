
package com.schoolkonw.app;

import java.util.ArrayList;

import com.tencent.street.animation.AnimGLSet;
import com.tencent.street.animation.ScaleResumeAnimGL;
import com.tencent.street.animation.TranslateAnimGL;
import com.tencent.street.overlay.ItemizedOverlay;
import com.tencent.street.overlay.model.ItemModel;
import android.graphics.Bitmap;

/**
 * �?��自己实现的overlay的example
 * 
 */
public class StreetOverlay extends ItemizedOverlay {

    private ArrayList<StreetPoiData> mPois;

    public StreetOverlay(ArrayList<StreetPoiData> pois) {
        this.mPois = pois;
    }

    @Override
    public int size() {
        return mPois.size();
    }

    @Override
    public void onTap(int index, float x, float y) {

        // test 更新某个poi信息
        Bitmap bitmap = Bitmap.createBitmap(mPois.get(1).marker);
        
        StreetPoiData poi = mPois.get(index);
        poi.updateMarker(bitmap, bitmap.toString());
        refresh(index);
    }

    @Override
    public ItemModel getItem(int index) {
        final StreetPoiData poi = mPois.get(index);
        if (poi == null)
            return null;

        ItemModel item = new CustomerItem(poi.latE6, poi.lonE6, poi.heightOffset);
        item.setAdapter(new ItemModel.IItemMarkerAdapter() {

            @Override
            public int getMarkerWidth() {
                return poi.marker.getWidth();
            }

            @Override
            public int getMarkerHeight() {
                return poi.marker.getHeight();
            }

            @Override
            public Bitmap getMarker(int state) {
                return poi.marker;
//                if (state == CustomerItem.STATE_PRESSED && poi.markerPressed != null) {
//                    return poi.markerPressed;
//                } else {
//                }
            }

            @Override
            public void onGetMarker(boolean suc) {

            }

            @Override
            public String getMarkerUID() {
                return poi.uid;
            }
        });

        TranslateAnimGL translateAnim = new TranslateAnimGL(0, 0, 180, 0, 400);
        // ScaleAnimGL scaleAnimGLMin = new ScaleAnimGL(1, 1, 1, 0.7f, 200);
        // ScaleAnimGL scaleAnimGLResume = new ScaleAnimGL(1, 1, 0.7f, 1f,
        // 2000);
        ScaleResumeAnimGL scaleResumeAnimGL = new ScaleResumeAnimGL(1, 1, 1, 0.7f, 100, 100);
        // ScaleAnimGL empty = new ScaleAnimGL(1, 1, 1, 1, 1000L);
        AnimGLSet animset = new AnimGLSet(translateAnim, scaleResumeAnimGL);// ,
                                                                            // scaleAnimGLResume,
                                                                            // empty);
        // animset.setRepeat(true);
        item.startAnim(animset);

        return item;
    }

    /**
     * 自定义一个Item 用来实现每个item的动�?
     * 
     * @author michaelzuo
     */
    private class CustomerItem extends ItemModel {

        private float heightOffset;

        public CustomerItem(int latE6, int lonE6, IItemMarkerAdapter adapter, float offset) {
            super(latE6, lonE6, adapter);
            this.heightOffset = offset;
        }

        public CustomerItem(int latE6, int lonE6, float offset) {
            super(latE6, lonE6);
            this.heightOffset = offset;
        }

        @Override
        public float onGetItemScale(double distance, float angleScale) {
            /*
             * 当distance < minDis , scale为maxScale 当distance > maxDis ,
             * scale为minScale 当distance在minDis和maxDis之间 , scale按比例变�?当distance >
             * maxShowDis , 不再显示
             */
            float scale = 1.0f;

            // 近大远小功能
            // final float minScale = 0.8f;
            // final float maxScale = 1.5f;
            // final double minDis = 20;
            // final double maxDis = 40;
            // final double maxShowDis = 150;
            //
            // if(distance < minDis) {
            // scale = maxScale;
            // } else if (distance < maxDis) {
            // scale = (float) (maxScale - (maxScale - minScale) * (distance -
            // minDis) / (maxDis - minDis));
            // } else if (distance < maxShowDis) {
            // scale = minScale;
            // } else {
            // scale = SCALE_INVISIBLE;
            // return scale;
            // }

            // 根据视角poi进行缩放
            final float factor = 0.2f; // 根据视角放大倍数对Item缩放的因�?
            scale = scale + (angleScale - 1) * factor;

            return scale;
        }

        @Override
        protected float onGetItemHeightOffset() {
            return this.heightOffset;
        }

    }

}
