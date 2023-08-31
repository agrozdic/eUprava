package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.NarudzbenicaDAO;
import com.ftn.eUprava.models.Narudzbenica;
import com.ftn.eUprava.services.NarudzbenicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NarudzbenicaServiceImplementation implements NarudzbenicaService {

    @Autowired
    private NarudzbenicaDAO narudzbenicaDAO;


    public Narudzbenica findOne(int narudzbenicaID) {
        return narudzbenicaDAO.findOne(narudzbenicaID);
    }

    public List<Narudzbenica> findAll() {
        return narudzbenicaDAO.findAll();
    }


    public Narudzbenica save(Narudzbenica  narudzbenica) {
        narudzbenicaDAO.save(narudzbenica);
        return narudzbenica;
    }

    public Narudzbenica update(Narudzbenica narudzbenica) {
        narudzbenicaDAO.update(narudzbenica);
        return narudzbenica;
    }

    public Narudzbenica delete(int narudzbenicaID) {
        Narudzbenica narudzbenica = narudzbenicaDAO.findOne(narudzbenicaID);
        if(narudzbenica != null) {
            narudzbenicaDAO.delete(narudzbenicaID);
        }
        return narudzbenica;
    }

}