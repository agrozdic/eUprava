package com.ftn.EUprava.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
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

import com.ftn.EUprava.model.Korisnik;

import com.ftn.EUprava.service.KorisnikService;


@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {
	
	public static final String KORISNIK_KEY = "korisnik";
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private KorisnikService korisnikService;
	
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}
	
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	} 

	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		postLogin(email, lozinka, session, response);
	}

	@PostMapping(value = "/login")
	@ResponseBody
	public void postLogin(@RequestParam(required = false) String email, @RequestParam(required = false) String lozinka,
			HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik korisnik = korisnikService.findOne(email, lozinka);
		if (korisnik == null) {
			  response.getWriter().println("<script>alert('Korisnik sa tim emailom ne postoji. Registrujte se!'); "
				  		+ "window.location.href='" + bURL + "registracija.html" + "';</script>");
				    return;
		}

		session.setAttribute(KORISNIK_KEY, korisnik);

		response.sendRedirect(bURL + "vakcine");
	}
	
	@GetMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {	

		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(KORISNIK_KEY);

		
		
		request.getSession().removeAttribute(KORISNIK_KEY);
		request.getSession().invalidate();
		response.sendRedirect(bURL+"");
	}
	
	@PostMapping(value="/registracija")
	public ModelAndView registracija(@RequestParam String email, @RequestParam String lozinka,
	        @RequestParam String ponovljenaLozinka,
	        @RequestParam String ime, 	@RequestParam String prezime, 
	         @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
	            @RequestParam String jmbg,
	        @RequestParam String adresa,@RequestParam String brojTelefona,
	        HttpSession session, HttpServletResponse response) throws IOException {
	    try {
	        // validacija
	        Korisnik postojeciKorisnik = korisnikService.findOne(email);
	        Korisnik postojeciKorisnikJMBG = korisnikService.findOneByJMBG(jmbg);
	      
	        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"; 
	        Pattern pattern = Pattern.compile(emailRegex); 
	       
	        if (postojeciKorisnik != null ) {
	            throw new Exception("Korisnik sa tim emailom vec postoji!");
	        }
	        if (email.equals("") || !pattern.matcher(email).matches()) { 
	            throw new Exception("Email ne sme biti prazan i mora biti u formatu: user@mail.com"); 
	        }
	        if (lozinka.equals("")) {
	            throw new Exception("Lozinka ne sme biti prazna!");
	        }
	        if (!lozinka.equals(ponovljenaLozinka)) {
	            throw new Exception("Lozinke se ne podudaraju!");
	        }
	        if (ime.equals("")) {
	            throw new Exception("Ime ne sme biti prazno!");
	        }
	        if (prezime.equals("")) {
	            throw new Exception("Prezime ne sme biti prazno!");
	        }
	        if (datumRodjenja == null) {
	            throw new Exception("Datum rođenja ne sme biti prazan!");
	        }

	        if (postojeciKorisnikJMBG != null ) {
	            throw new Exception("Korisnik sa tim JMBG vec postoji!");
	        }
	        if (jmbg.equals("") || jmbg.length() != 13) {
	            throw new Exception("JMBG ne sme biti prazan i mora sadržati tacno 13 karaktera!");
	        } 

	        if (adresa.equals("")) {
	            throw new Exception("Adresa ne sme biti prazna!");
	        }
	        if (brojTelefona.equals("")) {
	        	  throw new Exception("Broj telefona ne sme biti prazan!");
	        	}

	      

	     
	        Korisnik korisnik = new Korisnik( email,  lozinka,  ime,  prezime,  datumRodjenja,  jmbg,
	                 adresa,  brojTelefona);
	        korisnikService.save(korisnik);

	       
	        response.sendRedirect(bURL + "prijava.html");
	        return null;
	    } catch (Exception ex) {
	        // ispis greške
	        String poruka = ex.getMessage();

	        // prosleđivanje
	        ModelAndView rezultat = new ModelAndView("registracija");
	        rezultat.addObject("poruka", poruka);

	        return rezultat;
	    }
	}
	   

	
	
	@GetMapping
	public ModelAndView index() {
		List<Korisnik> korisnici = korisnikService.findAll();
		
		ModelAndView rezultat = new ModelAndView("korisnici"); 
		rezultat.addObject("korisnici", korisnici); 
				
	

		return rezultat; 
	}
	


	@GetMapping(value="/add")
	public String create(HttpSession session, HttpServletResponse response){
		return "dodavanjeKorisnika"; 
	}

	@SuppressWarnings("unused")
	@PostMapping(value="/add")
	public void create(@RequestParam String email, @RequestParam String lozinka,@RequestParam String ime,  
			@RequestParam String prezime,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
			@RequestParam String jmbg,@RequestParam String adresa, @RequestParam String brojTelefona,
			 HttpServletResponse response) throws IOException {	
		
		Korisnik korisnik = new Korisnik(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona);
		Korisnik saved = korisnikService.save(korisnik);
		response.sendRedirect(bURL+"korisnici");
	}
	
	@SuppressWarnings("unused")
	@PostMapping(value="/edit")
	public void Edit(@RequestParam Long id,@RequestParam String email, @RequestParam String lozinka, @RequestParam String ponovljenaLozinka,@RequestParam String ime,  
			@RequestParam String prezime,@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate datumRodjenja,
			@RequestParam String jmbg,@RequestParam String adresa, @RequestParam String brojTelefona,  
		 HttpServletResponse response) throws IOException {	
		Korisnik korisnik = korisnikService.findOneById(id);
		if(korisnik != null) {
			if(email != null && !email.trim().equals(""))
				korisnik.setEmail(email);
			
			
			if(lozinka != null && !lozinka.trim().equals("") && ponovljenaLozinka != null 
					&& !ponovljenaLozinka.trim().equals("")) {
				  if(!lozinka.equals(ponovljenaLozinka)) {
					  response.getWriter().println("<script>alert('Lozinke se ne podudaraju!'); "
					  		+ "window.location.href='" + bURL + "korisnici/details?id=" + id +
					  		"';</script>");
					    return;
				  } else {
				    korisnik.setLozinka(lozinka);
				  }
				}

			if(ime != null && !ime.trim().equals(""))
				korisnik.setIme(ime);
			if(prezime != null && !prezime.trim().equals(""))
				korisnik.setPrezime(prezime);
			if(datumRodjenja != null)
				korisnik.setDatumRodjenja(datumRodjenja);
			if(jmbg != null && !jmbg.trim().equals(""))
				korisnik.setJmbg(jmbg);
			if(adresa != null && !adresa.trim().equals(""))
				korisnik.setAdresa(adresa);
			if(brojTelefona != null && !brojTelefona.trim().equals(""))
				korisnik.setBrojTelefona(brojTelefona);
			
			
		}
		Korisnik saved = korisnikService.update(korisnik);
		response.sendRedirect(bURL+"korisnici");
	}
	
	
	@SuppressWarnings("unused")
	@PostMapping(value="/delete")
	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
		Korisnik deleted = korisnikService.delete(id);
		response.sendRedirect(bURL+"korisnici");
	}
	
	@GetMapping(value="/details")
	@ResponseBody
	public ModelAndView details(@RequestParam Long id) {	
		Korisnik korisnik  = korisnikService.findOneById(id);
		
		
		ModelAndView rezultat = new ModelAndView("korisnik"); 
		rezultat.addObject("korisnik", korisnik); 

		return rezultat; 
	}
	
	
}

	
