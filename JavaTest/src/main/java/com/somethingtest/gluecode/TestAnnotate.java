package com.somethingtest.gluecode;

import wooity.api.java.OtherAnnotation;
import wooity.api.java.WooityFoo;

/**
 */
public class TestAnnotate {
	@WooityFoo({"It's annotated","another"})
	@OtherAnnotation({"Other string","another"})
	public int SomeFunction() {
		return 1234;
	}

	@WooityFoo("^I create (\\d+) (.*) things")
//	public void whenICreateThings(int count, String theThing) throws Exception {
	public void whenICreateThings(String count, String theThing) throws Exception {
		System.out.println("*************** Executing whenICreateThings!!!");
		if (theThing.equals("foo")) {
		} else if (theThing.equals("bar")) {
		} else {
			throw new Exception("invalid things");
		}
	}
}
