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

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.woozoo73.ht.Invocation;

public class XmlFormat implements Format {

	private static final Log logger = LogFactory.getLog(XmlFormat.class);

	@Override
	public String format(Invocation invocation) {
		String output = null;

		try {
			output = formatInternal(invocation);
		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		return output;
	}

	public String formatInternal(Invocation invocation) throws Exception {
		JAXBContext jaxbContext = JAXBContext.newInstance(invocation.getClass());
		Marshaller marshaller = jaxbContext.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

		StringWriter writer = new StringWriter();
		marshaller.marshal(invocation, writer);

		String xml = writer.getBuffer().toString();
		
		return xml;
	}
	
}
