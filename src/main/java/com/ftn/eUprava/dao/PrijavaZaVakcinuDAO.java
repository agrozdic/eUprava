package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface PrijavaZaVakcinuDAO {

    PrijavaZaVakcinu findOne(int prijavaID);
    List<PrijavaZaVakcinu> findAll();
    int save(PrijavaZaVakcinu prijavaZaVakcinu);
    int delete(int prijavaID);
    List<PrijavaZaVakcinu> find(String ime, String prezime, String jmbg);

}
