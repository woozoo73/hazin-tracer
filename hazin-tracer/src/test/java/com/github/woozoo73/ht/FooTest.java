package com.github.woozoo73.ht;

import org.junit.Before;
import org.junit.Test;

public class FooTest {

	private Foo foo;

	@Before
	public void setUp() {
		foo = new Foo();
	}

	@Test
	public void test() {
		foo.sayHello("hazin");
	}

}
