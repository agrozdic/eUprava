package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface PrijavaZaVakcinuDAO {

    PrijavaZaVakcinu findOne(int prijavaID);
    List< PrijavaZaVakcinu> findAll();
    PrijavaZaVakcinu save(PrijavaZaVakcinu prijavaZaVakcinu);
    PrijavaZaVakcinu delete(int prijavaID);
    List<PrijavaZaVakcinu> find(String ime, String prezime, String jmbg);

}
