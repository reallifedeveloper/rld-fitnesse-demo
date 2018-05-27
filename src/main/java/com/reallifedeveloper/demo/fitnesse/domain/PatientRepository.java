package com.reallifedeveloper.demo.fitnesse.domain;

public interface PatientRepository {

	Patient findByPatientId(String patientId);

	<P extends Patient> P save(P patient);

}
