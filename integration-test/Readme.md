# Testes de Integração com Rest Assured

Link para a documentação do projeto:
[Rest Assured](https://github.com/rest-assured/rest-assured/wiki/GettingStarted)
Segue o pom.xml do projeto:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>

	<groupId>com.felipe</groupId>
	<artifactId>integration-test</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<packaging>jar</packaging>

	<name>integration-test</name>
	<description>Demo project for Spring Boot</description>

	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.0.1.RELEASE</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

	<properties>
		<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
		<project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
		<java.version>1.8</java.version>
	</properties>

	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-actuator</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>rest-assured</artifactId>
			<version>3.1.0</version>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>json-path</artifactId>
			<version>3.1.0</version>
		</dependency>
		<dependency>
			<groupId>io.rest-assured</groupId>
			<artifactId>xml-path</artifactId>
			<version>3.1.0</version>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-failsafe-plugin</artifactId>
				<version>2.12</version>
				<dependencies>
					<dependency>
						<groupId>org.apache.maven.surefire</groupId>
						<artifactId>surefire-junit47</artifactId>
						<version>2.12</version>
					</dependency>
				</dependencies>
				<configuration>
					<groups>com.felipe.integrationtest.IntegrationTest</groups>
				</configuration>
				<executions>
					<execution>
						<goals>
							<goal>integration-test</goal>
						</goals>
						<configuration>
							<includes>
								<include>**/*.class</include>
							</includes>
						</configuration>
					</execution>
				</executions>
			</plugin>
		</plugins>
	</build>
</project>
```

**As dependências abaixo são referentes ao Rest Assured.** 

```xml
<dependency>
	<groupId>io.rest-assured</groupId>
	<artifactId>rest-assured</artifactId>
	<version>3.1.0</version>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>io.rest-assured</groupId>
	<artifactId>json-path</artifactId>
	<version>3.1.0</version>
</dependency>
<dependency>
	<groupId>io.rest-assured</groupId>
	<artifactId>xml-path</artifactId>
	<version>3.1.0</version>
</dependency>
```		

Para rodar os testes de forma categorizada, precisamos criar uma interface sem nenhum método. Exemplo:

```java
public interface IntegrationTest {}
```		
Depois, na nossa classe de teste nós colocamos essa interface em uma anotação:

```java
@Category(IntegrationTest.class)
public class IntegrationTestSampleTest {
 // métodos de teste...
}
```

e para rodarmos os testes de integração de forma categorizada, usaremos um plugin do maven:

```xml
<plugin>
	<groupId>org.apache.maven.plugins</groupId>
	<artifactId>maven-failsafe-plugin</artifactId>
	<version>2.12</version>
	<dependencies>
		<dependency>
    		<groupId>org.apache.maven.surefire</groupId>
			<artifactId>surefire-junit47</artifactId>
			<version>2.12</version>
		</dependency>
	</dependencies>
	<configuration>
	        <!-- path da nossa interface de teste-->
			<groups>com.felipe.integrationtest.IntegrationTest</groups>
	</configuration>
	<executions>
		<execution>
			<goals>
	    		<goal>integration-test</goal>
			</goals>
			<configuration>
				<includes>
					<include>**/*.class</include>
				</includes>
			</configuration>
		</execution>
	</executions>
</plugin>
```
e para rodar, usamos o seguinte comando:

```bash
mvn integration-test 
```

Baseado no exemplo do site: [javacodegeeks](https://examples.javacodegeeks.com/core-java/junit/junit-integration-test-example/)

Link para a documentação do plugin do maven: [maven-plugin-failsafe](http://maven.apache.org/surefire/maven-failsafe-plugin/examples/junit.html)



