package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface VakcinaDAO {

    Vakcina findOne(int vakcinaID);
    List<Vakcina> findAll();
    Vakcina save(Vakcina vakcina);
    Vakcina update(Vakcina vakcina);
    Vakcina delete(int vakcinaID);
    List<Vakcina> find(String ime, int dostupnaKolicinaMin, int dostupnaKolicinaMax,
                       String proizvodjac, String drzava);

}
