package com.github.classyex.chapter4;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

public class ConfigurationCreateTest {

	@Test
	public void configuration() throws Exception {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-config.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		subject.login(token);
		
		assertTrue(subject.isAuthenticated());
	}
	
}
