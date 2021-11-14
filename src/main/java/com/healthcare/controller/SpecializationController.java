package com.healthcare.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcare.entity.Specialization;
import com.healthcare.exception.SpecializationNotFoundException;
import com.healthcare.service.ISpecializationService;
import com.healthcare.view.SpacializationPdfView;
import com.healthcare.view.SpecializationExcelView;

@Controller
@RequestMapping("/spec")
public class SpecializationController {

	@Autowired
	private ISpecializationService service;

	@GetMapping("/register")
	public String displayRegister() {
		return "SpecializationRegister";
	}

	@PostMapping("/save")
	public String saveSpecialization(@ModelAttribute Specialization specialization, Model model) {
		Long id= service.saveSpecialization(specialization);
		String message="Specialization '"+id+"' saved.";
		model.addAttribute("message", message);
		return "SpecializationRegister";
	}

	//@GetMapping("/all")
	public String getAllSpecialization(
			@RequestParam(value = "message",required = false)String message,
			Model model) {
		List<Specialization> list = service.getAllSpecializations();
		model.addAttribute("list", list);
		model.addAttribute("message", message);
		return "SpecializationData";
	}

	@GetMapping("/all")
	public String getAllSpecializationPage(
			@PageableDefault(page = 0,size = 2)Pageable pageable,
			@RequestParam(value = "message",required = false)String message,
			Model model) {
		Page<Specialization> page = service.getAllSpecializations(pageable);
		model.addAttribute("list", page.getContent());
		model.addAttribute("page", page);
		model.addAttribute("message", message);
		return "SpecializationData";
	}

	
	
	@GetMapping("/edit")
	public String getOneSpecialization(
			@RequestParam Long id,
			Model model,
			RedirectAttributes attributes) {
		String page = null;
		try {
			Specialization spec = service.getOneSpecialization(id);
			model.addAttribute("specialization", spec);
			page = "SpecializationEdit";
		} catch (SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
			page = "redirect:all";
		}
		return page;
	}

	@PostMapping("/update")
	public String updateSpecialization(@ModelAttribute Specialization specialization,RedirectAttributes attributes) {
		service.updateSpecialization(specialization);
		String message="Specialization '"+specialization.getId()+"' updated.";
		attributes.addAttribute("message", message);
		return "redirect:all";
	}

	@GetMapping("/delete")
	public String deleteSpecialization(
			@RequestParam Long id,
			RedirectAttributes attributes) {
		try {
			service.removeSpecialization(id);
			attributes.addAttribute("message", "Specialization "+id+" removed");
		}catch(SpecializationNotFoundException e) {
			e.printStackTrace();
			attributes.addAttribute("message", e.getMessage());
		}
		return "redirect:all";
	}
	
	//Ajax call
	@GetMapping("/checkCode")
	@ResponseBody
	public String validateSpecCode(
			@RequestParam String code,
			@RequestParam Long id
			) 
	{
		String message = "";
		if(id!=0 && service.isSpecCodeExistEdit(code, id)) {
			message = code + ", already exist";
		}else if(id==0 && service.isSpecCodeExist(code)) {
			message = code + ", already exist";
		}
		return message; //this is not viewName(it is message)
	}
	
	//Ajax call
	@GetMapping("/checkName")
	@ResponseBody
	public String validateSpecName(
			@RequestParam String name,
			@RequestParam Long id
			) 
	{
		String message = "";
		if(id!=0 && service.isSpecNameExistEdit(name, id)) {
			message = name + ", already exist";
		}else if(id==0 && service.isSpecNameExist(name)) {
			message = name + ", already exist";
		}

		return message; //this is not viewName(it is message)
	}

	@GetMapping("/excel")
	public ModelAndView excelExport() {
		ModelAndView mv=new ModelAndView();
		mv.setView(new SpecializationExcelView());
		mv.addObject("list", service.getAllSpecializations());
		return mv;
	}
	
	@GetMapping("/pdf")
	public ModelAndView exportPdf() {
		ModelAndView mv=new ModelAndView();
		mv.setView(new SpacializationPdfView());
		mv.addObject("list", service.getAllSpecializations());
		return mv;
	}
}
