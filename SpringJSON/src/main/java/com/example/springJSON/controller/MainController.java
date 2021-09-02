package com.example.springJSON.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/data")
	@ResponseBody
	public String Main() {
		return "Works in Papa's lappy...";
	}
	@RequestMapping("/")
	@ResponseBody
	public String home() {
		return "Home";
	}
}
