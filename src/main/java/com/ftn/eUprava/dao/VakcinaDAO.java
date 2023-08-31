package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface VakcinaDAO {

    Vakcina findOne(int vakcinaID);
    List<Vakcina> findAll();
    int save(Vakcina vakcina);
    int update(Vakcina vakcina);
    int delete(int vakcinaID);
    List<Vakcina> find(String naziv, Integer dostupnaKolicinaMin, Integer dostupnaKolicinaMax,
                       String proizvodjac, String drzava);

}
