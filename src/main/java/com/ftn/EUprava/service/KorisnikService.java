package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.Korisnik;


public interface KorisnikService {
	Korisnik findOneById(Long id);
	Korisnik findOne(String email); 
	Korisnik findOneByJMBG(String jmbg); 
	Korisnik findOne(String email, String lozinka);
	List<Korisnik> findAll(); 
	Korisnik save(Korisnik korisnik); 
	Korisnik update(Korisnik korisnik); 
	Korisnik delete(Long id);
	
}
