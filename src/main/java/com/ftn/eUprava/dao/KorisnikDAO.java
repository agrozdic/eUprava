package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface KorisnikDAO {
    Korisnik findOne(String email);
    Korisnik findOneByJMBG(String jmbg);

    Korisnik findOne(int korisnikID);

    Korisnik findOne(String email, String lozinka);
    List<Korisnik> findAll();
    int save(Korisnik korisnik);
    int update(Korisnik korisnik);
    int delete(int korisnikID);

}
