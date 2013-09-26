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
package com.github.woozoo73.ht;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.StringWriter;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;

public abstract class XmlUtils {

	protected final static Log logger = LogFactory.getLog(XmlUtils.class);

	public static boolean validate(String xsd, String xml) {
		try {
			SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
			Schema schema = factory.newSchema(new StreamSource(new ByteArrayInputStream(xsd.getBytes("UTF-8"))));
			Validator validator = schema.newValidator();

			boolean valid = false;
			try {
				validator.validate(new StreamSource(new ByteArrayInputStream(xml.getBytes("UTF-8"))));
				valid = true;
			} catch (Exception e) {
				logger.warn(e.getMessage(), e);
			}

			return valid;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String getContent(String path) {
		try {
			URL url = XmlUtils.class.getClassLoader().getResource(path);
			File schemaLocation = new File(url.toURI());

			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
			Document document = documentBuilder.parse(schemaLocation);

			StringWriter writer = new StringWriter();
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(writer));

			String content = writer.getBuffer().toString();

			return content;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
