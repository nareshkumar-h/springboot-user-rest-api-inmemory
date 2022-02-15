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
