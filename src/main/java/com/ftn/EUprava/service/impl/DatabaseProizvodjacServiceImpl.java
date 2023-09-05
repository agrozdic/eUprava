package com.ftn.EUprava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.ProizvodjacDAO;
import com.ftn.EUprava.model.ProizvodjacVakcine;
import com.ftn.EUprava.service.ProizvodjacVakcineService;

@Service
public class DatabaseProizvodjacServiceImpl implements ProizvodjacVakcineService{

	
	@Autowired
	private ProizvodjacDAO proizvodjacDAO;

	@Override
	public ProizvodjacVakcine findOne(Long id) {
		return proizvodjacDAO.findOne(id);
	}

	@Override
	public List<ProizvodjacVakcine> findAll() {
		return proizvodjacDAO.findAll();
	}

	@Override
	public ProizvodjacVakcine save(ProizvodjacVakcine  proizvodjacVakcine) {
		proizvodjacDAO.save(proizvodjacVakcine);
		return proizvodjacVakcine;
	}

	@Override
	public ProizvodjacVakcine update(ProizvodjacVakcine proizvodjacVakcine) {
		proizvodjacDAO.update(proizvodjacVakcine);
		return proizvodjacVakcine;
	}

	@Override
	public ProizvodjacVakcine delete(Long id) {
		ProizvodjacVakcine proizvodjacVakcine  = proizvodjacDAO.findOne(id);
		if(proizvodjacVakcine != null) {
			proizvodjacDAO.delete(id);
		}
		return proizvodjacVakcine;
	}
	

}