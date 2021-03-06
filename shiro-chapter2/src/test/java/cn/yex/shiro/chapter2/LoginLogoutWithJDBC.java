package cn.yex.shiro.chapter2;

import static org.junit.Assert.*;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class LoginLogoutWithJDBC {

	@Test
	public void jdbcRealm() throws Exception {
		
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			fail("登陆失败");
		}
		
		assertTrue(subject.isAuthenticated());
		
		subject.logout();
		assertFalse(subject.isAuthenticated());
		
	}
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
	
}
