package com.cx.shiro.handlers;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.cx.shiro.services.ShiroService;
@Controller
@RequestMapping("/shiro")
public class ShiroHandler {
	@Autowired
	private ShiroService shiroService;
	@RequestMapping("/testShiroAnnotation")
	public String testShiroAnnotation() {
		shiroService.testMethod();
		return "redirect:/list.jsp";
	}
	@RequestMapping("/login")
	public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
		 //获取当前的Subject。调用SecurityUtils.getSubject();
        Subject currentUser = SecurityUtils.getSubject();
        
        if (!currentUser.isAuthenticated()) {
        	//把用户名和密码封装为UsernamePasswordToken对象
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            //rememberme
            token.setRememberMe(true);
            try {
            	System.out.println(token.hashCode());
            	//执行登陆
                currentUser.login(token);
                //若没有指定的账户则Shiro将会抛出UnknownAccountException异常
            } 
            // ... catch more exceptions here (maybe custom ones specific to your application?
            //所有认证时异常的父类
            catch (AuthenticationException ae) {
                System.out.println("登陆失败"+ae.getMessage());
            }
        }
		return "redirect:/list.jsp";
	}
}
