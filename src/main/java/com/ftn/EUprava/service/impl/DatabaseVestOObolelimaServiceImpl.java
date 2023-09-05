package com.ftn.EUprava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.VestOObolelimaDAO;
import com.ftn.EUprava.model.VestOObolelima;
import com.ftn.EUprava.service.VestOObolelimaService;

@Service
public class DatabaseVestOObolelimaServiceImpl implements VestOObolelimaService{

	@Autowired
	private VestOObolelimaDAO vestOObolelimaDAO;

	@Override
	public VestOObolelima findOne(Long id) {
		return vestOObolelimaDAO.findOne(id);
	}

	@Override
	public List<VestOObolelima> findAll() {
		return vestOObolelimaDAO.findAll();
	}

	@Override
	public VestOObolelima save(VestOObolelima vestOObolelima) {
		vestOObolelimaDAO.save(vestOObolelima);
		return vestOObolelima;
	}

	@Override
	public VestOObolelima update(VestOObolelima vestOObolelima) {
		vestOObolelimaDAO.update(vestOObolelima);
		return vestOObolelima;
	}

	@Override
	public VestOObolelima delete(Long id) {
		VestOObolelima vestOObolelima = vestOObolelimaDAO.findOne(id);
		if(vestOObolelima != null) {
			vestOObolelimaDAO.delete(id);
		}
		return vestOObolelima;
	}

}

