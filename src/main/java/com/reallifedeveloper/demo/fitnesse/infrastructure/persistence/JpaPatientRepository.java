package com.reallifedeveloper.demo.fitnesse.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reallifedeveloper.demo.fitnesse.domain.Patient;
import com.reallifedeveloper.demo.fitnesse.domain.PatientRepository;

public interface JpaPatientRepository extends PatientRepository, JpaRepository<Patient, String> {

}
