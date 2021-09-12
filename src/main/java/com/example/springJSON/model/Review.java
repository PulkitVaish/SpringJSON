package com.example.springJSON.model;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Review {
	
	private JSONArray data = new JSONArray();
	private JSONArray results;
	private JSONArray reviewData;
	
	public Review(JSONArray results, JSONArray reviewData) {
		super();
		this.results = results;
		this.reviewData = reviewData;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getData() {
		for(int i=0;i<results.size();i++) {
	    	  Random r = new Random();
	    	  float ratings = r.nextFloat() * (5);
	    	  int randomHelpfulCount = ThreadLocalRandom.current().nextInt(0, 500 + 1);
	    	  boolean verified = r.nextBoolean();
		      
		      /** Transforming into the required format of data **/
		      JSONObject response = new JSONObject();
		      JSONObject props = (JSONObject)results.get(i);
		      JSONObject revprops = (JSONObject)reviewData.get(i);
		      
		      response.put("title", revprops.get("title"));
		      response.put("review", revprops.get("body"));
		      
		      JSONObject nameObj = (JSONObject)props.get("name");
		      String name = nameObj.get("first").toString()+" "+nameObj.get("last").toString();
		      response.put("name", name);
		      
		      JSONObject imgObj = (JSONObject)props.get("picture");
		      String imgURL = imgObj.get("thumbnail").toString();
		      response.put("avatar", imgURL);
		      
		      JSONObject loginObj = (JSONObject)props.get("login");
		      String reviewId = loginObj.get("md5").toString();
		      response.put("rid", reviewId);
		      
		      JSONObject dateObj = (JSONObject)props.get("registered");
		      String isoDate = dateObj.get("date").toString();
		      response.put("date", isoDate);
		      
		      response.put("helpful",randomHelpfulCount);
		      response.put("rating", ratings);
		      response.put("verified", verified);
		      
		      data.add(response);
	      }
	      
	      return  data;
	}
}


