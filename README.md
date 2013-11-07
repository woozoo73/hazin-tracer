hazin-tracer
============

Java profiler using AOP.

JUnit ProcessorTest

VM arguments:
-javaagent:~/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
-Dht.format=com.github.woozoo73.ht.format.TextFormat
-Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter
-Dht.classfilter.pattern=com.github.woozoo73.test.dummy.ProcessorImpl

JUnit ProcessorTest

VM arguments:
-javaagent:~/.m2/repository/org/aspectj/aspectjweaver/1.7.2/aspectjweaver-1.7.2.jar
-Dht.format=com.github.woozoo73.ht.format.XmlFormat
-Dht.writer=com.github.woozoo73.ht.writer.ConsoleWriter
-Dht.classfilter.pattern=com.github.woozoo73.test.dummy.ProcessorImpl
