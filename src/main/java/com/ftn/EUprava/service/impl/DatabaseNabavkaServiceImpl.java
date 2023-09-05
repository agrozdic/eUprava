package com.ftn.EUprava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.NabavkaDAO;
import com.ftn.EUprava.model.Nabavka;
import com.ftn.EUprava.service.NabavkaService;


@Service
public class DatabaseNabavkaServiceImpl implements NabavkaService {
	
	@Autowired
	private NabavkaDAO nabavkaDAO;
	
	
	public Nabavka findOne(Long id) {
		return nabavkaDAO.findOne(id);
	}

	public List<Nabavka> findAll() {
		return nabavkaDAO.findAll();
	}


	public Nabavka save(Nabavka  nabavka) {
		nabavkaDAO.save(nabavka);
		return nabavka;
	}

	public Nabavka update(Nabavka nabavka) {
		nabavkaDAO.update(nabavka);
		return nabavka;
	}

	public Nabavka delete(Long id) {
		Nabavka nabavka = nabavkaDAO.findOne(id);
		if(nabavka != null) {
			nabavkaDAO.delete(id);
		}
		return nabavka;
	}
	
	

	
}
