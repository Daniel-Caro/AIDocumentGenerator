package com.ABADCO.AIDocumentGenerator.data;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ABADCO.AIDocumentGenerator.model.pojo.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	public Optional<User> findById(Long userid);

}
