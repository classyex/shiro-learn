package cn.yex.shiro.chapter3;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class PermissionTest extends BaseTest {

	private static final String USER_VIEW = "user:view";
	private static final String USER_DELETE = "user:delete";
	private static final String USER_UPDATE = "user:update";
	private static final String USER_CREATE = "user:create";
	private static final String PASSWORD = "123";
	private static final String USERNAME = "zhang";
	private static final String CONFIG = "classpath:shiro-permission.ini";
	
	@Test
	public void isPermitted() throws Exception {
		Subject subject = login(CONFIG, USERNAME, PASSWORD);
		assertTrue(subject.isPermitted(USER_CREATE));
		assertTrue(subject.isPermittedAll(USER_UPDATE, USER_DELETE));
		assertFalse(subject.isPermitted(USER_VIEW));
	}
	
	@Test(expected = AuthorizationException.class)
	public void checkPermission() throws Exception {
		Subject subject = login(CONFIG, USERNAME, PASSWORD);
		subject.checkPermission(USER_CREATE);
		subject.checkPermissions(USER_DELETE, USER_UPDATE);
		subject.checkPermission(USER_VIEW);
	}
	
	@Test
	public void wildcardPermission() throws Exception {
		Subject subject = login(CONFIG, "li", PASSWORD);
		subject.checkPermission("system:user:update");
		subject.checkPermissions("system:user:update", "system:user:delete");
		subject.checkPermissions("system:user:update,delete");
		subject.checkPermissions("system:user:create,delete,update,view");
		subject.checkPermissions("system:user:*");
		subject.checkPermissions("system:user");
		subject.checkPermissions("system:user:view:1");
	}
	
}
