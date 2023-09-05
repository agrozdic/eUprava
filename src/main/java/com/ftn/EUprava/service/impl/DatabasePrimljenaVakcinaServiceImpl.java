package com.ftn.EUprava.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.EUprava.dao.PrimljenaVakcinaDAO;
import com.ftn.EUprava.model.PrimljenaVakcina;
import com.ftn.EUprava.service.PrimljenaVakcinaService;

@Service
public class DatabasePrimljenaVakcinaServiceImpl implements PrimljenaVakcinaService {

		@Autowired
		private PrimljenaVakcinaDAO primljenaVakcinaDAO;
		
	
		@Override
		public List<PrimljenaVakcina> findAll() {
			return primljenaVakcinaDAO.findAll();
		}
}