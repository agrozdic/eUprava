package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.Korisnik;


public interface KorisnikDAO {
	public Korisnik findOne(Long id);
	
	public Korisnik findOne(String email); 
	
	public Korisnik findOneByJMBG(String jmbg); 
	
	public Korisnik findOne(String email, String lozinka);

	public List<Korisnik> findAll();

	public int save(Korisnik korisnik);

	public int update(Korisnik korisnik);

	public int delete(Long id);
	
	
	}
