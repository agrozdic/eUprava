package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface VakcinaService {

    Vakcina findOne(int vakcinaID);
    List<Vakcina> findAll();
    Vakcina save(Vakcina vakcina);
    Vakcina update(Vakcina vakcina);
    Vakcina delete(int vakcinaID);
    List<Vakcina> find(String ime, Integer dostupnaKolicinaMin, Integer dostupnaKolicinaMax,
                       String proizvodjac, String drzava);

}
