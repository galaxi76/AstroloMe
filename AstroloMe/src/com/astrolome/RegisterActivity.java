package com.astrolome;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.astrolome.Constants.*;

import org.apache.http.NameValuePair;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings.Secure;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;

import android.widget.Button;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.astrolome.DatePickerFragment;

public class RegisterActivity extends FragmentActivity implements ResponseManager{
	//test checkin number 2 - working copy
	Activity activity = this;
	boolean is24hourFormat;
	String deviceVersion;
	String email;
	String fullName;
	String gender;
	String dobDate;
	String dob;
	
	ImageView zodiac;
	EditText nameET;
	EditText emailET;
	EditText birthCity;
	Button register;
	Button bday;
	Button birthTime;
	TextView cityTV;
	
	String deviceID;
	String deviceModel;
	
	String cityID;
	String cityName;
	String user_id = "";
	String isBirthTime = "0";
	
	List<NameValuePair> registerParams;
	List<NameValuePair> birthLocParams;
	List<NameValuePair> citySuggestMap;
	private AutoCompleteTextView citySugg;
	
	RequestManager manager;
	 
	int hourBirth;
	int minBirth; 
	int yearBirth;
	int monthBirth;
	int dayBirth;
	int dobTimeStamp;
	
	String actionsKeyString;
	String emailString;
	String passString;
	String userLocString;
	String loginTimeString;
	long dateMillis = 0;
	long timeMillis = 0;
	
	HashMap<String, String> autologinMap;
	List<NameValuePair> autoLoginParams;	

	ArrayList<HashMap<String, String>> planetListArray;
	HashMap<String, String> planetListResult;
	List<NameValuePair> planetsParams;	
	PlanetManager pm;
	String gotWhat;
//	ArrayList<HashMap<String, String>> houseListArray;
	List<NameValuePair> houseParams;
//	HashMap<String, String> houseListResult;
	
//	ArrayList<HashMap<String, String>> aspectListArray;
	List<NameValuePair> aspectParams;
//	HashMap<String, String> aspectListResult;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		is24hourFormat = android.text.format.DateFormat.is24HourFormat(this);
//		Spinning Zodiac
		zodiac = (ImageView)findViewById(R.id.zodiac);	   
	    Animation myRotation =  AnimationUtils.loadAnimation(this, R.anim.decelerate_center_anim);
	    zodiac.startAnimation(myRotation);
		
//	    Check Network
		 if (!isNetworkAvailable()){
	        	Toast.makeText(getApplicationContext(), "No Internet," +
	        			" Please make sure internet connection is available", Toast.LENGTH_LONG).show();
	        }
	
		 
		 nameET = (EditText)findViewById(R.id.name);
		 emailET = (EditText)findViewById(R.id.email);
		 register = (Button)findViewById(R.id.done);
	     bday = (Button)findViewById(R.id.birthday);
	     birthTime = (Button)findViewById(R.id.birthtime);
	     citySugg = (AutoCompleteTextView) findViewById(R.id.birthLocTV);
	     
	     nameET.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					 in.hideSoftInputFromWindow(nameET.getWindowToken(), 0);
					 bday.requestFocus();
					return false;
				}
			});

	     citySugg.setAdapter(new AutoCompleteAdapter(this, R.layout.locations_list_item));
	     citySugg.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
//				cityID = user.getBirth_location_id();
//				cityName = user.getBirth_location_name();   
				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			    cityID = settings.getString("key_bl_id", null);
			    cityName = settings.getString("key_bl_name", null);
				
				InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    in.hideSoftInputFromWindow(citySugg.getWindowToken(), 0);
			    citySugg.setImeOptions(EditorInfo.IME_ACTION_NEXT);
			    emailET.requestFocus();
			}
		});

