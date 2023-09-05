package com.ftn.EUprava.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.EUprava.model.ProizvodjacVakcine;
import com.ftn.EUprava.model.Vakcina;
import com.ftn.EUprava.service.ProizvodjacVakcineService;
import com.ftn.EUprava.service.VakcinaService;

@Controller
@RequestMapping(value="/proizvodjaciVakcine")
public class ProizvodjacVakcineController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private ProizvodjacVakcineService proizvodjacVackineService;
	
	
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
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVackineService.findAll();
		
		ModelAndView rezultat = new ModelAndView("proizvodjaciVakcine"); 
		rezultat.addObject("proizvodjaciVakcine", proizvodjaciVakcine); 

		return rezultat; 
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeProizvodjacaVakcine"; 
	}
	
	
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam(required = true) String proizvodjac, @RequestParam(required = true) String drzavaProizvodnje,  
			 HttpServletResponse response) throws IOException {		
		ProizvodjacVakcine proizvodjacVakcine = new ProizvodjacVakcine(proizvodjac, drzavaProizvodnje);
		ProizvodjacVakcine saved = proizvodjacVackineService.save(proizvodjacVakcine);
		response.sendRedirect(bURL+"proizvodjaciVakcine");
	}
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, @RequestParam(required = true) String proizvodjac, 
			@RequestParam(required = true) String drzavaProizvodnje,  
		 HttpServletResponse response) throws IOException {	
		ProizvodjacVakcine proizvodjacVakcine = proizvodjacVackineService.findOne(id);
		if(proizvodjacVakcine != null) {
			if(proizvodjac != null && !proizvodjac.trim().equals(""))
				proizvodjacVakcine.setProizvodjac(proizvodjac);
			if(drzavaProizvodnje != null && !drzavaProizvodnje.trim().equals(""))
				proizvodjacVakcine.setDrzavaProizvodnje(drzavaProizvodnje);
			
		}
		ProizvodjacVakcine saved = proizvodjacVackineService.update(proizvodjacVakcine);
		response.sendRedirect(bURL+"proizvodjaciVakcine");
	}
	
	
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		ProizvodjacVakcine deleted = proizvodjacVackineService.delete(id);
		response.sendRedirect(bURL+"proizvodjaciVakcine");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		ProizvodjacVakcine proizvodjacVakcine  = proizvodjacVackineService.findOne(id);
		
		
		ModelAndView rezultat = new ModelAndView("proizvodjacVakcine"); 
		rezultat.addObject("proizvodjacVakcine", proizvodjacVakcine); 

		return rezultat; 
	}
	

}
