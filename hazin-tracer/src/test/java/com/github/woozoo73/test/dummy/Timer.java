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

public class Timer {

	public void sleep(final long delay) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(delay);
				} catch (InterruptedException e) {
				}
			}
		};
		Thread t = new Thread(r);
		t.start();
	}

}
