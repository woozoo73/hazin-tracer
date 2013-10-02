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
		ClassFilter filter = new ClassFilter();

		when(joinPoint.getSignature()).thenReturn(signature);
		when(signature.getDeclaringType()).thenReturn(declaringType);

		filter.setPattern("*");
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getName());
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getPackage().getName() + ".*");
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getPackage().getName() + ".S*");
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getPackage().getName() + ".*trin*");
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getPackage().getName() + ".T*");
		assertThat(filter.accept(joinPoint), is(false));

		filter.setPattern(declaringType.getPackage().getName() + ".Strin*");
		assertThat(filter.accept(joinPoint), is(true));

		filter.setPattern(declaringType.getPackage().getName() + ".String*");
		assertThat(filter.accept(joinPoint), is(true));
	}

}
