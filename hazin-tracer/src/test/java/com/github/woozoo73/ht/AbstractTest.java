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

	protected String declaringTypeName0 = "class0";

	protected String method0 = "method0";

	protected Invocation makeInvocation() {
		Invocation invocation = new Invocation();
		JoinPointInfo joinPointInfo = new JoinPointInfo();
		invocation.setJoinPointInfo(joinPointInfo);
		SignatureInfo signatureInfo = new SignatureInfo();
		joinPointInfo.setSignatureInfo(signatureInfo);
		signatureInfo.setDeclaringTypeName(declaringTypeName0);
		signatureInfo.setName(method0);

		return invocation;
	}

}
