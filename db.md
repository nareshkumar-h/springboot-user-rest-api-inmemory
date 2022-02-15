##### application.properties
```
spring.datasource.driver-class-name=oracle.jdbc.driver.OracleDriver
spring.datasource.url=jdbc:oracle:thin:@localhost:1521:XE
spring.datasource.username=yourusername
spring.datasource.password=yourpassword

```

##### DataSourceConfiguration => JdbcTemplate Configuration

```java
package in.taskapp.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class DataSourceConfig {

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}
}

```


##### Create UserDAO
```java
package in.taskapp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import in.taskapp.model.User;

@Repository
public class UserDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	/**
	 * This method used to register user details
	 * @param user
	 * @return 
	 */
	public boolean save(User user) {
		
		String query = "insert into taskapp_users(id,name,email,password,role) values ( taskapp_users_id_seq.nextval, ?,?,?,?)";
		String role = user.getRole() != null ? user.getRole() : "USER";
		int rows = jdbcTemplate.update(query, user.getName(), user.getEmail(), user.getPassword(),role);
		return rows == 1;
	}
	
	public List<User> findAll() {
		String query = "select id,name,email,role,active from taskapp_users";
		
		List<User> users = jdbcTemplate.query(query, (rs,rowNo)->{
			User user = toRow(rs);
			return user;
		});
		return users;
	}
	
	public List<User> findByRole(String role) {
		String query = "select id,name,email,role,active from taskapp_users where role = ?";
		
		List<User> users = jdbcTemplate.query(query, (rs,rowNo)->{
			User user = toRow(rs);
			return user;
		}, role);
		return users;
	}

	private User toRow(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setRole(rs.getString("role"));
		user.setActive(rs.getBoolean("active"));
		return user;
	}
	
	public User findOne(Integer id) {
		String query = "select id,name,email,role,active from taskapp_users where id = ?";
		
		User userObj = null;
		
		try {
			userObj = jdbcTemplate.queryForObject(query, (rs,rowNo)->{
				User user = toRow(rs);
				return user;
			}, id);
		} catch (DataAccessException e) {
			
		}
		return userObj;
	}
	
	public User findByEmailAndPassword(String email, String password) {
		String query = "select id,name,email,role,active from taskapp_users where email = ? and password = ?";
		
		User userObj = null;
		
		try {
			userObj = jdbcTemplate.queryForObject(query, (rs, rowNo) -> {
				User user = toRow(rs);
				return user;
			}, email, password);
		}
		catch(DataAccessException e) {
			
		}
		return userObj;
	}
	
	public boolean changePassword(Integer id, String password) {
		String query = "update taskapp_users set password = ? where id = ?";
		int rows = jdbcTemplate.update(query, password , id);
		return rows != 0;
	}
	
	public boolean update(User user) {
		String query = "update taskapp_users set name = ? , email = ?, password = ?, role = ? where id = ?";
		int rows = jdbcTemplate.update(query,user.getName(), user.getEmail(), user.getPassword(), user.getRole(), user.getId());
		return rows != 0;
	}

	public boolean delete(Integer id) {
		String query = "update taskapp_users set active=? where id = ?";
		Boolean active = false;
		int rows = jdbcTemplate.update(query, active , id);
		return rows != 0;
	}
	
}

```
