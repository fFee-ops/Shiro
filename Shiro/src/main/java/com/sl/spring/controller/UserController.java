package com.sl.spring.controller;

import java.lang.ProcessBuilder.Redirect;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
private final String PREFIX = "user/";
	/**
	 * 测试方法
	 */
	@RequestMapping("/test")
	public String test(){
		return "index";
	}
	@RequestMapping("/add")
	public String add(){
		return PREFIX+"add";
	}
	@RequestMapping("/update")
	public String update(){
		return PREFIX+"update";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	
	/**
	 * 登录逻辑处理
	 */
	@RequestMapping("/tologin")
	public String tologin(String name,String password,Model model){
		//使用Shiro编写认证操作
		Subject subject = SecurityUtils.getSubject();
		//封装用户数据
		UsernamePasswordToken token = new UsernamePasswordToken(name, password);
		
		//执行登录方法
		try {
			subject.login(token);
		//没有捕获到异常就是登录成功	
			return "redirect:/test";//重定向到/test请求从而跳转到index.html
		} catch (UnknownAccountException e) {
//			  登陆失败:用户名不存在
			model.addAttribute("msg", "用户名不存在！");
			return "login";//因为存放有数据，重定向会丢失数据，所以直接返回页面
			
		}catch (IncorrectCredentialsException e) {
//			 登陆失败:密码错误
			model.addAttribute("msg", "密码错误！");
			return "login";
			
		}
	
	}
	
	
	@RequestMapping("/unAuth")
	public String unAuth(){
		return "unAuth";
	}
	
	
	
}