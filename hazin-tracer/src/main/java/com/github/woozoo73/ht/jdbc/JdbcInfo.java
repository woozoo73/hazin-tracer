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
package com.github.woozoo73.ht.jdbc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "jdbc")
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(propOrder = { "statements" })
public class JdbcInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElementWrapper(name = "statements")
	@XmlElement(name = "statement")
	private List<JdbcStatementInfo> statements;

	public void add(JdbcStatementInfo statement) {
		if (statement == null) {
			return;
		}

		if (statements == null) {
			statements = new ArrayList<JdbcStatementInfo>();
		}

		statements.add(statement);
	}

	public void fixData() {
		if (statements != null) {
			for (JdbcStatementInfo statement : statements) {
				statement.fixData();
			}
		}
	}

	public List<JdbcStatementInfo> getStatements() {
		return statements;
	}

	public void setStatements(List<JdbcStatementInfo> statements) {
		this.statements = statements;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("JdbcInfo [statements=");
		builder.append(statements);
		builder.append("]");
		return builder.toString();
	}

}
