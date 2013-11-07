hazin-tracer
============

Java profiler using AOP.

JUnit ProcessorTest

VM arguments:
-javaagent:~/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
-Dht.format=com.github.woozoo73.ht.format.TextFormat
-Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter
-Dht.classfilter.pattern=com.github.woozoo73.test.dummy.ProcessorImpl

    ----> com.github.woozoo73.test.dummy.ProcessorImpl.process(foo) (ProcessorImpl.java:31) (628.74ms:100.00%)
        ----> com.github.woozoo73.test.dummy.Timer.sleep(10) (Timer.java:20) (13.13ms:2.15%)
            ----> com.github.woozoo73.test.dummy.Timer$1.<init>(10) (Timer.java:21) (0.10ms:100.00%)
                ----> com.github.woozoo73.test.dummy.Timer$1.<init>() (Timer.java:21) (0.02ms:100.00%)
                <---- null (0.02ms:100.00%)
            <---- null (0.10ms:100.00%)
        <---- null (13.13ms:2.15%)
        ----> com.github.woozoo73.test.dummy.ProcessorImpl.processInternal(foo) (ProcessorImpl.java:39) (596.89ms:97.85%)
            ----> com.github.woozoo73.test.dummy.User.<init>(c56f233a-5e6b-4d47-8739-2e4f0d601a83, foo) (User.java:26) (0.14ms:0.03%)
                ----> com.github.woozoo73.test.dummy.User.setId(c56f233a-5e6b-4d47-8739-2e4f0d601a83) (User.java:38) (0.02ms:58.82%)
                <---- null (0.02ms:58.82%)
                ----> com.github.woozoo73.test.dummy.User.setName(foo) (User.java:46) (0.02ms:41.18%)
                <---- null (0.02ms:41.18%)
            <---- null (0.14ms:0.03%)
            ----> com.github.woozoo73.test.dummy.UserDao.insert(com.github.woozoo73.test.dummy.User@142022d) (UserDao.java:22) (279.95ms:63.99%)
                ----> com.github.woozoo73.test.dummy.User.getId() (User.java:34) (0.02ms:0.01%)
                <---- c56f233a-5e6b-4d47-8739-2e4f0d601a83 (0.02ms:0.01%)
                ----> com.github.woozoo73.test.dummy.User.getName() (User.java:42) (0.02ms:0.01%)
                <---- foo (0.02ms:0.01%)
                ----> com.github.woozoo73.test.dummy.UserDao.executeUpdate(INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? ), [c56f233a-5e6b-4d47-8739-2e4f0d601a83, foo]) (AbstractDao.java:32) (279.77ms:99.99%)
                       [JDBC/Statement]
                           | sql: INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )
                           | parameters: [c56f233a-5e6b-4d47-8739-2e4f0d601a83, foo]
                           | duration: 0.01ms
                    ----> com.github.woozoo73.test.dummy.UserDao.getConnection() (AbstractDao.java:102) (117.46ms:100.00%)
                    <---- org.hsqldb.jdbc.JDBCConnection@126df42 (117.46ms:100.00%)
                <---- 1 (279.77ms:99.99%)
            <---- null (279.95ms:63.99%)
            ----> com.github.woozoo73.test.dummy.UserDao.insertInvalid() (UserDao.java:26) (9.09ms:2.08%)
                ----> com.github.woozoo73.test.dummy.UserDao.executeUpdate(INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? ), null) (AbstractDao.java:32) (9.05ms:100.00%)
                       [JDBC/Statement]
                           | sql: INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )
                           | duration: 0.00ms
                           | throw: java.sql.SQLException: Parameter not set
                    ----> com.github.woozoo73.test.dummy.UserDao.getConnection() (AbstractDao.java:102) (0.97ms:100.00%)
                    <---- org.hsqldb.jdbc.JDBCConnection@3ca41c (0.97ms:100.00%)
                <<<<< java.lang.RuntimeException: java.sql.SQLException: Parameter not set (9.05ms:100.00%)
            <<<<< java.lang.RuntimeException: java.sql.SQLException: Parameter not set (9.09ms:2.08%)
            ----> com.github.woozoo73.test.dummy.UserDao.select(c56f233a-5e6b-4d47-8739-2e4f0d601a83) (UserDao.java:30) (148.26ms:33.89%)
                ----> com.github.woozoo73.test.dummy.UserDao.executeQuery(SELECT * FROM USER WHERE ID = ?, [c56f233a-5e6b-4d47-8739-2e4f0d601a83]) (AbstractDao.java:67) (147.92ms:99.93%)
                       [JDBC/Statement]
                           | sql: SELECT * FROM USER WHERE ID = ?
                           | parameters: [c56f233a-5e6b-4d47-8739-2e4f0d601a83]
                           | duration: 0.15ms
                    ----> com.github.woozoo73.test.dummy.UserDao.getConnection() (AbstractDao.java:102) (0.30ms:100.00%)
                    <---- org.hsqldb.jdbc.JDBCConnection@97304b (0.30ms:100.00%)
                <---- org.hsqldb.jdbc.JDBCResultSet@1682962 (147.92ms:99.93%)
                ----> com.github.woozoo73.test.dummy.User.<init>(c56f233a-5e6b-4d47-8739-2e4f0d601a83, foo) (User.java:26) (0.11ms:0.07%)
                    ----> com.github.woozoo73.test.dummy.User.setId(c56f233a-5e6b-4d47-8739-2e4f0d601a83) (User.java:38) (0.02ms:50.93%)
                    <---- null (0.02ms:50.93%)
                    ----> com.github.woozoo73.test.dummy.User.setName(foo) (User.java:46) (0.02ms:49.07%)
                    <---- null (0.02ms:49.07%)
                <---- null (0.11ms:0.07%)
            <---- com.github.woozoo73.test.dummy.User@17515f7 (148.26ms:33.89%)
            ----> com.github.woozoo73.test.dummy.User.getName() (User.java:42) (0.03ms:0.01%)
            <---- foo (0.03ms:0.01%)
        <---- Hello, foo. (596.89ms:97.85%)
    <---- Hello, foo.  (628.74ms:100.00%)

JUnit ProcessorTest

VM arguments:
-javaagent:~/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
-Dht.format=com.github.woozoo73.ht.format.XmlFormat
-Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter
-Dht.classfilter.pattern=com.github.woozoo73.test.dummy.ProcessorImpl
