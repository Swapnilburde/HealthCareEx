package com.healthcare.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.entity.Appointment;
import com.healthcare.exception.AppointmentNotFoundException;
import com.healthcare.repo.AppointmentRepository;
import com.healthcare.service.IAppointmentService;

@Service
public class AppointmentServiceImpl implements IAppointmentService{

	@Autowired
	private AppointmentRepository repo;

	@Transactional
	@Override
	public Long saveAppointment(Appointment app) {
		return repo.save(app).getId();
	}

	@Override
	public List<Appointment> getAllAppointments() {
		return repo.findAll();
	}

	@Override
	public void removeAppointment(Long id) {
		repo.delete(getOneAppointment(id));
	}

	@Override
	public Appointment getOneAppointment(Long id) {
		Optional<Appointment> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new AppointmentNotFoundException(id+ " Not Found");
		}
	}

	@Transactional
	@Override
	public void updateAppointment(Appointment app) {
		repo.save(app);
	}

	@Override
	public List<Object[]> getAppoinmentsByDoctor(Long docId) {
		return repo.getAppointmentsByDoctor(docId);
	}

	@Override
	public List<Object[]> getAppoinmentsByDoctorEmail(String email) {
		return repo.getAppoinmentsByDoctorEmail(email);
	}
	
	@Transactional
	@Override
	public void updateSlotCountForAppoinment(Long id, Integer count) {
		repo.updateSlotCountForAppoinment(id, count);
	}
	
	@Override
	public long getAppointmentCount() {
		return repo.count();
	}
}
