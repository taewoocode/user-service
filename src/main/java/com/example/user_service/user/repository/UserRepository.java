package com.example.user_service.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.member_service.member.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
