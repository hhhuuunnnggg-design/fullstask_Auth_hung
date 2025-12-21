package com.example.demo.domain.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.domain.entity.User;

public interface UserRepository {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshTokenAndEmail(String token, String email);

    boolean existsByEmail(String email);

    void deleteById(Long id);

    List<User> findAll();

    List<User> findAllWithSpecification(Object spec, int page, int size);

    long count();
}
