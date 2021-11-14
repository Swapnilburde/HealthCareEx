package com.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcare.entity.Patient;
import com.healthcare.exception.PatientNotFoundException;
import com.healthcare.service.IPatientService;
import com.healthcare.service.ISpecializationService;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private IPatientService service;

	@Autowired
	private ISpecializationService specService;

	private void createDynamicUi(Model model) {
		model.addAttribute("specialization", specService.getSpacIdAndName());
	}

	@GetMapping("/register")
	public String displayRegister(Model model,@RequestParam(name = "message",required = false) String message) {
		createDynamicUi(model);
		model.addAttribute("message", message);
		return "PatientRegister";
	}

	//2. save on submit
	@PostMapping("/save")
	public String save(
			@ModelAttribute Patient Patient,
			RedirectAttributes attributes
			)
	{
		Long id = service.savePatient(Patient);
		String message="Patient ("+id+") is created";
		attributes.addAttribute("message", message);
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String getAllPatient(
			@RequestParam(value = "message",required = false)String message,
			Model model) {
		List<Patient> list = service.getAllPatients();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "PatientData";
	}

	@GetMapping("/edit")
	public String getOnePatient(
			@RequestParam Long id,
			Model model,
			RedirectAttributes attributes) {
		String page = null;
		try {
			Patient doc = service.getOnePatient(id);
			model.addAttribute("Patient", doc);
			createDynamicUi(model);
			page = "PatientEdit";
		} catch (PatientNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updatePatient(@ModelAttribute Patient Patient,RedirectAttributes attributes) {
		service.updatePatient(Patient);
		String message="Patient '"+Patient.getId()+"' updated.";
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

	@GetMapping("/delete")
	public String deletePatient(
			@RequestParam Long id,
			RedirectAttributes attributes) {
		try {
			service.removePatient(id);
			attributes.addAttribute("message", "Patient "+id+" removed");
		}catch(PatientNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}

}
