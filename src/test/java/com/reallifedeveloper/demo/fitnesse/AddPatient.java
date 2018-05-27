package com.reallifedeveloper.demo.fitnesse;

import com.reallifedeveloper.demo.fitnesse.domain.Patient;
import com.reallifedeveloper.demo.fitnesse.domain.PatientRepository;
import com.reallifedeveloper.tools.test.fitnesse.AbstractFitNesseFixture;

public class AddPatient extends AbstractFitNesseFixture {

	private String patientId;
	private String firstName;
	private String lastName;

	private PatientRepository patientRepository;

	public AddPatient() {
		super("META-INF/spring-context-rld-fitnesse-demo-func-test.xml");
		this.patientRepository = getBean(PatientRepository.class);
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void execute() {
		Patient patient = new Patient(patientId, firstName, lastName);
		patientRepository.save(patient);
	}
}
