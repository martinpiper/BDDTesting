package com.somethingtest.gluecode;

import junit.framework.TestCase;

public class TestAnnotateTest extends TestCase {

	public void testSomeFunction() throws Exception {
		TestAnnotate foo = new TestAnnotate();
		assertEquals(foo.SomeFunction(), 1234);
	}

	public void testWhenICreateThings() throws Exception {

	}
}