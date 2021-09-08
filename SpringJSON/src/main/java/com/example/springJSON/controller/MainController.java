package com.example.springJSON.controller;

import java.util.concurrent.ThreadLocalRandom;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Arrays;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
		      
		      /*************************/
		      String apiURL1 ="https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo";
		      String apiURL2 ="https://baconipsum.com/api/?type=meat-and-filler&paras="+total+"";
		      
		      ThreadDemo obj1 = new ThreadDemo(apiURL1);
			   ThreadDemo obj2 = new ThreadDemo(apiURL2);
			   Thread t1 = new Thread(obj1);
			   Thread t2 = new Thread(obj2);
			   t1.start();
			   t2.start();
			   t1.join();
			   t2.join();
//			   System.out.println(obj1.getDataObject());
//			   System.out.println(obj2.getDataObject());
		      /** Getting data from randomuserapi **/
//		      String userString =  restTemplate.exchange("https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo", HttpMethod.GET, entity, String.class).getBody();
//		      Object userObject=JSONValue.parse(userString);  
		      JSONObject userData = (JSONObject) obj1.getDataObject();
		      JSONArray results = (JSONArray) userData.get("results");

		      /** Getting data from baconipsum.com **/
//		      String commentString =  restTemplate.exchange("https://baconipsum.com/api/?type=meat-and-filler&paras="+total+"", HttpMethod.GET, entity, String.class).getBody();
//		      JSONObject commentObject = (JSONObject) obj2.getDataObject();
		      JSONArray commentData = (JSONArray) obj2.getDataObject();
		      
		      JSONArray data = new JSONArray();
		      
		      
		      for(int i=0;i<results.size();i++) {
//		    	  int randomCommentLength = ThreadLocalRandom.current().nextInt(80, 140 + 1);
		    	  int randomLikeCount = ThreadLocalRandom.current().nextInt(0, 999 + 1);
			      String comment = commentData.get(i).toString();
//			      String comment = eachComment.substring(0,randomCommentLength);
			      
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
