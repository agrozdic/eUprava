package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.VakcinaDAO;
import com.ftn.eUprava.models.Vakcina;
import com.ftn.eUprava.services.VakcinaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VakcinaServiceImplementation implements VakcinaService {

    @Autowired
    private VakcinaDAO vakcinaDAO;

    @Override
    public Vakcina findOne(int vakcinaID) {
        return vakcinaDAO.findOne(vakcinaID);
    }

    @Override
    public List<Vakcina> findAll() {
        return vakcinaDAO.findAll();
    }

    @Override
    public Vakcina save(Vakcina vakcina) {
        vakcinaDAO.save(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina update(Vakcina vakcina) {
        vakcinaDAO.update(vakcina);
        return vakcina;
    }

    @Override
    public Vakcina delete(int vakcinaID) {
        Vakcina vakcina = vakcinaDAO.findOne(vakcinaID);
        if(vakcina != null) {
            vakcinaDAO.delete(vakcinaID);
        }
        return vakcina;
    }

    @Override
    public List<Vakcina> find(String naziv, Integer dostupnaKolicinaMin, Integer dostupnaKolicinaMax, String proizvodjac, String drzava) {
        return vakcinaDAO.find(naziv, dostupnaKolicinaMin, dostupnaKolicinaMax, proizvodjac, drzava);
    }

}