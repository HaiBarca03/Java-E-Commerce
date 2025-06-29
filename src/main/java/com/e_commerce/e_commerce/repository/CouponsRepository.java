package com.e_commerce.e_commerce.repository;

import com.e_commerce.e_commerce.entity.Coupons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CouponsRepository extends JpaRepository<Coupons, String> {
    Optional<Coupons> findByCode(String code);
    boolean existsByCode(String code);
    Page<Coupons> findByIsActive(Boolean isActive, Pageable pageable);
}
