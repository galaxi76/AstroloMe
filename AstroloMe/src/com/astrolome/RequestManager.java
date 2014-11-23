package com.astrolome;

import static com.astrolome.Constants.URL_LOGIN;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

public class RequestManager extends AsyncTask<List<NameValuePair>, Integer, HashMap<String, String>>{
	
	public ResponseManager delegate = null;
	List<NameValuePair> postParams = new ArrayList<NameValuePair>();
	private static CookieStore cookieStore; 
	HttpContext localContext;
	Object mLock = new Object();
	static Header[] headers = null;
	String URL;
	private String cookies;
	List<Cookie> cookiesList;
		
	Activity activity;
	static HashMap<String, String> resultMap;
	ProgressDialog dialog;
	
	
	
	public RequestManager(String url, Activity sendingActivity, List<NameValuePair> params){	
		super();
		URL=url;
        postParams=params;
        activity = sendingActivity;
     
	}
	
	@Override
	protected void onPreExecute() {
		
////		if(URL == "https://mamic.astrolome.com/_api3.sandbox/user/register"){
//		if ((activity instanceof SplashScreenActivity)|| (activity instanceof RegisterActivity)){
//			dialog = new ProgressDialog(activity);
////			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			dialog.show();
//			dialog.setContentView(R.layout.progress_dialog);
////	    	dialog.setMessage(activity.getString(R.string.calculating));
//		}
		
	}

//	@Override
//	protected void onProgressUpdate(Integer... values) {
//		// TODO Auto-generated method stub
//		super.onProgressUpdate(values);
//	}

	@Override
	protected HashMap<String, String> doInBackground(List<NameValuePair>...listParams) {
		
		
		try{	
			DefaultHttpClient client = new DefaultHttpClient();
			HostnameVerifier hostnameVerifier = org.apache.http.conn.ssl.SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;

			SchemeRegistry registry = new SchemeRegistry();
			SSLSocketFactory socketFactory = SSLSocketFactory.getSocketFactory();
			socketFactory.setHostnameVerifier((X509HostnameVerifier) hostnameVerifier);
			registry.register(new Scheme("https", socketFactory, 443));

			SingleClientConnManager mgr = new SingleClientConnManager(client.getParams(), registry);
	
			DefaultHttpClient httpClient = new DefaultHttpClient(mgr, client.getParams());

			// Set verifier     
			HttpsURLConnection.setDefaultHostnameVerifier(hostnameVerifier);
			
			HttpPost httpPost = new HttpPost(URL);
			
			
			 localContext = new BasicHttpContext();
//			 if(activity instanceof RegisterActivity || activity instanceof LoginManager){
				 if(URL.equals(URL_LOGIN)){
					 setMyCookieStore(null);
				 }
//			 }
			 localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
			  System.out.println("Executing request " + httpPost.getRequestLine());	

				httpPost.setEntity(new UrlEncodedFormEntity(postParams, "utf-8"));
		
			 Log.i("urlParameters", listParams.toString());	 

			 HttpResponse response = httpClient.execute(httpPost, localContext);	

			 headers = response.getAllHeaders();

			 System.out.println("\nSending 'POST' request to URL : " + URL);
			System.out.println("Post parameters : " + httpPost.getEntity());
			System.out.println("Response Code : "
				+ response.getStatusLine().getStatusCode());
				
			HttpEntity entity= response.getEntity();
			if (entity != null) {
				
				InputStream instream = entity.getContent();
				String resultString = convertStreamToString(instream);
				instream.close();
				resultString = resultString.substring(0,
						resultString.length() - 1); // remove wrapping "[" and
														// "]"
				Log.i("resultString from " + activity, resultString.toString());
			
				JSONObject jsonObjRecv = null;
					
				try {
					
					jsonObjRecv = new JSONObject(resultString.toString());
					
					List<Cookie> cookiesList = ((AbstractHttpClient) httpClient)
							.getCookieStore().getCookies();
					if(cookiesList != null)
					{
//						String cookieString = null;
//					    for(Cookie cookie : cookiesList)
//					    {
//					        cookieString = cookie.getName() + "=" + cookie.getValue() + "; domain=" + cookie.getDomain();                        
//					        Log.i("cookie string", cookieString);
//					        if (cookieString.equalsIgnoreCase("Set-Cookie"));
//					    }
						if (URL.equals(URL_LOGIN)) {
							headers = response.getAllHeaders();
							setCookies(headers.toString());
							setCookiesList(cookiesList);
							setMyCookieStore(httpClient.getCookieStore());
						}
					}

					toMap(jsonObjRecv);
						
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
	return resultMap;
	}
	
	
	@Override
	protected void onPostExecute(HashMap<String, String> result) {
//		if(URL=="https://mamic.astrolome.com/_api3.sandbox/user/register"){
		
//		if((activity instanceof SplashScreenActivity)|| (activity instanceof RegisterActivity)){
//			dialog.dismiss();
//		}
		delegate.sendResponse(result);
		super.onPostExecute(result);
	}
	
	public List<Cookie> getCookiesList() {
		return cookiesList;
	}

	public void setCookiesList(List<Cookie> cookiesList) {
		this.cookiesList = cookiesList;
	}
	
	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public CookieStore getMyCookieStore() {
		Log.i("got the damn cookies to" + URL, cookieStore.toString());
		return cookieStore;
		
	}

	public void setMyCookieStore(CookieStore cookieStore) {
		RequestManager.cookieStore = cookieStore;
	
	}

	public static Map<String, String> toMap(JSONObject object)
	    {
	        resultMap = new HashMap<String, String>();

	        @SuppressWarnings("unchecked")
			Iterator<String> keysItr = object.keys();
	        while(keysItr.hasNext())
	        {
	            String key = keysItr.next();
	            Object value = null;
				try {
					value = object.getString(key);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

	            if(value instanceof JSONArray)
	            {
	                try {
						value = toList((JSONArray) value);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            else if(value instanceof JSONObject)
	            {
	            value = toMap((JSONObject) value);
	            }
			
	            resultMap.put(key, (String) value);
	        }
	        return resultMap;
	    }

	    public static List<Object> toList(JSONArray array) throws JSONException
	    {
	        List<Object> list = new ArrayList<Object>();
	        for(int i = 0; i < array.length(); i++)
	        {
	            list.add(array.get(i));
	        }
	        return list;
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

