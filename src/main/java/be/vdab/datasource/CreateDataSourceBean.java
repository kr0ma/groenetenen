package be.vdab.datasource;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@PropertySource("classpath:/dao.properties")
public class CreateDataSourceBean {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Autowired
	private Environment environment;

	@Bean
	DataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		if (environment.containsProperty("OPENSHIFT_MYSQL_DB_HOST")) {
			// uitvoering op OpenShift
			dataSource.setJdbcUrl("jdbc:mysql://" + environment.getProperty("OPENSHIFT_MYSQL_DB_HOST") + ':'
					+ environment.getProperty("OPENSHIFT_MYSQL_DB_PORT") + '/'
					+ environment.getProperty("openshift.database"));
			dataSource.setDriverClassName(environment.getProperty("openshift.driverClassName"));
			dataSource.setUsername(environment.getProperty("OPENSHIFT_MYSQL_DB_USERNAME"));
			dataSource.setPassword(environment.getProperty("OPENSHIFT_MYSQL_DB_PASSWORD"));
		} else {
			dataSource.setDriverClassName(environment.getProperty("local.driverClassName"));
			dataSource.setJdbcUrl(environment.getProperty("local.jdbcURL"));
			dataSource.setUsername(environment.getProperty("local.userName"));
			dataSource.setPassword(environment.getProperty("local.password"));
		}
		return dataSource;
	}
}
