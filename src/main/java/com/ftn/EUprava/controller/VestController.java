package com.ftn.EUprava.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.EUprava.model.Vest;
import com.ftn.EUprava.service.VestService;

@Controller
@RequestMapping(value= "/vesti")
public class VestController implements ServletContextAware {

		
		@Autowired
		private ServletContext servletContext;
		private  String bURL; 
		
		@Autowired
		private VestService vestService;
		
		
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
			List<Vest> vesti = vestService.findAll();		
			
			ModelAndView rezultat = new ModelAndView("vesti"); 
			rezultat.addObject("vesti", vesti); 

			return rezultat; 
		}

		@GetMapping(value="/add")
		public String create(HttpSession session, HttpServletResponse response){
			return "dodavanjeVesti"; 
		}

		
		@SuppressWarnings("unused")
		@PostMapping(value="/add")
		public void create(@RequestParam(required = true) String nazivVesti, @RequestParam(required = true) String sadrzajVesti,  
				@RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeObjavljivanja, 
				HttpServletResponse response) throws IOException {		
			Vest vest = new Vest(nazivVesti, sadrzajVesti, datumIVremeObjavljivanja);
			Vest saved = vestService.save(vest);
			response.sendRedirect(bURL+"vesti");
		}
		
	
		@SuppressWarnings("unused")
		@PostMapping(value="/edit")
		public void Edit(@RequestParam Long id, @RequestParam(required = true) String nazivVesti, @RequestParam(required = true) String sadrzajVesti,  
				@RequestParam(required = true) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE_TIME) LocalDateTime datumIVremeObjavljivanja, 
				HttpServletResponse response) throws IOException {	
			Vest vest = vestService.findOne(id);
			if(vest != null) {
				if(nazivVesti != null && !nazivVesti.trim().equals(""))
					vest.setNazivVesti(nazivVesti);
				if(sadrzajVesti != null && !sadrzajVesti.trim().equals(""))
					vest.setSadrzajVesti(sadrzajVesti);
				if(datumIVremeObjavljivanja != null)
					vest.setDatumIVremeObjavljivanja(datumIVremeObjavljivanja);
			}
			Vest saved = vestService.update(vest);
			response.sendRedirect(bURL+"vesti");
		}
		
	
		@SuppressWarnings("unused")
		@PostMapping(value="/delete")
		public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
			Vest deleted = vestService.delete(id);
			response.sendRedirect(bURL+"vesti");
		}
		
		@GetMapping(value="/details")
		@ResponseBody
		public ModelAndView details(@RequestParam Long id) {	
			Vest vest = vestService.findOne(id);
			
		
			ModelAndView rezultat = new ModelAndView("vest"); 
			rezultat.addObject("vest", vest); 

			return rezultat; 
		}

	}
