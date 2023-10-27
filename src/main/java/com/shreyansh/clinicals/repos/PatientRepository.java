package com.shreyansh.clinicals.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shreyansh.clinicals.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, Integer> {

}
