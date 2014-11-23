package com.astrolome;

import static com.astrolome.Constants.API_KEY;
import static com.astrolome.Constants.PREFS_NAME;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

public class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable{

	private ArrayList<String> resultList;
	
	ArrayList<NameValuePair> resultListwithID;
	
	ArrayList<NameValuePair> geoSearchParams;

	String geoUrl = "https://mamic.astrolome.com/_api3.sandbox/geo/location/search";
	 
	String cityID;
	String locString;
	User user = new User();
	Context mContext;
	
	
//	CityIDInterface cityIDInterface;
	
    public AutoCompleteAdapter(Context context, int textViewResourceId) {
    	super(context, textViewResourceId);
    	this.mContext = context;
    }
    

    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public String getItem(int index) {
    	setItem(index);
        return resultList.get(index);
    }

    private void setItem(int index) {
    	
    	cityID = resultListwithID.get(index).getName();
    	locString = resultListwithID.get(index).getValue();
    	setCity(cityID, locString);
//    	user.setBirth_location_id(cityID);
//    	user.setBirth_location_name(locString);
		Log.i("SET THE CITY!!!!", "ID: " + cityID + ", City: " + locString);
//		RegisterActivity.setcityInfo(cityID);
//		user.setBirth_location_id(cityID);
	}

	private void setCity(String cityID2, String loc) {
   	 SharedPreferences settings = mContext.getSharedPreferences(PREFS_NAME, 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putString("key_bl_id", cityID2);
      editor.putString("key_bl_name", loc);
      // Commit the edits!
      editor.commit();
      user.setBirth_location_id(cityID2);
	}


	@Override
    public Filter getFilter() {
        Filter filter = new Filter() {
        	FilterResults filterResults;
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
            	
            	filterResults  = new FilterResults();
                
                if (constraint != null) {
                    // Retrieve the autocomplete results.
                	String input = constraint.toString();
                	if(constraint.length()>=3){
               
                		resultList = autocomplete(input);                  		
////            	  // Assign the data to the FilterResults
                		filterResults.values = resultList;
                		filterResults.count = resultList.size();   	

                	}
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                if (results != null && results.count > 0) {
                    notifyDataSetChanged();
                }
                else {
                    notifyDataSetInvalidated();
                }
            }
            
            };
        return filter;
    }

	private ArrayList<String> autocomplete(String input) {
	    ArrayList<String> resultList = null;

	  geoSearchParams = new ArrayList<NameValuePair>();
      geoSearchParams.add(new BasicNameValuePair("apiver", "1"));
      geoSearchParams.add(new BasicNameValuePair("apikey", API_KEY));
      geoSearchParams.add(new BasicNameValuePair("cmd","geo.location.search"));
      geoSearchParams.add(new BasicNameValuePair("name",
     		 input));
      
      HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

		DefaultHttpClient client = new DefaultHttpClient();

		SchemeRegistry registry = new SchemeRegistry();
		SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
		socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
		registry.register(new Scheme("https", socketFactory, 443));
		SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
		DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

		try{
		// Set verifier     
		HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

		// send http request				
		HttpPost httpPost = new HttpPost(geoUrl);

		httpPost.setEntity(new UrlEncodedFormEntity(geoSearchParams, "utf-8"));
		 Log.i("urlParameters", geoSearchParams.toString());

		 HttpResponse response = httpClient.execute(httpPost);	

			
		HttpEntity entity= response.getEntity();
		if (entity != null) {
			// Read the content stream
			InputStream instream = entity.getContent();
			// convert content stream to a String
			String resultString = convertStreamToString(instream);
			instream.close();
			resultString = resultString.substring(0,
					resultString.length() - 1); // remove wrapping "[" and
													// "]"
			Log.i("resultString from " + this, resultString.toString());
				
			// Transform the String into a JSONObject
			
		

			try {
	    	JSONObject jsonObjRecv = null;
	    
	        // Create a JSON object hierarchy from the results
	    	jsonObjRecv = new JSONObject(resultString.toString());
	        JSONArray predsJsonArray = jsonObjRecv.getJSONArray("result");

	        // Extract the Place descriptions from the results
	        resultList = new ArrayList<String>(predsJsonArray.length());
	        
	        resultListwithID = new ArrayList<NameValuePair>();
	        
	        for (int i = 0; i < predsJsonArray.length(); i++) {
	        	cityID = predsJsonArray.getJSONObject(i).getString("id");
	        	locString = predsJsonArray.getJSONObject(i).getString("location_string");
	        	resultListwithID.add(new BasicNameValuePair(cityID, locString));
//	        	resultList.add(predsJsonArray.getJSONObject(i).getString("id"));
	            resultList.add(predsJsonArray.getJSONObject(i).getString("location_string"));
	        }
	        
			}catch (JSONException e) {
		        Log.e("ERROR JSON RESULTS", "Cannot process JSON results", e);
		    }
	    } 
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	    return resultList;
	}


private String convertStreamToString(InputStream instream) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
	StringBuilder sb = new StringBuilder();

	String line = null;
	try {
		while ((line = reader.readLine()) != null) {
			sb.append(line + "\n");
		}
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		try {
			instream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	return sb.toString();
}
public ArrayList<NameValuePair> getItemArray(){
	
	return resultListwithID;
}
}