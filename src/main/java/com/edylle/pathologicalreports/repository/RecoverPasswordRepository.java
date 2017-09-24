package com.edylle.pathologicalreports.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edylle.pathologicalreports.model.entity.RecoverPassword;
import com.edylle.pathologicalreports.model.entity.User;

public interface RecoverPasswordRepository extends JpaRepository<RecoverPassword, String> {

	List<RecoverPassword> findByUser(User uer);

	@Modifying(clearAutomatically = true)
	@Query(value = "DELETE FROM recover_password WHERE date_requested < (:date_requested);", nativeQuery = true)
	void deleteByDateRequested(@Param("date_requested") Date dateRequested);

}
