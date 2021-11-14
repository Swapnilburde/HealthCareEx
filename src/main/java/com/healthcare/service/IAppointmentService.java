package com.healthcare.service;

import java.util.List;

import com.healthcare.entity.Appointment;

public interface IAppointmentService {

	public Long saveAppointment(Appointment app);
	public List<Appointment> getAllAppointments();
	public void removeAppointment(Long id);
	public Appointment getOneAppointment(Long id);
	public void updateAppointment(Appointment app);
	public List<Object[]> getAppoinmentsByDoctor(Long docId);
	public List<Object[]> getAppoinmentsByDoctorEmail(String email);
	public void updateSlotCountForAppoinment(Long id,Integer count);
	
	public long getAppointmentCount();
}
