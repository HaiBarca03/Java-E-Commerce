package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Address;
import com.e_commerce.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, String> {
    List<Address> findByUserId(String userId);
}