//		 RadioGroup radioGenderGroup = (RadioGroup)findViewById(R.id.radioGender);
//	
//		  // get selected radio button from radioGroup
//		 int selectedId = radioGenderGroup.getCheckedRadioButtonId();
//		// find the radiobutton by returned id
//		 RadioButton genderButton =
//	       (RadioButton) findViewById(selectedId);
//		 Log.i("genderBtn", genderButton.getText().toString());
//		 gender = (genderButton.equals("1")? "1" : "2");
		 
		 deviceID = Secure.getString(getBaseContext().getContentResolver(),
	                Secure.ANDROID_ID); 
		 
		 deviceModel = getDeviceModel();
		 deviceVersion = Build.VERSION.RELEASE;
		 		 
		 bday.setOnClickListener(new View.OnClickListener() {	 
				@Override
				public void onClick(View v) {
					Bundle args = new Bundle();
					args.putString("callingActivity", "RegisterActivity");
				/*	if(timeMillis!=0){*/
					args.putLong("setDate", dateMillis);
					args.putString("yearString", Integer.toString(yearBirth));
					args.putString("monthString", Integer.toString(monthBirth));
					args.putString("dayString", Integer.toString(dayBirth));
					DatePickerFragment date = new DatePickerFragment();	
					date.setArguments(args);
					date.show(getSupportFragmentManager(), "Date Picker");			    
				}
			});	 
  			
	     register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
								
				 fullName = nameET.getText().toString();
				 email = emailET.getText().toString();
				 String citySet = citySugg.getText().toString();
				 if (fullName.matches("")){
					 blinkText(nameET);
				 }
				 else if(bday.getText().equals("")){
					 blinkText(bday);
				 }
				 else if (citySet.matches("")){
					    blinkText(citySugg);
					}	 
				 else if(email.matches("")){
					 blinkText(emailET);
				 }
				 else if(!isEmailValid(email)){
					 Toast toast = Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, -100);
					 toast.show();
				 }
				 else if(birthTime.getText().equals("")){
					 hourBirth = 12;
					 minBirth = 0;
					 componentTimeToTimestamp(yearBirth, monthBirth, dayBirth, hourBirth, minBirth);
					 register();
				 }
				 else{
					 componentTimeToTimestamp(yearBirth, monthBirth, dayBirth, hourBirth, minBirth);
					 isBirthTime = "1";
					 register();
				 }
			}

			private void blinkText(View view) {
			 
			        Animation anim = new AlphaAnimation(0.0f, 1.0f);
			        anim.setDuration(500); 
			        anim.setStartOffset(20);
			        anim.setRepeatCount(2);
			        anim.setRepeatMode(Animation.REVERSE);
			        anim.setRepeatCount(2);
			        view.startAnimation(anim);			
			}
	     });
	}
	
	public static boolean isEmailValid(String email) {
	    boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	    }
	    return isValid;
	}
	
	public void showTimePickerDialog(View v) {
		Bundle args = new Bundle();
		args.putString("callingActivity", "RegisterActivity");
		args.putLong("", timeMillis);
		args.putString("hourString", Integer.toString(hourBirth));
		args.putString("minString", Integer.toString(minBirth));
		DialogFragment timeFragment = new TimePickerFragment();
		timeFragment.setArguments(args);
		timeFragment.show(activity.getFragmentManager(), "Time Picker");
	}

