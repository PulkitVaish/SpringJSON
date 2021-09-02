package com.example.springJSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.example.springJSON.model.Users;

@SpringBootApplication
public class SpringJsonApplication {
	private static final Logger log = LoggerFactory.getLogger(SpringJsonApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringJsonApplication.class, args);
	}
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {
			Users user = restTemplate.getForObject(
					"https://randomuser.me/api/?results=1&inc=name&noinfo", Users.class);
			log.info(user.toString());
		};
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
 