package com.ftn.EUprava.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping(value="/vakcine")
public class VakcinaController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private VakcinaService vakcinaService;
	
	@Autowired
	private ProizvodjacVakcineService proizvodjacVakcineService;
	
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 
	

	@GetMapping
	public ModelAndView Index(
			@RequestParam(required=false) String ime, 
			@RequestParam(required=false) Integer dostupnaKolicinaMin, 
			@RequestParam(required=false) Integer dostupnaKolicinaMax,
			@RequestParam(required=false) String proizvodjac,
			@RequestParam(required=false) String drzava,
			
			HttpSession session) throws IOException {
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		
		List<Vakcina> vakcine = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

		ModelAndView rezultat = new ModelAndView("vakcine");
		rezultat.addObject("vakcine", vakcine);
		rezultat.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return rezultat;
		
	}
//	@GetMapping
//	public ModelAndView index() {
//		List<Vakcina> vakcine = vakcinaService.findAll();		
//		
//		ModelAndView rezultat = new ModelAndView("vakcine"); 
//		rezultat.addObject("vakcine", vakcine); 
//
//		return rezultat; 
//	}

	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response ) {
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		session.setAttribute("proizvodjaciVakcine", proizvodjaciVakcine);
		

		return "dodavanjeVakcine"; 
	}
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String ime,  @RequestParam(value = "proizvodjacId") @PathVariable("id")
	     Long proizvodjacId, HttpServletResponse response) throws IOException {	
	  
	     ProizvodjacVakcine proizvodjacVakcine = proizvodjacVakcineService.findOne(proizvodjacId);
	    
	     Vakcina vakcina = new Vakcina(ime, proizvodjacVakcine);
	    Vakcina saved = vakcinaService.save(vakcina);
	    response.sendRedirect(bURL+"vakcine");
	}

	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id, 
			@RequestParam String ime,
			@RequestParam int dostupnaKolicina,  
			 @RequestParam Long proizvodjacId ,
			 HttpServletResponse response) throws IOException {	
		
		ProizvodjacVakcine proizvodjacVakcine  = proizvodjacVakcineService.findOne(proizvodjacId);
		Vakcina vakcina = vakcinaService.findOne(id);
		if(vakcina != null) {
			if(ime != null && !ime.trim().equals(""))
				vakcina.setIme(ime);
			if(dostupnaKolicina > 0)
				vakcina.setDostupnaKolicina(dostupnaKolicina);
			if(proizvodjacId != null)
			{
			
				vakcina.setProizvodjac(proizvodjacVakcine);
			}
				
		}
		Vakcina saved = vakcinaService.update(vakcina);
		response.sendRedirect(bURL+"vakcine");
	}
	

	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Vakcina deleted = vakcinaService.delete(id);
		response.sendRedirect(bURL+"vakcine");
	}
	
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Vakcina vakcina  = vakcinaService.findOne(id);
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		ModelAndView rezultat = new ModelAndView("vakcina"); 
		rezultat.addObject("vakcina", vakcina); 
		rezultat.addObject("proizvodjaciVakcine", proizvodjaciVakcine); 

		return rezultat; 
	}
	

	@PostMapping(value ="/search")
	public ModelAndView pretraga( @RequestParam(required=false) String ime, 
	        @RequestParam(required=false) Integer dostupnaKolicinaMin, 
	        @RequestParam(required=false) Integer dostupnaKolicinaMax,
	    	@RequestParam(required=false) String proizvodjac,
			@RequestParam(required=false) String drzava,
	      
			HttpServletResponse response) throws IOException {
		
		ModelAndView rezlutat = new ModelAndView("vakcine");
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

		
		if(ime!=null && ime.trim().equals(""))
			ime=null;

		List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax,  proizvodjac, drzava);
		rezlutat.addObject("vakcine", vakcineFilter);
		rezlutat.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return rezlutat;
		
	
	}
	@PostMapping(value = "/ascIme")
	public ModelAndView ascIme(@RequestParam(required = false) String ime,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMin,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMax,
	                        	@RequestParam(required=false) String proizvodjac,
	                			@RequestParam(required=false) String drzava,
	                            HttpServletResponse response) throws IOException {
	    ModelAndView result = new ModelAndView("vakcine");

	    List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

	    if (ime != null && ime.trim().equals("")) {
	        ime = null;
	    }

	    List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
	    vakcineFilter.sort(Comparator.comparing(Vakcina::getIme));
	    result.addObject("vakcine", vakcineFilter);
	    result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
	    return result;
	}

	
	@PostMapping(value = "/descIme")
	public ModelAndView descIme(@RequestParam(required=false) String ime, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMin, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMax,
		    	@RequestParam(required=false) String proizvodjac,
				@RequestParam(required=false) String drzava,
		        HttpServletResponse response) throws IOException {
		
		ModelAndView result = new ModelAndView("vakcine");
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
		
		vakcineFilter.sort(Comparator.comparing(Vakcina::getIme).reversed());
		
		result.addObject("vakcine", vakcineFilter);
		result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return result;
	}
	@PostMapping(value = "/ascKolicina")
	public ModelAndView ascKolicina(@RequestParam(required = false) String ime,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMin,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMax,
	                        	@RequestParam(required=false) String proizvodjac,
	                			@RequestParam(required=false) String drzava,
	                            HttpServletResponse response) throws IOException {
	    ModelAndView result = new ModelAndView("vakcine");

	    List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

	    if (ime != null && ime.trim().equals("")) {
	        ime = null;
	    }

	    List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
	    vakcineFilter.sort(Comparator.comparing(Vakcina::getDostupnaKolicina));
	    result.addObject("vakcine", vakcineFilter);
	    result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
	    return result;
	}

	
	@PostMapping(value = "/descKolicina")
	public ModelAndView descKolicina(@RequestParam(required=false) String ime, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMin, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMax,
		    	@RequestParam(required=false) String proizvodjac,
				@RequestParam(required=false) String drzava,
		        HttpServletResponse response) throws IOException {
		
		ModelAndView result = new ModelAndView("vakcine");
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
		
		vakcineFilter.sort(Comparator.comparing(Vakcina::getDostupnaKolicina).reversed());
		
		result.addObject("vakcine", vakcineFilter);
		result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return result;
	}
	@PostMapping(value = "/ascProizvodjac")
	public ModelAndView ascProizvodjac(@RequestParam(required = false) String ime,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMin,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMax,
	                        	@RequestParam(required=false) String proizvodjac,
	                			@RequestParam(required=false) String drzava,
	                            HttpServletResponse response) throws IOException {
	    ModelAndView result = new ModelAndView("vakcine");

	    List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

	    if (ime != null && ime.trim().equals("")) {
	        ime = null;
	    }

	    List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
	    vakcineFilter.sort(Comparator.comparing(vakcina -> vakcina.getProizvodjac().getProizvodjac().toString()));

	    result.addObject("vakcine", vakcineFilter);
	    result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
	    return result;
	}

	
	@PostMapping(value = "/descProizvodjac")
	public ModelAndView descProizvodjac(@RequestParam(required=false) String ime, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMin, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMax,
		        @RequestParam(required=false) String proizvodjac,
    			@RequestParam(required=false) String drzava,
		        HttpServletResponse response) throws IOException {
		
		ModelAndView result = new ModelAndView("vakcine");
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
		
		Comparator<Vakcina> proizvodjacComparator = Comparator.comparing(vakcina -> vakcina.getProizvodjac().getProizvodjac().toString(), 
		        Comparator.reverseOrder());
		vakcineFilter.sort(proizvodjacComparator);

		result.addObject("vakcine", vakcineFilter);
		result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return result;
	}
	@PostMapping(value = "/ascDrzava")
	public ModelAndView ascDrzava(@RequestParam(required = false) String ime,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMin,
	                            @RequestParam(required = false) Integer dostupnaKolicinaMax,
	                        	@RequestParam(required=false) String proizvodjac,
	                			@RequestParam(required=false) String drzava,
	                            HttpServletResponse response) throws IOException {
	    ModelAndView result = new ModelAndView("vakcine");

	    List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();

	    if (ime != null && ime.trim().equals("")) {
	        ime = null;
	    }

	    List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
	    vakcineFilter.sort(Comparator.comparing(vakcina -> vakcina.getProizvodjac().getDrzavaProizvodnje().toString()));

	    result.addObject("vakcine", vakcineFilter);
	    result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
	    return result;
	}

	
	@PostMapping(value = "/descDrzava")
	public ModelAndView descDrzava(@RequestParam(required=false) String ime, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMin, 
		        @RequestParam(required=false) Integer dostupnaKolicinaMax,
		        @RequestParam(required=false) String proizvodjac,
    			@RequestParam(required=false) String drzava,
		        HttpServletResponse response) throws IOException {
		
		ModelAndView result = new ModelAndView("vakcine");
		
		List<ProizvodjacVakcine> proizvodjaciVakcine = proizvodjacVakcineService.findAll();
		
		if(ime!=null && ime.trim().equals(""))
			ime=null;
		
		List<Vakcina> vakcineFilter = vakcinaService.find(ime, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
		
		Comparator<Vakcina> proizvodjacComparator = Comparator.comparing(vakcina -> vakcina.getProizvodjac().getProizvodjac().toString(), 
		        Comparator.reverseOrder());
		vakcineFilter.sort(proizvodjacComparator);

		result.addObject("vakcine", vakcineFilter);
		result.addObject("proizvodjaciVakcine", proizvodjaciVakcine);
		return result;
	}
}
