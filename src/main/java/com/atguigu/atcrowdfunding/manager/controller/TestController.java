/**
 * 
 */
package com.atguigu.atcrowdfunding.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.atguigu.atcrowdfunding.manager.service.TestService;

/**
 * @Description: 
 * @Author: chenyihong
 * @Date: 2019年1月9日
 */
@Controller
public class TestController {

	@Autowired
	private TestService testService;
	
	@RequestMapping("/test")
	public String test(String name){
		System.out.println("TestController");
		testService.insert();
		return "success";
	}
	// TODO day02 part10 7:15
	
}
