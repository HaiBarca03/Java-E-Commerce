package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Category;
import com.e_commerce.e_commerce.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(String name);
}
