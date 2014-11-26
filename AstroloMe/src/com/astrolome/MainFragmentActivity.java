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
	            		
	            }

	            @Override
	            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
	            }

	            @Override
	            public void onPageScrollStateChanged(int state) {
	            }
	        });
	 }	 

	 @Override
	    protected void onResume() {
	           super.onResume();
	    }
	
	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

		
}

