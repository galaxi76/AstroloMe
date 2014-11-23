package com.astrolome;

import static com.astrolome.Constants.API_KEY;
import static com.astrolome.Constants.CMD_BC;
import static com.astrolome.Constants.CONTENT;
import static com.astrolome.Constants.DEGREES;
import static com.astrolome.Constants.ERROR;
import static com.astrolome.Constants.KEY_CONTENT;
import static com.astrolome.Constants.KEY_DEGREES;
import static com.astrolome.Constants.KEY_ZODIAC_ID;
import static com.astrolome.Constants.HOUSE_ID;
import static com.astrolome.Constants.TYPE_ID;
import static com.astrolome.Constants.URL_BC;
import static com.astrolome.Constants.ZODIAC_ID;
import static com.astrolome.Constants.KEY_HOUSE_ID;
import static com.astrolome.Constants.KEY_HOUSE_NAME;
import static com.astrolome.Constants.HOUSE_NAME;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class HouseFragment extends ListFragment{
	
	LayoutInflater inflater;
	ListView list;
	TextView name;
	TextView explanation;
	ImageView zodiac;
	Button getDetails;
	
	Activity activity = getActivity();
	
	List<NameValuePair> houseParams;
	HashMap<String, String> houseListResult;
	ArrayList<HashMap<String, String>> housesListArray;
	ArrayList<HashMap<String, String>> houseListArray;
	HashMap<String, String> houseMap;
//	Houses - house_id, zodiac_id, degrees, minutes, seconds, read_date, full_angle, house_name, content
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.houses_tab_layout, container, false);
        inflater=(LayoutInflater)getLayoutInflater(savedInstanceState);
        
        String dbTableName = "Table_Houses";
 		 SQLiteHelper info = new SQLiteHelper(getActivity().getBaseContext());		
 		 info.open();
 		 
 		 ArrayList<HashMap<String, String>> datafromSQL = info.getData(dbTableName);
 		
 		 if(!datafromSQL.isEmpty()){	
 			housesListArray = new  ArrayList<HashMap<String, String>>();
 			 for (int i = 0; i<datafromSQL.size(); i++){
 				 
 				houseMap = new HashMap<String, String>();
 				houseMap.put(HOUSE_ID, datafromSQL.get(i).get(KEY_HOUSE_ID));
 				houseMap.put(ZODIAC_ID, datafromSQL.get(i).get(KEY_ZODIAC_ID));
 				houseMap.put(DEGREES, datafromSQL.get(i).get(KEY_DEGREES));
 				houseMap.put(CONTENT, datafromSQL.get(i).get(KEY_CONTENT));
 				houseMap.put(HOUSE_NAME, datafromSQL.get(i).get(KEY_HOUSE_NAME));
 				 
 				housesListArray.add(houseMap);
 		 	}	
 			 info.close();
 		 }
 		

 	   list = (ListView) v.findViewById(android.R.id.list); 
       HouseAdapter adapter=new HouseAdapter(getActivity(), R.layout.houses_row, housesListArray); 
       list.setAdapter(adapter);
        
       return v;
    }
	
	

 	 @Override
 	    public void onViewCreated(View view, Bundle savedInstanceState) {
 	        super.onViewCreated(view, savedInstanceState);
 	        //the dividers 
 	        getListView().setDivider(getResources().getDrawable(R.drawable.purplebartop));
 	    }
 	 @Override
 	    public void onListItemClick(ListView l, View v, int position, long id) {
 	        // retrieve theListView item
 		 Context context = this.getActivity().getApplicationContext();
 	        HashMap<String, String> item = housesListArray.get(position);
 	 }
 	 

//	@Override
//	public void sendResponse(HashMap<String, String> result) {
//		String error = result.get(ERROR);
//		if (error.equals("no")) {
//			saveHousesLocalDB(result);
//		}
//	}
	
//	private void saveHousesLocalDB(HashMap<String, String> houseResult) {
//
//		String houseListString = null;
//
//	    houseListString = houseResult.get("result");
//		houseListArray = new ArrayList<HashMap<String, String>>();
//		
//		try {
//			JSONArray arr = new JSONArray(houseListString);
//			for (int i = 0; i < arr.length(); i++) {
//				houseListResult = new HashMap<String, String>();
//				JSONObject jObject = arr.getJSONObject(i);
//				Iterator<?> keys = jObject.keys();
//
//				while (keys.hasNext()) {
//					String key = (String) keys.next();
//					String value = null;
//					try {
//						value = jObject.getString(key);
//					} catch (JSONException e) {
//						e.printStackTrace();
//					}				
//					houseListResult.put(key, value);
//				}
//				houseListArray.add(i, houseListResult);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		 Log.i("THE HOUSES!!!!", "houseID:" + houseResult.get("house_id") + "  descrip: " + houseResult.get("content"));
//	    
//			boolean isHousesLocalDB = false;
//			try{
//				SQLiteHelper regEntry2 = new SQLiteHelper(activity);
//				regEntry2.open();
//				regEntry2.createEntry2(houseListArray);
//				regEntry2.close();
//				isHousesLocalDB = true;
//			}catch(Exception e){
//				Log.i("SQLITEDB", "not uploaded into local db", e);
//				isHousesLocalDB = false;
//			}finally{
//				if(isHousesLocalDB){
//
//				}
//			}
	
		
//	}
}
