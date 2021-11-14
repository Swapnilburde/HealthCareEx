package com.healthcare.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.healthcare.entity.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

	
	@Query("SELECT id, firstName, lastName FROM Doctor")
	List<Object []> getDoctorIdAndNames();
	
	@Query("SELECT doc FROM Doctor doc INNER JOIN Specialization spc "
			+ "ON doc.specialization = spc WHERE spc.id = :specId")
	List<Doctor> findDoctorBySpecId(Long specId);
}
