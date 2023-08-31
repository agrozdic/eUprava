package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.PrijavaZaVakcinuDAO;
import com.ftn.eUprava.services.PrijavaZaVakcinuService;
import com.ftn.eUprava.models.PrijavaZaVakcinu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrijavaZaVakcinuServiceImplementation implements PrijavaZaVakcinuService {

    @Autowired
    private PrijavaZaVakcinuDAO prijavaZaVakcinuDAO;



    @Override
    public PrijavaZaVakcinu findOne(int prijavaID) {
        return prijavaZaVakcinuDAO.findOne(prijavaID);
    }

    @Override
    public List<PrijavaZaVakcinu> findAll() {
        return prijavaZaVakcinuDAO.findAll();
    }

    @Override
    public PrijavaZaVakcinu save(PrijavaZaVakcinu prijavaZaVakcinu) {
        prijavaZaVakcinuDAO.save(prijavaZaVakcinu);
        return prijavaZaVakcinu;
    }

    @Override
    public PrijavaZaVakcinu delete(int prijavaID) {
        PrijavaZaVakcinu prijavaZaVakcinu = prijavaZaVakcinuDAO.findOne(prijavaID);
        if(prijavaZaVakcinu != null) {
            prijavaZaVakcinuDAO.delete(prijavaID);
        }
        return prijavaZaVakcinu;
    }
    @Override
    public List<PrijavaZaVakcinu> find(String ime, String prezime, String jmbg) {
        return prijavaZaVakcinuDAO.find(ime, prezime, jmbg);
    }

}