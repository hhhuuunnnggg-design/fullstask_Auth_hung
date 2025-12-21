package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {
    UserEntity findByEmail(String email);
    UserEntity findByRefreshTokenAndEmail(String token, String email);
    boolean existsByEmail(String email);
}

