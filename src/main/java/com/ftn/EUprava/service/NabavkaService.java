package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.Nabavka;

public interface NabavkaService {
	Nabavka findOne(Long id); 
	List<Nabavka> findAll(); 
	Nabavka save(Nabavka nabavka); 
	Nabavka update(Nabavka nabavka); 
	Nabavka delete(Long id); 
}
