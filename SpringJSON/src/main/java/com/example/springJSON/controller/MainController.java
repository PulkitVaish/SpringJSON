package com.example.springJSON.controller;

import org.springframework.stereotype.Controller;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Arrays;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController
public class MainController {
	
	@Autowired
	   RestTemplate restTemplate;
	
	   /*
	    * Endpoint to get random comments
	    * Data being populated using asdfast API and randomuserapi
	    * (To be sped up with use of Multi-threading soon...)
	   */
	
	   @CrossOrigin
	   @RequestMapping(value = "/comments")
	   public JSONArray getComments(HttpServletRequest req) {
		   try {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      String total = req.getParameter("num");
	      if(total==null) total = "1";//Param not set
	      
	      /** Getting data from randomuserapi **/
	      String userString =  restTemplate.exchange("https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo", HttpMethod.GET, entity, String.class).getBody();
	      Object userObject=JSONValue.parse(userString);  
	      JSONObject userData = (JSONObject) userObject;
	      JSONArray results = (JSONArray) userData.get("results");
	      
	      JSONArray data = new JSONArray();
	      
	      for(int i=0;i<results.size();i++) {
	    	  /** Getting data from asdfast **/
	    	  int randomCommentLength = ThreadLocalRandom.current().nextInt(20, 40 + 1);
	    	  int randomLikeCount = ThreadLocalRandom.current().nextInt(0, 999 + 1);
		      String commentString =  restTemplate.exchange("https://asdfast.beobit.net/api/?length="+ Integer.toString(randomCommentLength)+"&type=word", HttpMethod.GET, entity, String.class).getBody();
		      Object commentObject=JSONValue.parse(commentString);  
		      JSONObject commentData = (JSONObject) commentObject;
		      String comment = commentData.get("text").toString();
		      
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
	      		return  data;
      
	   	}catch (Exception e) {
			JSONArray errorList = new JSONArray();
			JSONObject error = new JSONObject();
			error.put("Error", e.getMessage());
			errorList.add(error);
			return errorList;
		}
	  }
	   
	   /*
	    * Endpoint to get random reviews
	    * Data being populated using JSONPlaceholderapi and randomuserapi
	   */
	   
	   @CrossOrigin
	   @RequestMapping(value = "/reviews")
	   public JSONArray getReviews(HttpServletRequest req) {
		   try {
			   HttpHeaders headers = new HttpHeaders();
			      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			      HttpEntity <String> entity = new HttpEntity<String>(headers);
			      
			      String total = req.getParameter("num");
			      if(total==null) total = "1";//Parameter not set
			      if(Integer.parseInt(total)>100) total="100";//Limitation in the API used
			      
			      /** Getting data from randomuserapi **/
			      String userString =  restTemplate.exchange("https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo", HttpMethod.GET, entity, String.class).getBody();
			      Object userObject=JSONValue.parse(userString);  
			      JSONObject userData = (JSONObject) userObject;
			      JSONArray results = (JSONArray) userData.get("results");
			      
			      /** Getting data from JSONPlaceholderapi **/
			      String reviewString = restTemplate.exchange("https://jsonplaceholder.typicode.com/posts?_start=0&_limit="+total, HttpMethod.GET, entity, String.class).getBody();
			      Object reviewObject=JSONValue.parse(reviewString);  
			      JSONArray reviews = (JSONArray) reviewObject;

			      JSONArray data = new JSONArray();
			      
			      for(int i=0;i<results.size();i++) {
			    	  Random r = new Random();
			    	  float ratings = r.nextFloat() * (5);
			    	  int randomHelpfulCount = ThreadLocalRandom.current().nextInt(0, 500 + 1);
			    	  boolean verified = r.nextBoolean();
				      
				      /** Transforming into the required format of data **/
				      JSONObject response = new JSONObject();
				      JSONObject props = (JSONObject)results.get(i);
				      JSONObject revprops = (JSONObject)reviews.get(i);
				      
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
			      
		   }catch (Exception e) {
			JSONArray errorList = new JSONArray();
			JSONObject error = new JSONObject();
			error.put("Error", e.getMessage());
			errorList.add(error);
			return errorList;
		}
	      
	  }
	   
}
