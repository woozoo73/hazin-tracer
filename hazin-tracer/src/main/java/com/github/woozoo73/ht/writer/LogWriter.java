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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.format.Format;

/**
 * Writer using log.
 * 
 * @author woozoo73
 */
public class LogWriter implements Writer {

	private final Log logger = LogFactory.getLog(getClass());

	private Format format;

	@Override
	public void setFormat(Format format) {
		this.format = format;
	}

	@Override
	public void write(Invocation invocation) {
		if (logger.isDebugEnabled()) {
			logger.debug(format.format(invocation));
		}
	}

}
