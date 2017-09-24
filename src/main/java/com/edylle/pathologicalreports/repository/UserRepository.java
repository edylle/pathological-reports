package com.edylle.pathologicalreports.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.edylle.pathologicalreports.model.entity.User;

public interface UserRepository extends JpaRepository<User, String> {

	User findByUsername(String username);

	User findByEmail(String email);

}
