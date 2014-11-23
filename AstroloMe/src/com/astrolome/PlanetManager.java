package com.astrolome;

import static com.astrolome.Constants.API_KEY;
import static com.astrolome.Constants.CMD_BC;
import static com.astrolome.Constants.TYPE_ID;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;

public class PlanetManager {

	Activity mActivity;
	List<NameValuePair> params;
	String typeID;

	public PlanetManager(){
		super();
	}
	public PlanetManager(String typeID){
		super();
		this.typeID = typeID;
	}
	public PlanetManager(Activity sendingActivity, String typeID){
		 this.mActivity = sendingActivity;
		 this.typeID = typeID;
	 }
	public List<NameValuePair> getBCParams() {
		 params = new ArrayList<NameValuePair>();
		 params.add(new BasicNameValuePair("apiver", "1"));
		 params.add(new BasicNameValuePair("apikey", API_KEY));
		 params.add(new BasicNameValuePair("cmd",
					 CMD_BC));
		 params.add(new BasicNameValuePair(TYPE_ID,
					 typeID));
		return params;
		
		}
}
