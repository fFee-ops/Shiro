package com.sl.spring.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sl.spring.realm.UserRealm;

@Configuration
public class ShiroConfig {
/**
 * 创建Realm
 */
	@Bean(value = "getRealm")
	public  UserRealm getRealm() {
		
		return new UserRealm();
	}
	
	/**
	 * 创建DefaultWebSecurityManager
	 */
	
	@Bean(value = "security")
	public DefaultWebSecurityManager gDefaultWebSecurityManager(@Qualifier("getRealm") UserRealm realm) {
		DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
		//关联Realm
		securityManager.setRealm(realm);
		return securityManager;
	}
	
	/**
	 * 创建ShiroFilterFactoryBean
	 * @return
	 */
	@Bean
	public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("security")DefaultWebSecurityManager securityManager) {
		ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
	
		//设置安全管理器
		factoryBean.setSecurityManager(securityManager);
		
		//添加shiro的内置过滤器
		/**
		 * Shiro内置过滤器，可以实现权限相关的拦截器
		 *    常用的过滤器：
		 *       anon: 无需认证（登录）可以访问
		 *       authc: 必须认证才可以访问
		 *       user: 如果使用rememberMe的功能可以直接访问
		 *       perms： 该资源必须得到资源权限才可以访问
		 *       role: 该资源必须得到角色权限才可以访问
		 */
		Map<String, String> map=new LinkedHashMap<String, String>();
		//要过滤什么请求
		map.put("/tologin", "anon");
		map.put("/*", "authc");
		
		//修改被拦截后的跳转页面
		factoryBean.setLoginUrl("/login");
		
		factoryBean.setFilterChainDefinitionMap(map);
		return factoryBean;
	}
}
