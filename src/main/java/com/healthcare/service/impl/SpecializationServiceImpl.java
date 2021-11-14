package com.healthcare.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.healthcare.entity.Specialization;
import com.healthcare.exception.SpecializationNotFoundException;
import com.healthcare.repo.SpecializationRepository;
import com.healthcare.service.ISpecializationService;
import com.healthcare.util.MyCollectionsUtil;

@Service
public class SpecializationServiceImpl implements ISpecializationService {

	@Autowired
	private SpecializationRepository repo;
	
	@Transactional
	@Override
	public Long saveSpecialization(Specialization spec) {
		return repo.save(spec).getId();
	}

	@Override
	public List<Specialization> getAllSpecializations() {
		return repo.findAll();
	}
	
	@Override
	public Page<Specialization> getAllSpecializations(Pageable pageable) {
		return repo.findAll(pageable);
	}

	@Override
	public void removeSpecialization(Long id) {
		repo.delete(getOneSpecialization(id));
	}

	@Override
	public Specialization getOneSpecialization(Long id) {
		Optional<Specialization> optional = repo.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		} else {
			throw new SpecializationNotFoundException(id+ " Not Found");
		}
	}

	@Transactional
	@Override
	public void updateSpecialization(Specialization spec) {
		repo.save(spec);
	}

	@Override
	public boolean isSpecCodeExist(String specCode) {
		return repo.getSpecCodeCount(specCode)>0;
	}

	@Override
	public boolean isSpecNameExist(String specName) {
		return repo.getSpecNameCount(specName)>0;
	}

	@Override
	public boolean isSpecCodeExistEdit(String specCode, Long id) {
		return repo.getSpecCodeCountForEdit(specCode, id)>0;
	}

	@Override
	public boolean isSpecNameExistEdit(String specName, Long id) {
		return repo.getSpecNameCountForEdit(specName, id)>0;
	}

	@Override
	public Map<Long, String> getSpacIdAndName() {
		List <Object[]> list=repo.getSpacIdAndName();
		return MyCollectionsUtil.convertListToMap(list);
	}

	@Override
	public long getSpecializationCount() {
		return repo.count();
	}
}
