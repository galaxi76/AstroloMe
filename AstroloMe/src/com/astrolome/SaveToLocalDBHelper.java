package com.astrolome;

import static com.astrolome.Constants.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.widget.Toast;

public class SaveToLocalDBHelper {

	HashMap<String, String> resultMap;	
	ArrayList<HashMap<String, String>> listArray;
	HashMap<String, String> listResultMap;
	Activity activity;
	String gotWhat = null;
	ContentValues cv;
	String row;
	SQLiteHelper sqlHelper;
	HashMap<String, String> map;
	
	public SaveToLocalDBHelper(Activity activity, HashMap<String, String> resultMap, String gotWHat){
		super();
		this.resultMap = resultMap;
		this.activity = activity;
		this.gotWhat = gotWHat;
	}
	
	public SaveToLocalDBHelper(Activity activity, ContentValues cv) {
		super();
		this.activity = activity;
		this.cv = cv;
	}

	protected void updateUserLocalDB() {
		boolean isSavedLocDB=false;
		try{
		sqlHelper = new SQLiteHelper(activity);
		sqlHelper.open();
		sqlHelper.updateColumns(DB_TABLENAME_USER, cv, null, null);
		
		HashMap<String, String> datafromSQL = sqlHelper.getDataFromUser();
		String name = datafromSQL.get(KEY_NAME);
		String emailString = datafromSQL.get(KEY_EMAIL);
		String dob = datafromSQL.get(KEY_DOB);
		String city = datafromSQL.get(KEY_BIRTH_LOCAL_ID);
		
		Log.i("DB UPDATED", "" + name +", "+ emailString +", " +  dob +", "+ city);
		sqlHelper.close();
		}catch(SQLiteException e){
			Log.i("SQLITEDB ERROR", "user NOT uploaded into local db", e);
			isSavedLocDB = false;
		}finally{
			if(isSavedLocDB){
				Toast.makeText(activity, "Changes Saved", Toast.LENGTH_LONG).show();
			}
		}
	}
	
