package com.example.user_service.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.user_service.user.domain.User;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
	boolean existsByEmail(String email);
}
