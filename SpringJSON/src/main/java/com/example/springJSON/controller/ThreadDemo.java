package com.example.springJSON.controller;

import java.util.Arrays;

import org.json.simple.JSONValue;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

class ThreadDemo implements Runnable {

	private String url;
	private Object data;
	
	public ThreadDemo(String url) {
		super();
		this.url = url;
	}

	RestTemplate restTemplate = new RestTemplate();
	
	public Object getDataObject() {
		return data;
	}
	
	@Override
	public void run() {
		HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity <String> entity = new HttpEntity<String>(headers);
		System.out.println("Thread :"+Thread.currentThread().getName()+" Started");
		String responseString =  restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
		data=JSONValue.parse(responseString);  
	}
	
}
