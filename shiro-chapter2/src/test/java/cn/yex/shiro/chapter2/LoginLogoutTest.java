package cn.yex.shiro.chapter2;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LoginLogoutTest {
	
	private static final String INVALID_PASSWORD = "invalidPassword";
	private static final String INVALID_USERNAME = "invalidUsername";
	private static final String password = "123";
	private static final String username = "zhang";
	private Subject subject;
	
	@Before
	public void setUp(){
		//获取securityManager工厂，使用shiro.ini初始化securityManager
		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		//将初始化好的securityManager注入SecurityUtils
		SecurityUtils.setSecurityManager(factory.getInstance());
		subject = SecurityUtils.getSubject();
	}
	
	@Test
	public void testValidUsernameAndPassword() {
		//创建用户名/密码凭据
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			//用户登陆
			subject.login(token);
		} catch (AuthenticationException e) {
			fail("用户名或者密码错误");
		}
		//断言用户登陆成功
		assertTrue(subject.isAuthenticated());
		//退出登陆
		subject.logout();
		//断言用户退出成功
		assertFalse(subject.isAuthenticated());
	}

	@Test
	public void testInvalidUserName() throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken(INVALID_USERNAME, password);
		try {
			subject.login(token);
		} catch (UnknownAccountException e) {
			assertTrue(true);
		} catch (AuthenticationException e) {
			fail("没有捕捉到用户名出错");
		}
		assertFalse(subject.isAuthenticated());
	}
	
	@Test
	public void testInvalidPassword() throws Exception {
		UsernamePasswordToken token = new UsernamePasswordToken(username, INVALID_PASSWORD);
		try {
			subject.login(token);
		} catch (IncorrectCredentialsException e) {
			assertTrue(true);
		} catch (AuthenticationException e) {
			fail("没有捕捉到密码出错");
		}
		assertFalse(subject.isAuthenticated());
	}
	
	@After
	public void tearDown() throws Exception {
		ThreadContext.unbindSubject();// 退出时请解除绑定Subject到线程 否则对下次测试造成影响
	}
	
}
