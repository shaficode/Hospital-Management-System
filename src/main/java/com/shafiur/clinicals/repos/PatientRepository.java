package com.shafiur.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shafiur.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
