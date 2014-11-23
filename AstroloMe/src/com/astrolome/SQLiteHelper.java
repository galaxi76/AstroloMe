package com.astrolome;


import java.util.ArrayList;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class SQLiteHelper {
	
	private static final String DATABASE_NAME = "BirthchartProfile";
	private static final int DATABASE_VERSION = 21;
	public static final String DB_TABLENAME_USER = "Table_User";
	public static final String DB_TABLENAME_PLANETS = "Table_Planets";	
	public static final String DB_TABLENAME_HOUSES = "Table_Houses";
	public static final String DB_TABLENAME_ASPECTS = "Table_Aspects";

		//Register values
		public final static String KEY_ROW_ID = "_rowID";
		public final static String KEY_UID = "_id";
		public final static String KEY_DEVICE_UID = "_deviceuid";
		public final static String KEY_DEVICE_MODEL = "_deviceModel";
		public final static String KEY_DEVICE_VERSION = "_deviceVersion";
		public final static String KEY_IS_ACTIVE = "_isActive";
		public final static String KEY_NAME = "_fullName";
		public final static String KEY_DOB = "_dob";
		public final static String KEY_DATE_REG = "_dateReg";
		public final static String KEY_BIRTH_LOCAL_ID = "_birthLocationId";
		public final static String KEY_EMAIL = "_email";		
		public final static String KEY_GENDER = "_gender";
		public final static String KEY_ACTIONS_KEY = "_actionsKey";
		public final static String KEY_PASSWORD = "_password";
		
//		Planets
		public final static String KEY_PLANET_ID = "_planetID";
		public final static String KEY_HOUSE_ID = "_houseID";
		public final static String KEY_ZODIAC_ID = "_zodiacID";
		public final static String KEY_DEGREES = "_degrees";
		public final static String KEY_CONTENT = "_content";
		public final static String KEY_FULL_ANGLE = "_fullAngle";
		public final static String KEY_READ_DATE = "_readDate";
		public final static String KEY_MINUTE_DEGREES = "_minuteDegrees";
		public final static String KEY_RISING_ID = "_risingID";
		public final static String KEY_SECONDS_DEGREES = "_secondsDegrees";
		//houses
		public final static String KEY_HOUSE_NAME = "_houseName";
		//aspects

		public final static String KEY_PLANETID_ONE = "_planetOne";
		public final static String KEY_PLANETID_TWO = "_planetTwo";
		public final static String KEY_TRANSIT_ID = "_transitID";
		
		private DbHelper dbHelper;
		private SQLiteDatabase sqlitedb;
		private static Context context;
		
			
		private static class DbHelper extends SQLiteOpenHelper{

			public DbHelper(Context context){
				super(context, DATABASE_NAME, null, DATABASE_VERSION);
//				Toast.makeText(context, "SQL constructor called", Toast.LENGTH_SHORT).show();
			}
			private static final String CREATE_TABLE_USER = "create table "
					 + DB_TABLENAME_USER + " (" +
						KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_UID + " TEXT, " + 
						KEY_DEVICE_UID + " TEXT, " + 
						KEY_DEVICE_MODEL + " TEXT, " + 
						KEY_DEVICE_VERSION + " TEXT, " + 
						KEY_IS_ACTIVE + " TEXT, " + 
						KEY_NAME + " TEXT, " + 
						KEY_DOB + " TEXT, " + 
						KEY_BIRTH_LOCAL_ID + " TEXT, " + 
						KEY_DATE_REG + " TEXT, " +
						KEY_EMAIL + " TEXT, " + 
						KEY_ACTIONS_KEY + " TEXT, " +
						KEY_GENDER + " TEXT, " +
						KEY_PASSWORD + " TEXT);" ;
			
			private static final String CREATE_TABLE_PLANETS = "create table "
					 + DB_TABLENAME_PLANETS + " (" +
						KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
//						KEY_UID + " TEXT, " + 
						KEY_PLANET_ID + " TEXT, " +
						KEY_HOUSE_ID + " TEXT, " +
						KEY_ZODIAC_ID + " TEXT, " + 
						KEY_DEGREES + " TEXT, " + 
						KEY_CONTENT + " TEXT, " + 
						KEY_FULL_ANGLE + " TEXT, " + 
						KEY_READ_DATE + " TEXT, " + 
						KEY_MINUTE_DEGREES + " TEXT, " + 
						KEY_RISING_ID + " TEXT, " +
						KEY_SECONDS_DEGREES + " TEXT);" ;
			
			private static final String CREATE_TABLE_HOUSES = "create table "
					 + DB_TABLENAME_HOUSES + " (" +
						KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_HOUSE_ID + " TEXT, " +
						KEY_ZODIAC_ID + " TEXT, " + 
						KEY_DEGREES + " TEXT, " + 
						KEY_CONTENT + " TEXT, " + 
						KEY_FULL_ANGLE + " TEXT, " + 
						KEY_READ_DATE + " TEXT, " + 
						KEY_MINUTE_DEGREES + " TEXT, " + 
						KEY_SECONDS_DEGREES + " TEXT, " + 
						KEY_HOUSE_NAME + " TEXT);" ;

			private static final String CREATE_TABLE_ASPECTS = "create table "
					 + DB_TABLENAME_ASPECTS + " (" +
						KEY_ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
						KEY_PLANETID_ONE + " TEXT, " +
						KEY_PLANETID_TWO + " TEXT, " + 
						KEY_TRANSIT_ID + " TEXT, " + 
						KEY_CONTENT + " TEXT, " + 
						KEY_DEGREES + " TEXT, " + 
						KEY_MINUTE_DEGREES + " TEXT, " + 
						KEY_SECONDS_DEGREES + " TEXT);" ;
		
			@Override
			public void onCreate(SQLiteDatabase db) {
//				
//				String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_SIM + "( "    
//	                     + KEY_NAME + " TEXT, "    
//	                     + KEY_SIM_NO + " TEXT PRIMARY KEY, "    
//	                    + KEY_SIM_TYPE + " TEXT " + " )";    
//	            db.execSQL(CREATE_CONTACTS_TABLE);    
				try{
					db.execSQL(CREATE_TABLE_USER);
					db.execSQL(CREATE_TABLE_PLANETS);
					db.execSQL(CREATE_TABLE_HOUSES);
					db.execSQL(CREATE_TABLE_ASPECTS);
					Toast.makeText(context, "SQL onCreate called", Toast.LENGTH_SHORT).show();
				}
				catch (SQLException e){
					Toast.makeText(context, "" + e, Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) {
				try{
					Toast.makeText(context, "SQL onUpgrade called", Toast.LENGTH_SHORT).show();
					db.execSQL("DROP TABLE IF EXISTS " + DB_TABLENAME_USER);
					db.execSQL("DROP TABLE IF EXISTS " + DB_TABLENAME_PLANETS);
					db.execSQL("DROP TABLE IF EXISTS " + DB_TABLENAME_HOUSES);
					db.execSQL("DROP TABLE IF EXISTS " + DB_TABLENAME_ASPECTS);
					onCreate(db);
				}
				catch(SQLException e){
					Toast.makeText(context, ""+ e, Toast.LENGTH_SHORT).show();
				}
			}
		}
		
		public boolean isTableExists(String tableName) {
		    
//		        if(sqlitedb == null || !sqlitedb.isOpen()) {
//		        	sqlitedb = getReadableDatabase();
//		        
//
//		        if(!sqlitedb.isReadOnly()) {
//		        	sqlitedb.close();
//		        	sqlitedb = getReadableDatabase();
//		        }
//		    }

		    Cursor cursor = sqlitedb.rawQuery("select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'", null);
		    if(cursor!=null) {
		        if(cursor.getCount()>0) {
		                            cursor.close();
		            return true;
		        }
		                    cursor.close();
		    }
			return false;
		    
		}
		
	public SQLiteHelper(Context c){
		context = c;
	}
	
	public SQLiteHelper open() throws SQLiteException{
		dbHelper = new DbHelper(context);
		sqlitedb = dbHelper.getWritableDatabase();
		return this;
	}
	
	public void close(){
		dbHelper.close();
	}

	public void createEntry(String deviceID, String deviceModel,
			String deviceVersion, String dob, String gender, String fullName,
			String email, String birthLocID, String dateReg, String uid, String actionKey, String password) {
			ContentValues cv = new ContentValues();
			cv.put(KEY_DEVICE_UID, deviceID);
			cv.put(KEY_DEVICE_MODEL, deviceModel);
			cv.put(KEY_DEVICE_VERSION, deviceVersion);
			cv.put(KEY_DOB, dob);
			cv.put(KEY_GENDER, gender);
			cv.put(KEY_NAME, fullName);
			cv.put(KEY_EMAIL, email);
			cv.put(KEY_BIRTH_LOCAL_ID, birthLocID);
			cv.put(KEY_DATE_REG, dateReg);
			cv.put(KEY_UID, uid);
			cv.put(KEY_ACTIONS_KEY, actionKey);
			cv.put(KEY_PASSWORD, password);
			sqlitedb.insert(DB_TABLENAME_USER, null, cv);
			//return above line and make public long createEntry()?
	}
	
	 public void updateOneColumn(String TABLE_NAME, String Column, String rowId, String ColumnName, String newValue){
		 ContentValues cv = new ContentValues();
		 cv.put(ColumnName, newValue);
		 sqlitedb.update(TABLE_NAME, cv, Column + "= ?", new String[] {rowId});
}
	
	public void deleteTable(String tableName){
		sqlitedb.delete(tableName, null, null);
	}
	
	public void createEntry1(ArrayList<HashMap<String, String>> planetArray){
		ContentValues cv1 = new ContentValues();

		for (int i = 0; i < planetArray.size(); i++) {
			   HashMap<String, String> map = new HashMap<String, String>();
			   map = planetArray.get(i);
			  cv1.put(KEY_PLANET_ID, map.get("planet_id"));
			  cv1.put(KEY_HOUSE_ID, map.get("house_id"));
			  cv1.put(KEY_ZODIAC_ID, map.get("zodiac_id"));
			  cv1.put(KEY_DEGREES, map.get("degrees"));
			  cv1.put(KEY_CONTENT, map.get("content"));
			  cv1.put(KEY_FULL_ANGLE, map.get("full_angle"));
			  cv1.put(KEY_READ_DATE, map.get("read_date"));
			  cv1.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
			  cv1.put(KEY_RISING_ID, map.get("rising_zodiac_id")); 
			  cv1.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
			  sqlitedb.insert(DB_TABLENAME_PLANETS, null, cv1);
			
			}
		Log.i("CREATE PLANETS TABLE", cv1.toString());
		sqlitedb.close();
	}
	public void createEntry2(ArrayList<HashMap<String, String>> houseArray){
		ContentValues cv2 = new ContentValues();

		for (int i = 0; i < houseArray.size(); i++) {
			   HashMap<String, String> map = new HashMap<String, String>();
			   map = houseArray.get(i);
			
			   cv2.put(KEY_HOUSE_ID, map.get("house_id"));
			   cv2.put(KEY_ZODIAC_ID, map.get("zodiac_id"));
			   cv2.put(KEY_DEGREES, map.get("degrees"));
			   cv2.put(KEY_CONTENT, map.get("content"));
			   cv2.put(KEY_FULL_ANGLE, map.get("full_angle"));
			   cv2.put(KEY_READ_DATE, map.get("read_date"));
			   cv2.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
			   cv2.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
			   cv2.put(KEY_HOUSE_NAME, map.get("house_name")); 
			  sqlitedb.insert(DB_TABLENAME_HOUSES, null, cv2);
			
			}
		Log.i("CREATE APSECTS TABLE", cv2.toString());
		sqlitedb.close();
	}
	public void createEntry3(ArrayList<HashMap<String, String>> aspectArray){
		ContentValues cv3 = new ContentValues();

		for (int i = 0; i < aspectArray.size(); i++) {
			   HashMap<String, String> map = new HashMap<String, String>();
			   map = aspectArray.get(i);
//				 planet_id1, planet_id2, transit_aspect_id, degrees, minutes, seconds, content
			   cv3.put(KEY_PLANETID_ONE, map.get("planet_id1"));
			   cv3.put(KEY_PLANETID_TWO, map.get("planet_id2"));
			   cv3.put(KEY_TRANSIT_ID, map.get("transit_aspect_id"));
			   cv3.put(KEY_DEGREES, map.get("degrees"));
			   cv3.put(KEY_CONTENT, map.get("content"));  
			   cv3.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
			   cv3.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
			  sqlitedb.insert(DB_TABLENAME_ASPECTS, null, cv3);		
			}
		Log.i("CREATE HOUSES TABLE", cv3.toString());
		sqlitedb.close();
	}
	
	public final ArrayList<HashMap<String, String>> getData(String tableName)
	  {
		Cursor c = null;
		
		if(tableName==DB_TABLENAME_PLANETS){
			String[] columns = new String[]{ KEY_PLANET_ID, KEY_HOUSE_ID, KEY_ZODIAC_ID, KEY_DEGREES, 
					KEY_CONTENT, KEY_FULL_ANGLE, KEY_READ_DATE, KEY_MINUTE_DEGREES, KEY_RISING_ID, KEY_SECONDS_DEGREES};
			c = sqlitedb.query(DB_TABLENAME_PLANETS, columns, null, null, null, null, null);
		}
		if(tableName==DB_TABLENAME_HOUSES){
			String[] columns = new String[]{ KEY_HOUSE_ID, KEY_ZODIAC_ID, KEY_DEGREES, 
					KEY_CONTENT, KEY_FULL_ANGLE, KEY_READ_DATE, KEY_MINUTE_DEGREES, KEY_SECONDS_DEGREES, KEY_HOUSE_NAME};
			c = sqlitedb.query(DB_TABLENAME_HOUSES, columns, null, null, null, null, null);
		}
		if(tableName==DB_TABLENAME_ASPECTS){
			String[] columns = new String[]{ KEY_PLANETID_ONE, KEY_PLANETID_TWO, KEY_TRANSIT_ID,
					KEY_DEGREES, KEY_CONTENT, KEY_MINUTE_DEGREES, KEY_SECONDS_DEGREES};
			c = sqlitedb.query(DB_TABLENAME_ASPECTS, columns, null, null, null, null, null);
		}

	    // ????? ??? ????? ???? ?? ??
	    int columnSize = c.getColumnCount();
	    // ????? ?????????? ????
	    int resultSize = c.getCount();
	    
	    ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String,String>>();
	    
	    c.moveToFirst();
	    
	    while(c.isAfterLast() == false)
	    {
	      HashMap<String, String> map = new HashMap<String, String>(columnSize);
	      
	      for(int j = 0; j < columnSize; j++)
	      {
	        map.put(c.getColumnName(j), c.getString(j));
//	        		new Integer(c.getInt(j)));
	      }
	      
	      c.moveToNext();
	      
	      data.add(map);
	    }
	    
	    return  data; 
	  }
	  
	public HashMap<String, String> getDataFromUser(){
		String[] columns = new String[]{ KEY_ROW_ID, KEY_DEVICE_UID, KEY_DEVICE_MODEL, KEY_DEVICE_VERSION, 
				KEY_DOB, KEY_GENDER, KEY_NAME, KEY_EMAIL, KEY_BIRTH_LOCAL_ID, KEY_ACTIONS_KEY, KEY_DATE_REG, KEY_UID,
				KEY_PASSWORD};
		Cursor c = sqlitedb.query(DB_TABLENAME_USER, columns, null, null, null, null, null);
		
		if (c!=null){
			HashMap<String, String> dataFromUserMap = new HashMap<String, String>();
			
			while(c.moveToNext()){
				
				for (int i = 0; i<columns.length; i++){
			
			
					dataFromUserMap.put(c.getColumnName(i), c.getString(i));
				}
			}
			return dataFromUserMap;
			
		}
		
		return null;
	}
	
	public HashMap<String, String> getDataFromUserTable(){
		String[] columns = new String[]{ KEY_ROW_ID, KEY_DEVICE_UID, KEY_DEVICE_MODEL, KEY_DEVICE_VERSION, 
				KEY_DOB, KEY_GENDER, KEY_NAME, KEY_EMAIL, KEY_BIRTH_LOCAL_ID, KEY_ACTIONS_KEY, KEY_DATE_REG, KEY_UID,
				KEY_PASSWORD};
		Cursor c = sqlitedb.query(DB_TABLENAME_USER, columns, null, null, null, null, null);
								
	
			 for (c.moveToFirst(); c.moveToNext(); ) {
		          
			HashMap<String, String> dataFromUserMap = new HashMap<String, String>();
			dataFromUserMap.put(KEY_ROW_ID, c.getString(0));
//			dataFromUserMap.put(columns[i], c.getString(i));
			dataFromUserMap.put(KEY_DEVICE_MODEL, c.getString(2));
			dataFromUserMap.put(KEY_DEVICE_VERSION, c.getString(3));
			dataFromUserMap.put(KEY_DOB, c.getString(4));
			dataFromUserMap.put(KEY_GENDER, c.getString(5));
			dataFromUserMap.put(KEY_NAME, c.getString(6));
			dataFromUserMap.put(KEY_EMAIL, c.getString(7));
			dataFromUserMap.put(KEY_BIRTH_LOCAL_ID, c.getString(8));
			dataFromUserMap.put(KEY_ACTIONS_KEY, c.getString(9));
			dataFromUserMap.put(KEY_DATE_REG, c.getString(10));
			dataFromUserMap.put(KEY_UID, c.getString(11));
			dataFromUserMap.put(KEY_PASSWORD, c.getString(12));

			return dataFromUserMap;
		}
		return null;
	}
	
//	public String getDatafromUser() {
//	String[] columns = new String[]{ KEY_ROW_ID, KEY_DEVICE_UID, KEY_DEVICE_MODEL, KEY_DEVICE_VERSION, 
//			KEY_DOB, KEY_GENDER, KEY_NAME, KEY_EMAIL, KEY_BIRTH_LOCAL_ID, KEY_DATE_REG, KEY_UID, KEY_ACTIONS_KEY,
//			KEY_PASSWORD};
//	Cursor c = sqlitedb.query(DB_TABLENAME_USER, columns, null, null, null, null, null);
//	String result = null;
//	
//	int iRow = c.getColumnIndex(KEY_ROW_ID);
//	int iDeviceID = c.getColumnIndex(KEY_DEVICE_UID);
//	int iDeviceModel = c.getColumnIndex(KEY_DEVICE_MODEL);
//	int iDeviceVersion = c.getColumnIndex(KEY_DEVICE_VERSION);
//	int iDOB = c.getColumnIndex(KEY_DOB);
//	int iGender = c.getColumnIndex(KEY_GENDER);
//	int iName = c.getColumnIndex(KEY_NAME);
//	int iEmail = c.getColumnIndex(KEY_EMAIL);
//	int iBirthLocalID = c.getColumnIndex(KEY_BIRTH_LOCAL_ID);
//	int iKeyDateReg = c.getColumnIndex(KEY_DATE_REG);
//	int iUserID = c.getColumnIndex(KEY_UID);
//	int iActionsKey = c.getColumnIndex(KEY_ACTIONS_KEY);
//	int iPassword = c.getColumnIndex(KEY_PASSWORD);		
//	for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
//		result = result + c.getString(iRow) + " " + 
//				c.getString(iRow) + " " +
//				c.getString(iDeviceID) + " " +
//				c.getString(iDeviceModel) + " " +
//				c.getString(iDeviceVersion) + " " +
//				c.getString(iDOB) + " " +
//				c.getString(iGender) + " " +
//				c.getString(iName) + " " +
//				c.getString(iEmail) + " " +
//				c.getString(iBirthLocalID) + " " +
//				c.getString(iKeyDateReg) + " "+ 
//				c.getString(iUserID) + " " + 
//				c.getString(iActionsKey) + " " +
//				c.getString(iPassword) + "\n";
//	}
//	return result;
//}
//public String getUIDfromUserTable(){
//	String[] columns = new String[]{ KEY_ROW_ID, KEY_DEVICE_UID, KEY_DEVICE_MODEL, KEY_DEVICE_VERSION, 
//			KEY_DOB, KEY_GENDER, KEY_NAME, KEY_EMAIL, KEY_BIRTH_LOCAL_ID, KEY_DATE_REG, KEY_UID, KEY_ACTIONS_KEY,
//			KEY_PASSWORD};
//	Cursor c = sqlitedb.query(DB_TABLENAME_USER, columns, KEY_UID, null, null, null, null);
//	if (c!=null){
//		c.moveToFirst();
//		String userID = c.getString(10);
//		return userID;
//	}
//	return null;
//}
//public String getActionsKeyfromUserTable(){
//	String[] columns = new String[]{ KEY_ROW_ID, KEY_DEVICE_UID, KEY_DEVICE_MODEL, KEY_DEVICE_VERSION, 
//			KEY_DOB, KEY_GENDER, KEY_NAME, KEY_EMAIL, KEY_BIRTH_LOCAL_ID, KEY_DATE_REG, KEY_UID, KEY_ACTIONS_KEY,
//			KEY_PASSWORD};
//	Cursor c = sqlitedb.query(DB_TABLENAME_USER, columns, KEY_ACTIONS_KEY, null, null, null, null);
//	if (c!=null){
//		c.moveToFirst();
//		String actionString = c.getString(10);
//		return actionString;
//	}
//	return null;
//}
}
