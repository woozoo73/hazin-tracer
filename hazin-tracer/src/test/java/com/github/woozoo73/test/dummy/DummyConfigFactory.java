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

import java.util.ArrayList;
import java.util.List;

import com.github.woozoo73.ht.callback.CompositeCallback;
import com.github.woozoo73.ht.callback.InvocationCallback;
import com.github.woozoo73.ht.callback.StatisticsRecorder;
import com.github.woozoo73.ht.callback.WriterCallback;
import com.github.woozoo73.ht.conf.Config;
import com.github.woozoo73.ht.conf.factory.ConfigFactory;
import com.github.woozoo73.ht.format.TextFormat;
import com.github.woozoo73.ht.writer.ConsoleWriter;
import com.github.woozoo73.ht.writer.Writer;

public class DummyConfigFactory implements ConfigFactory {

	@Override
	public Config getConfig() {
		Config config = new Config();

		CompositeCallback cc = new CompositeCallback();

		List<InvocationCallback> callbacks = new ArrayList<InvocationCallback>();
		WriterCallback wc = new WriterCallback();
		Writer w = new ConsoleWriter();
		w.setFormat(new TextFormat());
		wc.setWriter(w);
		StatisticsRecorder sr = new StatisticsRecorder();
		callbacks.add(wc);
		callbacks.add(sr);
		cc.setCallbacks(callbacks);
		config.setInvocationCallback(cc);

		return config;
	}

}
