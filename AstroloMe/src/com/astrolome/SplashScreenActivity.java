package com.astrolome;

import static com.astrolome.Constants.*;

import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;

import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreenActivity extends Activity implements ResponseManager {

	boolean error = false;
	CookieStore cookieStore;
	Activity activity = this;
	HashMap<String, String> systemStatus;
	String table_name = "User";
	
	String actionsKeyString;
	String emailString;
	String passString;
	String userLocString;
	String loginTimeString;

	RequestManager manager;	
	String urlSysStatus = "";	
	List<NameValuePair> autoLoginParams;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash_screen);
			
		// check network
		Utils utils = new Utils(this);
		if (!utils.isNetworkAvailable()) {
			Toast.makeText(getApplicationContext(), R.string.NoInternet,
					Toast.LENGTH_LONG).show();
		}
				
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		String locationProvider = LocationManager.NETWORK_PROVIDER;

		Location lastKnownLocation = locationManager.getLastKnownLocation(locationProvider);
		double lat = 	lastKnownLocation.getLatitude();
		double longi  = lastKnownLocation.getLongitude();
        long userTime = lastKnownLocation.getTime(); 
        userLocString = "" + lat + "," + longi;
        loginTimeString = "" + userTime;
		
		// splash
		 LinearLayout progress = (LinearLayout)findViewById(R.id.splashProgLayout);
		 progress.setVisibility(View.VISIBLE);

		 TextView loadingInfo = (TextView)findViewById(R.id.initializationInfo);
		 loadingInfo.setText(R.string.loading);
		 
		
		
		 String dbTableName = "Table_User";
		 SQLiteHelper info = new SQLiteHelper(this);		
		 info.open();
		 	 
		 if(!info.isTableExists(dbTableName)){
		 	info.close();
		 	
	        new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                Intent mainIntent = new Intent(activity, RegisterActivity.class);
	                mainIntent.putExtra("calling-activity", "SpalshScreenActivity");
	                activity.startActivity(mainIntent);
	                activity.finish();
	            }
	        }, SPLASH_DISPLAY_LENGTH);
		 }
		

		 HashMap<String, String> datafromSQL = info.getDataFromUser();
		 	
		 if(!datafromSQL.isEmpty()){
			 
		 	emailString = datafromSQL.get(KEY_EMAIL);
		 	passString = datafromSQL.get(KEY_PASSWORD);
		 	actionsKeyString = datafromSQL.get(KEY_ACTIONS_KEY);
		 		 
		 	if(actionsKeyString!=null){
		 		info.close();
		 			 
		 		LoginManager lm = new LoginManager(this);
				autoLoginParams =  lm.autoLogin();
				manager = new RequestManager(URL_LOGIN, this, autoLoginParams);
				manager.delegate = this;
				manager.execute(autoLoginParams);
		 			
		 	}
		 	else{
		 		info.close();
		 		
		        new Handler().postDelayed(new Runnable(){
		            @Override
		            public void run() {
		                /* Create an Intent that will start the Menu-Activity. */
		                Intent mainIntent = new Intent(activity, RegisterActivity.class);
		                activity.startActivity(mainIntent);
		                activity.finish();
		            }
		        }, SPLASH_DISPLAY_LENGTH);
		 	}
//			Intent intent = new Intent(activity, LoginActivity.class);
//			intent.putExtra("actionKey", actionsKeyString);
//			intent.putExtra(name, value);	 			
		  }
		 // TODO: LOGIN screen App info was wiped or fresh app install
		 		else{
		 			info.close();
			        new Handler().postDelayed(new Runnable(){
			            @Override
			            public void run() {
			                Intent mainIntent = new Intent(activity, RegisterActivity.class);
			                activity.startActivity(mainIntent);
			                activity.finish();
			            }
			        }, SPLASH_DISPLAY_LENGTH);
		 		}
	}
	

	@Override
	public void sendResponse(HashMap<String, String> result) {
		
		String errorValue = result.get(ERROR);
		String timeStamp = result.get(TIMESTAMP);
		String userID = result.get(UID);
		String cmd = result.get(CMD);
		
		if (!errorValue.equals("no")){
			startActivity(new Intent(activity, LoginManager.class));
			finish();
		}
		if(cmd.equals(CMD_LOGIN)){
			new Handler().postDelayed(new Runnable(){
	            @Override
	            public void run() {
	                Intent mainIntent = new Intent(activity, MainFragmentActivity.class);
	                mainIntent.putExtra("calling-activity", "SplashScreenActivity");
	                activity.startActivity(mainIntent);
	                activity.finish();
	            }
	        }, SPLASH_DISPLAY_LENGTH);

		}

	}

	@Override
    protected void onResume() {
           super.onResume();
//           CookieSyncManager.getInstance().stopSync();
    }
    @Override
     protected void onPause() {
           super.onPause();
    }
}			


