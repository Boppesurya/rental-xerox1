package com.xerox.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xerox.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	 List<User> findByRole(String role);
	    Optional<User> findByUsername(String username);
		Object findByEmail(String email);

}
