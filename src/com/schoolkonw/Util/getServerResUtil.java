package com.schoolkonw.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class getServerResUtil {
	private String url=null;
	
	public getServerResUtil(String url){
		this.url=url;
	}
	
	
	public String getRes(){
		try {
			URL u=new URL(url);
			HttpURLConnection conn=(HttpURLConnection) u.openConnection();
			 StringBuffer buffer = new StringBuffer();
			 InputStreamReader r = new InputStreamReader(conn.getInputStream());
			 BufferedReader rd = new BufferedReader(r);
			 String line;
			 while ((line = rd.readLine()) != null) {
				    buffer.append(line);
			 }
			 rd.close();
			 conn.getInputStream().close();
			 return buffer.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	public Bitmap getBitMap(){
		URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
                myFileUrl = new URL(this.url);
        } catch (MalformedURLException e) {
                e.printStackTrace();
        }
        try {
	        HttpURLConnection conn = (HttpURLConnection) myFileUrl
	                        .openConnection();
	        conn.setDoInput(true);
	        conn.connect();
	        InputStream is = conn.getInputStream();
	        bitmap = BitmapFactory.decodeStream(is);
	        is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
	}
}
