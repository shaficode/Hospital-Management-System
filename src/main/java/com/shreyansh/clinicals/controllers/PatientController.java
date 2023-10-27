package com.shreyansh.clinicals.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shreyansh.clinicals.model.ClinicalData;
import com.shreyansh.clinicals.model.Patient;
import com.shreyansh.clinicals.repos.PatientRepository;
import com.shreyansh.clinicals.util.BMICalculator;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class PatientController {
	
	private PatientRepository repository;
	Map<String,String> filters = new HashMap<>();
	
	@Autowired
	PatientController(PatientRepository repository){
		this.repository = repository;
	}
	
	@RequestMapping(value="/patients", method=RequestMethod.GET)
	public List<Patient> getpatients(){
		return repository.findAll();
		
	}
	
	@RequestMapping(value="patients/{id}", method=RequestMethod.GET)
	public Patient getPatient(@PathVariable("id")int id) {
		return repository.findById(id).get();
	}
	
	@RequestMapping(value="patients", method=RequestMethod.POST)
	public Patient savePatient(@RequestBody Patient patient) {
		return repository.save(patient);
	}
	
	@RequestMapping(value = "/patients/analyze/{id}", method = RequestMethod.GET)
	public Patient analyse(@PathVariable("id") int id) {
		Patient patient = repository.findById(id).get();
		List<ClinicalData> clinicalData = patient.getClinicalData();
		List<ClinicalData> duplicateClinicalData = new ArrayList<>(patient.getClinicalData());
		for (ClinicalData eachEntry : duplicateClinicalData) {
			
			if (filters.containsKey(eachEntry.getComponentName())) {
				clinicalData.remove(eachEntry);
				continue;
			}else {
				filters.put(eachEntry.getComponentName(), null);
			}
			BMICalculator.calculateBMI(clinicalData, eachEntry);
		}
		filters.clear();
		return patient;
	}

}
