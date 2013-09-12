package com.schoolkonw.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{


	public DatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}


	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE schedule (" + 
				" _id integer primary key autoincrement , " + 
				"classid	VARCHAR(50)		NOT NULL ," + 
				"classname	VARCHAR(50)		NOT NULL ," + 
				"term	 VARCHAR(50)		NOT NULL )";		
		db.execSQL(sql);
		System.out.println("数据库建立成功!");
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS schedule";		
		db.execSQL(sql);										
		this.onCreate(db);
		System.out.println("数据库更新!");
	}

	/**
	* 执行一条SQL语句
	* @param sql
	*/
	public void executeSQL(String sql){
		SQLiteDatabase db = null;
		try{
			db = this.getWritableDatabase();
			db.execSQL(sql);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db.isOpen()){
				//db.close();
			}
		}
	}

	/**
	* 执行一条SQL语句，返回一个游标
	* @param sql
	* @return
	*/
	public Cursor executeSQLwithCursorReturn(String sql){
		Cursor retCursor = null;
		SQLiteDatabase db = null;
		try{
			db = this.getReadableDatabase();
			retCursor = db.rawQuery(sql, null);
		}catch(Exception e){
			e.printStackTrace();
		}
		return retCursor;
	}


	/**
	* 执行一条SQL语句，判断是否存在该记录，存在返回true，不存在返回false
	* @param sql
	* @return
	*/
	public boolean isExist(String sql){
		boolean retBoolean = false;
		Cursor c = null;
		SQLiteDatabase db = null;
		try{
			db = this.getReadableDatabase();
			c = db.rawQuery(sql, null);
			if(c.getCount() == 0){
				retBoolean = false;
			}
			else{
				retBoolean = true;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(db.isOpen()){
				//db.close();
			}
		}
		return retBoolean;
	}

	/**
	* 
	* @param table
	* @param columns
	* @param selection
	* @param selectionArgs
	* @param groupBy
	* @param having
	* @param orderBy
	* @return
	*/
	public Cursor select(String table,String[] columns,String selection,
	String[] selectionArgs,String groupBy,String having,String orderBy){
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = this.getReadableDatabase();
			cursor = db.query(table, columns, selection, selectionArgs, groupBy, having, orderBy);
		} catch (Exception e) {
		}finally{
			//cursor.close();
			//db.close();			
		} 
		return cursor;	
	}

	/**
	* 
	* @param table
	* @param fields
	* @param values
	* @return the row ID of the newly inserted row, or -1 if an error occurred
	*/
	public long insert(String table, String[] fields, String[] values){
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		for(int i = 0;i < fields.length;i ++){
			cv.put(fields[i], values[i]);
		} 
		return db.insert(table, null, cv);
	}

	/**
	* 
	* @param table
	* @param cv
	* @return the row ID of the newly inserted row, or -1 if an error occurred 
	*/
	public long insertWithContentValues(String table,ContentValues cv){
		SQLiteDatabase db = this.getWritableDatabase();
		long i = 0;
		i = db.insert(table, null, cv);
		return i;
	}
	/**
	* 
	* @param table
	* @param cv
	* @param whereClause
	* @param whereArgs
	* @return the number of rows affected 
	*/
	public int updateTableWithContentValues(String table,ContentValues cv,String whereClause,String[] whereArgs)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		int i = 0;
		i = db.update(table, cv, whereClause, whereArgs);
		return i;
	}

	/**
	* @param table
	* @param updateFields
	* @param updateValues
	* @param whereClause
	* @param whereArgs
	* @return
	*/
	public int update(String table,String[] updateFields,String[] updateValues,
	String whereClause , String[] whereArgs)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		for(int i=0;i<updateFields.length;i++){
			cv.put(updateFields[i], updateValues[i]);
		}
		return db.update(table, cv, whereClause, whereArgs);
	}

	/**
	* @param table
	* @param where
	* @param whereArgs
	* @return
	*/
	public int delete(String table,String where,String[] whereArgs){
		SQLiteDatabase db = this.getWritableDatabase();
		return db.delete(table, where, whereArgs);
	}
	
	public synchronized void close() {
		super.close();
	}
	
	/**
	 * clear the table
	 * @param table
	 */
	public void clearAll(String table){
		this.executeSQL("DELETE FROM  "+table);
	}

}