//	public static void setcityInfo(String cityID){
//		user = new User();
//		user.setBirth_location_id(cityID);
//		Log.i("GOT IT!!", cityID);
//	}

		
	protected int populateSetDate(int year, int month, int day) {
		yearBirth = year;
		monthBirth = month;
		dayBirth = day;
		Calendar cal = new GregorianCalendar(year, month, day);
		SimpleDateFormat sdf;
		if (!is24hourFormat){
			sdf = new SimpleDateFormat("MMM d, yyyy");
		}
		else{
			sdf = new SimpleDateFormat("d MMM, yyyy");
		}	
		String date = sdf.format(cal.getTime());
		bday.setText(date);
		dateMillis = cal.getTimeInMillis();
		return (int) (cal.getTimeInMillis() / 1000L);
	}

	protected void populateSetTime(Date time, int hour, int min){
		
		hourBirth = hour;
		minBirth = min;
		
		SimpleDateFormat sdf;
		if (!is24hourFormat){		
			sdf = new SimpleDateFormat("h:mm a");      
		}
		else{
			sdf = new SimpleDateFormat("H:mm"); 
		} 
		String timeString = sdf.format(time);
        
		birthTime.setText(timeString);		
	}
		

	private void register() {
		
		
				startAcceleration();
		 
				
				
				gender = "2";
								
//			    birthLocID = user.getBirth_location_id();
			    dob = Integer.toString(dobTimeStamp);
				
			    registerParams = new ArrayList<NameValuePair>();
			    registerParams.add(new BasicNameValuePair("apiver", "1"));
			    registerParams.add(new BasicNameValuePair("apikey", API_KEY));
			    registerParams.add(new BasicNameValuePair("cmd",
						 CMD_REG));
			    registerParams.add(new BasicNameValuePair("full_name",
						 fullName));
			    registerParams.add(new BasicNameValuePair("email",
						 email));
			    registerParams.add(new BasicNameValuePair("gender", gender));
			    registerParams.add(new BasicNameValuePair("dob", dob));
			    registerParams.add(new BasicNameValuePair("deviceuid", deviceID));
			    registerParams.add(new BasicNameValuePair("devicemodel", deviceModel));
			    registerParams.add(new BasicNameValuePair("deviceversion", deviceVersion));
			    registerParams.add(new BasicNameValuePair("birth_location_id", cityID));
			    registerParams.add(new BasicNameValuePair("birth_istime", isBirthTime));
			    registerParams.add(new BasicNameValuePair("birth_isloc", "1"));
			    manager = new RequestManager(URL_REG, this, registerParams);
		 		manager.delegate = this;
		 		manager.execute(registerParams);
	}
   
	
	private void startAcceleration() {
		Animation accelerateRotate =  AnimationUtils.loadAnimation(this, R.anim.accelerate_center_anim);
		zodiac.startAnimation(accelerateRotate);
//		Animation anim = new AlphaAnimation(0.0f, 1.0f);
//        anim.setDuration(500); 
//        anim.setStartOffset(20);
//        anim.setRepeatCount(2);
//        anim.setRepeatMode(Animation.REVERSE);
//        anim.setRepeatCount(2);
//        view.startAnimation(anim);			
		
	}

	private int componentTimeToTimestamp(int yearBirth2, int monthBirth2,
			int dayBirth2, int hourBirth2, int minBirth2) {
		 Calendar c = Calendar.getInstance();
		    c.set(Calendar.YEAR, yearBirth2);
		    c.set(Calendar.MONTH, monthBirth2);
		    c.set(Calendar.DAY_OF_MONTH, dayBirth2);
		    c.set(Calendar.HOUR, hourBirth2);
		    c.set(Calendar.MINUTE, minBirth2);
		    c.set(Calendar.SECOND, 0);
		    c.set(Calendar.MILLISECOND, 0);
		    dobTimeStamp = (int) (c.getTimeInMillis()/1000L);
		    
//		    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
//		    // Create a calendar object that will convert the date and time value in
//		    // milliseconds to date.
//		  
//		   String timeConverted =  formatter.format(c.getTime());
//		   Toast.makeText(activity, timeConverted, Toast.LENGTH_LONG).show();
		    return dobTimeStamp;
	}

	@Override
	public void sendResponse(HashMap<String, String> result) {

		String cmd = result.get(CMD);
		String error = result.get(ERROR);
		
		if (cmd.equals(CMD_REG)) {

			if (error.equals("no")) {
//				Toast.makeText(activity, "Your user id: " + result.get("user_id"),
//						Toast.LENGTH_SHORT).show();
				saveRegLocalDB(result);
			}
			if (error.equals("dob")) {
				Toast.makeText(activity, "Please enter your birthdate",
						Toast.LENGTH_LONG).show();
			}
		}
		if (cmd.equals(CMD_LOGIN)){
			
			String errorValue = result.get(ERROR);
			String timeStamp = result.get(TIMESTAMP);
			String userID = result.get(UID);
			
			if (error.equals("no")){
				autologinMap= new HashMap<String, String>();
				autologinMap.put("timeStamp", timeStamp);
//				autologinMap.put("cmd", cmd_tag);
				autologinMap.put("userID", userID);
				
				getPlanets();
				getHouses();
				getAspects();				
		    }
			
			else{
				Toast.makeText(activity, "Error Logging In", Toast.LENGTH_SHORT).show();
		       activity.finish();
			}
		}
		if (cmd.equals(CMD_BC)) {
			Log.i("BIRTHCHART RESPONSE", result.toString());
			if (error.equals("no")) {
				String resultArrayString = result.get("result");
				User user = new User();
				gotWhat = user.getHouseID(resultArrayString);// to see if planet/house/aspect was selected

				if (gotWhat.equals(PLANET_ID)) {
					SaveToLocalDBHelper save = new SaveToLocalDBHelper(
							activity, result, PLANET_ID);
					save.saveBirthChartLocalDB();
				}

				if (gotWhat.equals(HOUSE_ID)) {
					SaveToLocalDBHelper save = new SaveToLocalDBHelper(
							activity, result, HOUSE_ID);
					save.saveBirthChartLocalDB();
				}
				if (gotWhat.equals(TRANSIT_ID)) {
					SaveToLocalDBHelper save = new SaveToLocalDBHelper(
							activity, result, TRANSIT_ID);
					save.saveBirthChartLocalDB();
					Intent mainIntent = new Intent(activity,
							MainFragmentActivity.class);
					mainIntent.putExtra("calling-activity", "RegisterActivity");
					activity.startActivity(mainIntent);
					finish();
				}

			}
		}
	}
	
	private void getHouses() {
		
		 pm = new PlanetManager(activity,"1");
		 houseParams = pm.getBCParams();
			    
		 RequestManager manager = new RequestManager(URL_BC, activity, houseParams);
	     manager.delegate = (ResponseManager)activity;
	     manager.execute(houseParams);
	 }
	
	private void getAspects(){
		pm = new PlanetManager(activity, "3");
		aspectParams =  pm.getBCParams();
		
		manager = new RequestManager(URL_BC, activity, aspectParams);
		manager.delegate = (ResponseManager)activity;
		manager.execute(aspectParams);
	}

