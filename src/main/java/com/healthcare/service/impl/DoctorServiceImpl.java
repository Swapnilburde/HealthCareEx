package com.healthcare.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.constants.UserRoles;
import com.healthcare.entity.Doctor;
import com.healthcare.entity.User;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.repo.DoctorRepository;
import com.healthcare.service.IDoctorService;
import com.healthcare.service.IUserService;
import com.healthcare.util.MyCollectionsUtil;
import com.healthcare.util.UserUtil;

@Service
public class DoctorServiceImpl implements IDoctorService{
	
	@Autowired
	private DoctorRepository repo;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private UserUtil util;
	
	@Transactional
	@Override
	public Long saveDoctor(Doctor doc) {
		Long id= repo.save(doc).getId();
		if(id!=null) {
			User user = new User();
			user.setDisplayName(doc.getFirstName()+" "+doc.getLastName());
			user.setUsername(doc.getEmail());
			user.setPassword(util.genPwd());
			user.setRole(UserRoles.DOCTOR.name());
			userService.saveUser(user);
			// TODO : Email part is pending
		}
		return id;
	}

	@Override
	public List<Doctor> getAllDoctors() {
		return repo.findAll();
	}

	@Override
	public void removeDoctor(Long id) {
		repo.delete(getOneDoctor(id));
	}

	@Override
	public Doctor getOneDoctor(Long id) {
		Optional<Doctor> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new DoctorNotFoundException(id+ " Not Found");
		}
	}

	@Transactional
	@Override
	public void updateDoctor(Doctor spec) {
		repo.save(spec);
	}

	@Override
	public Map<Long, String> getDoctorIdAndNames() {
		List<Object[]> list = repo.getDoctorIdAndNames();
		return MyCollectionsUtil.convertToMapIndex(list);
	}

	@Override
	public List<Doctor> findDoctorBySpecId(Long specId) {
		return repo.findDoctorBySpecId(specId);
	}
	
	@Override
	public long getDoctorCount() {
		return repo.count();
	}
}
