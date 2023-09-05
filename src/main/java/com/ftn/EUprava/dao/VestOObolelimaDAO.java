package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.VestOObolelima;

public interface VestOObolelimaDAO {

	public VestOObolelima findOne(Long id);

	public List<VestOObolelima> findAll();
	
	public int save(VestOObolelima vestOObolelima);

	public int update(VestOObolelima vestOObolelima);

	public int delete(Long id);
}
