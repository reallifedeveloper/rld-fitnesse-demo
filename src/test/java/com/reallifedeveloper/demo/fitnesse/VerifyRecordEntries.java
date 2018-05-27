package com.reallifedeveloper.demo.fitnesse;

import com.reallifedeveloper.demo.fitnesse.domain.Record;
import com.reallifedeveloper.demo.fitnesse.domain.RecordService;
import com.reallifedeveloper.tools.test.fitnesse.AbstractFitNesseFixture;

public class VerifyRecordEntries extends AbstractFitNesseFixture {

	private String patientId;
	private Integer index;

	private Record record;

	private RecordService recordService;

	public VerifyRecordEntries() {
		super("META-INF/spring-context-rld-fitnesse-demo-func-test.xml");
		this.recordService = getBean(RecordService.class);
	}

	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void execute() {
		if (index == null) {
			throw new IllegalArgumentException("You must specify index (0, 1, 2, ...) using the column 'index'");
		}
		if (index == 0) {
			this.record = recordService.findRecordForPatient(patientId);
		}
	}

	public String entry() {
		if (record == null) {
			throw new IllegalArgumentException("Record for patient with ID " + patientId + " is null");
		}
		if (record.entries() == null) {
			throw new IllegalArgumentException("Record for patient with ID " + patientId + " has no entries");
		}
		if (record.entries().get(index) == null) {
			throw new IllegalArgumentException("Record " + index + " for patient with ID " + patientId + " is null");
		}
		return record.entries().get(index).entry();
	}
}
