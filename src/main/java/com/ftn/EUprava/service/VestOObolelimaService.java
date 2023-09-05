package com.ftn.EUprava.service;

import java.util.List;

import com.ftn.EUprava.model.VestOObolelima;


public interface VestOObolelimaService {
	VestOObolelima findOne(Long id); 
	List<VestOObolelima> findAll(); 
	VestOObolelima save(VestOObolelima vestOObolelima); 
	VestOObolelima update(VestOObolelima vestOObolelima); 
	VestOObolelima delete(Long id); 
}
