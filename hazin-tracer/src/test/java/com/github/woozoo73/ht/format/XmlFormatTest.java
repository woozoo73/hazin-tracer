/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.woozoo73.ht.format;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.woozoo73.ht.AbstractTest;
import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.XmlUtils;

public class XmlFormatTest extends AbstractTest {

	private XmlFormat xmlFormat;

	@Before
	public void setUp() throws Exception {
		xmlFormat = new XmlFormat();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFormat() {
		Invocation foo = makeDummyInvocation("foo", "fooMethod");
		Invocation bar = makeDummyInvocation("bar", "barMethod");
		foo.add(bar);

		String xsd = XmlUtils.getContent(xsdPath);
		String xml = xmlFormat.format(foo);

		logger.debug("\n" + xml);

		assertThat(XmlUtils.validate(xsd, xml), is(true));
	}

}
