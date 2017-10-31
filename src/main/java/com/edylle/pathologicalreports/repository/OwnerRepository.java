package com.edylle.pathologicalreports.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edylle.pathologicalreports.model.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, String> {

	@Query(value = "SELECT * FROM owner o WHERE o.id LIKE :id AND o.name LIKE :name ORDER BY o.name \n#pageable\n",
		   countQuery = "SELECT COUNT(*) FROM owner o WHERE o.id LIKE :id AND o.name LIKE :name",
		   nativeQuery = true)
	Page<Owner> findBy(@Param("id") String id, @Param("name") String name, Pageable pageable);

}
