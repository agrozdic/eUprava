package com.ftn.EUprava.controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.EUprava.model.PrimljenaVakcina;
import com.ftn.EUprava.service.PrimljenaVakcinaService;


@Controller
@RequestMapping(value="/primljeneVakcine")
public class PrimljenaVakcinaController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private PrimljenaVakcinaService primljenaVakcinaService;
	
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	

	@GetMapping
	public ModelAndView index() {
		List<PrimljenaVakcina> primljeneVakcine = primljenaVakcinaService.findAll();		
		
		ModelAndView rezultat = new ModelAndView("primljeneVakcine"); 
		rezultat.addObject("primljeneVakcine", primljeneVakcine); 

		return rezultat; 
	}
	

}
