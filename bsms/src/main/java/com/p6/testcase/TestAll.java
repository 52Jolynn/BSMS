/*
 * @author: Laud
 * @version: 1.0.0
 * copyrihght powersix
 */

package com.p6.testcase;

import junit.framework.*;

public class TestAll {

	public static Test suite() {
		TestSuite testSuite = new TestSuite("Test CheckTools");
		testSuite.addTestSuite(TestCheckTools.class);
		return testSuite;
	}
}
