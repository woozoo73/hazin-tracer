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
package com.github.woozoo73.ht.writer;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.io.FileReader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import com.github.woozoo73.ht.AbstractTest;
import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.format.Format;
import com.github.woozoo73.ht.format.XmlFormat;

public class FileWriterTest extends AbstractTest {

	private FileWriter fileWriter;

	private Format format;

	private File directory;

	private Invocation invocation;

	@Before
	public void setUp() throws Exception {
		fileWriter = new FileWriter();

		format = new XmlFormat();
		fileWriter.setFormat(format);

		String tmpdir = System.getProperty("java.io.tmpdir");
		directory = new File(tmpdir);
		fileWriter.setDirectory(directory);

		invocation = makeDummyInvocation();
	}

	@After
	public void tearDown() throws Exception {
		File file = new File(directory, invocation.getJoinPointInfo().getSignatureInfo().getDeclaringTypeName() + "."
				+ invocation.getJoinPointInfo().getSignatureInfo().getName());
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	public void testWrite() throws Exception {
		fileWriter.write(invocation);

		File file = new File(directory, invocation.getJoinPointInfo().getSignatureInfo().getDeclaringTypeName() + "."
				+ invocation.getJoinPointInfo().getSignatureInfo().getName());
		logger.debug(file);
		assertThat(file.exists(), is(true));

		String contents = FileCopyUtils.copyToString(new FileReader(file));
		logger.debug("\n" + contents);
		assertThat(contents, is(format.format(invocation)));
	}

	@Test
	public void testGetOutputFile() {
		File outputFileName = fileWriter.getOutputFile(invocation);
		logger.debug(outputFileName);
		assertThat(outputFileName, is(new File(directory, invocation.getJoinPointInfo().getSignatureInfo()
				.getDeclaringTypeName()
				+ "." + invocation.getJoinPointInfo().getSignatureInfo().getName())));
	}

}
