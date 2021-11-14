package com.healthcare.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.healthcare.entity.Specialization;

public interface ISpecializationService {

	public Long saveSpecialization(Specialization spec);
	public List<Specialization> getAllSpecializations();
	public Page<Specialization> getAllSpecializations(Pageable pageable);
	public void removeSpecialization(Long id);
	public Specialization getOneSpecialization(Long id);
	public void updateSpecialization(Specialization spec);
	
	public boolean isSpecCodeExist(String specCode); 
	public boolean isSpecNameExist(String specName); 
	public boolean isSpecCodeExistEdit(String specCode,Long id); 
	public boolean isSpecNameExistEdit(String specName,Long id);
	
	public Map<Long,String>getSpacIdAndName();
	
	public long getSpecializationCount();
}
