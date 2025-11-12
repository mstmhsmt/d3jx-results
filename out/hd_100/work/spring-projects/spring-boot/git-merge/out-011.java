/*
 * Copyright 2012-2016 the original author or authors.
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

package org.springframework.boot.autoconfigure.liquibase;

import java.io.File;
import java.util.Map;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDataSourceConfiguration;
import org.springframework.boot.liquibase.CommonsLoggingLiquibaseLogger;
import org.springframework.boot.liquibase.LiquibaseServiceLocatorApplicationListener;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.boot.test.util.EnvironmentTestUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.FileCopyUtils;

<<<<<<< commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/A.java
import static org.assertj.core.api.Assertions.assertThat;
||||||| commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/O.java
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.contains;
=======
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
>>>>>>> commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/B.java

/**
 * Tests for {@link LiquibaseAutoConfiguration}.
 *
 * @author Marcel Overdijk
 * @author Eddú Meléndez
 * @author Andy Wilkinson
 */
public class LiquibaseAutoConfigurationTests {

	@Rule
	public ExpectedException expected = ExpectedException.none();

	@Rule
	public TemporaryFolder temp = new TemporaryFolder();

	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

	@Before
	public void init() {
		EnvironmentTestUtils.addEnvironment(this.context,
				"spring.datasource.name:liquibasetest");
		new LiquibaseServiceLocatorApplicationListener().onApplicationEvent(null);
	}

	@After
	public void close() {
		if (this.context != null) {
			this.context.close();
		}
	}

	@Test
	public void testNoDataSource() throws Exception {
		this.context.register(LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		assertThat(this.context.getBeanNamesForType(SpringLiquibase.class).length)
				.isEqualTo(0);
	}

	@Test
	public void testDefaultSpringLiquibase() throws Exception {
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getChangeLog())
				.isEqualTo("classpath:/db/changelog/db.changelog-master.yaml");
		assertThat(liquibase.getContexts()).isNull();
		assertThat(liquibase.getDefaultSchema()).isNull();
		assertThat(liquibase.isDropFirst()).isFalse();
	}

	@Test
	public void testXmlChangeLog() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.change-log:classpath:/db/changelog/db.changelog-override.xml");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getChangeLog())
				.isEqualTo("classpath:/db/changelog/db.changelog-override.xml");
	}

	@Test
	public void testJsonChangeLog() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.change-log:classpath:/db/changelog/db.changelog-override.json");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getChangeLog())
				.isEqualTo("classpath:/db/changelog/db.changelog-override.json");
	}

	@Test
	public void testSqlChangeLog() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.change-log:classpath:/db/changelog/db.changelog-override.sql");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getChangeLog())
				.isEqualTo("classpath:/db/changelog/db.changelog-override.sql");
	}

	@Test
	public void testOverrideContexts() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.contexts:test, production");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getContexts()).isEqualTo("test, production");
	}

	@Test
	public void testOverrideDefaultSchema() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.default-schema:public");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getDefaultSchema()).isEqualTo("public");
	}

	@Test
	public void testOverrideDropFirst() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context, "liquibase.drop-first:true");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.isDropFirst()).isTrue();
	}

	@Test
	public void testOverrideDataSource() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.url:jdbc:hsqldb:mem:liquibase", "liquibase.user:sa");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getDataSource().getConnection().getMetaData().getURL())
				.isEqualTo("jdbc:hsqldb:mem:liquibase");
	}

	@Test(expected = BeanCreationException.class)
	public void testChangeLogDoesNotExist() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.change-log:classpath:/no-such-changelog.yaml");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
	}

	@Test
	public void testLogger() throws Exception {
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		Object log = ReflectionTestUtils.getField(liquibase, "log");
<<<<<<< commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/A.java
		assertThat(log).isInstanceOf(CommonsLoggingLiquibaseLogger.class);
		assertThat(this.outputCapture.toString()).doesNotContain(": liquibase:");
||||||| commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/O.java
		assertThat(log, instanceOf((CommonsLoggingLiquibaseLogger.class)));
		assertThat(this.outputCapture.toString(), not(contains(": liquibase:")));
=======
		assertThat(log, instanceOf((CommonsLoggingLiquibaseLogger.class)));
		assertThat(this.outputCapture.toString(), not(containsString(": liquibase:")));
>>>>>>> commits-hd_100/spring-projects/spring-boot/32d0021eec262405e8d682a614190146337c82e3-38a95d7a9fd4a23080dc5eaf2aa058e988f61f1a/B.java
	}

	@Test
	public void testOverrideLabels() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.labels:test, production");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getLabels()).isEqualTo("test, production");
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testOverrideParameters() throws Exception {
		EnvironmentTestUtils.addEnvironment(this.context, "liquibase.parameters.foo:bar");
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		Map<String, String> parameters = (Map<String, String>) ReflectionTestUtils
				.getField(liquibase, "parameters");
		assertThat(parameters.containsKey("foo")).isTrue();
		assertThat(parameters.get("foo")).isEqualTo("bar");
	}

	@Test
	public void testRollbackFile() throws Exception {
		File file = this.temp.newFile("rollback-file.sql");
		EnvironmentTestUtils.addEnvironment(this.context,
				"liquibase.rollbackFile:" + file.getAbsolutePath());
		this.context.register(EmbeddedDataSourceConfiguration.class,
				LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		File actualFile = (File) ReflectionTestUtils.getField(liquibase, "rollbackFile");
		assertThat(actualFile).isEqualTo(file).exists();
		String content = new String(FileCopyUtils.copyToByteArray(file));
		assertThat(content).contains("DROP TABLE PUBLIC.customer;");
	}

	@Test
	public void testLiquibaseDateSource() {
		this.context.register(LiquibaseDataSourceConfiguration.class,
				EmbeddedDataSourceConfiguration.class, LiquibaseAutoConfiguration.class,
				PropertyPlaceholderAutoConfiguration.class);
		this.context.refresh();
		SpringLiquibase liquibase = this.context.getBean(SpringLiquibase.class);
		assertThat(liquibase.getDataSource())
				.isEqualTo(this.context.getBean("liquibaseDataSource"));
	}

	@Configuration
	static class LiquibaseDataSourceConfiguration {

		@Bean
		@Primary
		public DataSource normalDataSource() {
			return DataSourceBuilder.create().url("jdbc:hsqldb:mem:normal").username("sa")
					.build();
		}

		@LiquibaseDataSource
		@Bean
		public DataSource liquibaseDataSource() {
			return DataSourceBuilder.create().url("jdbc:hsqldb:mem:liquibasetest")
					.username("sa").build();
		}

	}

}
