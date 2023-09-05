package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.Vakcina;


public interface VakcinaService {
	Vakcina findOne(Long id); 
	List<Vakcina> findAll(); 
	Vakcina save(Vakcina vakcina); 
	Vakcina update(Vakcina vakcina); 
	Vakcina delete(Long id); 
	List<Vakcina> find(String ime, Integer dostupnaKolicinaMin, Integer dostupnaKolicinaMax, 
			String proizvodjac, String drzava);

}
