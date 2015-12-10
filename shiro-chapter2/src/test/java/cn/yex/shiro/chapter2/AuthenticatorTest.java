package cn.yex.shiro.chapter2;

import static org.junit.Assert.assertEquals;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.apache.shiro.util.ThreadContext;
import org.junit.After;
import org.junit.Test;

public class AuthenticatorTest {

	@Test
	public void allSuccessfulStrategyWithSuccess() throws Exception {
		Subject subject = login("classpath:shiro-authenticator-all-success.ini");
		assertPrincipalsSize(subject, 2);
	}

	@Test(expected = UnknownAccountException.class)
	public void allSuccessfulStrategyWithFail() throws Exception {
		login("classpath:shiro-authenticator-all-fail.ini");
	}
	
	@Test
	public void atLeastOneSuccessfulStrategyWithSuccess() throws Exception {
		Subject subject = login("classpath:shiro-authenticator-atLeastOne-success.ini");
		assertPrincipalsSize(subject, 2);
	}
	
	@Test
	public void firstOneSuccessfulStrategyWithSuccess() throws Exception {
		Subject subject = login("classpath:shiro-authenticator-first-success.ini");
		assertPrincipalsSize(subject, 1);
	}
	
	@Test
	public void onlyOneStrategyStrategyWithSuccess() throws Exception {
		Subject subject = login("classpath:shiro-authenticator-onlyone-success.ini");
		assertPrincipalsSize(subject, 1);
	}
	
	@Test
	public void atLeasTwoStrategyWithSuccess() throws Exception {
		Subject subject = login("classpath:shiro-authenticator-atLeastTwo-success.ini");
		assertPrincipalsSize(subject, 1);
	}
	
	private Subject login(String config) throws AuthenticationException {
		Factory<SecurityManager> factory = new IniSecurityManagerFactory(config);
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);
		Subject subject = SecurityUtils.getSubject();
		AuthenticationToken token = new UsernamePasswordToken("zhang", "123");
		try {
			subject.login(token);
		} catch (AuthenticationException e) {
			throw e;
		}
		return subject;
	}
	
	private void assertPrincipalsSize(Subject subject, int size) {
		PrincipalCollection principalCollection = subject.getPrincipals();
		assertEquals(size, principalCollection.asList().size());
	}
	
	@After
    public void tearDown() throws Exception {
        ThreadContext.unbindSubject();//退出时请解除绑定Subject到线程 否则对下次测试造成影响
    }
	
}
