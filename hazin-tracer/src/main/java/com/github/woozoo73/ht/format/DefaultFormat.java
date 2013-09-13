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
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.woozoo73.ht.Invocation;
import com.github.woozoo73.ht.JoinPointInfo;
import com.github.woozoo73.ht.SignatureInfo;
import com.github.woozoo73.ht.SourceLocationInfo;
import com.github.woozoo73.ht.jdbc.JdbcInfo;
import com.github.woozoo73.ht.jdbc.JdbcStatementInfo;

/**
 * Default output format.
 * 
 * @author woozoo73
 */
public class DefaultFormat implements Format {

	private static final Log logger = LogFactory.getLog(DefaultFormat.class);

	private static NumberFormat timeFormat = new DecimalFormat("###,##0.00");

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

	private String formatInternal(Invocation invocation) {
		if (invocation == null) {
			return null;
		}

		StringBuilder builder = new StringBuilder();
		indent(builder, invocation.getDepth());
		builder.append("----> ");
		callInfo(builder, invocation);
		if (invocation.getJdbcInfo() != null) {
			jdbcInfo(builder, invocation);
		}
		if (invocation.getChildInvocationList() != null) {
			for (Invocation childInvocation : invocation.getChildInvocationList()) {
				builder.append("\n");
				builder.append(formatInternal(childInvocation));
			}
		}
		builder.append("\n");
		indent(builder, invocation.getDepth());
		if (invocation.getThrowableInfo() == null) {
			builder.append("<---- ");
			returnInfo(builder, invocation);
		} else {
			builder.append("<<<<< ");
			throwInfo(builder, invocation);
		}

		return builder.toString();
	}

	private void indent(StringBuilder builder, int depth) {
		for (int i = 0; i < depth; i++) {
			builder.append("    ");
		}
	}

	private void callInfo(StringBuilder builder, Invocation invocation) {
		JoinPointInfo joinPoint = invocation.getJoinPointInfo();
		SignatureInfo signature = joinPoint.getSignatureInfo();
		if (signature != null) {
			String targetName = null;
			if (joinPoint.getTarget().getDeclaringType() != null) {
				targetName = joinPoint.getTarget().getDeclaringType().getName();
			} else {
				targetName = signature.getDeclaringTypeName();
			}
			builder.append(targetName);
			builder.append(".");
			builder.append(signature.getName());
			builder.append("(");
			argsInfo(builder, invocation);
			builder.append(")");

			SourceLocationInfo sourceLocation = joinPoint.getSourceLocation();
			if (sourceLocation != null) {
				builder.append(" (");
				builder.append(sourceLocation.getFileName());
				builder.append(":");
				builder.append(sourceLocation.getLine());
				builder.append(")");
			}
		}

		builder.append(" ");
		durationInfo(builder, invocation);
	}

	private void argsInfo(StringBuilder builder, Invocation invocation) {
		JoinPointInfo joinPoint = invocation.getJoinPointInfo();
		Object[] arguments = joinPoint.getArgs();
		if (arguments != null) {
			boolean first = true;
			for (Object arg : arguments) {
				if (!first) {
					builder.append(", ");
				}
				argInfo(builder, arg);
				first = false;
			}
		}
	}

	private void argInfo(StringBuilder builder, Object argument) {
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
	}

	private void returnInfo(StringBuilder builder, Invocation invocation) {
		builder.append(invocation.getReturnValueInfo());
		builder.append(" ");
		durationInfo(builder, invocation);
	}

	private void throwInfo(StringBuilder builder, Invocation invocation) {
		builder.append(invocation.getThrowableInfo());
		builder.append(" ");
		durationInfo(builder, invocation);
	}

	private void durationInfo(StringBuilder builder, Invocation invocation) {
		builder.append("(");
		builder.append(invocation.getDurationMiliTime() == null ? "0" : timeFormat.format(invocation
				.getDurationMiliTime()));
		builder.append("ms");
		builder.append(":");
		builder.append(timeFormat.format(invocation.getDurationPercentage()));
		builder.append("%");
		builder.append(")");
	}

	private void jdbcInfo(StringBuilder builder, Invocation invocation) {
		JdbcInfo jdbcInfo = invocation.getJdbcInfo();
		List<JdbcStatementInfo> statements = jdbcInfo.getStatements();
		if (statements != null) {
			for (JdbcStatementInfo statement : statements) {
				builder.append("\n");
				indentJdbc(builder, invocation.getDepth(), false);
				builder.append("[JDBC/Statement]");
				builder.append("\n");
				indentJdbc(builder, invocation.getDepth(), true);
				builder.append("sql: ");
				builder.append(statement.getSql());
				builder.append("\n");
				indentJdbc(builder, invocation.getDepth(), true);
				builder.append("parameters: ");
				builder.append(statement.getParameters());
				builder.append("\n");
				indentJdbc(builder, invocation.getDepth(), true);
				builder.append("duration: ");
				builder.append(statement.getDurationMiliTime() == null ? "0" : timeFormat.format(statement
						.getDurationMiliTime() / 1000));
				builder.append("ms");
			}
		}
	}

	private void indentJdbc(StringBuilder builder, int depth, boolean sub) {
		indent(builder, depth);
		builder.append("       ");
		if (sub) {
			builder.append("    |    ");
		}
	}

}
