package com.reallifedeveloper.demo.fitnesse.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reallifedeveloper.demo.fitnesse.domain.User;
import com.reallifedeveloper.demo.fitnesse.domain.UserRepository;

public interface JpaUserRepository extends UserRepository, JpaRepository<User, String> {

}
