package com.healthcare.service;

import java.util.List;
import java.util.Map;

import com.healthcare.entity.Doctor;

public interface IDoctorService {

	public Long saveDoctor(Doctor spec);
	public List<Doctor> getAllDoctors();
	public void removeDoctor(Long id);
	public Doctor getOneDoctor(Long id);
	public void updateDoctor(Doctor spec);
	
	public Map<Long,String> getDoctorIdAndNames();
	public List<Doctor> findDoctorBySpecId(Long specId);
	
	public long getDoctorCount();
}
