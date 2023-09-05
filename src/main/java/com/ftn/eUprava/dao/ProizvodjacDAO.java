package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.ProizvodjacVakcine;

public interface ProizvodjacDAO {
	
	public ProizvodjacVakcine findOne(Long id);
	
	public List<ProizvodjacVakcine> findAll();

	public int save(ProizvodjacVakcine proizvodjacVakcine);

	public int update(ProizvodjacVakcine proizvodjacVakcine);

	public int delete(Long id);

	
}
