package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.PrijavaZaVakcinaciju;

public interface PrijavaZaVakcinacijuService {
	
	PrijavaZaVakcinaciju findOne(Long id); 
	List<PrijavaZaVakcinaciju> findAll(); 
	PrijavaZaVakcinaciju save(PrijavaZaVakcinaciju prijavaZaVakcinaciju); 
	PrijavaZaVakcinaciju delete(Long id); 
	List<PrijavaZaVakcinaciju> find(String ime, String prezime, String jmbg);
}
