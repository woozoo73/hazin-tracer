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
package com.github.woozoo73.test.dummy;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

public class ProcessorImpl implements Processor {

	@Autowired
	private UserDao userDao;

	public ProcessorImpl() {
	}

	@Override
	public String process(String name) {
		Timer.sleep(10L);

		String result = processInternal(name) + " ";

		return result;
	}

	private String processInternal(String name) {
		if (name == null) {
			throw new IllegalStateException("name must not be null.");
		}

		String id = UUID.randomUUID().toString();
		User user = new User(id, name);
		userDao.insert(user);

		try {
			userDao.insertInvalid();
		} catch (Exception e) {
		}

		user = userDao.select(id);

		return "Hello, " + user.getName() + ".";
	}

}
