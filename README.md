hazin-tracer
============

Java profiler using AOP.

##### JUnit test class

    com.github.woozoo73.test.dummy.ProcessorTest

##### VM arguments

    -javaagent:/{your-home-path}/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
    -Dht.format=com.github.woozoo73.ht.format.TextFormat
    -Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter

##### aop.xml

    <!DOCTYPE aspectj PUBLIC
            "-//AspectJ//DTD//EN" "http://www.eclipse.org/aspectj/dtd/aspectj.dtd">
    <aspectj>
    	<weaver options="-verbose -showWeaveInfo -debug">
    		<include within="com.github.woozoo73.ht..*" />
    		<include within="com.github.woozoo73.test..*" />
    		<include within="java.sql..*+" />
    		<exclude within="com.github.woozoo73.test.dummy.*Dao" />
    	</weaver>
    	<aspects>
    		<concrete-aspect name="com.github.woozoo73.ht.ConfiguredInvocationAspect"
    			extends="com.github.woozoo73.ht.InvocationAspect">
    			<pointcut name="endpointPointcut"
    				expression="execution(* com.github.woozoo73.test.dummy.ProcessorImpl.*(..))" />
    		</concrete-aspect>
    		<aspect name="com.github.woozoo73.ht.JdbcAspect" />
    	</aspects>
    </aspectj>

##### Output

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

##### VM arguments

    -javaagent:~/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
    -Dht.format=com.github.woozoo73.ht.format.XmlFormat
    -Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter

