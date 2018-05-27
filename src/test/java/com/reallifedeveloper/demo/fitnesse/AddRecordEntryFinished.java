package com.reallifedeveloper.demo.fitnesse;

import com.reallifedeveloper.demo.fitnesse.domain.Patient;
import com.reallifedeveloper.demo.fitnesse.domain.PatientRepository;
import com.reallifedeveloper.demo.fitnesse.domain.RecordService;
import com.reallifedeveloper.demo.fitnesse.domain.User;
import com.reallifedeveloper.demo.fitnesse.domain.UserRepository;
import com.reallifedeveloper.tools.test.fitnesse.AbstractFitNesseFixture;

public class AddRecordEntryFinished extends AbstractFitNesseFixture {

	private User user;
	private Patient patient;
	private String recordEntry;
	private String errorMessage = "";

	private UserRepository userRepository;
	private PatientRepository patientRepository;
	private RecordService recordService;

	public AddRecordEntryFinished() {
		super("META-INF/spring-context-rld-fitnesse-demo-func-test.xml");
		this.userRepository = getBean(UserRepository.class);
		this.patientRepository = getBean(PatientRepository.class);
		this.recordService = getBean(RecordService.class);
	}

	public void setUserId(String userId) {
		this.user = userRepository.findByUserId(userId);
	}

	public void setPatientId(String patientId) {
		this.patient = patientRepository.findByPatientId(patientId);
	}

	public void setEntry(String recordEntry) {
		this.recordEntry = recordEntry;
	}

	public void execute() {
		try {
			recordService.addRecordEntry(user, patient, recordEntry);
		} catch (IllegalArgumentException e) {
			this.errorMessage = e.getMessage();
		}
	}

	public void reset() {
		this.errorMessage = "";
	}

	public String errorMessage() {
		return errorMessage;
	}}
