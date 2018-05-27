package com.reallifedeveloper.demo.fitnesse.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reallifedeveloper.demo.fitnesse.domain.Role;
import com.reallifedeveloper.demo.fitnesse.domain.RoleRepository;

public interface JpaRoleRepository extends RoleRepository, JpaRepository<Role, String> {

}
