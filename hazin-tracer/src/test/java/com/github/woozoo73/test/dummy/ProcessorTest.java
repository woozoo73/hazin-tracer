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

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hsqldb.Server;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.woozoo73.ht.callback.StatisticsRecorder;
import com.github.woozoo73.test.AbstractSpringTestCase;

public class ProcessorTest extends AbstractSpringTestCase {

	protected static final Log logger = LogFactory.getLog(ProcessorTest.class);

	@Autowired
	private Processor processor;

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

	@AfterClass
	public static void tearDownAfterClass() {
		showStatistics();
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

	protected static void showStatistics() {
		logger.debug(StatisticsRecorder.prettyPrint());
	}

	@Test
	public void testProcess() {
		processor.process("foo");
	}

	@Test(expected = IllegalStateException.class)
	public void testProcessWithNull() {
		processor.process(null);
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

	private Connection getConnection() throws Exception {
		Connection con = DriverManager.getConnection("jdbc:hsqldb:mem:testdb", "SA", "");

		return con;
	}

}
