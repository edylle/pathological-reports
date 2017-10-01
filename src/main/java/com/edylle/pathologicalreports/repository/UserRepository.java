package com.edylle.pathologicalreports.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edylle.pathologicalreports.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	User findByEmail(String email);

	@Query(value = "SELECT * FROM user u JOIN role_user r ON u.username = r.username_user AND r.role LIKE :role WHERE u.username LIKE :usernameOrEmail OR u.email LIKE :usernameOrEmail ORDER BY u.username ASC", nativeQuery = true)
	List<User> findBy(@Param("role") String roleName, @Param("usernameOrEmail") String usernameOrEmail);

}
