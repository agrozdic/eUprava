package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface KorisnikService {

    Korisnik findOneById(int korisnikID);
    Korisnik findOne(String email);
    Korisnik findOneByJMBG(String jmbg);
    Korisnik findOne(String email, String lozinka);
    List<Korisnik> findAll();
    Korisnik save(Korisnik korisnik);
    Korisnik update(Korisnik korisnik);
    Korisnik delete(int id);

}
