package com.astrolome;

import static com.astrolome.Constants.API_KEY;
import static com.astrolome.Constants.CMD;
import static com.astrolome.Constants.ERROR;
import static com.astrolome.Constants.KEY_ACTIONS_KEY;
import static com.astrolome.Constants.KEY_EMAIL;
import static com.astrolome.Constants.KEY_PASSWORD;
import static com.astrolome.Constants.SPLASH_DISPLAY_LENGTH;
import static com.astrolome.Constants.TIMESTAMP;
import static com.astrolome.Constants.UID;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class LoginManager{
	
	Activity mActivity;
	String actionsKeyString;
	String emailString;
	String passString;
	String userLocString;
	String loginTimeString;

	List<NameValuePair> autoLoginParams;	
	
	public LoginManager(){
		super();
	}
 public LoginManager(Activity sendingActivity){
	 this.mActivity = sendingActivity;
 }
	public List<NameValuePair> autoLogin() {

		LocationManager locationManager = (LocationManager) mActivity
				.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(locationProvider);
		double lat = lastKnownLocation.getLatitude();
		double longi = lastKnownLocation.getLongitude();
		long userTime = lastKnownLocation.getTime();
		userLocString = "" + lat + "," + longi;
		loginTimeString = "" + userTime;

		SQLiteHelper info = new SQLiteHelper(mActivity);
		info.open();

		HashMap<String, String> datafromSQL = info.getDataFromUser();

		emailString = datafromSQL.get(KEY_EMAIL);
		passString = datafromSQL.get(KEY_PASSWORD);
		actionsKeyString = datafromSQL.get(KEY_ACTIONS_KEY);

		if (actionsKeyString != null) {
			info.close();

			autoLoginParams = new ArrayList<NameValuePair>();

			autoLoginParams.add(new BasicNameValuePair("apiver", "1"));
			autoLoginParams.add(new BasicNameValuePair("apikey", API_KEY));
			autoLoginParams.add(new BasicNameValuePair("cmd", "user.login"));
			autoLoginParams.add(new BasicNameValuePair("actions_key",
					actionsKeyString));
			autoLoginParams.add(new BasicNameValuePair("email", emailString));
			autoLoginParams.add(new BasicNameValuePair("pass", passString));
			autoLoginParams.add(new BasicNameValuePair("user_loc",
					userLocString));
			autoLoginParams.add(new BasicNameValuePair("user_time",
					loginTimeString));
		}
		return autoLoginParams;
	}
//	@Override
//	public void sendResponse(HashMap<String, String> result) {
//		
//		String error = result.get(ERROR);
//		if(!error.equals("no")){
//			Log.i("loginerror", error.toString());
//		}
//		else{
//			RegisterActivity regAct = new RegisterActivity();
//			regAct.getPlanets();
//		}
//		
//	}
	
}
