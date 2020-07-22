package com.example.syka;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class dbHelper extends SQLiteOpenHelper {
	String TAG = "facedb";
	private final static String DATABASE_NAME="facedb";
	private final static int DATABASE_VERSION=1;
	private final static String TABLE_NAME="facemodule";
	public final static String ID="_id";
	public final static String FIELD_ID="username";
	public final static String FIELD_TIME="time";
	public final static String FIELD_TITLE="module";
	
	
	public dbHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		//Log.i(TAG, "db init");
	}
	
	
	 
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="Create table "+TABLE_NAME+"("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+FIELD_ID+" TEXT not null,"+FIELD_TIME+" TEXT not null,"
		+FIELD_TITLE+" BLOB );";
		//Log.i(TAG,sql);
		db.execSQL(sql);
		
		 
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql=" DROP TABLE IF EXISTS "+TABLE_NAME;
		//Log.i(TAG,sql);
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select(String arg)
	{
		Cursor cursor;
		SQLiteDatabase db=this.getReadableDatabase();
		if(arg == "all")
		{
			cursor=db.query(TABLE_NAME, null, null, null, null, null, null);
			//Log.i(TAG,"all select");
		}
		else
		{
			String[] columns = {FIELD_TITLE};
			String where=ID+"=?";
			String[] whereValue={arg};
			cursor=db.query(TABLE_NAME, columns, where, whereValue, null, null, null);
		}	
		return cursor;
	}
	
	public long insert(String Title,String Time,byte[] mod)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		ContentValues cv=new ContentValues(); 
		//cv.put(ID, "null");
		cv.put(FIELD_ID, Title);
		cv.put(FIELD_TIME, Time);
		cv.put(FIELD_TITLE, mod);
		long row=db.insert(TABLE_NAME, null, cv);
		return row;
	}
	
	public void delete(String id)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		if(id == "*")
		{
			db.delete(TABLE_NAME, null, null);
		}
		else
		{
			String where = ID + "=?";
			String[] whereValue = {id};
			db.delete(TABLE_NAME, where, whereValue);
		}
	}
	
	public void update(String id,String Title,String Time,byte[] mod)
	{
		SQLiteDatabase db=this.getWritableDatabase();
		String where=ID+"=?";
		String[] whereValue={id};
		ContentValues cv=new ContentValues(); 
		cv.put(FIELD_ID, Title);
		cv.put(FIELD_TIME, Time);
		cv.put(FIELD_TITLE, mod);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
}
