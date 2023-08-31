package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.KorisnikDAO;
import com.ftn.eUprava.models.Korisnik;
import com.ftn.eUprava.services.KorisnikService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class KorisnikServiceImplementation implements KorisnikService {

    @Autowired
    private KorisnikDAO korisnikDAO;

    @Override
    public Korisnik findOneById(int korisnikID) {
        return korisnikDAO.findOne(korisnikID);
    }

    @Override
    public Korisnik findOne(String email) {
        return korisnikDAO.findOne(email);
    }
    @Override
    public Korisnik findOneByJMBG(String jmbg) {
        return korisnikDAO.findOneByJMBG(jmbg);
    }

    @Override
    public Korisnik findOne(String email, String lozinka) {
        return korisnikDAO.findOne(email, lozinka);
    }

    @Override
    public List<Korisnik> findAll() {
        return korisnikDAO.findAll();
    }

    @Override
    public Korisnik save(Korisnik korisnik) {
        korisnikDAO.save(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik update(Korisnik korisnik) {
        korisnikDAO.update(korisnik);
        return korisnik;
    }

    @Override
    public Korisnik delete(int korisnikID) {
        Korisnik korisnik = korisnikDAO.findOne(korisnikID);
        korisnikDAO.delete(korisnikID);
        return korisnik;
    }

}
