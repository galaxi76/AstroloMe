package com.astrolome;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	
	protected Utils(){	
	}
	
	 Context context;
	 
	 public Utils(Context context){
	       this.context = context;
	  }
	
	public boolean isNetworkAvailable(){
		
		ConnectivityManager connMgr = (ConnectivityManager)	context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = connMgr.getActiveNetworkInfo();

		if (activeInfo != null && activeInfo.isConnected()) {
			return true;
		} else {
			return false;
		}
	}
}
