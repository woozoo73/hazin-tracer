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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Ignore;

@Ignore
public abstract class AbstractTest {

	protected final Log logger = LogFactory.getLog(getClass());

	protected String xsdPath = "xsd/invocation.xsd";

	protected String declaringTypeName0 = "class0";

	protected String method0 = "method0";

	protected Invocation makeDummyInvocation() {
		return makeDummyInvocation(declaringTypeName0, method0);
	}

	protected Invocation makeDummyInvocation(String declaringTypeName, String method) {
		Invocation invocation = new Invocation();
		invocation.setDepth(0);
		invocation.setDurationNanoTime(1L);
		invocation.setDurationPercentage(100.0D);

		JoinPointInfo joinPoint = new JoinPointInfo();
		invocation.setJoinPointInfo(joinPoint);
		addArgs(joinPoint);
		addSourceLocation(joinPoint);

		ObjectInfo target = new ObjectInfo();
		target.setDeclaringType(String.class);
		target.setToStringValue(String.class.getName());
		joinPoint.setTarget(target);

		SignatureInfo signatureInfo = new SignatureInfo();
		signatureInfo.setDeclaringTypeName(declaringTypeName);
		signatureInfo.setName(method);
		joinPoint.setSignatureInfo(signatureInfo);

		return invocation;
	}

	protected void addArgs(JoinPointInfo joinPoint) {
		ObjectInfo arg = new ObjectInfo();
		arg.setDeclaringType(String.class);
		arg.setToStringValue(String.class.getName());
		joinPoint.setArgsInfo(new ObjectInfo[] { arg });
	}

	protected void addSourceLocation(JoinPointInfo joinPoint) {
		SourceLocationInfo sourceLocation = new SourceLocationInfo();
		sourceLocation.setWithinType(String.class);
		sourceLocation.setFileName("String.java");
		sourceLocation.setLine(5);
		sourceLocation.setColumn(20);
		joinPoint.setSourceLocation(sourceLocation);
	}

}
