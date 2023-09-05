package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.ProizvodjacVakcine;


public interface ProizvodjacVakcineService {
	ProizvodjacVakcine findOne(Long id); 
	List<ProizvodjacVakcine> findAll(); 
	ProizvodjacVakcine save(ProizvodjacVakcine proizvodjacVakcine); 
	ProizvodjacVakcine update(ProizvodjacVakcine proizvodjacVakcine); 
	ProizvodjacVakcine delete(Long id); 
	
}
