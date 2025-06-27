package com.e_commerce.e_commerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.e_commerce.e_commerce.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
