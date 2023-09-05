package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.PrijavaZaVakcinaciju;

public interface PrijavaZaVakcinacijuDAO {
	
	public PrijavaZaVakcinaciju findOne(Long id);

	public List<PrijavaZaVakcinaciju> findAll();
	
	public int save(PrijavaZaVakcinaciju prijavaZaVakcinaciju);


	public int delete(Long id);
	public List<PrijavaZaVakcinaciju> find(String ime, String prezime, String jmbg);

}
