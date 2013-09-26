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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbstractDao {

	public static final String DATABASE_PATH = "/hsqldb/test";

	public AbstractDao() {
		super();
	}

	protected int executeUpdate(String sql, Object[] args) {
		int result = 0;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
			}

			result = ps.executeUpdate();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}

	protected ResultSet executeQuery(String sql, Object[] args) {
		ResultSet rs = null;
		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = getConnection();
			ps = con.prepareStatement(sql);
			if (args != null) {
				for (int i = 0; i < args.length; i++) {
					ps.setObject(i + 1, args[i]);
				}
			}

			rs = ps.executeQuery();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
				}
			}
		}

		return rs;
	}

	protected Connection getConnection() throws Exception {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:file:" + DATABASE_PATH, "SA", "");

		return con;
	}

}