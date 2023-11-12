package com.ABADCO.AIDocumentGenerator.data;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ABADCO.AIDocumentGenerator.model.pojo.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findById(Long userid);

	@Query("SELECT u FROM User u WHERE (:username is null or u.username = :username) and (:email is null or u.email = :email)")
	public List<User> findByUsernameAndEmail(String username, String email);

}
