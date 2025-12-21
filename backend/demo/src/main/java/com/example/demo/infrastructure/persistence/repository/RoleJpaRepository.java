package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleJpaRepository extends JpaRepository<RoleEntity, Long>, JpaSpecificationExecutor<RoleEntity> {
    boolean existsByName(String name);
}

