package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.VakcinacijaDAO;
import com.ftn.eUprava.models.Vakcinacija;
import com.ftn.eUprava.services.VakcinacijaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VakcinacijaServiceImplementation implements VakcinacijaService {

    @Autowired
    private VakcinacijaDAO vakcinacijaDAO;


    @Override
    public List<Vakcinacija> findAll() {
        return vakcinacijaDAO.findAll();
    }
}