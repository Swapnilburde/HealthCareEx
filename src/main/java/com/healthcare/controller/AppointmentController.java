package com.healthcare.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcare.entity.Appointment;
import com.healthcare.entity.Doctor;
import com.healthcare.exception.AppointmentNotFoundException;
import com.healthcare.service.IAppointmentService;
import com.healthcare.service.IDoctorService;
import com.healthcare.service.ISpecializationService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	@Autowired
	private IAppointmentService service;

	@Autowired
	private IDoctorService doctorService;
	
	@Autowired
	private ISpecializationService specializationService;

	private void commonUi(Model model) {
		model.addAttribute("doctors", doctorService.getDoctorIdAndNames());
	}

	@GetMapping("/register")
	public String registerAppointment(Model model) {
		model.addAttribute("appointment",new Appointment());
		commonUi(model);
		return "AppointmentRegister";
	}

	@PostMapping("/save")
	public String saveAppointment(@ModelAttribute Appointment appointment, Model model) {
		java.lang.Long id=service.saveAppointment(appointment);
		model.addAttribute("message","Appointment created with Id:"+id);
		model.addAttribute("appointment",new Appointment()) ;
		commonUi(model);
		return "AppointmentRegister";
	}

	@GetMapping("/all")
	public String getAllAppointments(Model model,
			@RequestParam(value = "message", required = false) String message) {
		List<Appointment> list=service.getAllAppointments();
		model.addAttribute("list",list);
		model.addAttribute("message",message);
		return "AppointmentData";
	}

	@GetMapping("/delete")
	public String deleteAppointment(@RequestParam Long id, RedirectAttributes attributes) {
		try {
			service.removeAppointment(id);
			attributes.addAttribute("message","Appointment deleted with Id:"+id);
		} catch(AppointmentNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
		}
		return "redirect:all";
	}

	@GetMapping("/edit")
	public String editAppointment(@RequestParam Long id, Model model, RedirectAttributes attributes) {
		String page=null;
		try {
			Appointment ob=service.getOneAppointment(id);
			model.addAttribute("appointment",ob);
			commonUi(model);
			page="AppointmentEdit";
		} catch(AppointmentNotFoundException e) {
			e.printStackTrace() ;
			attributes.addAttribute("message",e.getMessage());
			page="redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateAppointment(@ModelAttribute Appointment appointment,
			RedirectAttributes attributes) {
		service.updateAppointment(appointment);
		attributes.addAttribute("message","Appointment updated");
		return "redirect:all";
	}
	
	@GetMapping("/view")
	public String viewSlot(@RequestParam(required = false,defaultValue = "0") Long specId, Model model) {
		Map<Long, String> specMap = specializationService.getSpacIdAndName();
		model.addAttribute("specializations", specMap);
		
		List<Doctor> docList=null;
		String message=null;
		if(specId==0) {
			docList=doctorService.getAllDoctors();
			message="Result : All doctors";
		}else {
			docList=doctorService.findDoctorBySpecId(specId);
			message="Result : "+ specializationService.getOneSpecialization(specId).getSpecName() +" doctors";
		}
		model.addAttribute("docList", docList);
		model.addAttribute("message", message);
		
		return "AppointmentSearch";
	}
	
	@GetMapping("/viewslots")
	public String viewSoltsAppointment(@RequestParam Long id, Model model) {
		List<Object[]> list = service.getAppoinmentsByDoctor(id);
		model.addAttribute("list", list);
		Doctor doc = doctorService.getOneDoctor(id);
		model.addAttribute("message", "RESULTS SHOWING FOR : " + doc.getFirstName()+" "+doc.getLastName());
		return "AppointmentSlots";
	}

	@GetMapping("/currectDoc")
	public String getCurrectDoctorAppointments(Model model,Principal p) {
		List<Object[]> list = service.getAppoinmentsByDoctorEmail(p.getName());
		model.addAttribute("list", list);
		return "AppointmentForDoctor";
	}
	
	
}