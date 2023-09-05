package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.Vest;


public interface VestService {
	Vest findOne(Long id); 
	List<Vest> findAll(); 
	Vest save(Vest vest); 
	Vest update(Vest vest); 
	Vest delete(Long id); 
}
