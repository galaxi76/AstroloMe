package com.astrolome;

import static com.astrolome.Constants.CMD;
import static com.astrolome.Constants.API_KEY;
import static com.astrolome.Constants.ERROR;
import static com.astrolome.Constants.DB_TABLENAME_USER;
import static com.astrolome.Constants.HOUSE_ID;
import static com.astrolome.Constants.KEY_NAME;
import static com.astrolome.Constants.KEY_DOB;
import static com.astrolome.Constants.KEY_BIRTH_LOCAL_ID;
import static com.astrolome.Constants.KEY_EMAIL;
import static com.astrolome.Constants.PLANET_ID;
import static com.astrolome.Constants.PREFS_NAME;
import static com.astrolome.Constants.TRANSIT_ID;
import static com.astrolome.Constants.URL_BC;
import static com.astrolome.Constants.URL_CHK_SETTINGS;
import static com.astrolome.Constants.URL_SAVE_SETTINGS;
import static com.astrolome.Constants.CMD_CHK_SETTINGS;
import static com.astrolome.Constants.CMD_SAVE_SETTINGS;
import static com.astrolome.Constants.CMD_BC;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends FragmentActivity implements ResponseManager{
	
	boolean is24hourFormat;
//	String birthLoc = null;
//	HashMap<String, String> editSettings;
	ArrayList<NameValuePair> chkSettingsParams;
	ArrayList<NameValuePair> chngSettingsParams;
	
	HashMap<String, String> chkSettingsMap;
	HashMap<String, String> chngSettingsMap;
	List<NameValuePair> planetParams;
	List<NameValuePair> houseParams;
	List<NameValuePair> aspectParams;
	
	PlanetManager pm;
	
	RequestManager manager;
	Context c;
	Activity activity;
	Calendar cal;
	
	String fullNameSet;
	String btBtnText;
	String dob;
	String tob;
	String bLoc;
	String emailSet;
	TextView birthTime;
	Button bdBtn;
	Button bTimebtn;
	Button cancel;
	Button done;
	AutoCompleteTextView autoCompView;
	EditText nameET;
	EditText emailET;
	User user;
	String cityIDSet;
	String cityNameSet;
	String birthIsTime;
	String bDate;
	String bTime;
	String date;
	String timeString;
	
	int hourBirth;
	int minBirth; 
	int yearBirth;
	int monthBirth;
	int dayBirth;
	int dobTimeStamp;
	int newDob;
	
	int dobTSNum;
	long tslong;
	String bdBtnText;
	
	String dobTSString;
	
	boolean isLocChange = false;
	boolean isBDateChange = false;
	boolean isCompleteBDChange = false;
	
	boolean populatedTime = false;
	boolean populatedDate = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_frag_layout);	
//        View v = inflater.inflate(R.layout.settings_frag_layout, null);
//        inflater=(LayoutInflater)getLayoutInflater();
//        c = getApplicationContext();
        activity = this;
        is24hourFormat = android.text.format.DateFormat.is24HourFormat(activity);
//	    Check Network
		 if (!isNetworkAvailable()){
	        	Toast.makeText(getApplicationContext(), "No Internet," +
	        			"Please make sure internet connection is available and try again", Toast.LENGTH_LONG).show();
	        }
        
         nameET = (EditText)findViewById(R.id.settingsName);
		 emailET = (EditText)findViewById(R.id.settingsEmail);
		 autoCompView = (AutoCompleteTextView)findViewById(R.id.settingsBirthLocTV);
		 bdBtn = (Button)findViewById(R.id.settingsBirthday);
		 bTimebtn = (Button)findViewById(R.id.settingsBirthTime);
		 cancel = (Button)findViewById(R.id.settingsCancel);
		 done = (Button)findViewById(R.id.settingsDone);
		 
		 user = new User();
		 checkSettings();
		 
		 nameET.setOnEditorActionListener(new OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					 in.hideSoftInputFromWindow(nameET.getWindowToken(), 0);
//					 bday.requestFocus();
					return false;
				}
			});
		 
		 bdBtn.setOnClickListener(new View.OnClickListener() {	 
				@Override
				public void onClick(View v) {
					Bundle args = new Bundle();
					args.putLong("setDate", tslong);
					args.putString("callingActivity", "SettingsActivity");
					args.putString("yearString", Integer.toString(yearBirth));
					args.putString("monthString", Integer.toString(monthBirth));
					args.putString("dayString", Integer.toString(dayBirth));
					DatePickerFragment date = new DatePickerFragment();	
					date.setArguments(args);
					date.show(getSupportFragmentManager(), "Date Picker");				    
				}
			});
		 
		 autoCompView.setAdapter(new AutoCompleteAdapter(this, R.layout.locations_list_item));
		 autoCompView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
			    cityIDSet = settings.getString("key_bl_id", null);
			    cityNameSet = settings.getString("key_bl_name", null);
			    
				InputMethodManager in = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			    in.hideSoftInputFromWindow(autoCompView.getWindowToken(), 0);
			    autoCompView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
