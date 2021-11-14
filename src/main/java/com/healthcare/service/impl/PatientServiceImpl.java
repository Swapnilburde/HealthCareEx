package com.healthcare.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.constants.UserRoles;
import com.healthcare.entity.Patient;
import com.healthcare.entity.User;
import com.healthcare.exception.PatientNotFoundException;
import com.healthcare.repo.PatientRepository;
import com.healthcare.service.IPatientService;
import com.healthcare.service.IUserService;
import com.healthcare.util.UserUtil;

@Service
public class PatientServiceImpl implements IPatientService{
	
	@Autowired
	private PatientRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil util;
	
	@Transactional
	@Override
	public Long savePatient(Patient patient) {
		Long id = repo.save(patient).getId();
		if(id!=null) {
			User user = new User();
			user.setDisplayName(patient.getFirstName()+" "+patient.getLastName());
			user.setUsername(patient.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.PATIENT.name());
			userService.saveUser(user);
			// TODO : Email part is pending
		}
		return id;
	}

	@Override
	public List<Patient> getAllPatients() {
		return repo.findAll();
	}

	@Override
	public void removePatient(Long id) {
		repo.delete(getOnePatient(id));
	}

	@Override
	public Patient getOnePatient(Long id) {
		Optional<Patient> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new PatientNotFoundException(id+ " Not Found");
		}
	}
	
	@Transactional
	@Override
	public void updatePatient(Patient patient) {
		repo.save(patient);
	}
	
	@Override
	public Patient getOneByEmail(String email) {
		return repo.findByEmail(email).get();
	}
	
	@Override
	public long getPatientCount() {
		return repo.count();
	}
}
