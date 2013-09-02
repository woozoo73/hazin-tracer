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

import java.io.File;

import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.JoinPointInfo;
import com.github.woozoo73.ht.SignatureInfo;
import com.github.woozoo73.ht.format.Format;

public class FileWriter implements Writer {

	private Format format;

	private File directory;

	@Override
	public void setFormat(Format format) {
		this.format = format;
	}

	public void setDirectory(File directory) {
		this.directory = directory;
	}

	@Override
	public void write(Invocation invocation) {
		try {
			File outputFile = getOutputFile(invocation);
			java.io.FileWriter writer = new java.io.FileWriter(outputFile);
			writer.write(format.format(invocation));
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public File getOutputFile(Invocation invocation) {
		JoinPointInfo joinPointInfo = invocation.getJoinPointInfo();
		SignatureInfo signatureInfo = joinPointInfo.getSignatureInfo();

		return new File(directory, signatureInfo.getDeclaringTypeName() + "." + signatureInfo.getName());
	}

}
