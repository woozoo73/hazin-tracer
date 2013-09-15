package com.github.woozoo73.ht.filter;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ClassFilterTest {

	@Mock
	private JoinPoint joinPoint;

	@Mock
	private Signature signature;

	private Class<?> declaringType;

	@Before
	public void setUp() throws Exception {
		declaringType = String.class;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAccept() {
		ClassFilter filter = null;

		when(joinPoint.getSignature()).thenReturn(signature);
		when(signature.getDeclaringType()).thenReturn(declaringType);

		System.setProperty("ht.classfilter.pattern", "*");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getName());
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".*");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".S*");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".*trin*");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".T*");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(false));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".Strin?");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(true));

		System.setProperty("ht.classfilter.pattern", declaringType.getPackage().getName() + ".Strin??");
		filter = new ClassFilter();
		assertThat(filter.accept(joinPoint), is(false));
	}

}
