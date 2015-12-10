package com.github.classyex;

import static org.junit.Assert.*;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.CodecSupport;
import org.apache.shiro.codec.Hex;
import org.junit.Before;
import org.junit.Test;

public class CodecAndCryptoTest {

	private static final String UTF_8 = "utf-8";
	private String orgStr;

	@Before
	public void setUp(){
		orgStr = "hello";
	}
	
	@Test
	public void base64() throws Exception {
		String encodeString = Base64.encodeToString(orgStr.getBytes());
		String decodeString = Base64.decodeToString(encodeString);
		assertStringEquals(decodeString);
	}
	
	@Test
	public void hex() throws Exception {
		String encode = Hex.encodeToString(orgStr.getBytes());
		String decode = new String(Hex.decode(encode));
		assertStringEquals(decode);
	}

	@Test
	public void codeSupport() throws Exception {
		byte[] bytes = CodecSupport.toBytes(orgStr, UTF_8);
		String string = CodecSupport.toString(bytes, UTF_8);
		assertStringEquals(string);
	}
	
	private void assertStringEquals(String decode) {
		assertEquals(orgStr, decode);
	}
	
}
