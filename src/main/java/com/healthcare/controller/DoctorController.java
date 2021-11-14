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

import com.healthcare.entity.Doctor;
import com.healthcare.exception.DoctorNotFoundException;
import com.healthcare.service.IDoctorService;
import com.healthcare.service.ISpecializationService;
import com.healthcare.util.EmailUtil;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private EmailUtil email;

	@Autowired
	private IDoctorService service;

	@Autowired
	private ISpecializationService specService;

	private void createDynamicUi(Model model) {
		model.addAttribute("specialization", specService.getSpacIdAndName());
	}

	@GetMapping("/register")
	public String displayRegister(Model model,@RequestParam(name = "message",required = false) String message) {
		createDynamicUi(model);
		model.addAttribute("message", message);
		return "DoctorRegister";
	}

	//2. save on submit
	@PostMapping("/save")
	public String save(
			@ModelAttribute Doctor doctor,
			RedirectAttributes attributes
			)
	{
		Long id = service.saveDoctor(doctor);
		String message="Doctor ("+id+") is created";
		attributes.addAttribute("message", message);
		new Thread(()-> {
			email.send(doctor.getEmail(), "Doctor Info", message);
		}).start();
		return "redirect:register";
	}
	
	@GetMapping("/all")
	public String getAllDoctor(
			@RequestParam(value = "message",required = false)String message,
			Model model) {
		List<Doctor> list = service.getAllDoctors();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "DoctorData";
	}

	@GetMapping("/edit")
	public String getOneDoctor(
			@RequestParam Long id,
			Model model,
			RedirectAttributes attributes) {
		String page = null;
		try {
			Doctor doc = service.getOneDoctor(id);
			model.addAttribute("doctor", doc);
			createDynamicUi(model);
			page = "DoctorEdit";
		} catch (DoctorNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateDoctor(@ModelAttribute Doctor doctor,RedirectAttributes attributes) {
		service.updateDoctor(doctor);
		String message="Doctor '"+doctor.getId()+"' updated.";
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

	@GetMapping("/delete")
	public String deleteDoctor(
			@RequestParam Long id,
			RedirectAttributes attributes) {
		try {
			service.removeDoctor(id);
			attributes.addAttribute("message", "Doctor "+id+" removed");
		}catch(DoctorNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}

}
