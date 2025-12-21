package com.example.demo.infrastructure.persistence.adapter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.example.demo.domain.entity.User;
import com.example.demo.domain.port.UserRepositoryPort;
import com.example.demo.infrastructure.persistence.entity.UserEntity;
import com.example.demo.infrastructure.persistence.mapper.UserEntityMapper;
import com.example.demo.infrastructure.persistence.repository.RoleJpaRepository;
import com.example.demo.infrastructure.persistence.repository.UserJpaRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {
    private final UserJpaRepository jpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public User save(User user) {
        UserEntity entity = UserEntityMapper.toEntity(user, roleJpaRepository);
        UserEntity saved = jpaRepository.save(entity);
        return UserEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return jpaRepository.findById(id)
                .map(UserEntityMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        UserEntity entity = jpaRepository.findByEmail(email);
        return entity != null ? Optional.of(UserEntityMapper.toDomain(entity)) : Optional.empty();
    }

    @Override
    public Optional<User> findByRefreshTokenAndEmail(String token, String email) {
        UserEntity entity = jpaRepository.findByRefreshTokenAndEmail(token, email);
        return entity != null ? Optional.of(UserEntityMapper.toDomain(entity)) : Optional.empty();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAllWithSpecification(Object spec, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Specification<UserEntity> specification = (Specification<UserEntity>) spec;
        Page<UserEntity> pageResult = jpaRepository.findAll(specification, pageable);
        return pageResult.getContent().stream()
                .map(UserEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public long count() {
        return jpaRepository.count();
    }
}