	protected void parseThatShit() {
		String resultArrayString = null;

		resultArrayString = resultMap.get("result");
		listArray = new ArrayList<HashMap<String, String>>();
		
		try {
			JSONArray arr = new JSONArray(resultArrayString);
			for (int i = 0; i < arr.length(); i++) {
				listResultMap = new HashMap<String, String>();
				JSONObject jObject = arr.getJSONObject(i);
				Iterator<?> keys = jObject.keys();

				while (keys.hasNext()) {
					String key = (String) keys.next();
					String value = null;
					try {
						value = jObject.getString(key);
					} catch (JSONException e) {
						e.printStackTrace();
					}				
					listResultMap.put(key, value);
				}
				listArray.add(i, listResultMap);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	protected void saveBirthChartLocalDB(){
		if(gotWhat.equals(PLANET_ID)){
			boolean isPlanetsLocalDB = false;
			try{
				SQLiteHelper regEntry1 = new SQLiteHelper(activity);
				regEntry1.open();
				regEntry1.createEntry1(listArray);
				regEntry1.close();
				isPlanetsLocalDB = true;
			}catch(Exception e){
				Log.i("SQLITEDB", "not uploaded into local db", e);
				isPlanetsLocalDB = false;
			}finally{
				if(isPlanetsLocalDB){

				}
			}
		}
		if (gotWhat.equals(HOUSE_ID)) {
			 Log.i("THE HOUSES!!!!", "houseID:" + resultMap.get("house_id") + "  descrip: " + resultMap.get("content"));
		    
				boolean isHousesLocalDB = false;
				try{
					SQLiteHelper regEntry2 = new SQLiteHelper(activity);
					regEntry2.open();
					regEntry2.createEntry2(listArray);
					regEntry2.close();
					isHousesLocalDB = true;
				}catch(Exception e){
					Log.i("SQLITEDB", "not uploaded into local db", e);
					isHousesLocalDB = false;
				}finally{
					if(isHousesLocalDB){

					}
				}
			}
			if(gotWhat.equals(TRANSIT_ID)){
				boolean isAspectsLocalDB = false;
				try{
					SQLiteHelper regEntry3 = new SQLiteHelper(activity);
					regEntry3.open();
					regEntry3.createEntry3(listArray);
					regEntry3.close();
					isAspectsLocalDB = true;
				}catch(Exception e){
					Log.i("SQLITEDB", "not uploaded into local db", e);
					isAspectsLocalDB = false;
				}finally{
					if(isAspectsLocalDB){

					}
				}
			}
	}
	
	protected void updateBirthChartLocalDB() {
//	String resultArrayString = null;
//
//	resultArrayString = resultMap.get("result");
//	listArray = new ArrayList<HashMap<String, String>>();
//	
//	try {
//		JSONArray arr = new JSONArray(resultArrayString);
//		
//		for (int i = 0; i < arr.length(); i++) {
//			listResultMap = new HashMap<String, String>();
//			JSONObject jObject = arr.getJSONObject(i);
//			Iterator<?> keys = jObject.keys();
//
//			while (keys.hasNext()) {
//				String key = (String) keys.next();
//				String value = null;
//				try {
//					value = jObject.getString(key);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}				
//				listResultMap.put(key, value);
//			}
//			listArray.add(i, listResultMap);
//		}
//	} catch (JSONException e) {
//		e.printStackTrace();
//	}
	if(gotWhat.equals(PLANET_ID)){
		boolean isPlanetsLocalDB = false;
		cv = new ContentValues();
		try{
		sqlHelper = new SQLiteHelper(activity);
		sqlHelper.open();
		for (int i = 0; i < listArray.size(); i++) {
		   map = new HashMap<String, String>();
		   map = listArray.get(i);
		   cv.put(KEY_PLANET_ID, map.get("planet_id"));
		   cv.put(KEY_HOUSE_ID, map.get("house_id"));
		   cv.put(KEY_ZODIAC_ID, map.get("zodiac_id"));
		   cv.put(KEY_DEGREES, map.get("degrees"));
		   cv.put(KEY_CONTENT, map.get("content"));
		   cv.put(KEY_FULL_ANGLE, map.get("full_angle"));
		   cv.put(KEY_READ_DATE, map.get("read_date"));
		   cv.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
		   cv.put(KEY_RISING_ID, map.get("rising_zodiac_id")); 
		   cv.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
		   row = String.valueOf(i+1);
		   sqlHelper.updateColumns(DB_TABLENAME_PLANETS, cv, null, row);	
		   cv.clear();
			}
		sqlHelper.close();
		isPlanetsLocalDB = true;
		}catch(Exception e){
			Log.i("SQLITEDB ERROR", "planets NOT updated into local db", e);
			isPlanetsLocalDB = false;
		}finally{
			if(isPlanetsLocalDB){
				Log.i("SQLITEDB", "planets updated into local db");
			}
		}
	}
	
	if (gotWhat.equals(HOUSE_ID)) {
	 Log.i("THE HOUSES!!!!", "houseID:" + resultMap.get("house_id") + "  descrip: " + resultMap.get("content"));   
		boolean isHousesLocalDB = false;
		cv = new ContentValues();
		try{
			sqlHelper = new SQLiteHelper(activity);
			sqlHelper.open();
			for(int i=0; i<listArray.size(); i++){
				map = new HashMap<String, String>();
				map = listArray.get(i);
				cv.put(KEY_HOUSE_ID, map.get("house_id"));
				cv.put(KEY_ZODIAC_ID, map.get("zodiac_id"));
				cv.put(KEY_DEGREES, map.get("degrees"));
				cv.put(KEY_CONTENT, map.get("content"));
				cv.put(KEY_FULL_ANGLE, map.get("full_angle"));
				cv.put(KEY_READ_DATE, map.get("read_date"));
				cv.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
				cv.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
				cv.put(KEY_HOUSE_NAME, map.get("house_name")); 
				row = String.valueOf(i+1);
				sqlHelper.updateColumns(DB_TABLENAME_HOUSES, cv, null, row);
				cv.clear();
			}
			sqlHelper.close();
			isHousesLocalDB = true;
		}catch(Exception e){
			Log.i("SQLITEDB ERROR", "houses NOT updated into local db", e);
			isHousesLocalDB = false;
		}finally{
			if(isHousesLocalDB){
				Log.i("SQLITEDB", "houses updated into local db");
			}
		}
	}
	if(gotWhat.equals(TRANSIT_ID)){
		boolean isAspectsLocalDB = false;
		cv = new ContentValues();
		try{
			sqlHelper = new SQLiteHelper(activity);
			sqlHelper.open();
			for (int i=0; i<listArray.size(); i++){
				map = new HashMap<String, String>();
				map = listArray.get(i);
				cv.put(KEY_PLANET_ONE, map.get("planet_id1"));
				cv.put(KEY_PLANET_TWO, map.get("planet_id2"));
				cv.put(KEY_TRANSIT_ID, map.get("transit_aspect_id"));
				cv.put(KEY_DEGREES, map.get("degrees"));
				cv.put(KEY_CONTENT, map.get("content"));  
				cv.put(KEY_MINUTE_DEGREES, map.get("minutes")); 
				cv.put(KEY_SECONDS_DEGREES, map.get("seconds")); 
				row = String.valueOf(i+1);
				sqlHelper.updateColumns(DB_TABLENAME_ASPECTS, cv, null, row);
				cv.clear();
			}
			sqlHelper.close();
			isAspectsLocalDB = true;
		}catch(Exception e){
			Log.i("SQLITEDB", "aspects NOT updated into local db", e);
			isAspectsLocalDB = false;
		}finally{
			if(isAspectsLocalDB){
				Log.i("SQLITEDB", "aspects updated into local db");
			}
		}
	}
	}

}
