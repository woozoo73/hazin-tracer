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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.SourceLocation;

import com.github.woozoo73.ht.Invocation;

/**
 * Default output format.
 * 
 * @author woozoo73
 */
public class DefaultFormat implements Format {

	private static NumberFormat timeFormat = new DecimalFormat("###,##0.00");

	@Override
	public String format(Invocation invocation) {
		StringBuilder builder = new StringBuilder();
		builder.append("\n");
		builder.append("--------------------------------------------------------------------------------------------------------------------------------------------------------");
		builder.append("\n");
		builder.append(formatInteranl(invocation));
		builder.append("\n");
		builder.append("--------------------------------------------------------------------------------------------------------------------------------------------------------");

		return builder.toString();
	}

	private String formatInteranl(Invocation invocation) {
		if (invocation == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < invocation.getDepth(); i++) {
			builder.append("    ");
		}
		builder.append("----> ");
		builder.append(callInfo(invocation));
		if (invocation.getChildInvocationList() != null) {
			for (Invocation childInvocation : invocation.getChildInvocationList()) {
				builder.append("\n");
				builder.append(formatInteranl(childInvocation));
			}
		}
		builder.append("\n");
		for (int i = 0; i < invocation.getDepth(); i++) {
			builder.append("    ");
		}
		if (invocation.getT() == null) {
			builder.append("<---- ");
			builder.append(returnInfo(invocation));
		} else {
			builder.append("<<<<< ");
			builder.append(throwInfo(invocation));
		}

		return builder.toString();
	}

	private String callInfo(Invocation invocation) {
		StringBuilder builder = new StringBuilder();

		JoinPoint joinPoint = invocation.getJoinPoint();
		Signature signature = joinPoint.getSignature();
		if (signature != null) {
			builder.append(signature.getDeclaringType().getName());
			builder.append(".");
			builder.append(signature.getName());
			builder.append("(");
			builder.append(argsInfo(invocation));
			builder.append(")");

			SourceLocation sourceLocation = joinPoint.getSourceLocation();
			if (sourceLocation != null) {
				builder.append(" (");
				builder.append(sourceLocation.getFileName());
				builder.append(":");
				builder.append(sourceLocation.getLine());
				builder.append(")");
			}
		}

		builder.append(" ");
		builder.append(durationInfo(invocation));

		return builder.toString();
	}

	private String argsInfo(Invocation invocation) {
		StringBuilder builder = new StringBuilder();

		JoinPoint joinPoint = invocation.getJoinPoint();
		Object[] arguments = joinPoint.getArgs();
		if (arguments != null) {
			boolean first = true;
			for (Object arg : arguments) {
				if (!first) {
					builder.append(", ");
				}
				builder.append(argInfo(arg));
				first = false;
			}
		}

		return builder.toString();
	}

	private String argInfo(Object argument) {
		StringBuilder builder = new StringBuilder();

		if (argument != null && argument instanceof Object[]) {
			Object[] arguments = (Object[]) argument;
			builder.append("[");
			if (arguments != null) {
				boolean firstArg = true;
				for (Object arg : arguments) {
					if (!firstArg) {
						builder.append(", ");
					}
					builder.append(arg);
					firstArg = false;
				}
			}
			builder.append("]");
		} else {
			builder.append(argument);
		}

		return builder.toString();
	}

	private String returnInfo(Invocation invocation) {
		StringBuilder builder = new StringBuilder();
		builder.append(invocation.getReturnValue());
		builder.append(" ");
		builder.append(durationInfo(invocation));

		return builder.toString();
	}

	private String throwInfo(Invocation invocation) {
		StringBuilder builder = new StringBuilder();
		builder.append(invocation.getT());
		builder.append(" ");
		builder.append(durationInfo(invocation));

		return builder.toString();
	}

	private String durationInfo(Invocation invocation) {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(timeFormat.format(invocation.getDurationMiliTime()));
		builder.append("ms");
		builder.append(":");
		builder.append(timeFormat.format(invocation.getDurationPercentage()));
		builder.append("%");
		builder.append(")");

		return builder.toString();
	}

}
