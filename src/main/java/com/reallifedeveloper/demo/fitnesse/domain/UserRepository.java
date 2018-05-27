package com.reallifedeveloper.demo.fitnesse.domain;

public interface UserRepository {

	User findByUserId(String userId);

	<U extends User> U save(U user);

}
