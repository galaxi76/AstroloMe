package com.astrolome;

import static com.astrolome.Constants.HOUSE_ID;
import static com.astrolome.Constants.PLANET_ID;
import static com.astrolome.Constants.TRANSIT_ID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;

public class SaveToLocalDBHelper {

	HashMap<String, String> resultMap;	
	ArrayList<HashMap<String, String>> listArray;
	HashMap<String, String> listResultMap;
	Activity activity;
	String gotWhat = null;
	
	public SaveToLocalDBHelper(Activity activity, HashMap<String, String> resultMap, String gotWHat){
		super();
		this.resultMap = resultMap;
		this.activity = activity;
		this.gotWhat = gotWHat;
	}
	
	protected void saveBirthChartLocalDB() {
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
			regEntry3.deleteTable("Table_Aspects");
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

}
