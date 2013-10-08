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
package com.github.woozoo73.ht.callback;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.lang.reflect.Method;
import java.util.Map;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.woozoo73.ht.Clazz;
import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.stats.Record;

@RunWith(MockitoJUnitRunner.class)
public class StatsRecorderTest {

	private StatisticsRecorder recorder;

	@Mock
	private Invocation invocation;

	@Mock
	private JoinPoint joinPoint;

	@Mock
	private MethodSignature signature;

	@Before
	public void setUp() throws Exception {
		recorder = new StatisticsRecorder();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRecord() throws Exception {
		Method setValueMethod = Clazz.class.getDeclaredMethod("setValue", new Class[] { Object.class });

		when(invocation.getJoinPoint()).thenReturn(joinPoint);
		when(joinPoint.getSignature()).thenReturn(signature);
		when(signature.getMethod()).thenReturn(setValueMethod);
		when(invocation.getDurationNanoTime()).thenReturn(1000L);
		recorder.after(invocation);

		when(invocation.getDurationNanoTime()).thenReturn(500L);
		recorder.after(invocation);

		when(invocation.getT()).thenReturn(new RuntimeException());
		recorder.after(invocation);

		Map<Method, Record> statsMap = StatisticsRecorder.getRecordMap();
		Record statsData = statsMap.get(setValueMethod);

		assertThat(new Integer(statsData.getRequestCount()), is(new Integer(3)));
		assertThat(new Integer(statsData.getSuccessCount()), is(new Integer(2)));
		assertThat(new Integer(statsData.getErrorCount()), is(new Integer(1)));
		assertThat(new Long(statsData.getQuickestDurationNanoTime()), is(new Long(500L)));
		assertThat(new Long(statsData.getSlowestDurationNanoTime()), is(new Long(1000L)));
		assertThat(new Long(statsData.getTotalDurationNanoTime()), is(new Long(1500L)));
		assertThat(new Long(statsData.getAverageDurationNanoTime()), is(new Long(750L)));
		assertThat(statsData.getSlowestInvocation(), is(invocation));
	}

}
