# springboot-user-rest-api-inmemory


##### Features

##### Module 1 : User Module
* Register User
* Login User
* Get User Details for the given id
* Update Password
* Update Profile Details
* Delete User Details for the given id
* Search by Role
* List all users


##### Step 1: Create UserController

```java
package in.taskapp.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.taskapp.model.User;
import in.taskapp.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController {

	
	//@Autowired
	UserService userService = new UserService();
	
	//{ "name":"Naresh", "email":"n@gmail.com", "password":"pass123"} 
	
	
	//insert into users (name,email,password) values ( ?, ?, ?)
	@PostMapping("register")
	public String register(@RequestBody User user) {
		
		//1. Get input values
		System.out.println("UserController => Register Method called");
		System.out.println("User:" + user);
		
		//2. Call Service and pass the input
		userService.register(user);
		
		//3. Return response
		return "SUCCESS";
	}
	
	//select id,name,email from users where email = ? and password = ?
	
	@PostMapping("login")
	public User login(@RequestBody User user) {
		
		//1. Get Input values
		System.out.println("UserController => login method called");
		System.out.println("User:"+  user);
		
		//2. Call service method and pass data
		User result = userService.login(user.getEmail(), user.getPassword());	
		
		//3. Return response
		return result;
	}
	
	
	//select id,name,email from users
	@GetMapping
	public List<User> getAllUsers() {
		return userService.getAllUsers();
	}
	
	//select id,name,email from users where id= ?
	@GetMapping("{id}")
	public User findUser(@PathVariable("id") Integer id) {
		return userService.findOne(id);
	}
	
	
	//Partial update => update users set password = ? where id = ?
	@PatchMapping("{id}/changePassword")
	public void changePassword(@PathVariable("id") Integer id, @RequestBody User user) {
		userService.updatePassword(id, user);
	}
	
	// update users set name =?,email=?,password=? where id = ?
	@PutMapping("{id}")
	public void updateUserDetails(@PathVariable("id") Integer id, @RequestBody User user) {
		userService.updateUserDetails(id, user);
	}
	
	//delete from users where id = ?
	@DeleteMapping("{id}")
	public void deleteUser(@PathVariable("id") Integer id) {
		userService.deleteUser(id);
	}
	
	//search?role=USER
	//search?role=ADMIN
	@GetMapping("search")
	public List<User> getUsers(@RequestParam("role") String role) {
		return userService.searchByRole(role);
	}
	
}
```

##### Step 2: Create Model - User.java
```java
package in.taskapp.model;

public class User {
	
	private Integer id;// int => 0, Integer => null

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private String name;
	
	private String email;
	
	private String password;
	
	private Boolean active;
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	private String role;

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", active="
				+ active + ", role=" + role + "]";
	}

	public User(String name, String email, String password) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public User() {
		super();
	}
	
	
}
```

##### Step 3: Create UserService.java

```js
package in.taskapp.service;

import java.util.ArrayList;
import java.util.List;

import in.taskapp.model.User;

//@Service
public class UserService {

	// Temporary static list ( instead of database )
	// Server restart => data will be cleared from static variable => []
	// inmemory storage
	public static final List<User> usersList = new ArrayList<>();

	public void register(User user) {
		System.out.println("UserService => register => ");
		System.out.println("User:" + user);

		// Generate id;
		int id = usersList.size() + 1;
		user.setId(id);

		// Activate account during registration
		user.setActive(true);
		
		//if role is not present, set as "USER" role
		if(user.getRole() == null) {
			user.setRole("USER");
		}

		// 1. Save the data
		usersList.add(user);

	}

	/**
	 * Check whether the given email/password exists
	 * 
	 * @param email
	 * @param password
	 * @return
	 */
	public User login(String email, String password) {

		User searchResult = null;

		for (User user : usersList) {
			if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
				searchResult = user;
				break;
			}
		}

		return searchResult;
	}

	public List<User> getAllUsers() {
		return usersList;
	}

	public User findOne(Integer id) {

		User searchResult = null;

		for (User user : usersList) {
			if (user.getId() == id) {
				searchResult = user;
				break;
			}
		}

		return searchResult;

	}

	public void deleteUser(Integer id) {

		User searchResult = null;

		// find index position
		int i = 0;
		for (User user : usersList) {
			if (user.getId() == id) {
				searchResult = user;
				break;
			}
			i++;
		}

		if (searchResult != null) {
			User user = usersList.get(i); // Get the record from that searched index
			user.setActive(false);
		}

	}

	public void updatePassword(Integer id, User userDTO) {

		User searchResult = null;

		// find index position
		int i = 0;
		for (User user : usersList) {
			if (user.getId() == id) {
				searchResult = user;
				break;
			}
			i++;
		}

		if (searchResult != null) {
			User user = usersList.get(i); // Get the record from that searched index
			user.setPassword(userDTO.getPassword()); //Set New Password
		}
	}

	public void updateUserDetails(Integer id, User userDTO) {
		
		User searchResult = null;

		// find index position
		int i = 0;
		for (User user : usersList) {
			if (user.getId() == id) {
				searchResult = user;
				break;
			}
			i++;
		}

		if (searchResult != null) {
			User user = usersList.get(i); // Get the record from that searched index
			
			if(userDTO.getName() != null) {
				user.setName(userDTO.getName());
			}
			
			if(userDTO.getEmail() != null) {
				user.setEmail(userDTO.getEmail());
			}
			
			if(userDTO.getPassword() != null ) {
				user.setPassword(userDTO.getPassword()); //Set New Password	
			}
			
		}
	}

	public List<User> searchByRole(String role) {
	
		
		List<User> searchResult = new ArrayList<>();

		for (User user : usersList) {
			if (user.getRole().equalsIgnoreCase(role)) {
				searchResult.add(user);				
			}
		}

		return searchResult;
	}

}
```
