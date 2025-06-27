package com.e_commerce.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.e_commerce.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
