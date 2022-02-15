package in.taskapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

//java file => .java compile => .class file convertion

//xml => easy to change => no need to compile

//annotations (instead of xml configuration)

//spring boot => beans.xml default configurations internally adds

@Configuration
public class DataSourceConfig {

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}
