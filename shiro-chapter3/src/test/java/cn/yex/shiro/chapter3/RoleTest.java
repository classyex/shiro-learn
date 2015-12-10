package cn.yex.shiro.chapter3;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

public class RoleTest extends BaseTest {

	private static final String ROLE1 = "role1";
	private static final String ROLE2 = "role2";
	private static final String ROLE3 = "role3";
	private static final String PASSWORD = "123";
	private static final String USERNAME = "zhang";
	private static final String CONFIG = "classpath:shiro-role.ini";

	@Test
	public void hasRole() throws Exception {
		Subject subject = login(CONFIG, USERNAME, PASSWORD);
		//判断拥有角色：role1
        assertTrue(subject.hasRole(ROLE1));
        //判断拥有角色：role1 and role2
        assertTrue(subject.hasAllRoles(Arrays.asList(ROLE1, ROLE2)));
        //判断拥有角色：role1 and role2 and !role3
        boolean[] result = subject.hasRoles(Arrays.asList(ROLE1, ROLE2, ROLE3));
        assertEquals(true, result[0]);
        assertEquals(true, result[1]);
        assertEquals(false, result[2]);
	}
	
	@Test(expected = AuthorizationException.class)
	public void checkRole() throws Exception {
		Subject subject = login(CONFIG, USERNAME, PASSWORD);
		subject.checkRole(ROLE1);
		subject.checkRoles(ROLE2, ROLE3);
	}
	
}
