package com.astrolome;

import static com.astrolome.Constants.CONTENT;
import static com.astrolome.Constants.DEGREES;
import static com.astrolome.Constants.KEY_CONTENT;
import static com.astrolome.Constants.KEY_DEGREES;
import static com.astrolome.Constants.KEY_PLANET_ONE;
import static com.astrolome.Constants.KEY_PLANET_TWO;
import static com.astrolome.Constants.KEY_TRANSIT_ID;
import static com.astrolome.Constants.PLANET_ID_ONE;
import static com.astrolome.Constants.PLANET_ID_TWO;
import static com.astrolome.Constants.TRANSIT_ID;
import static com.astrolome.Constants.URL_BC;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.NameValuePair;
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
import android.widget.Toast;

public class AspectsFragment extends ListFragment{
	
	LayoutInflater inflater;
	ListView list;
	TextView name;
	TextView explanation;
	ImageView zodiac;
	Button getDetails;
	
	ArrayList<HashMap<String, String>> aspectsListArray;
	HashMap<String, String> aspectMap;
	
	Activity activity; 
	ResponseManager shmoops;
	RequestManager manager;
	
	
	ArrayList<HashMap<String, String>> aspectListArray;
	List<NameValuePair> aspectParams;
	HashMap<String, String> aspectListResult;
	boolean gotAspects = false;
//	 planet_id1, planet_id2, transit_aspect_id, degrees, minutes, seconds, content
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.apects_tab_layout, container, false);
        inflater=(LayoutInflater)getLayoutInflater(savedInstanceState);
        
        activity = getActivity();
        String dbTableName = "Table_Aspects";
 		 SQLiteHelper info = new SQLiteHelper(getActivity().getBaseContext());		
 		 info.open();
 		 
 		 ArrayList<HashMap<String, String>> datafromSQL = info.getData(dbTableName);
 		
 		 
 		 if(!datafromSQL.isEmpty()){	
 			aspectsListArray = new  ArrayList<HashMap<String, String>>();
 			 for (int i = 0; i<datafromSQL.size(); i++){
 				 
 				aspectMap = new HashMap<String, String>();
 				aspectMap.put(PLANET_ID_ONE, datafromSQL.get(i).get(KEY_PLANET_ONE));
 				aspectMap.put(PLANET_ID_TWO, datafromSQL.get(i).get(KEY_PLANET_TWO));
 				aspectMap.put(DEGREES, datafromSQL.get(i).get(KEY_DEGREES));
 				aspectMap.put(CONTENT, datafromSQL.get(i).get(KEY_CONTENT));
 				aspectMap.put(TRANSIT_ID, datafromSQL.get(i).get(KEY_TRANSIT_ID));
 				 
 				aspectsListArray.add(aspectMap);
 		 	}	
 			 info.close();
 		 }

		if(!gotAspects){
			list = (ListView) v.findViewById(android.R.id.list); 
			AspectsAdapter adapter=new AspectsAdapter(getActivity(), R.layout.aspects_row, aspectsListArray); 
			list.setAdapter(adapter);
		}
		else{
			Toast.makeText(activity, "You must have internet connection to get your live aspects!", Toast.LENGTH_SHORT).show();
		}
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
 	        HashMap<String, String> item = aspectsListArray.get(position);
    }

//	@Override
//	public void sendResponse(HashMap<String, String> result) {
//		gotAspects = true;
//		saveAspectsLocalDB(result);
//		
//	}

//	private void saveAspectsLocalDB(HashMap<String, String> aspectResult) {
//		String aspectListString = null;
//
//		aspectListString = aspectResult.get("result");
//		Log.i("ASPECTS RESULT SPLASH", aspectResult.toString() );
//		aspectListArray = new ArrayList<HashMap<String, String>>();
//		
//		try {
//			JSONArray arr = new JSONArray(aspectListString);
//			for (int i = 0; i < arr.length(); i++) {
//				aspectListResult = new HashMap<String, String>();
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
//					aspectListResult.put(key, value);
//				}
//				aspectListArray.add(i, aspectListResult);
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//	    
//			boolean isAspectsLocalDB = false;
//			try{
//				SQLiteHelper regEntry3 = new SQLiteHelper(activity);
//				regEntry3.open();
//				regEntry3.deleteTable("Table_Aspects");
//				regEntry3.createEntry3(aspectListArray);
//				regEntry3.close();
//				isAspectsLocalDB = true;
//			}catch(Exception e){
//				Log.i("SQLITEDB", "not uploaded into local db", e);
//				isAspectsLocalDB = false;
//			}finally{
//				if(isAspectsLocalDB){
//
//				}
//			}
//
//		
//	}
}
