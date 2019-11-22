package com.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mvc.entity.Role;
import com.mvc.entity.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	List<Role> getRoleByUser(User user);
}