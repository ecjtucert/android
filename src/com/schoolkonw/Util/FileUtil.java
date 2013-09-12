package com.schoolkonw.Util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.util.EncodingUtils;

import android.annotation.SuppressLint;
import android.content.Context;

public class FileUtil {
	
	private Context context=null;
	private String filename=null;
	
	public FileUtil(Context context,String filename){
		this.context=context;
		this.filename=filename;
	}
	
	
	/**
	 * 写入文件到本地存储
	 * @param message
	 */
	@SuppressLint("WorldReadableFiles")
	public Boolean writeFileData(String message){
		 try {
	            FileOutputStream outStream=context.openFileOutput(filename,Context.MODE_WORLD_READABLE);
	            outStream.write(message.getBytes());
	            outStream.close();
	        } catch (FileNotFoundException e) {
	            return false;
	        }catch (IOException e){
	            return false;
	        }
		 return true;
	}
	
	/**
	 * 从本地存储读出数据
	 * @return
	 */
	 public String readFileData(){
		 String result="";
         try {
                FileInputStream fin =context.openFileInput(filename);
                //获取文件长度
                int lenght = fin.available();
                byte[] buffer = new byte[lenght];
                fin.read(buffer);
                result = EncodingUtils.getString(buffer,"UTF-8");
        } catch (Exception e) {
                e.printStackTrace();
        }
        return result;
	 }

}
