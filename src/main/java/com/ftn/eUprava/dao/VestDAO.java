package com.ftn.EUprava.dao;

import java.util.List;

import com.ftn.EUprava.model.Vest;

public interface VestDAO {
	
	public Vest findOne(Long id);

	public List<Vest> findAll();
	
	public int save(Vest vest);

	public int update(Vest vest);

	public int delete(Long id);
}
