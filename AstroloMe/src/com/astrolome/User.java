package com.astrolome;

import static com.astrolome.Constants.HOUSE_ID;
import static com.astrolome.Constants.PLANET_ID;
import static com.astrolome.Constants.TRANSIT_ID;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.MessageQueue.IdleHandler;
import android.provider.Settings.Secure;

public class User implements IDataObject{
	
	
	private String deviceuid; //string, udid or imsi
	private String deviceModel;
	private String deviceVersion;
	private Long dob;
	private int dobInt;
	private String email; //optional
	private String full_name;
	private int gender;  //optional
	private  String birth_location_id;//(optional), ID from geo_locations table
	private String dobString;
	private String dateString;
	public String timeString;
	private  String birth_location_name;
	private int dobHour;
	private int dobMin;

//	devicemodel:
//	private String deviceuid;	
//		deviceversion:	
//		dob:	
//		email:	
//		full_name:	
//		gender:
//			full_name - string,
//			gender - (optional) int, see options in Data Types
//			email - (optional) string,
//			dob - unix timestamp, can be set with 00:00:00 for the time,
//			deviceuid - string, udid or imsi,
//			birth_location_id - (optional) int, ID from geo_locations table
//			devicemodel - string, from the device's details. values like: iphone, ipad, ipod...
//			deviceversion - string, from the device's details
			
			//new user id or error description
			//for new user, also the 'actions_key' will be returned
	private UUID RealPUID;
				
					
					
//	private long TmpLocalID;
//	private long PN;//personal number
//	private String Description;
//	private UUID DeviceID;
//	private long DateTimeCreated;
//	
//	//non database members
//	private String VisualID;
//
//	private Event LastPulse;
//	private Event LastBreath;
//	private Event LastBloodPressure;
//	private String CaregiverName;
//
//	private boolean isHot;
//	private boolean isFrontView = true;//Remembers if the the patient "card" was displayed to the front or back.
//	private boolean isSide1 = true;
//	//TODO: temporary patient images

	
//	private String layoutString;//for local uses, remember the specific layout in the monitor screen.
//								//format: "plot1,plot2,plot3,first measurement....sixth measurement" (all values separated by commas)
//	
//	private ArrayList<Event> Events; //lists of patien't events.
//	private ArrayList<Event> EventsBySeverity; //lists of patien't events.
//	private ArrayList<PatientThresholds> CommonPatientThresholds;
//	
	public User(){
		super();
	}
	public User(String cityIDnum, String cityName){
		super();
		this.birth_location_name = cityName;
		this.birth_location_id = cityIDnum;
	}

	@Override
	public JSONObject getJSONObject() {
		

		JSONObject obj = new JSONObject();
//    	
//    	try {
//    		
//    		//obj.put("PUID", PUID.toString()); 		
//    		obj.put("RealPUID", RealPUID != null ? RealPUID : null);
//    		obj.put("TmpLocalID", TmpLocalID);
//    		
//    		if(PN != 0)
//    			obj.put("PN", PN);
//    		
//    		obj.put("Description", Description != null ? Description : null);
//    		obj.put("DeviceID", DeviceID);	
//    		obj.put("DateTimeCreated", new Timestamp(DateTimeCreated));
//    		obj.put("NoaID", NoaID != null ? NoaID : null);
//	
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
    	return obj;
	}
	

//TODO: implement similar??
//	@Override
//	public boolean InsertIntoLocalDB() {
//		return LocalDAL.GetInstance().InsertPatient(this);
//	}
//
//
//	@Override
//	public void addToCache() {
//		CommonCachingLists.GetInstance().AddPatient(this, -1);
//	}
//
//	@Override
//	public void InsertIntoUnsyncData() {
//		// TODO Auto-generated method stub
//		
//	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public String getDeviceVersion() {
		return deviceVersion;
	}

	public void setDeviceVersion(String deviceVersion) {
		this.deviceVersion = deviceVersion;
	}
	public String getDobString() {
		return dateString;
	}

	public void setDobString(String dateString) {
		this.dateString = dateString;
	}
	
	public String getCompleteDob() {
		return dobString;
	}
	
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	
	public String getTimeStringb() {
		return timeString;
	}

	public void setCompleteDob(String dobString) {
		this.dobString = dobString;
	}
	public Long getDob() {
		return dob;
	}

	public void setDob(Long dob) {
		this.dob = dob;
	}
	
	public int getDobInt() {
		return dobInt;
	}

	public void setDobInt(int dobInt) {
		this.dobInt = dobInt;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public int getHour() {
		return dobHour;
	}

	public void setHour(int dobHour) {
		this.dobHour = dobHour;
	}
	
	public int getMin() {
		return dobMin;
	}

	public void setMin(int dobMin) {
		this.dobMin = dobMin;
	}

	public String getDeviceuid() {
		
		return deviceuid;
	}

	public void setDeviceuid(String deviceuid) {
		this.deviceuid = deviceuid;
	}

	@Override
	public void setID(UUID id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean InsertIntoLocalDB() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void addToCache() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void InsertIntoUnsyncData() {
		// TODO Auto-generated method stub
		
	}

	public String getBirth_location_id() {
		return birth_location_id;
	}

	public void setBirth_location_id(String birth_l_id) {
		birth_location_id = birth_l_id;
	}
	public String getBirth_location_name() {
		return birth_location_name;
	}

	public void setBirth_location_name(String birth_l_name) {
		birth_location_name = birth_l_name;
	}
	
	public String getHouseID(String resultArray) {
		String gotWhat = null;
		HashMap<String, String> firstResultMap;
		try {
			JSONArray arr = new JSONArray(resultArray);

			firstResultMap = new HashMap<String, String>();
			JSONObject jObject = arr.getJSONObject(0);
			Iterator<?> keys = jObject.keys();

			while (keys.hasNext()) {
				String key = (String) keys.next();
				String value = null;
				try {
					value = jObject.getString(key);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				firstResultMap.put(key, value);
				
			}
			if(firstResultMap.containsKey(PLANET_ID)){
				gotWhat = PLANET_ID;
			}
			else if(firstResultMap.containsKey(HOUSE_ID)){
				gotWhat = HOUSE_ID;
			}
			else{
				gotWhat = TRANSIT_ID;
			}			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return gotWhat;
	}

}
