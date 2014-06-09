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
package com.github.woozoo73.test;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Server;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JdbcProfilingTest {

	protected final Log logger = LogFactory.getLog(getClass());

	private Server server;

	@Before
	public void setUp() throws Exception {
		startHsqldb();

		try {
			executeUpdate("CREATE TABLE USER ( ID VARCHAR(36) NOT NULL, NAME VARCHAR(100) NOT NULL )", null);
		} catch (Exception e) {
		}
	}

	@After
	public void tearDown() throws Exception {
		shutdownHsqldb();
	}

	protected void startHsqldb() {
		server = new Server();
		server.setLogWriter(new PrintWriter(System.out));
		server.setErrWriter(new PrintWriter(System.out));
		server.start();
	}

	protected void shutdownHsqldb() {
		try {
			server.shutdown();
		} catch (Exception t) {
			logger.warn(t.getMessage(), t);
		}
	}

	@Test
	public void testConnection() throws Exception {
		Connection con = getConnection();

		assertThat(con, notNullValue());
	}

	@Test
	public void testUpdateSql() {
		String id = "woo";
		executeUpdate("INSERT INTO USER ( ID, NAME ) VALUES ( '" + id + "', 'woo' )", null);
	}

	@Test
	public void testUpdateSqlWithParameter() {
		String id = "woo";
		executeUpdate("INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )", new Object[] { id, id });
	}

	@Test
	public void testSelectSqlWithParameter() {
		executeQuery("SELECT * FROM USER WHERE ID = ?", new Object[] { "woo" });
	}

	private int executeUpdate(String sql, Object[] args) {
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

	private ResultSet executeQuery(String sql, Object[] args) {
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

	private Connection getConnection() throws Exception {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");

		return con;
	}

}
