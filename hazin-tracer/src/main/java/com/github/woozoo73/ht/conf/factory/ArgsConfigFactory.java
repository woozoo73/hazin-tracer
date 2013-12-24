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

import com.github.woozoo73.ht.callback.WriterCallback;
import com.github.woozoo73.ht.conf.Config;
import com.github.woozoo73.ht.format.Format;
import com.github.woozoo73.ht.format.TextFormat;
import com.github.woozoo73.ht.writer.LogWriter;
import com.github.woozoo73.ht.writer.Writer;

/**
 * Config factory supports VM arguments.
 * 
 * @author woozoo73
 */
public class ArgsConfigFactory implements ConfigFactory {

	private Config config;

	public ArgsConfigFactory() {
		init();
	}

	private void init() {
		try {
			config = new Config();

			WriterCallback writerCallback = new WriterCallback();

			String witerName = System.getProperty("ht.writer", LogWriter.class.getName());
			Writer writer = (Writer) Class.forName(witerName).newInstance();

			String formatName = System.getProperty("ht.format", TextFormat.class.getName());
			Format format = (Format) Class.forName(formatName).newInstance();

			writer.setFormat(format);
			writerCallback.setWriter(writer);

			config.setInvocationCallback(writerCallback);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Config getConfig() {
		return this.config;
	}

}
