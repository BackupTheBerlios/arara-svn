package net.indrix.arara.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static void main(String[] args) {
		junit.textui.TestRunner.run(AllTests.suite());
	}

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for net.indrix.arara.test");
		//$JUnit-BEGIN$
		suite.addTestSuite(SchemaTest.class);
		suite.addTestSuite(CryptTest.class);
		//$JUnit-END$
		return suite;
	}

}
