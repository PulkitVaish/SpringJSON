package com.example.springJSON.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/data")
	@ResponseBody
	public String Main() {
		return "Testing...";
	}
}
