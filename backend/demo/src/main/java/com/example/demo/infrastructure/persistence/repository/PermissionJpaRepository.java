package com.example.demo.infrastructure.persistence.repository;

import com.example.demo.infrastructure.persistence.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionEntity, Long>, JpaSpecificationExecutor<PermissionEntity> {
    boolean existsByModuleAndApiPathAndMethod(String module, String apiPath, String method);
    List<PermissionEntity> findByIdIn(List<Long> id);
}

