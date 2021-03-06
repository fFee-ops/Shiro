package com.sl.spring.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sl.spring.realm.UserRealm;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

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
		//放行/tologin请求
		map.put("/tologin", "anon");
		
		
		//授权过滤器
		//注意，当前授权拦截后，Shiro会自动跳转到一个提示未授权的页面
		map.put("/add", "perms[user:add]");
		map.put("/update", "perms[user:update]");
		
		//要过滤什么请求
		map.put("/*", "authc");//这个一定要写最后，不然没办法执行授权逻辑（过滤链是有顺序的）
		
		//修改被拦截后的跳转页面
		factoryBean.setLoginUrl("/login");
		
		
		//设置未授权提示页面
		factoryBean.setUnauthorizedUrl("/unAuth");
		
		
		factoryBean.setFilterChainDefinitionMap(map);
		return factoryBean;
	}
	
	/**
	 * 配置ShiroDialect，用于thymeleaf和shiro标签配合使用
	 */
	@Bean
	public ShiroDialect getShiroDialect(){
		return new ShiroDialect();
	}

}
