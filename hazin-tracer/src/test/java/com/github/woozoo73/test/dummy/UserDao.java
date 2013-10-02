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

import java.sql.ResultSet;

public class UserDao extends AbstractDao {

	public void insert(User user) {
		executeUpdate("INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )", new Object[] { user.getId(), user.getName() });
	}

	public void insertInvalid() {
		executeUpdate("INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )", null);
	}

	public User select(String id) {
		ResultSet rs = null;
		User user = null;

		try {
			rs = executeQuery("SELECT * FROM USER WHERE ID = ?", new Object[] { id });
			if (rs.next()) {
				user = new User(rs.getString("ID"), rs.getString("NAME"));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
		}

		return user;
	}

}
