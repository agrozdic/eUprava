package com.ftn.eUprava.services.implementations;

import com.ftn.eUprava.models.Korisnik;
import com.ftn.eUprava.services.KorisnikService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KorisnikServiceDB implements KorisnikService {
    @Override
    public Korisnik findOneById(int korisnikID) {
        return null;
    }

    @Override
    public Korisnik findOne(String email) {
        return null;
    }

    @Override
    public Korisnik findOneByJMBG(String jmbg) {
        return null;
    }

    @Override
    public Korisnik findOne(String email, String lozinka) {
        return null;
    }

    @Override
    public List<Korisnik> findAll() {
        return null;
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        return null;
    }

    @Override
    public Korisnik update(Korisnik korisnik) {
        return null;
    }

    @Override
    public Korisnik delete(int id) {
        return null;
    }
}
