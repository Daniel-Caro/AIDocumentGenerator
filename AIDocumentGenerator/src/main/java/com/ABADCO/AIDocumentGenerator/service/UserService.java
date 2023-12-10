package com.ABADCO.AIDocumentGenerator.service;

import java.util.List;

import com.ABADCO.AIDocumentGenerator.model.pojo.User;

public interface UserService {

	User getUserById(String userid);

	List<User> getUsersByCombinedSearch(String username, String email);

	User createUser(String username, String email, String password);

	User updateUser(String userid, String username, String email, String password);

	Boolean deleteUser(String userid);

	String checkLogin(String email, String password);

	User getUserByUUID(String userCookie);

}