##### Output

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <invocation durationPercentage="100.0" durationNanoTime="539123831" depth="0">
        <joinPoint>
            <target toString="com.github.woozoo73.test.dummy.ProcessorImpl@6a6484" declaringType="com.github.woozoo73.test.dummy.ProcessorImpl" />
            <signature modifiers="1" name="process" declaringType="com.github.woozoo73.test.dummy.ProcessorImpl" />
            <args>
                <arg toString="foo" declaringType="java.lang.String" />
            </args>
            <sourceLocation line="31" withinType="com.github.woozoo73.test.dummy.ProcessorImpl" />
        </joinPoint>
        <childInvocationList>
            <invocation durationPercentage="1.4488091284031017" durationNanoTime="7668702" depth="1">
                <joinPoint>
                    <target />
                    <signature modifiers="9" name="sleep" declaringType="com.github.woozoo73.test.dummy.Timer" />
                    <args>
                        <arg toString="10" declaringType="java.lang.Long" />
                    </args>
                    <sourceLocation line="20" withinType="com.github.woozoo73.test.dummy.Timer" />
                </joinPoint>
                <childInvocationList>
                    <invocation durationPercentage="100.0" durationNanoTime="52081" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.Timer$1@1192b0c" declaringType="com.github.woozoo73.test.dummy.Timer$1" />
                            <signature modifiers="0" name="&lt;init&gt;" declaringType="com.github.woozoo73.test.dummy.Timer$1" />
                            <args>
                                <arg toString="10" declaringType="java.lang.Long" />
                            </args>
                            <sourceLocation line="21" withinType="com.github.woozoo73.test.dummy.Timer$1" />
                        </joinPoint>
                        <childInvocationList>
                            <invocation durationPercentage="100.0" durationNanoTime="13133" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.Timer$1@1192b0c" declaringType="com.github.woozoo73.test.dummy.Timer$1" />
                                    <signature modifiers="1" name="&lt;init&gt;" declaringType="java.lang.Runnable" />
                                    <sourceLocation line="21" withinType="com.github.woozoo73.test.dummy.Timer$1" />
                                </joinPoint>
                            </invocation>
                        </childInvocationList>
                    </invocation>
                </childInvocationList>
                <returnValue />
            </invocation>
            <invocation durationPercentage="98.5511908715969" durationNanoTime="521642016" depth="1">
                <joinPoint>
                    <target toString="com.github.woozoo73.test.dummy.ProcessorImpl@6a6484" declaringType="com.github.woozoo73.test.dummy.ProcessorImpl" />
                    <signature modifiers="2" name="processInternal" declaringType="com.github.woozoo73.test.dummy.ProcessorImpl" />
                    <args>
                        <arg toString="foo" declaringType="java.lang.String" />
                    </args>
                    <sourceLocation line="39" withinType="com.github.woozoo73.test.dummy.ProcessorImpl" />
                </joinPoint>
                <childInvocationList>
                    <invocation durationPercentage="0.029681807074313723" durationNanoTime="138130" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                            <signature modifiers="1" name="&lt;init&gt;" declaringType="com.github.woozoo73.test.dummy.User" />
                            <args>
                                <arg toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                <arg toString="foo" declaringType="java.lang.String" />
                            </args>
                            <sourceLocation line="26" withinType="com.github.woozoo73.test.dummy.User" />
                        </joinPoint>
                        <childInvocationList>
                            <invocation durationPercentage="59.09068098559743" durationNanoTime="23550" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <signature modifiers="1" name="setId" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <args>
                                        <arg toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                    </args>
                                    <sourceLocation line="38" withinType="com.github.woozoo73.test.dummy.User" />
                                </joinPoint>
                                <returnValue />
                            </invocation>
                            <invocation durationPercentage="40.90931901440257" durationNanoTime="16304" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <signature modifiers="1" name="setName" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <args>
                                        <arg toString="foo" declaringType="java.lang.String" />
                                    </args>
                                    <sourceLocation line="46" withinType="com.github.woozoo73.test.dummy.User" />
                                </joinPoint>
                                <returnValue />
                            </invocation>
                        </childInvocationList>
                    </invocation>
                    <invocation durationPercentage="49.172510628334614" durationNanoTime="228833739" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <signature modifiers="1" name="insert" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <args>
                                <arg toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                            </args>
                            <sourceLocation line="22" withinType="com.github.woozoo73.test.dummy.UserDao" />
                        </joinPoint>
                        <childInvocationList>
                            <invocation durationPercentage="0.00811994428902244" durationNanoTime="18569" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <signature modifiers="1" name="getId" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <sourceLocation line="34" withinType="com.github.woozoo73.test.dummy.User" />
                                </joinPoint>
                                <returnValue toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                            </invocation>
                            <invocation durationPercentage="0.008317597087699706" durationNanoTime="19021" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.User@7a9fa7" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <signature modifiers="1" name="getName" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <sourceLocation line="42" withinType="com.github.woozoo73.test.dummy.User" />
                                </joinPoint>
                                <returnValue toString="foo" declaringType="java.lang.String" />
                            </invocation>
                            <invocation durationPercentage="99.98356245862328" durationNanoTime="228646245" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                    <signature modifiers="4" name="executeUpdate" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                    <args>
                                        <arg toString="INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )" declaringType="java.lang.String" />
                                        <arg toString="[Ljava.lang.Object;@450fe6" declaringType="[Ljava.lang.Object;" />
                                    </args>
                                    <sourceLocation line="32" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                </joinPoint>
                                <jdbc>
                                    <statements>
                                        <statement durationNanoTime="9513303">
                                            <sql>INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )</sql>
                                            <parameters>
                                                <parameter toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                                <parameter toString="foo" declaringType="java.lang.String" />
                                            </parameters>
                                        </statement>
                                    </statements>
                                </jdbc>
                                <childInvocationList>
                                    <invocation durationPercentage="100.0" durationNanoTime="83205393" depth="4">
                                        <joinPoint>
                                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                            <signature modifiers="4" name="getConnection" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                            <sourceLocation line="102" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                        </joinPoint>
                                        <returnValue toString="org.hsqldb.jdbc.JDBCConnection@1fa2e2e" declaringType="org.hsqldb.jdbc.JDBCConnection" />
                                    </invocation>
                                </childInvocationList>
                                <returnValue toString="1" declaringType="java.lang.Integer" />
                            </invocation>
                        </childInvocationList>
                        <returnValue />
                    </invocation>
                    <invocation durationPercentage="1.3359724849884793" durationNanoTime="6217205" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <signature modifiers="1" name="insertInvalid" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <sourceLocation line="26" withinType="com.github.woozoo73.test.dummy.UserDao" />
                        </joinPoint>
                        <childInvocationList>
                            <invocation durationPercentage="100.0" durationNanoTime="6148367" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                    <signature modifiers="4" name="executeUpdate" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                    <args>
                                        <arg toString="INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )" declaringType="java.lang.String" />
                                        <arg />
                                    </args>
                                    <sourceLocation line="32" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                </joinPoint>
                                <jdbc>
                                    <statements>
                                        <statement durationNanoTime="2525287">
                                            <sql>INSERT INTO USER ( ID, NAME ) VALUES ( ?, ? )</sql>
                                            <t toString="java.sql.SQLException: Parameter not set" declaringType="java.sql.SQLException" />
                                        </statement>
                                    </statements>
                                </jdbc>
                                <childInvocationList>
                                    <invocation durationPercentage="100.0" durationNanoTime="805683" depth="4">
                                        <joinPoint>
                                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                            <signature modifiers="4" name="getConnection" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                            <sourceLocation line="102" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                        </joinPoint>
                                        <returnValue toString="org.hsqldb.jdbc.JDBCConnection@13a5041" declaringType="org.hsqldb.jdbc.JDBCConnection" />
                                    </invocation>
                                </childInvocationList>
                                <t toString="java.lang.RuntimeException: java.sql.SQLException: Parameter not set" declaringType="java.lang.RuntimeException" />
                            </invocation>
                        </childInvocationList>
                        <t toString="java.lang.RuntimeException: java.sql.SQLException: Parameter not set" declaringType="java.lang.RuntimeException" />
                    </invocation>
                    <invocation durationPercentage="49.45628808642359" durationNanoTime="230154352" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <signature modifiers="1" name="select" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                            <args>
                                <arg toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                            </args>
                            <sourceLocation line="30" withinType="com.github.woozoo73.test.dummy.UserDao" />
                        </joinPoint>
                        <childInvocationList>
                            <invocation durationPercentage="99.95351213370238" durationNanoTime="229804725" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                    <signature modifiers="4" name="executeQuery" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                    <args>
                                        <arg toString="SELECT * FROM USER WHERE ID = ?" declaringType="java.lang.String" />
                                        <arg toString="[Ljava.lang.Object;@5514f9" declaringType="[Ljava.lang.Object;" />
                                    </args>
                                    <sourceLocation line="67" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                </joinPoint>
                                <jdbc>
                                    <statements>
                                        <statement durationNanoTime="227024916">
                                            <sql>SELECT * FROM USER WHERE ID = ?</sql>
                                            <parameters>
                                                <parameter toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                            </parameters>
                                        </statement>
                                    </statements>
                                </jdbc>
                                <childInvocationList>
                                    <invocation durationPercentage="100.0" durationNanoTime="330153" depth="4">
                                        <joinPoint>
                                            <target toString="com.github.woozoo73.test.dummy.UserDao@87c7a8" declaringType="com.github.woozoo73.test.dummy.UserDao" />
                                            <signature modifiers="4" name="getConnection" declaringType="com.github.woozoo73.test.dummy.AbstractDao" />
                                            <sourceLocation line="102" withinType="com.github.woozoo73.test.dummy.AbstractDao" />
                                        </joinPoint>
                                        <returnValue toString="org.hsqldb.jdbc.JDBCConnection@1d418d7" declaringType="org.hsqldb.jdbc.JDBCConnection" />
                                    </invocation>
                                </childInvocationList>
                                <returnValue toString="org.hsqldb.jdbc.JDBCResultSet@9e98ac" declaringType="org.hsqldb.jdbc.JDBCResultSet" />
                            </invocation>
                            <invocation durationPercentage="0.04648786629762396" durationNanoTime="106881" depth="3">
                                <joinPoint>
                                    <target toString="com.github.woozoo73.test.dummy.User@3953e8" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <signature modifiers="1" name="&lt;init&gt;" declaringType="com.github.woozoo73.test.dummy.User" />
                                    <args>
                                        <arg toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                        <arg toString="foo" declaringType="java.lang.String" />
                                    </args>
                                    <sourceLocation line="26" withinType="com.github.woozoo73.test.dummy.User" />
                                </joinPoint>
                                <childInvocationList>
                                    <invocation durationPercentage="50.0" durationNanoTime="24003" depth="4">
                                        <joinPoint>
                                            <target toString="com.github.woozoo73.test.dummy.User@3953e8" declaringType="com.github.woozoo73.test.dummy.User" />
                                            <signature modifiers="1" name="setId" declaringType="com.github.woozoo73.test.dummy.User" />
                                            <args>
                                                <arg toString="b48aeb1a-319e-4600-800d-886b3e122cc5" declaringType="java.lang.String" />
                                            </args>
                                            <sourceLocation line="38" withinType="com.github.woozoo73.test.dummy.User" />
                                        </joinPoint>
                                        <returnValue />
                                    </invocation>
                                    <invocation durationPercentage="50.0" durationNanoTime="24003" depth="4">
                                        <joinPoint>
                                            <target toString="com.github.woozoo73.test.dummy.User@3953e8" declaringType="com.github.woozoo73.test.dummy.User" />
                                            <signature modifiers="1" name="setName" declaringType="com.github.woozoo73.test.dummy.User" />
                                            <args>
                                                <arg toString="foo" declaringType="java.lang.String" />
                                            </args>
                                            <sourceLocation line="46" withinType="com.github.woozoo73.test.dummy.User" />
                                        </joinPoint>
                                        <returnValue />
                                    </invocation>
                                </childInvocationList>
                            </invocation>
                        </childInvocationList>
                        <returnValue toString="com.github.woozoo73.test.dummy.User@3953e8" declaringType="com.github.woozoo73.test.dummy.User" />
                    </invocation>
                    <invocation durationPercentage="0.005546993179007705" durationNanoTime="25814" depth="2">
                        <joinPoint>
                            <target toString="com.github.woozoo73.test.dummy.User@3953e8" declaringType="com.github.woozoo73.test.dummy.User" />
                            <signature modifiers="1" name="getName" declaringType="com.github.woozoo73.test.dummy.User" />
                            <sourceLocation line="42" withinType="com.github.woozoo73.test.dummy.User" />
                        </joinPoint>
                        <returnValue toString="foo" declaringType="java.lang.String" />
                    </invocation>
                </childInvocationList>
                <returnValue toString="Hello, foo." declaringType="java.lang.String" />
            </invocation>
        </childInvocationList>
        <returnValue toString="Hello, foo. " declaringType="java.lang.String" />
    </invocation>
