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

import java.sql.Statement;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.github.woozoo73.ht.jdbc.JdbcContext;
import com.github.woozoo73.ht.jdbc.JdbcInfo;
import com.github.woozoo73.ht.jdbc.JdbcStatementInfo;

@Aspect
public class JdbcAspect {

	@Pointcut("within(java.sql.Connection+) && (execution(java.sql.PreparedStatement+ prepareStatement(String)))")
	public void prepareStatementPointcut() {
	}

	@Pointcut("within(java.sql.Statement+) && (execution(java.sql.ResultSet executeQuery()) || execution(int executeUpdate()))")
	public void executePointcut() {
	}

	@Pointcut("within(java.sql.PreparedStatement+) && (execution(void java.sql.PreparedStatement+.set*(int, *)))")
	public void setParameterPointcut() {
	}
	
	@Around("prepareStatementPointcut()")
	public Object profilePrepareStatement(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = null;

		JdbcStatementInfo statementInfo = null;
		Long start = System.nanoTime();
		
		try {
			returnValue = joinPoint.proceed();

			Statement statement = (Statement) returnValue;
			statementInfo = new JdbcStatementInfo();
			statementInfo.setSql((String) joinPoint.getArgs()[0]);

			JdbcContext.put(statement, statementInfo);

			return returnValue;
		} catch (Throwable t) {
			throw t;
		} finally {
			Long end = System.nanoTime();
			statementInfo.setDurationNanoTime(end - start);
		}
	}

	@Around("executePointcut()")
	public Object profileExecute(ProceedingJoinPoint joinPoint) throws Throwable {
		Object returnValue = null;

		try {
			returnValue = joinPoint.proceed();

			Statement statement = (Statement) joinPoint.getTarget();
			JdbcStatementInfo statementInfo = JdbcContext.get(statement);
			
			if (statementInfo != null) {
				Invocation invocation = Context.peekFromInvocationStack();

				if (invocation != null) {
					JdbcInfo jdbcInfo = invocation.getJdbcInfo();
					if (jdbcInfo == null) {
						jdbcInfo = new JdbcInfo();
						invocation.setJdbcInfo(jdbcInfo);
					}

					jdbcInfo.add(statementInfo);
					jdbcInfo.fixData();
				}
			}

			return returnValue;
		} catch (Throwable t) {
			throw t;
		}
	}

	@Before("setParameterPointcut()")
	public void profileSetParameter(JoinPoint joinPoint) throws Throwable {
		Statement statement = (Statement) joinPoint.getTarget();
		JdbcStatementInfo statementInfo = JdbcContext.get(statement);

		Object[] args = joinPoint.getArgs();
		if (args[0] == null) {
			return;
		}

		Integer index = (Integer) args[0];
		Object value = args[1];
		
		if (statementInfo != null) {
			statementInfo.setParameter(index, value);
		}
	}

}
