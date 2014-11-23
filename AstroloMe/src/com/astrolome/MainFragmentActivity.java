package com.astrolome;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;

import com.viewpagerindicator.IconPageIndicator;
import com.viewpagerindicator.PageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;


import static com.astrolome.Constants.*;


public class MainFragmentActivity extends BaseActivity{
	
	PlanetManager pm;
	FragmentAdapter adapter;
	ViewPager pager;
	PageIndicator indicator;
	int Number = 0;	
	String planetID;
	Activity activity = this;
	RequestManager manager;
	
	ArrayList<HashMap<String, String>> aspectListArray;
	List<NameValuePair> aspectParams;
	HashMap<String, String> aspectListResult;
	
	ArrayList<HashMap<String, String>> houseListArray;
	List<NameValuePair> houseParams;
	HashMap<String, String> houseListResult;
	
	String gotWhat = null;
	boolean gotAspects = false;
	boolean gotHouses = false;

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
	        setContentView(R.layout.main_layout);   
	        TextView sqlUIDtv = (TextView)findViewById(R.id.sqlUserID);
	        
	        SQLiteHelper uidInfo = new SQLiteHelper(this);
	        uidInfo.open();
	        HashMap<String, String> sqlUID = uidInfo.getDataFromUser();
	        Log.i("sqlUID", sqlUID.toString());
	        String uid = sqlUID.get(KEY_UID);
	        sqlUIDtv.setText("User ID: " + uid);
	        uidInfo.close();

	        ImageView settings = (ImageView)findViewById(R.id.settingsBtn);
			settings.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(activity, SettingsActivity.class);
					startActivity(intent);
//					finish();
				}
				});
			
			final TextView heading = (TextView)findViewById(R.id.fragHeading);
			heading.setText(R.string.myPlanetsHeading);
	        adapter = new FragmentAdapter(getSupportFragmentManager());
	        ViewPager pager = (ViewPager) findViewById(R.id.pager);
	        pager.setAdapter(adapter);
	        
	        indicator = (IconPageIndicator)findViewById(R.id.indicator);
	        indicator.setViewPager(pager);
	        
	      //We set this on the indicator, NOT the pager
	        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
	            @Override
	            public void onPageSelected(int position) {
	            	switch(position){
	            	case 0:
	            		heading.setText(R.string.myPlanetsHeading);
	            		
	            		break;
	            	case 1:
	            		heading.setText(R.string.myHousesHeading);
	            		
	            		break;
	            	case 2:
	            		heading.setText(R.string.myAspectsHeading);
	            		break;
	            	}
	            		
//	            	if(position==2){
//	            		if(!gotAspects){
//	         			AspectManager am = new AspectManager(activity);
//	        			aspectParams =  am.getNewAspects();
//	        			
//	        			manager = new RequestManager(URL_BC, activity, aspectParams);
//	        			manager.delegate = MainFragmentActivity.this;
//	        			manager.execute(aspectParams);
//	            		}
//	            	}
	            }

	            @Override
	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	            }

	            @Override
	            public void onPageScrollStateChanged(int state) {
	            }
	        });
	 }	 
	 
//		@Override
//		public void sendResponse(HashMap<String, String> result) {
//			String error = result.get(ERROR);
//			if (error.equals("no")) {
//				String resultArrayString = result.get("result");
//				gotWhat = getHouseID(resultArrayString); //to see if planet/house/aspect was selected
//				
//				if (gotWhat.equals(HOUSE_ID)) {
//					gotHouses = true;
//					SaveToLocalDBHelper save = new SaveToLocalDBHelper(activity, result, gotWhat);
//					save.saveBirthChartLocalDB();
//					Log.i("HOUSES", "gotHOUSES!!!!");
////					saveHousesLocalDB(result);
//				}
//				if(gotWhat.equals(TRANSIT_ID)){
//					gotAspects = true;
//					Log.i("ASPECTS", "gotASPECTS!!!!");
//					SaveToLocalDBHelper save = new SaveToLocalDBHelper(activity, result, gotWhat);
//					save.saveBirthChartLocalDB();
//				}
//			}		
//		}
		
//		private void getHouses() {
//			
//			 pm = new PlanetManager(activity,"1");
//			 houseParams = pm.getBCParams();
//				    
//			 RequestManager manager = new RequestManager(URL_BC, activity, houseParams);
//		     manager.delegate = (ResponseManager)activity;
//		     manager.execute(houseParams);
//		 }
//		
//		private String getHouseID(String resultArray) {
//			
//			HashMap<String, String> firstResultMap;
//			try {
//				JSONArray arr = new JSONArray(resultArray);
//
//				firstResultMap = new HashMap<String, String>();
//				JSONObject jObject = arr.getJSONObject(0);
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
//					firstResultMap.put(key, value);
//					
//				}
//				if(firstResultMap.containsKey(PLANET_ID)){
//					gotWhat = PLANET_ID;
//				}
//				else if(firstResultMap.containsKey(HOUSE_ID)){
//					gotWhat = HOUSE_ID;
//				}
//				else{
//					gotWhat = TRANSIT_ID;
//				}			
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//			return gotWhat;
//		}
		
//		private void saveHousesLocalDB(HashMap<String, String> houseResult) {
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
//		private void saveAspectsLocalDB(HashMap<String, String> aspectResult) {
//			String aspectListString = null;
//
//			aspectListString = aspectResult.get("result");
//			Log.i("ASPECTS RESULT SPLASH", aspectResult.toString() );
//			aspectListArray = new ArrayList<HashMap<String, String>>();
//			
//			try {
//				JSONArray arr = new JSONArray(aspectListString);
//				for (int i = 0; i < arr.length(); i++) {
//					aspectListResult = new HashMap<String, String>();
//					JSONObject jObject = arr.getJSONObject(i);
//					Iterator<?> keys = jObject.keys();
//
//					while (keys.hasNext()) {
//						String key = (String) keys.next();
//						String value = null;
//						try {
//							value = jObject.getString(key);
//						} catch (JSONException e) {
//							e.printStackTrace();
//						}				
//						aspectListResult.put(key, value);
//					}
//					aspectListArray.add(i, aspectListResult);
//				}
//			} catch (JSONException e) {
//				e.printStackTrace();
//			}
//		    
//				boolean isAspectsLocalDB = false;
//				try{
//					SQLiteHelper regEntry3 = new SQLiteHelper(activity);
//					regEntry3.open();
//					regEntry3.deleteTable("Table_Aspects");
//					regEntry3.createEntry3(aspectListArray);
//					regEntry3.close();
//					isAspectsLocalDB = true;
//				}catch(Exception e){
//					Log.i("SQLITEDB", "not uploaded into local db", e);
//					isAspectsLocalDB = false;
//				}finally{
//					if(isAspectsLocalDB){
//
//					}
//				}
//		}

	 @Override
	    protected void onResume() {
	           super.onResume();
	    }
	
	@Override
	protected void onStop() {
		super.onStop();
	}

		
}

