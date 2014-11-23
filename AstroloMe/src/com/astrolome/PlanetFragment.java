package com.astrolome;

import static com.astrolome.Constants.CONTENT;
import static com.astrolome.Constants.*;

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
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PlanetFragment extends ListFragment{
	
//	planet_id, zodiac_id, degrees, minutes, seconds, read_date, full_angle, content, rising_zodiac_id
//	the "rising_zodiac_id" value states the Rising Zodiac ID for SUN-specific birth events.
	
	LayoutInflater inflater;
	ListView list;
	TextView name;
	TextView explanation;
	ImageView zodiac;
	Button getDetails;
	ArrayList<HashMap<String, String>> planetListArray;

	String planetID;
	String zodiacID;
	String degrees;
	String content;
	String fullAngle;
	String readDate;
	String minuteDegrees;
	String risingID;
	String secondsDegrees;
	String houseID;
	HashMap<String, String> planetMap;
	Activity activity;
	    
	   @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	        View v = inflater.inflate(R.layout.planets_tab_layout, container, false);
       inflater=(LayoutInflater)getLayoutInflater(savedInstanceState);
       activity = getActivity();
       
//       ImageView settings = (ImageView)v.findViewById(R.id.settingsBtn);
//		settings.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(getActivity(), SettingsActivity.class);
//				startActivity(intent);
//			}
//			});
       
       String dbTableName = "Table_Planets";
		 SQLiteHelper info = new SQLiteHelper(getActivity().getBaseContext());		
		 info.open();
		 
		 ArrayList<HashMap<String, String>> datafromSQL = info.getData(dbTableName);
		
		 if(!datafromSQL.isEmpty()){	
			 planetListArray = new  ArrayList<HashMap<String, String>>();
			 for (int i = 0; i<datafromSQL.size(); i++){
				 
				 planetMap = new HashMap<String, String>();
				 planetMap.put(PLANET_ID, datafromSQL.get(i).get(KEY_PLANET_ID));
				 planetMap.put(ZODIAC_ID, datafromSQL.get(i).get(KEY_ZODIAC_ID));
				 planetMap.put(DEGREES, datafromSQL.get(i).get(KEY_DEGREES));
				 planetMap.put(CONTENT, datafromSQL.get(i).get(KEY_CONTENT));
				 
				 planetListArray.add(planetMap);
		 	}	
			 info.close();
		 }


	  list = (ListView) v.findViewById(android.R.id.list); 
      PlanetAdapter adapter=new PlanetAdapter(getActivity(), R.layout.planets_row, planetListArray); 
   
      //finally,set the adapter to the default ListView
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
	        HashMap<String, String> item = planetListArray.get(position);
////	        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity().getBaseContext());
//////			 View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_details, null);
////			 View view =  inflater.inflate(R.layout.dialog_details, null);
////			 dialog.setView(view);
////			 
////			 TextView explan = (TextView)view.findViewById(R.id.explanationDetail);
////			 Button ok = (Button)view.findViewById(R.id.ok);
////			 dialog.setTitle(viewHolder.planetName.getText() + 
////					 " " + viewHolder.planetDegree.getText() + " " + viewHolder.zodiacName.getText());
////            dialog.setIcon(viewHolder.zodIcon.getBackground());
////            explan.setText(descrip);
////            dialog.create();
////            ok.setOnClickListener(new View.OnClickListener() {
////				
////				@Override
////				public void onClick(View v) {
////					 ((DialogInterface) dialog).dismiss();
////                    return;							
////				}
////			});
////              
////            dialog.show();
//        
//		}
  
	 }
}
