package com.schoolkonw.helper;

import org.json.JSONException;
import org.json.JSONObject;

import com.schoolkonw.Util.Sha1Util;
import com.schoolkonw.Util.getServerResUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class LoginHelper{
	
	public SharedPreferences share;
	public SharedPreferences.Editor edit=null;
	private Context context;
	private static final String FILENAME="login";
	
	@SuppressLint("CommitPrefEdits")
	public LoginHelper(Context context){
		this.context=context;
		this.share=this.context.getSharedPreferences(FILENAME,
				Activity.MODE_PRIVATE);
		this.edit=share.edit();
	}
	
	
	public boolean login(String uid,String pwd){
		String temp=new getServerResUtil(
				"http://192.168.1.100:8082/schoolkonw/login.php?uid="+uid+"&pwd="+pwd).getRes();
		try {
			JSONObject JsonData = new JSONObject(temp);
			edit.putString("headImg", JsonData.getString("headImg"));
			edit.putString("nickname",JsonData.getString("nickname"));
			edit.putString("stuId", JsonData.getString("stuId"));
			edit.putString("collegeId",JsonData.getString("collegeId"));
			edit.putString("classId",JsonData.getString("classId"));
			edit.putString("time",JsonData.getString("time"));
			edit.putString("token",JsonData.getString("token"));
			edit.commit();
			
		}catch (JSONException e) {
			e.printStackTrace();
		}
		return true;		
	}
	
	public boolean hasLogin(){
		if(share.contains("token")){
			String token=share.getString("token","");
			String time=share.getString("time","");
			String classId=share.getString("classId","");
			String collegeId=share.getString("collegeId","");
			String stuId=share.getString("stuId","");
			if(token!=null&&!token.equals("")){
				String param1=stuId.substring(stuId.length()-2,stuId.length());
				String param2=classId.substring(classId.length()-3,classId.length());
				String param3=collegeId;
				String param4=time.substring(time.length()-5,time.length());
				
				String newData=param1+param2+param3+param4+"tokenEncryption";
				String sha1Data=new Sha1Util().getDigestOfString(newData.getBytes());
				if(sha1Data.equals(token)){
					return true;
				}
			}
		}
		return false;		
	}
	
	public Boolean logout(){
		if(share.contains("token")){
			this.clear();
			return true;
		}
		return false;
	}
	
	public void clear(){
		edit.clear().commit();
	}
	
	public String getNickname(){
		return share.getString("headImg","");
	}
	
	public String getHeadImg(){
		return share.getString("nickname","");
	}

}
