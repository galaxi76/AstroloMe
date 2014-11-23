package com.astrolome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class AutoCompRequestManager extends AsyncTask<ArrayList<NameValuePair>, Void, ArrayList<String>>{

	
	public ResponseManager delegate = null;
	List<NameValuePair> postParams = new ArrayList<NameValuePair>();

	String URL;

	Activity activity;
	ArrayList<String> resultList;
	
	public AutoCompRequestManager(String url, Activity sendingActivity, List<NameValuePair> params){	
		super();
		URL=url;
        postParams=params;
        activity = sendingActivity;
     
	}
	
	@Override
	protected ArrayList<String> doInBackground(ArrayList<NameValuePair>...listParams) {
		
		try{
	
			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			DefaultHttpClient client = new DefaultHttpClient();

			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
			socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			registry.register(new Scheme("https", socketFactory, 443));
			SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
			DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

			// Set verifier     
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);

			// send http request				
			HttpPost httpPost = new HttpPost(URL);

			httpPost.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
			 Log.i("urlParameters", listParams.toString());

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
				Log.i("resultString from " + activity, resultString.toString());
					
				// Transform the String into a JSONObject
				JSONObject jsonObjRecv = null;
					
				try {
					
					jsonObjRecv = new JSONObject(resultString.toString());
	
					JSONArray resultsJsonArray = jsonObjRecv.getJSONArray("result");

				        // Extract the Place descriptions from the results
				        resultList = new ArrayList<String>(resultsJsonArray.length());
				        for (int i = 0; i < resultsJsonArray.length(); i++) {
				        	
//				            resultList.add(resultsJsonArray.getJSONObject(i).getString("id"));
				            resultList.add(resultsJsonArray.getJSONObject(i).getString("location_string"));
				        }
						
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	return resultList;
	}
	
	
	@Override
	protected void onPostExecute(ArrayList<String> cityResultArray) {
//		adapter.asyncComplete(cityResultArray);
		super.onPostExecute(cityResultArray);
	}
 
		
	private static String convertStreamToString(InputStream is) {
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
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
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}


}

