package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.ProizvodjacDAO;
import com.ftn.eUprava.models.Proizvodjac;
import com.ftn.eUprava.services.ProizvodjacService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProizvodjacServiceImplementation implements ProizvodjacService {

    @Autowired
    private ProizvodjacDAO proizvodjacDAO;

    @Override
    public Proizvodjac findOne(int proizvodjacID) {
        return proizvodjacDAO.findOne(proizvodjacID);
    }

    @Override
    public List<Proizvodjac> findAll() {
        return proizvodjacDAO.findAll();
    }

    @Override
    public Proizvodjac save(Proizvodjac proizvodjac) {
        proizvodjacDAO.save(proizvodjac);
        return proizvodjac;
    }

    @Override
    public Proizvodjac update(Proizvodjac proizvodjac) {
        proizvodjacDAO.update(proizvodjac);
        return proizvodjac;
    }

    @Override
    public Proizvodjac delete(int proizvodjacID) {
        Proizvodjac proizvodjac  = proizvodjacDAO.findOne(proizvodjacID);
        if(proizvodjac != null) {
            proizvodjacDAO.delete(proizvodjacID);
        }
        return proizvodjac;
    }

}