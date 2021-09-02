package com.example.springJSON.controller;

import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerService {
	@Autowired
	   RestTemplate restTemplate;

	   @RequestMapping(value = "/gg")
	   public JSONObject getProductList() {
	      HttpHeaders headers = new HttpHeaders();
	      headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	      HttpEntity <String> entity = new HttpEntity<String>(headers);
	      
	      String str1 =  restTemplate.exchange("https://randomuser.me/api/?results=1&inc=name&noinfo", HttpMethod.GET, entity, String.class).getBody();
	      Object obj1=JSONValue.parse(str1);  
	      JSONObject jsonObject1 = (JSONObject) obj1;
	      System.out.println(jsonObject1.toJSONString());
	      String str2 =  restTemplate.exchange("https://jsonplaceholder.typicode.com/comments?_start=0&_limit=5", HttpMethod.GET, entity, String.class).getBody();
	      Object obj2=JSONValue.parse(str2);  
	      JSONArray jsonObject2 = (JSONArray) obj2;
	      System.out.println(jsonObject2.toJSONString());
	      JSONObject jsonObject = new JSONObject();
	      jsonObject.put("First", jsonObject1);
	      jsonObject.put("Second", jsonObject2);
	      return  jsonObject;
	   }
}
