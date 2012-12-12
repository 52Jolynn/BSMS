/*
 * @author: Laud
 * @version: 1.0.0
 * copyright powersix
 */

package com.p6.testcase;

import junit.framework.TestCase;
import org.junit.Test;
import static com.p6.toolkit.CheckTools.*;

public class TestCheckTools extends TestCase {

	@Test
	public void testIsEmailValid() {
		assertTrue(isEmailValid("eventer@163.com"));
		assertTrue(isEmailValid("su_re-@163.com"));
		assertFalse(isEmailValid("eventer"));
		assertFalse(isEmailValid("-h@163.com"));
	}
	
	@Test
	public void testIsMobileValid() {
		assertTrue(isMobileValid("13828452853"));
		assertFalse(isMobileValid("12254665"));
	}
	
	@Test
	public void testIsPhoneValid() {
		assertTrue(isPhoneValid("0203933161"));
		assertFalse(isPhoneValid("020393316"));
	}
	
	public static void main(String[] args) {
		junit.textui.TestRunner.run(TestCheckTools.class);
	}
}
