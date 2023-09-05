package com.ftn.EUprava.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.ModelAndView;

import com.ftn.EUprava.model.EStatus;
import com.ftn.EUprava.model.Nabavka;
import com.ftn.EUprava.model.Vakcina;
import com.ftn.EUprava.service.NabavkaService;
import com.ftn.EUprava.service.VakcinaService;

@Controller
@RequestMapping(value="/nabavke")
public class NabavkaController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private NabavkaService nabavkaService;
	
	@Autowired
	private VakcinaService vakcinaService;
	
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@GetMapping
	public ModelAndView index() {
		List<Nabavka> nabavke = nabavkaService.findAll();		
		List<Vakcina> vakcine = vakcinaService.findAll();		
		
		ModelAndView rezultat = new ModelAndView("nabavke"); 
		rezultat.addObject("nabavke", nabavke); 
		rezultat.addObject("vakcine", vakcine);

		return rezultat; 
	}

	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response ) {
		
		List<Vakcina> vakcine = vakcinaService.findAll();
		session.setAttribute("vakcine", vakcine);
		

		return "dodavanjeNabavke"; 
	}
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam(value = "vakcinaId") @PathVariable("id") Long vakcinaId,
	     @RequestParam int kolicina,
	 
	     @RequestParam(required = false) String komentar, 
	     @RequestParam EStatus status,
	     HttpServletResponse response) throws IOException {	
	  
	     Vakcina vakcina = vakcinaService.findOne(vakcinaId);
	    
	     Nabavka nabavka = new Nabavka(vakcina, kolicina,  komentar,status);
	    Nabavka saved = nabavkaService.save(nabavka);
	    response.sendRedirect(bURL+"nabavke");
	}

	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,
			@RequestParam Long vakcinaId,
		     @RequestParam int kolicina,
		     @RequestParam(required = false) String komentar, 
		     @RequestParam EStatus status,
			 HttpServletResponse response) throws IOException {	
		
		
		Vakcina vakcina  = vakcinaService.findOne(vakcinaId);
		Nabavka nabavka = nabavkaService.findOne(id);
		
		if(nabavka != null) {
		
			if(vakcinaId != null)
			{
				nabavka.setVakcina(vakcina);
				
			}
			if(kolicina > 0)
				nabavka.setKolicina(kolicina);
			
			
			if(komentar != null )
				nabavka.setKomentar(komentar);
			if(status != null )
				nabavka.setStatus(status);
		}
		Nabavka saved = nabavkaService.update(nabavka);
		response.sendRedirect(bURL+"nabavke");
		
	}
	
	
	@PostMapping(value ="/odobri")
	public String odobri(@RequestParam("vakcinaId") Long vakcinaId,
	                           @RequestParam("kolicina") Integer kolicina,
	                           @RequestParam("status") EStatus status,
	                           @RequestParam("komentar") String komentar,
	                           @RequestParam("id") Long nabavkaId) {
	    jdbcTemplate.update("UPDATE vakcine SET dostupnaKolicina = dostupnaKolicina + ? WHERE id = ?",
	    		kolicina, vakcinaId);

	    jdbcTemplate.update("UPDATE nabavke SET vakcinaId = ?, kolicina = ?, komentar = ?, eStatus = ? WHERE id = ?",
	                        vakcinaId, kolicina, komentar, status.toString(), nabavkaId);

	    jdbcTemplate.update("DELETE FROM nabavke WHERE id = ?", nabavkaId);

	    
	   return "redirect:/nabavke";
	}

	

	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Nabavka deleted = nabavkaService.delete(id);
		response.sendRedirect(bURL+"nabavke");
	}
	
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Nabavka nabavka  = nabavkaService.findOne(id);
		List<Vakcina> vakcine = vakcinaService.findAll();
		ModelAndView rezultat = new ModelAndView("nabavka"); 
		rezultat.addObject("nabavka", nabavka); 
		rezultat.addObject("vakcine", vakcine); 

		return rezultat; 
	}
	


}
