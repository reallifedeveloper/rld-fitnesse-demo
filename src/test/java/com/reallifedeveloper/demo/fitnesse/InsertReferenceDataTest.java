package com.reallifedeveloper.demo.fitnesse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.reallifedeveloper.demo.fitnesse.domain.RoleRepository;
import com.reallifedeveloper.demo.fitnesse.domain.UserRepository;
import com.reallifedeveloper.demo.fitnesse.infrastructure.persistence.JpaRoleRepository;
import com.reallifedeveloper.demo.fitnesse.infrastructure.persistence.JpaUserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:META-INF/spring-context-rld-fitnesse-demo-func-test.xml" })
public class InsertReferenceDataTest {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private UserRepository userRepository;

	@Before
	public void init() {
		((JpaUserRepository) userRepository).deleteAll();
		((JpaRoleRepository) roleRepository).deleteAll();
	}

	@Test
	public void execute() throws Exception {
		InsertReferenceData fixture = new InsertReferenceData();
		fixture.setFilename("/dbunit/role.xml");
		fixture.execute();
		Assert.assertNotNull("Role DOCTOR should be in the database", roleRepository.findByRoleId("DOCTOR"));
	}
}
