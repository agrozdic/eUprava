package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface PrijavaZaVakcinuService {

    PrijavaZaVakcinu findOne(int prijavaID);
    List< PrijavaZaVakcinu> findAll();
    PrijavaZaVakcinu save(PrijavaZaVakcinu prijavaZaVakcinu);
    PrijavaZaVakcinu delete(int prijavaID);
    List<PrijavaZaVakcinu> find(String ime, String prezime, String jmbg);

}