//	private String getHouseID(String resultArray) {
//		
//		String gotWhat = null;
//		HashMap<String, String> firstResultMap;
//		try {
//			JSONArray arr = new JSONArray(resultArray);
//
//			firstResultMap = new HashMap<String, String>();
//			JSONObject jObject = arr.getJSONObject(0);
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
//				firstResultMap.put(key, value);
//				
//			}
//			if(firstResultMap.containsKey(PLANET_ID)){
//				gotWhat = PLANET_ID;
//			}
//			else if(firstResultMap.containsKey(HOUSE_ID)){
//				gotWhat = HOUSE_ID;
//			}
//			else{
//				gotWhat = TRANSIT_ID;
//			}
//			
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//
//		return gotWhat;
//
//	}
//	private void saveHousesLocalDB(HashMap<String, String> houseResult) {
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
//				SQLiteHelper regEntry2 = new SQLiteHelper(RegisterActivity.this);
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
//	private void saveAspectsLocalDB(HashMap<String, String> aspectResult) {
//		String aspectListString = null;
//
//		aspectListString = aspectResult.get("result");
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
//		 Log.i("THE ASPECTS!!!!", "aspectid:" + aspectResult.get("transit_aspect_id") + "  descrip: " + aspectResult.get("content"));
//	    
//			boolean isAspectsLocalDB = false;
//			try{
//				SQLiteHelper regEntry3 = new SQLiteHelper(RegisterActivity.this);
//				regEntry3.open();
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
//	private void getAspects() {
//		
//		 aspectParams = new ArrayList<NameValuePair>();
//		 aspectParams.add(new BasicNameValuePair("apiver", "1"));
//		 aspectParams.add(new BasicNameValuePair("apikey", API_KEY));
//		 aspectParams.add(new BasicNameValuePair("cmd",
//					 CMD_BC));
//		 
//		 aspectParams.add(new BasicNameValuePair(TYPE_ID,
//					 "3"));
//			    
//		    RequestManager manager = new RequestManager(URL_BC, this, aspectParams);
//	 		manager.delegate = this ;
//	 		manager.execute(aspectParams);
//	 }

