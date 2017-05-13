package com.cx.shiro.realms;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;

public class ShiroRealms extends AuthenticatingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//1.把AuthenticationToken转换为UsernamePasswordToken
		UsernamePasswordToken uptoken = (UsernamePasswordToken) token;
		//2.从UsernamePasswordToken中来获取username
		String username = uptoken.getUsername();
		//3.调用数据库的方法，从数据库中查询username对应的记录
		System.out.println("从数据库中获取username:"+username+"所对应的用户信息");
		//4.若用户不存在则可以抛出UnknownAccountException异常
		if("unknown".equals(username)) {
			throw new UnknownAccountException("用户不存在");
		}
		//5.根据用户信息的情况决定是否需要抛出其他AuthenticationException异常
		if("monster".equals(username)) {
			throw new LockedAccountException("用户被锁定");
		}
		//6.根据用户的情况构建AuthenticationInfo对象并返回,通常使用的实现类为SimpleAuthenticationInfo
		//一下信息是从数据库中获取的
		//1)principal:认证的实体信息。可以使username，也可以是数据表对应的实体类对象
		Object principal = username;
		//2)credentials:从数据库中获取的密码
		Object credentials = "123456";
		//3)realmName:当前realm对应的name。调用父类的getName()方法即可
		String realmName = getName();
		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principal, credentials, realmName);
		return info;
	}

}
