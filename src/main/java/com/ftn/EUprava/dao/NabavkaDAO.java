package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.Nabavka;

public interface NabavkaDAO {
	public Nabavka findOne(Long id);

	public List<Nabavka> findAll();
	
	
	public int save(Nabavka nabavka);

	public int update(Nabavka nabavka);

	public int delete(Long id);
	
	
}
