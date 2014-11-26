package com.astrolome;

public class Constants {
	
	public Constants(){}
	
	public static final String DB_TABLENAME_USER = "Table_User";
	public static final String DB_TABLENAME_PLANETS = "Table_Planets";
	public static final String DB_TABLENAME_HOUSES = "Table_Houses";
	public static final String DB_TABLENAME_ASPECTS = "Table_Aspects";
	//NEW
	public static final String API_KEY = "72965fa00022-dbdb-3e11-2738-55Gb9dF7";
	public static final String PREFS_NAME = "birth time and loc";
	
	public static final String URL_LOGIN = "https://mamic.astrolome.com/_api3.sandbox/user/login";
	public static final String URL_REG = "https://mamic.astrolome.com/_api3.sandbox/user/register";
	public static final String URL_BC = "https://mamic.astrolome.com/_api3.sandbox/birth/chart";
	public static final String URL_CHK_SETTINGS = "https://mamic.astrolome.com/_api3.sandbox/user/settings";
	public static final String URL_SAVE_SETTINGS = "https://mamic.astrolome.com/_api3.sandbox/user/settings/details";
	
	public static final String CMD_REG = "user.register";
	public static final String CMD_LOGIN = "user.login";
	public static final String CMD_BC = "birth.chart";
	public static final String CMD_CHK_SETTINGS = "user.settings";
	public static final String CMD_SAVE_SETTINGS = "user.settings.details";
	//OLD
//	public static final String API_KEY = "72965fa00022-dbdb-3e11-2738-15786b97";

	public static final String TIMESTAMP = "ts";
	public static final String CMD = "cmd";
	public static final String STATUS = "status";
	public static final String IS_LOGIN = "is_login";
	public static final String ERROR = "err";
	public static final String UID = "uid";
	public static final String UTD = "utd";
	public static final String UT = "ut";
	public static final String LOGTIME = "logtime";
	public static final String FIRM_ID = "firm_id";
	public static final String LANGUAGE = "lang";
	public static final String CITY_ID = "id";
	public static final String LOCATION_STRING = "location_string";
	
	public static final String USER_LOC = "user_loc";
	public static final String USER_TIME = "user_time";
	
	
	public final static int SPLASH_DISPLAY_LENGTH = 2000;
	
	//User
	public final static String KEY_ROW_ID = "_rowID";
	public final static String KEY_UID = "_id";
	public final static String KEY_DEVICE_UID = "_deviceuid";
	public final static String KEY_DEVICE_MODEL = "_deviceModel";
	public final static String KEY_DEVICE_VERSION = "_deviceVersion";
	public final static String KEY_IS_ACTIVE = "_isActive";
	public final static String KEY_NAME = "_fullName";
	public final static String KEY_DOB = "_dob";
	public final static String KEY_DATE_REG = "_dateReg";
	public final static String KEY_BIRTH_LOCAL_ID = "_birthLocationId";
	public final static String KEY_EMAIL = "_email";		
	public final static String KEY_GENDER = "_gender";
	public final static String KEY_ACTIONS_KEY = "_actionsKey";
	public final static String KEY_PASSWORD = "_password";
	
	
	
	public static final int DATE_DIALOG_ID = 999;
	public static final String REGISTER = "RegisterActivity";
	
//	Planets
//	the "rising_zodiac_id" value states the Rising Zodiac ID for SUN-specific birth events.
	//Planet Values
			public final static String KEY_PLANET_ID = "_planetID";
			public final static String KEY_HOUSE_ID = "_houseID";
			public final static String KEY_ZODIAC_ID = "_zodiacID";
			public final static String KEY_DEGREES = "_degrees";
			public final static String KEY_CONTENT = "_content";
			public final static String KEY_FULL_ANGLE = "_fullAngle";
			public final static String KEY_READ_DATE = "_readDate";
			public final static String KEY_MINUTE_DEGREES = "_minuteDegrees";
			public final static String KEY_RISING_ID = "_risingID";
			public final static String KEY_SECONDS_DEGREES = "_secondsDegrees";
			
			public final static String KEY_HOUSE_NAME = "_houseName";
			
			public final static String KEY_PLANET_ONE = "_planetOne";
			public final static String KEY_PLANET_TWO = "_planetTwo";
			public final static String KEY_TRANSIT_ID = "_transitID";
			
			
	public static final String TYPE_ID ="type_id";
	public static final String PLANET_ID = "planet_id";
	public static final String ZODIAC_ID = "zodiac_id";
	public static final String HOUSE_ID = "house_id";
	public static final String DEGREES = "degrees";
	public static final String MINUTES = "minutes";
	public static final String SECONDS = "seconds";
	public static final String READ_DATE = "read_date";
	public static final String FULL_ANGLE = "full_angle";
	public static final String CONTENT = "content";
	public static final String RISING_ZODIAC_id = "rising_zodiac_id";
	//HOUSES
	public static final String HOUSE_NAME = "house_name";
	//ASPECTS
	public static final String PLANET_ID_ONE = "planet_id1";
	public static final String PLANET_ID_TWO = "planet_id2";
	public static final String TRANSIT_ID = "transit_aspect_id";

	
	public static final String SUN_ID = "13";
	public static final String MOON_ID = "14";
	public static final String MERC_ID = "15";
	public static final String VENUS_ID = "16";
	public static final String MARS_ID = "17";
	public static final String JUP_ID = "18";
	public static final String SAT_ID = "19";
	public static final String URANUS_ID = "20";
	public static final String NEPT_ID = "21";
	public static final String PLUTO_ID = "22";
	public static final String NODE_ID = "23";
	public static final String MIDHEAVEN_ID = "24";
	public static final String SNODE_ID = "25";
	public static final String ASCENDANT_ID = "26";
	
	public static final String ARIES_ID = "1";
	public static final String TAURUS_ID = "2";
	public static final String GEMINI_ID = "3";
	public static final String CANCER_ID = "4";
	public static final String LEO_ID = "5";
	public static final String VIRGO_ID = "6";
	public static final String LIBRA_ID = "7";
	public static final String SCORPIO_ID = "8";
	public static final String SAG_ID = "9";
	public static final String CAPRI_ID = "10";
	public static final String AQUA_ID = "11";
	public static final String PISCES_ID = "12";

}
