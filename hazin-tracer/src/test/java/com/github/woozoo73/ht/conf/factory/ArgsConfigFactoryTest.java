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
package com.github.woozoo73.ht.conf.factory;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.github.woozoo73.ht.callback.InvocationCallback;
import com.github.woozoo73.ht.callback.WriterCallback;
import com.github.woozoo73.ht.conf.Config;
import com.github.woozoo73.ht.filter.ClassFilter;
import com.github.woozoo73.ht.filter.Filter;
import com.github.woozoo73.ht.format.Format;
import com.github.woozoo73.ht.format.TextFormat;
import com.github.woozoo73.ht.format.XmlFormat;
import com.github.woozoo73.ht.writer.ConsoleWriter;
import com.github.woozoo73.ht.writer.LogWriter;
import com.github.woozoo73.ht.writer.Writer;

public class ArgsConfigFactoryTest {

	private ConfigFactory factory;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetConfigInvocationCallback() {
		factory = new ArgsConfigFactory();
		Config config = factory.getConfig();

		assertThat(config, notNullValue());

		InvocationCallback invocationCallback = config.getInvocationCallback();

		assertThat(invocationCallback, instanceOf(WriterCallback.class));

		WriterCallback writerCallback = (WriterCallback) invocationCallback;
		Writer writer = writerCallback.getWriter();

		assertThat(writer, instanceOf(LogWriter.class));

		Format format = writer.getFormat();

		assertThat(format, instanceOf(TextFormat.class));
	}

	@Test
	public void testWriter() {
		System.setProperty("ht.writer", ConsoleWriter.class.getName());
		System.setProperty("ht.format", XmlFormat.class.getName());

		factory = new ArgsConfigFactory();
		Config config = factory.getConfig();

		assertThat(config, notNullValue());

		InvocationCallback invocationCallback = config.getInvocationCallback();

		assertThat(invocationCallback, instanceOf(WriterCallback.class));

		WriterCallback writerCallback = (WriterCallback) invocationCallback;
		Writer writer = writerCallback.getWriter();

		assertThat(writer, instanceOf(ConsoleWriter.class));

		Format format = writer.getFormat();

		assertThat(format, instanceOf(XmlFormat.class));
	}

	@Test
	public void testGetConfigFilter() {
		factory = new ArgsConfigFactory();
		Config config = factory.getConfig();

		Filter filter = config.getFilter();

		assertThat(filter, instanceOf(ClassFilter.class));
	}

	@Test
	public void testGetConfigFilterPatternDefault() {
		factory = new ArgsConfigFactory();
		Config config = factory.getConfig();

		Filter filter = config.getFilter();
		ClassFilter classFilter = (ClassFilter) filter;
		String pattern = classFilter.getPattern();

		assertThat(pattern, is("*"));
	}

	@Test
	public void testGetConfigFilterPatternDefined() {
		System.setProperty("ht.classfilter.pattern", "foo.Foo*");

		factory = new ArgsConfigFactory();
		Config config = factory.getConfig();

		Filter filter = config.getFilter();
		ClassFilter classFilter = (ClassFilter) filter;
		String pattern = classFilter.getPattern();

		assertThat(pattern, is("foo.Foo*"));
	}

}
