package com.ftn.EUprava.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.PrijavaZaVakcinacijuDAO;
import com.ftn.EUprava.dao.VakcinaDAO;
import com.ftn.EUprava.model.Korisnik;
import com.ftn.EUprava.model.PrijavaZaVakcinaciju;
import com.ftn.EUprava.model.ProizvodjacVakcine;
import com.ftn.EUprava.model.Vakcina;
import com.ftn.EUprava.service.PrijavaZaVakcinacijuService;
import com.ftn.EUprava.service.VakcinaService;


@Service
public class DatabasePrijavaZaVakcinacijuServiceImpl implements PrijavaZaVakcinacijuService {
	
	@Autowired
	private PrijavaZaVakcinacijuDAO prijavaZaVakcinacijuDAO;
	
	
	
	@Override
	public PrijavaZaVakcinaciju findOne(Long id) {
		return prijavaZaVakcinacijuDAO.findOne(id);
	}

	@Override
	public List<PrijavaZaVakcinaciju> findAll() {
		return prijavaZaVakcinacijuDAO.findAll();
	}

	@Override
	public PrijavaZaVakcinaciju save(PrijavaZaVakcinaciju  prijavaZaVakcinaciju) {
		prijavaZaVakcinacijuDAO.save(prijavaZaVakcinaciju);
		return prijavaZaVakcinaciju;
	}

	

	@Override
	public PrijavaZaVakcinaciju delete(Long id) {
		PrijavaZaVakcinaciju prijavaZaVakcinaciju = prijavaZaVakcinacijuDAO.findOne(id);
		if(prijavaZaVakcinaciju != null) {
			prijavaZaVakcinacijuDAO.delete(id);
		}
		return prijavaZaVakcinaciju;
	}
	@Override
	public List<PrijavaZaVakcinaciju> find(String ime, String prezime, String jmbg) {
		
		return prijavaZaVakcinacijuDAO.find( ime,  prezime,  jmbg);

	}
	
}
