package com.sl.spring.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.sl.spring.entity.User;
import com.sl.spring.service.UserService;

public class UserRealm  extends AuthorizingRealm{
/*
 * 执行授权逻辑
 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行授权逻辑");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				
				
		//获取当前登录的用户
		Subject subject = SecurityUtils.getSubject();	
		User user=(User) subject.getPrincipal();
		
		User dUser=service.findById(user.getId());
		System.out.println(dUser.toString());
		info.addStringPermission(dUser.getPerms());
				return info;
	}

	/*
	 * 执行认证逻辑
	 */
	@Autowired
	private UserService service;
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println(" 执行认证逻辑");
		
		//编写判断逻辑，来判断用户名和密码是否正确
		UsernamePasswordToken token2=(UsernamePasswordToken) token;//从配置类传来的Token
		
		User user = service.findByName(token2.getUsername());
		
		
		
		//1、判断用户名
		if (user==null) {
			//用户名不存在
			return null;//Shiro底层会抛出UnknownAccountException
			
		}
		
		//2、判断密码
		return new SimpleAuthenticationInfo(user,user.getPassword(),"");
	}

}