//	private void getHouses() {
//		
//		 houseParams = new ArrayList<NameValuePair>();
//		 houseParams.add(new BasicNameValuePair("apiver", "1"));
//		 houseParams.add(new BasicNameValuePair("apikey", API_KEY));
//		 houseParams.add(new BasicNameValuePair("cmd",
//					 CMD_BC));
//		 
//		 houseParams.add(new BasicNameValuePair(TYPE_ID,
//					 "1"));
//			    
//		    RequestManager manager = new RequestManager(URL_BC, this, houseParams);
//	 		manager.delegate = this ;
//	 		manager.execute(houseParams);
//	 }
		
	protected void getPlanets() {

		PlanetManager pm = new PlanetManager(activity, "2");
		planetsParams = pm.getBCParams();

		RequestManager manager = new RequestManager(URL_BC, this, planetsParams);
		manager.delegate = this;
		manager.execute(planetsParams);
	}
	
	public void saveRegLocalDB(HashMap<String, String> registerResult){
		
		String dateReg;
		String uid;
		String actionKey;
		String password = null;
		
		boolean isRegLocalDB = false;
		dateReg = registerResult.get("ts");
		uid = registerResult.get("uid");
		actionKey = registerResult.get("actions_key");
		
		try{
			SQLiteHelper regEntry = new SQLiteHelper(RegisterActivity.this);
			regEntry.open();
			regEntry.createEntry(deviceID, deviceModel, deviceVersion, 
					dob, gender, fullName, email, cityID, dateReg, uid, actionKey, password);
			regEntry.close();
			isRegLocalDB = true;
		}catch(Exception e){
			Log.i("SQLITEDB", "not uploaded into local db", e);
			isRegLocalDB = false;
		}finally{
			if(isRegLocalDB){
				Log.i("INFO SAVED!!!","device id: "   +deviceID + ", device version: " + deviceVersion + 
						", device model: " + deviceModel  + ", dob: " + dob + ", gender: " + gender + 
						", name: " + fullName + ", email: " + email + ", birth location: " + cityID + 
						", date of registration: " + dateReg + ", user ID: " + uid + ", actions_key: " + actionKey);
				LoginManager lm = new LoginManager(this);
				autoLoginParams =  lm.autoLogin();
				manager = new RequestManager(URL_LOGIN, this, autoLoginParams);
				manager.delegate = this;
				manager.execute(autoLoginParams);
			}
		}

	}
	
//public void savePlanetsLocalDB(HashMap<String, String> planetResult){
//		
//	planetListResult = new HashMap<String, String>();
//	String planetListString = null;
//    
//    planetListString = planetResult.get("result");
//	planetListArray = new ArrayList<HashMap<String, String>>();
//	
//	try {
//		JSONArray arr = new JSONArray(planetListString);
//		for (int i = 0; i < arr.length(); i++) {
//			planetListResult = new HashMap<String, String>();
//			JSONObject jObject = arr.getJSONObject(i);
//			// String foo2 = planetListString.substring(1, planetListString.length() - 1).trim(); // hack off braces
//			Iterator<?> keys = jObject.keys();
//
//			while (keys.hasNext()) {
//				String key = (String) keys.next();
//				String value = null;
//				try {
//					value = jObject.getString(key);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//				planetListResult.put(key, value);
//			}
//			planetListArray.add(i, planetListResult);
//			
//		}
//	} catch (JSONException e) {
//		// TODO Auto-generated catch block
//		e.printStackTrace();
//	}
//    
//		boolean isPlanetsLocalDB = false;
//		try{
//			SQLiteHelper regEntry1 = new SQLiteHelper(RegisterActivity.this);
//			regEntry1.open();
//			regEntry1.createEntry1(planetListArray);
//			regEntry1.close();
//			isPlanetsLocalDB = true;
//		}catch(Exception e){
//			Log.i("SQLITEDB", "not uploaded into local db", e);
//			isPlanetsLocalDB = false;
//		}finally{
//			if(isPlanetsLocalDB){
////				Log.i("INFO SAVED!!!","device id: "   +deviceID + ", device version: " + deviceVersion + 
////						", device model: " + deviceModel  + ", dob: " + dob + ", gender: " + gender + 
////						", name: " + fullName + ", email: " + email + ", birth location: " + birthLocID + 
////						", date of registration: " + dateReg + ", user ID: " + uid + ", actions_key: " + actionKey);
//			}
//		}
//
//	}
			
	
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
	}
	
	public String getDeviceModel() {
		  String manufacturer = Build.MANUFACTURER;
		  String model = Build.MODEL;
		  if (model.startsWith(manufacturer)) {
		    return capitalize(model);
		  } else {
		    return capitalize(manufacturer) + " " + model;
		  }
		}

	private String capitalize(String s) {
		  if (s == null || s.length() == 0) {
		    return "";
		  }
		  char first = s.charAt(0);
		  if (Character.isUpperCase(first)) {
		    return s;
		  } else {
		    return Character.toUpperCase(first) + s.substring(1);
		  }
		} 

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