//	private void connect() {
//		try {
//			String url = "https://mamic.astrolome.com/_api3.sandbox/system.status";
//		
//			
//			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
//
//			DefaultHttpClient client = new DefaultHttpClient();
//
//			SchemeRegistry registry = new SchemeRegistry();
//			SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
//			socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
//			registry.register(new Scheme("https", socketFactory, 443));
//			SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
//			DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());
//
//			// Set verifier     
//			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
//
//			// Example send http request
//			
//			HttpPost httpPost = new HttpPost(url);
//				
////			MyVerifier myVerifier = new MyVerifier();
////			HttpClient client = myVerifier.getTolerantClient();
////			
////			HttpPost post = new HttpPost(url);
//
//			// cookieStore = new BasicCookieStore();
//
////			 String cookieStoreString = null;
////			 List<Cookie> cookies = ((AbstractHttpClient)
////			 client).getCookieStore().getCookies();
////			
////			 // if (cookies.isEmpty()) {
////			 // System.out.println("None");
////			 // } else {
////			 for (int i = 0; i < cookies.size(); i++) {
////			 System.out.println("- " + cookies.get(i).toString());
////			 String temp = "" + cookies.get(i).toString();
////			 cookieStoreString += "" + temp;
////			 }
//			
//			 // }
//			 // String cookieStoreString =
//			// "__utma=225833610.703975331.1400570032.1400570032.1400570032.1; __utmc=225833610; __utmz=225833610.1400570032.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); system=%7B%22lang%22%3A%22en%22%7D; firm=%7B%22id%22%3A%221%22%2C%22config%22%3A%7B%22credits_dial%22%3A%221%22%2C%22credits_loveletter%22%3A%2210%22%2C%22credits_advice2%22%3A%2210%22%2C%22credits_advice3%22%3A%2215%22%2C%22credits_advice4%22%3A%2225%22%2C%22credits_advice5%22%3A%2250%22%2C%22credits_onreg%22%3A%225%22%2C%22advice_timeout_ask%22%3A%2214400%22%2C%22advice_timeout_offline%22%3A%2228800%22%2C%22caller_num%22%3A%22972524455266%22%2C%22advice_freefollowup_time%22%3A%2224%22%2C%22advice_freefollowup_credits%22%3A%225%22%2C%22partner_comp_min%22%3A%2230%22%2C%22dial_default_astrid%22%3A%220%22%7D%2C%22stats%22%3A%7B%22love_total%22%3A%220%22%2C%22love_pending%22%3A%220%22%2C%22advice_pending%22%3A%220%22%2C%22customer_total%22%3A%220%22%2C%22login_24h%22%3A%220%22%2C%22login_total%22%3A%220%22%2C%22advice_24h%22%3A%220%22%2C%22calls_24h%22%3A%220%22%2C%22calls_total%22%3A%220%22%2C%22advice_total%22%3A%220%22%2C%22calls_byastrologers%22%3A%220%22%2C%22calls_avg_time%22%3A%22%22%2C%22love_24h%22%3A%220%22%2C%22calls_avg_astrologer%22%3A%220%22%2C%22calls_credits%22%3A%22%22%7D%7D";
//			//
//			 // add header
//	//		 post.setHeader("Cookie", cookieStoreString);
//
//			 List<NameValuePair> postParameters = new
//			 ArrayList<NameValuePair>();
//			 postParameters.add(new BasicNameValuePair("apiver", "1"));
//			 postParameters.add(new BasicNameValuePair("apikey",
//			 "72965fa00022-dbdb-3e11-2738-15786b97"));
//			 postParameters.add(new BasicNameValuePair("cmd",
//			 "system.status"));
//			
//			 httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
//
//			 Log.i("urlParameters", postParameters.toString());
//			 HttpResponse response = httpClient.execute(httpPost);
//
//			System.out.println("\nSending 'POST' request to URL : " + url);
//			System.out.println("Post parameters : " + httpPost.getEntity());
//			System.out.println("Response Code : "
//					+ response.getStatusLine().getStatusCode());
//
//			HttpEntity entity = response.getEntity();
//			if (entity != null) {
//				// Read the content stream
//				InputStream instream = entity.getContent();
//				// convert content stream to a String
//				String resultString = convertStreamToString(instream);
//				instream.close();
//				resultString = resultString.substring(0,
//						resultString.length() - 1); // remove wrapping "[" and
//													// "]"
//				Log.i("resultString from System.StatusIS", resultString.toString());
//				// Transform the String into a JSONObject
//				JSONObject jsonObjRecv = null;
//				try {
//					jsonObjRecv = new JSONObject(resultString.toString());
//					for (int i = 0; i<jsonObjRecv.length(); i++){
//						String timeStamp_tag = jsonObjRecv.getString(TIMESTAMP);
//						String cmd_tag = jsonObjRecv.getString(CMD);
//						String status_tag = jsonObjRecv.getString(STATUS);
//						String is_login_tag = jsonObjRecv.getString(IS_LOGIN);
//						String err_tag = jsonObjRecv.getString(ERROR);
//						String uid_tag = jsonObjRecv.getString(UID);
//						String utd_tag = jsonObjRecv.getString(UTD);
//						String ut_tag = jsonObjRecv.getString(UT);
//						String logTime_tag = jsonObjRecv.getString(LOGTIME);
//						String firmId_tag = jsonObjRecv.getString(FIRM_ID);
//						String language_tag = jsonObjRecv.getString(LANGUAGE);
//						
////						SharedPreferences sharedPrefs = activity.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);
////						sharedPrefs.edit().putString("ts", timeStamp_tag);
////						sharedPrefs.edit().putString("cmd", cmd_tag);
////						sharedPrefs.edit().putString("uid", uid_tag);
////						((Editor) sharedPrefs).commit();
////						Log.i("sharedPrefs", sharedPrefs.getString("ts", timeStamp_tag.toString()));
//
//						systemStatus = new HashMap<String, String>();
//						systemStatus.put("ts", timeStamp_tag);
//						systemStatus.put("cmd", cmd_tag);
//						systemStatus.put("status", status_tag);
//						systemStatus.put("is_login", is_login_tag);
//						systemStatus.put("uid", uid_tag);
//						systemStatus.put("err", err_tag);
//						systemStatus.put("utd", utd_tag);
//						systemStatus.put("ut", ut_tag);
//						systemStatus.put("logTime", logTime_tag);
//						systemStatus.put("firmID", firmId_tag);
//						systemStatus.put("language", language_tag);
//						
//						
//						//						{"ts":1406979432,
////							"cmd":"system.status",
////							"status":"yes",
////							"is_login":"no",
////							"err":"no",
////							"uid":0,
////							"utd":null,
////							"ut":1406979432,
////							"logtime":0,
////							"firm_id":"1",
////							"lang":"en"}
//					}
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				// Raw DEBUG output of our received JSON object:
//				Log.i("SYSTEM STATUS", systemStatus.toString());
//				Log.i("JSON OBJECT", "<JSONObject>\n" + jsonObjRecv.toString()
//						+ "\n</JSONObject>");
//				String errorValue = systemStatus.get(ERROR);
//				if(errorValue.equals("no")){
////					Intent loginIntent = new Intent(getBaseContext(), RegisterActivity.class);
////					 startActivityForResult(loginIntent, 0);
//				}
//				else{
//					Toast.makeText(activity, "Server error. Please try again", Toast.LENGTH_LONG).show();
//				}
//		
//			}
//
//		} catch (ClientProtocolException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}

	

