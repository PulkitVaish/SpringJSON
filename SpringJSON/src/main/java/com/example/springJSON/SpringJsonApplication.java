package com.example.springJSON;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class SpringJsonApplication {
	

	public static void main(String[] args) {
		SpringApplication.run(SpringJsonApplication.class, args);
	}
	 @Bean
	   public RestTemplate getRestTemplate() {
	      return new RestTemplate();
	   }
}
/*
 * Random api : https://randomuser.me/api/?results=${req.param}&inc=name,picture&noinfo
 * JSON placeholder : https://jsonplaceholder.typicode.com/comments?_start=${randomNum}&_limit=${randomNum}
 * NAME+PICTURE:randomapi
 * EMAIL+BODY+ID:jsonplaceholderapi
 * const randomNum = Math.random()+${req.param}
 * 
 * 
 * */
 