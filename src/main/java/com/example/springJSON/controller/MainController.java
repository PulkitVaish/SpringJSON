package com.example.springJSON.controller;

import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.management.ManagementFactory;

import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;


import com.example.springJSON.model.Comment;
import com.example.springJSON.model.Review;

@RestController
public class MainController {		
		
	   /*
	    * Endpoint to get random comments
	   */
	   @SuppressWarnings("unchecked")
	   @CrossOrigin
	   @RequestMapping(value = "/comments")
	   public JSONArray getComments(HttpServletRequest req) {
		   System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
		   if(ManagementFactory.getThreadMXBean().getThreadCount()>35) {
//			   System.out.println("Overload!!");
			   JSONArray errorOverload = new JSONArray();
			   errorOverload.add("Server overloaded with requests, Please try again later!");
			   return errorOverload;
		   }
		   try {
		      
		      String total = req.getParameter("num");
		      if(total==null) total = "1";//Param not set
		      
		      String apiURL1 ="https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo";
		      String apiURL2 ="https://baconipsum.com/api/?type=meat-and-filler&paras="+total+"";
		      
		      MainService obj1 = new MainService(apiURL1);
		      MainService obj2 = new MainService(apiURL2);
		      Thread t1 = new Thread(obj1);
		      Thread t2 = new Thread(obj2);
		      t1.start();
		      t2.start();
		      t1.join();
		      t2.join();
		      
		      /** Getting data from randomuserapi **/
		      JSONObject userData = (JSONObject) obj1.getDataObject();
		      JSONArray results = (JSONArray) userData.get("results");

		      /** Getting data from baconipsum.com **/
		      JSONArray commentData = (JSONArray) obj2.getDataObject();
		      
		      JSONArray responseData = new JSONArray();
		      Comment commentObj = new Comment(results,commentData);
	      	  responseData = commentObj.getData();
		      return  responseData; 
      
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
	   */
	   @SuppressWarnings("unchecked")
	   @CrossOrigin
	   @RequestMapping(value = "/reviews")
	   public JSONArray getReviews(HttpServletRequest req) {
//		   System.out.println(ManagementFactory.getThreadMXBean().getThreadCount());
		   if(ManagementFactory.getThreadMXBean().getThreadCount()>30) {
//			   System.out.println("Overload!!");
			   JSONArray errorOverload = new JSONArray();
			   errorOverload.add("Server overloaded with requests, Please try again later!");
			   return errorOverload;
		   }
		   try {

			      String total = req.getParameter("num");
			      if(total==null) total = "1";//Parameter not set
			      if(Integer.parseInt(total)>100) total="100";//Limitation in the API used
			      
			      String apiURL1 = "https://randomuser.me/api/?results="+total+"&inc=name,login,picture,registered&noinfo";
			      String apiURL2 = "https://jsonplaceholder.typicode.com/posts?_start=0&_limit="+total+"";
			      
			      MainService obj1 = new MainService(apiURL1);
			      MainService obj2 = new MainService(apiURL2);
			      Thread t1 = new Thread(obj1);
			      Thread t2 = new Thread(obj2);
			      t1.start();
			      t2.start();
			      t1.join();
			      t2.join();
			      
			      /** Getting data from randomuserapi **/
			      JSONObject userData = (JSONObject) obj1.getDataObject();
			      JSONArray results = (JSONArray) userData.get("results");
			      
			      /** Getting data from JSONPlaceholderAPI **/ 
			      JSONArray reviews = (JSONArray) obj2.getDataObject();

			      JSONArray responseData = new JSONArray();
			      Review reviewObj = new Review(results,reviews);
			      responseData = reviewObj.getData();
			      return  responseData;
			      
		   }catch (Exception e) {
			JSONArray errorList = new JSONArray();
			JSONObject error = new JSONObject();
			error.put("Error", e.getMessage());
			errorList.add(error);
			return errorList;
		   }   
	  }
}
