package com.astrolome;

import java.util.UUID;

import org.json.JSONObject;

public interface IDataObject {
	
	
	public void setID(UUID id);
	public boolean InsertIntoLocalDB();
	public void addToCache();
	public JSONObject getJSONObject();
	public void InsertIntoUnsyncData();
	

}
