package com.healthcare.service;

import java.util.List;

import com.healthcare.entity.Patient;

public interface IPatientService {

	public Long savePatient(Patient spec);
	public List<Patient> getAllPatients();
	public void removePatient(Long id);
	public Patient getOnePatient(Long id);
	public void updatePatient(Patient spec);
	public Patient getOneByEmail(String email);
	
	public long getPatientCount();
}
