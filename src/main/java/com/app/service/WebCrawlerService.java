package com.app.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public interface WebCrawlerService {
	
	public JsonObject crawl();

	JsonObject getJsonData(JsonArray jsonArray, double sumOfPrice);	

}
