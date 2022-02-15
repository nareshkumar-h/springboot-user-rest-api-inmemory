package in.taskapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	
	@Autowired
	UserService userService;// = new UserServiceOld();
	
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
