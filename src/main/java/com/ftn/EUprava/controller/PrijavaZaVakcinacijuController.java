package com.ftn.EUprava.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ftn.EUprava.model.EDoza;
import com.ftn.EUprava.model.EStatus;
import com.ftn.EUprava.model.Korisnik;
import com.ftn.EUprava.model.Nabavka;
import com.ftn.EUprava.model.PrijavaZaVakcinaciju;
import com.ftn.EUprava.model.PrimljenaVakcina;
import com.ftn.EUprava.model.ProizvodjacVakcine;
import com.ftn.EUprava.model.Vakcina;
import com.ftn.EUprava.service.KorisnikService;
import com.ftn.EUprava.service.PrijavaZaVakcinacijuService;
import com.ftn.EUprava.service.VakcinaService;

@Controller
@RequestMapping(value="/prijaveZaVakcinaciju")
public class PrijavaZaVakcinacijuController implements ServletContextAware {

	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private PrijavaZaVakcinacijuService prijavaZaVakcinacijuService;
	
	@Autowired
	private VakcinaService vakcinaService;
	
	@Autowired
	private KorisnikService korisnikService;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
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
			@RequestParam(required=false) String prezime, 
			@RequestParam(required=false) String jmbg,
			
			HttpSession session) throws IOException {
		
		
		
		List<PrijavaZaVakcinaciju> prijaveZaVakcinaciju = prijavaZaVakcinacijuService.find(ime, prezime, jmbg);
		List<Korisnik> korisnici = korisnikService.findAll();

		ModelAndView rezultat = new ModelAndView("prijaveZaVakcinaciju");
		rezultat.addObject("prijaveZaVakcinaciju", prijaveZaVakcinaciju);
		rezultat.addObject("korisnici", korisnici);
		return rezultat;
		
	}
	
	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response ) {
		
		List<Korisnik> korisnici = korisnikService.findAll();
		session.setAttribute("korisnici", korisnici);
		
		List<Vakcina> vakcine = vakcinaService.findAll();
		session.setAttribute("vakcine", vakcine);
		

		return "dodavanjePrijaveZaVakcinaciju"; 
	}
	
	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create
			(@RequestParam(value = "korisnikId") @PathVariable("id") Long korisnikId,
			@RequestParam(value = "vakcinaId") @PathVariable("id") Long vakcinaId,
			@RequestParam EDoza doza,
			
	HttpServletResponse response) throws IOException {	
	  
	     Korisnik korisnik = korisnikService.findOneById(korisnikId);
	     Vakcina vakcina = vakcinaService.findOne(vakcinaId);
	    
	     PrijavaZaVakcinaciju prijavaZaVakcinaciju = new PrijavaZaVakcinaciju(korisnik, vakcina, doza);
	     PrijavaZaVakcinaciju saved = prijavaZaVakcinacijuService.save(prijavaZaVakcinaciju);
	    response.sendRedirect(bURL+"prijaveZaVakcinaciju");
	}

	
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		PrijavaZaVakcinaciju deleted = prijavaZaVakcinacijuService.delete(id);
		response.sendRedirect(bURL+"prijaveZaVakcinaciju");
	}
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		PrijavaZaVakcinaciju prijavaZaVakcinaciju  = prijavaZaVakcinacijuService.findOne(id);
		List<Korisnik> korisnici = korisnikService.findAll();
		List<Vakcina> vakcine = vakcinaService.findAll();
		ModelAndView rezultat = new ModelAndView("prijavaZaVakcinaciju"); 
		rezultat.addObject("prijavaZaVakcinaciju", prijavaZaVakcinaciju); 
		rezultat.addObject("korisnici", korisnici); 
		rezultat.addObject("vakcine", vakcine); 
		return rezultat; 
	}
	
	@PostMapping(value = "/search")
	public ModelAndView search(@RequestParam(required=false) String ime, 
	                           @RequestParam(required=false) String prezime, 
	                           @RequestParam(required=false) String jmbg) {
		
		 ModelAndView rezlutat = new ModelAndView("prijaveZaVakcinaciju");
	  List<Korisnik> korisnici = korisnikService.findAll();
	  List<PrijavaZaVakcinaciju> prijaveFilter = prijavaZaVakcinacijuService.find(ime, prezime, jmbg);
		
	  rezlutat.addObject("prijaveZaVakcinaciju", prijaveFilter);
	  rezlutat.addObject("korisnici", korisnici);
	  
	  
	  return rezlutat;
	}
	
	
	@PostMapping(value = "/dajVakcinu")
	public String dajVakcinu(@RequestParam("korisnikId") Long korisnikId,
	                        @RequestParam("vakcinaId") Long vakcinaId,
	                        @RequestParam("doza") EDoza doza, 
	                        RedirectAttributes redirectAttributes) {
		
		  // Ako je doza PRVA
	    if (doza == EDoza.PRVA) {
	        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM primljeneVakcine WHERE korisnikId = ? "
	        		+ "AND EDoza = 'PRVA'", 
	            new Object[]{korisnikId}, Integer.class);
	        
	        // If PRVA doza postoji, vrati error
	        if (count > 0) {
	            redirectAttributes.addFlashAttribute("errorMessage", "Prva doza je vec primljena");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	    }

	    // Ako je doza DRUGA
	    if (doza == EDoza.DRUGA) {
	        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM primljeneVakcine WHERE korisnikId = ?"
	        		+ " AND (EDoza = 'PRVA' OR EDoza = 'DRUGA')", 
	            new Object[]{korisnikId}, Integer.class);
	        
	        // Ako PRVA i DRUGA doza postoje, vrati error
	        if (count == 2) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Druga doza je vec primljena");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	        
	        // Ako samo PRVA doza postoji, proveri vreme izmedju PRVA doza i trenutnog vremena
	       
	        if (count == 1) {
	        	LocalDateTime datumIVremeVakcinacije = jdbcTemplate.queryForObject("SELECT DatumIVremeVakcinacije"
	            		+ " FROM primljeneVakcine WHERE korisnikId = ? AND EDoza = 'PRVA'",
	                new Object[]{korisnikId}, LocalDateTime.class);

				LocalDateTime nowLDT = LocalDateTime.now();
			
				long differenceInMinutes = ChronoUnit.MINUTES.between(datumIVremeVakcinacije, nowLDT);
			
				if (differenceInMinutes <= 3) {
					 redirectAttributes.addFlashAttribute("errorMessage", "Nije proslo 3 meseca od prve doze");
		             return "redirect:/prijaveZaVakcinaciju";
				}
	        }

	        
	        // Ako ni PRVA ni DRUGA doza ne postoje vrati error
	        if (count == 0) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Nije primljena prva doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	    }
	    // Ako je doza TRECA
	    if (doza == EDoza.TRECA) {
	        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM primljeneVakcine WHERE "
	        		+ "korisnikId = ? AND (EDoza = 'PRVA' OR EDoza = 'DRUGA' OR EDoza = 'TRECA')", 
	            new Object[]{korisnikId}, Integer.class);
	        
	        // Ako PRVA,DRUGA, TRECA doza postoje, vrati error
	        if (count == 3) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", " Treca doza je vec primljena");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	        
	        // Ako samo PRVA i DRUGA doza postoji, proveri vreme izmedju DRUGA doza i trenutnog vremena
	       
	        if (count == 2) {
	        	LocalDateTime datumIVremeVakcinacije = jdbcTemplate.queryForObject("SELECT DatumIVremeVakcinacije"
	            		+ " FROM primljeneVakcine WHERE korisnikId = ? AND EDoza = 'DRUGA'",
	                new Object[]{korisnikId}, LocalDateTime.class);

				LocalDateTime nowLDT = LocalDateTime.now();
			
				long differenceInMinutes = ChronoUnit.MINUTES.between(datumIVremeVakcinacije, nowLDT);
			
				if (differenceInMinutes <= 6) {
					 redirectAttributes.addFlashAttribute("errorMessage", "Nije proslo 6 meseca od druge doze");
		             return "redirect:/prijaveZaVakcinaciju";
				}
	        }

	     // Ako  DRUGA  doza ne postoje vrati error
	        if (count == 1) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Nije primljena druga doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	        // Ako ni PRVA ni DRUGA ni TRECA doza ne postoje vrati error
	        if (count == 0) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Nije primljena prva doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	    }
	    // Ako je doza CETVRTA
	    if (doza == EDoza.CETVRTA) {
	        int count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM primljeneVakcine WHERE korisnikId = ? AND"
	        		+ " (EDoza = 'PRVA' OR EDoza = 'DRUGA' OR EDoza = 'TRECA' OR EDoza = 'CETVRTA')", 
	            new Object[]{korisnikId}, Integer.class);
	        
	        // Ako SVE doze postoje, vrati error
	        if (count == 4) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Cetvrta doza je vec primljena");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	        
	        // Ako PRVA DRUGA i TRECA doza postoji, proveri vreme izmedju TRECA doza i trenutnog vremena
	       
	        if (count == 3) {
	        	LocalDateTime datumIVremeVakcinacije = jdbcTemplate.queryForObject("SELECT DatumIVremeVakcinacije"
	            		+ " FROM primljeneVakcine WHERE korisnikId = ? AND EDoza = 'TRECA'",
	                new Object[]{korisnikId}, LocalDateTime.class);

				LocalDateTime nowLDT = LocalDateTime.now();
			
				long differenceInMinutes = ChronoUnit.MINUTES.between(datumIVremeVakcinacije, nowLDT);
			
				System.out.println(datumIVremeVakcinacije);
				System.out.println(nowLDT);
				System.out.println(differenceInMinutes);
				
				if (differenceInMinutes <= 3) {
					 redirectAttributes.addFlashAttribute("errorMessage", "Nije proslo 3 meseca od trece doze");
		             return "redirect:/prijaveZaVakcinaciju";
				}
	        }
	     // Ako  TRECA  doza ne postoje vrati error
	        if (count == 2) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Nije primljena treca doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	     // Ako  DRUGA  doza ne postoje vrati error
	        if (count == 1) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", "Nije primljena druga doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	        // Ako ni PRVA ni DRUGA ni TRECA doza ne postoje vrati error
	        if (count == 0) {
	        	 redirectAttributes.addFlashAttribute("errorMessage", " Nije primljena prva doza");
	             return "redirect:/prijaveZaVakcinaciju";
	        }
	    }

	    jdbcTemplate.update("INSERT INTO primljeneVakcine(korisnikId, vakcinaId, EDoza) values(?, ?, ?)",
	            korisnikId, vakcinaId, doza.toString());

	    jdbcTemplate.update("UPDATE vakcine SET dostupnaKolicina = dostupnaKolicina - 1 WHERE id = ?",
	    		vakcinaId);


	    jdbcTemplate.update("DELETE FROM prijaveZaVakcinaciju WHERE korisnikid = ?", korisnikId);


	   return "redirect:/prijaveZaVakcinaciju";
	}

	        

	
	
}