//			    emailET.requestFocus();
			}
		});
		 
		 cancel.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				finish();			
			}
		});
		 
		 done.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fullNameSet = nameET.getText().toString();
				emailSet = emailET.getText().toString();
				bdBtnText = bdBtn.getText().toString();
				btBtnText = bTimebtn.getText().toString();
//				cal = Calendar.getInstance();
				boolean isNameChange = !(user.getFull_name().equals(fullNameSet));
				boolean isEmailChange = !(user.getEmail().equals(emailSet));
				isLocChange = !(user.getBirth_location_id().equals(cityIDSet));
				isBDateChange = !(bdBtnText.equals(bDate));
				boolean isBTimeChange = false;
				if (populatedTime){
					isBTimeChange = (btBtnText != bTime);
					}
				
				if(isNameChange || isEmailChange || isLocChange || isBDateChange || isBTimeChange){
					
				 if (fullNameSet.matches("")){
					 blinkText(nameET);
				 }
				 else if(bdBtnText.equals("")){
					 blinkText(bdBtn);
				 }
				
				 else if ((autoCompView.getText().equals("")) || (autoCompView.getText().equals(R.string.enterLoc))){
					    blinkText(autoCompView);
					}	 
				 else if(emailSet.matches("")){
					 blinkText(emailET);
				 }
				 else if(!isEmailValid(emailSet)){
					 Toast toast = Toast.makeText(getBaseContext(), "Please enter a valid email address", Toast.LENGTH_LONG);
					 toast.setGravity(Gravity.CENTER, 0, -100);
					 toast.show();
				 }
				 else if(birthIsTime.equals("0")){
					 if(!isBTimeChange){
					 birthIsTime = "0";
					 hourBirth = 12;
					 minBirth = 0;
					 }
					 else{
						 birthIsTime ="1";
						 hourBirth = user.getHour();
						 minBirth = user.getMin();
					 }
				 }
				 
				 if(!birthIsTime.equals("0")){
					 cal = Calendar.getInstance();
					 
					 if(!isBTimeChange){
						
//						Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
						tslong = (long)dobTSNum * 1000L;
						cal.setTimeInMillis(tslong);
						hourBirth = cal.get(Calendar.HOUR_OF_DAY);
						minBirth = cal.get(Calendar.MINUTE);
					 }
					 else{
						 birthIsTime = "1";
						 
							hourBirth = user.getHour();
							minBirth = user.getMin();
					 }
				 }
				 if(!isBDateChange){					
//						cal = Calendar.getInstance();
//						Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
						tslong = (long)dobTSNum * 1000L;
						cal.setTimeInMillis(tslong);
						yearBirth = cal.get(Calendar.YEAR);
						monthBirth = cal.get(Calendar.MONTH);
						dayBirth = cal.get(Calendar.DAY_OF_MONTH);
				 }
				
				 if(isBDateChange || isBTimeChange){
						isCompleteBDChange = true;						
//						tslong = (long) dobTSNum * 1000L;
//						cal.setTimeInMillis(tslong);
//						yearBirth = cal.get(Calendar.YEAR);
//						monthBirth = cal.get(Calendar.MONTH);
//						dayBirth = cal.get(Calendar.DAY_OF_MONTH);
				 }
				 dobTimeStamp = componentTimeToTimestamp(yearBirth, monthBirth, dayBirth, hourBirth, minBirth);
			
					SharedPreferences sp = getSharedPreferences(PREFS_NAME, 0);
					 SharedPreferences.Editor editor = sp.edit();
				      editor.putString("key_email", emailSet);
//				      editor.putString("key_bl_name", loc);
				      // Commit the edits!
				      editor.commit();
					user.setEmail(emailSet);
					updateSettings();
				}
				else{
					finish();
				}
			}
			private void blinkText(View view) {
				 
		        Animation anim = new AlphaAnimation(0.0f, 1.0f);
		        anim.setDuration(500); //You can manage the time of the blink with this parameter
		        anim.setStartOffset(20);
		        anim.setRepeatMode(Animation.REVERSE);
		        anim.setRepeatCount(2);
		        view.startAnimation(anim);			
			}
			
		});
	}
	
	@Override
	public void sendResponse(HashMap<String, String> result) {
		chkSettingsMap = new HashMap<String, String>();
		
		String cmd = result.get(CMD);
		String error = result.get(ERROR);
		if (error.equals("no")) {
			
			if (cmd.equals(CMD_CHK_SETTINGS)) {

				// String birthIsLoc = null;

				dobTSString = null;
				String resultArrayString = result.get("result");
				try {
					JSONObject resultObject = new JSONObject(resultArrayString);
					
					birthIsTime = resultObject.getString("birth_istime");
					
					dobTSString = resultObject.getString("dob");
					user.setCompleteDob(dobTSString);
					dobTSNum = Integer.parseInt(dobTSString);
					bDate = timestampToComponentDate(dobTSNum);
					bdBtn.setText(bDate);
					
					if(birthIsTime.toString().equals("0")){
						bTimebtn.setText("12:00");
					}
					else{
						bTime = timestampToComponentTime(dobTSNum);
						user.setTimeString(bTime);
						bTimebtn.setText(bTime);
					}
					bLoc = resultObject.getString("birth_location_name");
					autoCompView.setText(bLoc);
					user.setBirth_location_name(bLoc);
					cityIDSet = resultObject.getString("birth_location_id");
					user.setBirth_location_id(cityIDSet);

					// String bLocIDString =
					// resultObject.getString("birth_location_id");
					// user.setBirth_location_id(bLocIDString);
					String fName = resultObject.getString("full_name");
					user.setFull_name(fName);
					nameET.setText(fName);
					String emailResult = resultObject.getString("email");
					user.setEmail(emailResult);
					emailET.setText(emailResult);

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}
		if (cmd.equals(CMD_SAVE_SETTINGS)){
			if (error.equals("no")){
				if(isLocChange || isCompleteBDChange){
					
					getPlanets();
					getHouses();
					getAspects();
					Toast.makeText(activity, "Recalculating your Birth Chart", Toast.LENGTH_LONG).show();
				}
				else{
					Toast.makeText(activity, "Changes Saved", Toast.LENGTH_SHORT).show();
//					Intent intent = new Intent(activity, MainFragmentActivity.class);
//					intent.putExtra("calling-activity", "SettingsActivity");
//					startActivity(intent);
//					saveSettingsLocalDB(result);
					finish();
				}
			}
		} 
		if (cmd.equals(CMD_BC)){
			String resultArrayString = result.get("result");
			String gotWhat = user.getHouseID(resultArrayString);// to see if planet/house/aspect was selected

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
//				Intent intent = new Intent(activity, MainFragmentActivity.class);
//				intent.putExtra("calling-activity", "SettingsActivity");
//				startActivity(intent);
				finish();
			}
		}
	}
	
	
	private void updateSettings() {
		chngSettingsParams = new ArrayList<NameValuePair>();
		dob = Integer.toString(dobTimeStamp);
		chngSettingsParams.add(new BasicNameValuePair("apiver", "1"));
		chngSettingsParams.add(new BasicNameValuePair("apikey", API_KEY));
		chngSettingsParams.add(new BasicNameValuePair("cmd", CMD_SAVE_SETTINGS));
//		full_name - string
//		email - string
//		gender - int, see options in Data Types
//		dob - int, unix timestamp
//		birth_location_id - int, ID from geo_locations table
//		birth_istime - boolean, 'yes'/'no: if the dob contains the user time of birth
//		birth_isloc - boolean, 'yes'/'no': if the birth location id was set on the client's side
		chngSettingsParams.add(new BasicNameValuePair("full_name", fullNameSet));
		chngSettingsParams.add(new BasicNameValuePair("email", emailSet));
		chngSettingsParams.add(new BasicNameValuePair("dob", dob));
		chngSettingsParams.add(new BasicNameValuePair("birth_location_id", cityIDSet));
		chngSettingsParams.add(new BasicNameValuePair("gender", "2"));
		chngSettingsParams.add(new BasicNameValuePair("birth_istime", birthIsTime));
		chngSettingsParams.add(new BasicNameValuePair("birth_isloc", "1"));
		Log.i("CHANGE SETTINGS PARAMS", chngSettingsParams.toString());
		manager = new RequestManager(URL_SAVE_SETTINGS, this, chngSettingsParams);
		manager.delegate = this;
		manager.execute(chngSettingsParams);
	}
	

	private void checkSettings() {
		chkSettingsParams = new ArrayList<NameValuePair>();
		chkSettingsParams.add(new BasicNameValuePair("apiver", "1"));
		chkSettingsParams.add(new BasicNameValuePair("apikey", API_KEY));
		chkSettingsParams.add(new BasicNameValuePair("cmd", CMD_CHK_SETTINGS));
		
		manager = new RequestManager(URL_CHK_SETTINGS, activity, chkSettingsParams);
		manager.delegate = this;
		manager.execute(chkSettingsParams);
	}

	
	protected void getPlanets() {

		PlanetManager pm = new PlanetManager(activity, "2");
		planetParams = pm.getBCParams();

		RequestManager manager = new RequestManager(URL_BC, this, planetParams);
		manager.delegate = this;
		manager.execute(planetParams);
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

	private void saveSettingsLocalDB(HashMap<String, String> result) {
		
		boolean isSavedLocDB=false;
		
		try{
			SQLiteHelper saveEntry = new SQLiteHelper(activity);
			saveEntry.open();
			
			saveEntry.updateOneColumn(DB_TABLENAME_USER, null, null, KEY_NAME, nameET.getText().toString());
			saveEntry.updateOneColumn(DB_TABLENAME_USER, null, null, KEY_DOB, bdBtn.getText().toString());
			saveEntry.updateOneColumn(DB_TABLENAME_USER, null, null, KEY_BIRTH_LOCAL_ID, autoCompView.getText().toString());
			saveEntry.updateOneColumn(DB_TABLENAME_USER, null, null, KEY_EMAIL, emailET.getText().toString());
			saveEntry.close();
			isSavedLocDB = true;
		}catch(Exception e){
			Log.i("SQLITEDB", "not uploaded into local db", e);
			isSavedLocDB = false;
		}finally{
			if(isSavedLocDB){
				Toast.makeText(activity, "Changes Saved", Toast.LENGTH_LONG).show();
			}
		}
	
	}
	public boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    return activeNetworkInfo != null;
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
		date = sdf.format(cal.getTime());
		bdBtn.setText(date);
		user.setDobString(date);
		return (int) (cal.getTimeInMillis() / 1000L);
	}
	
	public void showTimePickerDialog(View v){
		Bundle args = new Bundle();
		args.putString("callingActivity", "SettingsActivity");
		args.putLong("setTime", tslong);
		args.putString("hourString", Integer.toString(hourBirth));
		args.putString("minString", Integer.toString(minBirth));
		DialogFragment timeFragment = new TimePickerFragment();
		timeFragment.setArguments(args);
		timeFragment.show(activity.getFragmentManager(), "Time Picker");
		}
	
	protected void populateSetTime(Date time, int hour, int min){
		populatedTime = true;
		hourBirth = hour;
		minBirth = min;
		SimpleDateFormat sdf;
		if (!is24hourFormat){		
			sdf = new SimpleDateFormat("h:mm a");      
		}
		else{
			sdf = new SimpleDateFormat("H:mm"); 
		}
		timeString = sdf.format(time);
        user.setHour(hour);
        user.setMin(min);
		bTimebtn.setText(timeString);		
	}
	
	private int componentTimeToTimestamp(int yearBirth2, int monthBirth2,
			int dayBirth2, int hourBirth2, int minBirth2) {
		cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, yearBirth2);
		cal.set(Calendar.MONTH, monthBirth2);
		cal.set(Calendar.DAY_OF_MONTH, (dayBirth2));
		cal.set(Calendar.HOUR_OF_DAY, hourBirth2);
		cal.set(Calendar.MINUTE, minBirth2);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		dobTimeStamp = (int) (cal.getTimeInMillis() / 1000L);
		
		return dobTimeStamp;
	}
	
	private String timestampToComponentDate(int timeStamp) {
		int ts = timeStamp;
		cal = Calendar.getInstance();
//		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		tslong = (long)ts * 1000L;
		cal.setTimeInMillis(tslong);
		SimpleDateFormat formatter;
		if (!is24hourFormat){
			formatter = new SimpleDateFormat("MMM d, yyyy");
		}
		else{
			formatter = new SimpleDateFormat("d MMM, yyyy");
		}	
		String dateConverted =  formatter.format(cal.getTime());
		
		return dateConverted;
	}
	
	private String timestampToComponentTime(int timeStamp) {
		int ts = timeStamp;
		cal = Calendar.getInstance();
		tslong = (long)ts * 1000L;
		cal.setTimeInMillis(tslong);
		SimpleDateFormat formatter;
		if (!is24hourFormat){		
			formatter = new SimpleDateFormat("h:mm a");      
		}
		else{
			formatter = new SimpleDateFormat("H:mm"); 
		}
		String timeConverted =  formatter.format(cal.getTime());
		return timeConverted;
	}
}
