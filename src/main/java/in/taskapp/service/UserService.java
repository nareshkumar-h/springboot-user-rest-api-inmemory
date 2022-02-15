package in.taskapp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.taskapp.dao.UserDAO;
import in.taskapp.model.User;

@Service
public class UserService {

	@Autowired
	UserDAO userDAO;


	public void register(User user) {
		System.out.println("UserService => register => ");
		System.out.println("User:" + user);

		
		// Activate account during registration
		user.setActive(true);

		// if role is not present, set as "USER" role
		if (user.getRole() == null) {
			user.setRole("USER");
		}

		// update createdDate/modifiedDate
		user.setCreatedDate(LocalDateTime.now());
		user.setModifiedDate(LocalDateTime.now());

		// 1. Save the data
		userDAO.save(user);

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

//		for (User user : userDAO.findAll()) {
//			if (user.getEmail().equalsIgnoreCase(email) && user.getPassword().equals(password)) {
//				searchResult = user;
//				break;
//			}
//		}
		searchResult = userDAO.findByEmailAndPassword(email, password);

		return searchResult;
	}

	public List<User> getAllUsers() {
		return userDAO.findAll();
	}

	public User findOne(Integer id) {

		User searchResult = null;

//		//performance issue - BAD Practice
//		for (User user : userDAO.findAll()) {
//			if (user.getId() == id) {
//				searchResult = user;
//				break;
//			}
//		}
		
		searchResult = userDAO.findOne(id);

		return searchResult;

	}

	public void deleteUser(Integer id) {

		User searchResult = userDAO.findOne(id);
		if (searchResult != null) {
			
			userDAO.delete(id);
		}

	}

	public void updatePassword(Integer id, User userDTO) {

		User searchResult = userDAO.findOne(id);
		if (searchResult != null) {
			userDAO.changePassword(id, userDTO.getPassword());
		}
	}

	public void updateUserDetails(Integer id, User userDTO) {

		User searchResult = userDAO.findOne(id);
		if (searchResult != null) {
			

			if (userDTO.getName() != null) {
				searchResult.setName(userDTO.getName());
			}

			if (userDTO.getEmail() != null) {
				searchResult.setEmail(userDTO.getEmail());
			}

			if (userDTO.getPassword() != null) {
				searchResult.setPassword(userDTO.getPassword()); // Set New Password
			}

			searchResult.setModifiedDate(LocalDateTime.now());
			userDAO.update(searchResult);

		}
	}

	public List<User> searchByRole(String role) {

		List<User> searchResult = userDAO.findByRole(role);
		return searchResult;
	}

}
