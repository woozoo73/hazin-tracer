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

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;

public class Invocation {

	private Integer depth = 0;

	private List<Invocation> childInvocationList = new ArrayList<Invocation>();

	private JoinPoint joinPoint;

	private Long durationNanoTime;

	private Double durationPercentage = 100D;

	private Long startNanoTime;

	private Long endNanoTime;

	private Object returnValue;

	private Throwable t;

	public boolean equalsJoinPoint(Invocation another) {
		if (another == null) {
			return false;
		}

		return equalsJoinPoint(another.joinPoint);
	}

	public boolean equalsJoinPoint(JoinPoint anotherJoinPoint) {
		if (joinPoint == null) {
			return false;
		}

		return joinPoint.equals(anotherJoinPoint);
	}

	public Invocation getInvocationByJoinPoint(JoinPoint search) {
		if (search == null) {
			return null;
		}

		if (equalsJoinPoint(search)) {
			return this;
		}

		for (Invocation childInvocation : childInvocationList) {
			Invocation match = childInvocation.getInvocationByJoinPoint(search);
			if (match != null) {
				return match;
			}
		}

		return null;
	}
	
	public void start() {
		startNanoTime = System.nanoTime();
	}

	public void stop() {
		endNanoTime = System.nanoTime();
		durationNanoTime = endNanoTime - startNanoTime;
	}

	public void add(Invocation childInvocation) {
		childInvocation.setDepth(depth + 1);
		childInvocationList.add(childInvocation);
	}

	public void calculateChildDurationPercentage() {
		Long totalSibling = 0L;
		for (Invocation invocation : childInvocationList) {
			totalSibling += invocation.durationNanoTime;
		}

		for (Invocation invocation : childInvocationList) {
			Double percentage = 0D;
			if (totalSibling > 0L) {
				percentage = (100D * invocation.durationNanoTime) / totalSibling;

			}
			invocation.setDurationPercentage(percentage);

			invocation.calculateChildDurationPercentage();
		}
	}

	public Double getDurationMiliTime() {
		if (durationNanoTime == null) {
			return null;
		}

		return durationNanoTime.doubleValue() / (1000 * 1000);
	}

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	public List<Invocation> getChildInvocationList() {
		return childInvocationList;
	}

	public JoinPoint getJoinPoint() {
		return joinPoint;
	}

	public void setJoinPoint(JoinPoint joinPoint) {
		this.joinPoint = joinPoint;
	}

	public Long getDurationNanoTime() {
		return durationNanoTime;
	}

	public void setDurationNanoTime(Long durationNanoTime) {
		this.durationNanoTime = durationNanoTime;
	}

	public Double getDurationPercentage() {
		return durationPercentage;
	}

	public void setDurationPercentage(Double durationPercentage) {
		this.durationPercentage = durationPercentage;
	}

	public Object getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(Object returnValue) {
		this.returnValue = returnValue;
	}

	public Throwable getT() {
		return t;
	}

	public void setT(Throwable t) {
		this.t = t;
	}

}
