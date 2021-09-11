package com.example.springJSON.model;

import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Comment {
	
	private JSONArray data = new JSONArray();
	private JSONArray results;
	private JSONArray commentData;
	
	public Comment(JSONArray results, JSONArray commentData) {
		super();
		this.results = results;
		this.commentData = commentData;
	}
	
	@SuppressWarnings("unchecked")
	public JSONArray getData() {
		for(int i=0;i<results.size();i++) {
	    	  int randomLikeCount = ThreadLocalRandom.current().nextInt(0, 999 + 1);
		      String comment = commentData.get(i).toString();
		      
		      /** Transforming into the required format of data **/
		      JSONObject response = new JSONObject();
		      JSONObject props = (JSONObject)results.get(i); 
		      
		      JSONObject nameObj = (JSONObject)props.get("name");
		      String name = nameObj.get("first").toString()+" "+nameObj.get("last").toString();
		      response.put("name", name);
		      
		      JSONObject imgObj = (JSONObject)props.get("picture");
		      String imgURL = imgObj.get("thumbnail").toString();
		      response.put("avatar", imgURL);
		      
		      JSONObject loginObj = (JSONObject)props.get("login");
		      String uid = loginObj.get("uuid").toString();
		      String cid = loginObj.get("md5").toString();
		      response.put("uid", uid);
		      response.put("cid", cid);
		      
		      JSONObject dateObj = (JSONObject)props.get("registered");
		      String isoDate = dateObj.get("date").toString();
		      response.put("date", isoDate);
		      
		      response.put("comment",comment);
		      response.put("likes",randomLikeCount);
		      
		      data.add(response);
		     
	      	}	      
		return data;
	}
}
