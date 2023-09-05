package com.ftn.EUprava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.VestDAO;
import com.ftn.EUprava.model.Vest;
import com.ftn.EUprava.service.VestService;

@Service
public class DatabaseVestServiceImpl implements VestService{

	@Autowired
	private VestDAO vestDAO;

	@Override
	public Vest findOne(Long id) {
		return vestDAO.findOne(id);
	}

	@Override
	public List<Vest> findAll() {
		return vestDAO.findAll();
	}

	@Override
	public Vest save(Vest vest) {
		vestDAO.save(vest);
		return vest;
	}

	@Override
	public Vest update(Vest vest) {
		vestDAO.update(vest);
		return vest;
	}

	@Override
	public Vest delete(Long id) {
		Vest vest = vestDAO.findOne(id);
		if(vest != null) {
			vestDAO.delete(id);
		}
		return vest;
	}

}

