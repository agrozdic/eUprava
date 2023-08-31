package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.OboleliVestDAO;
import com.ftn.eUprava.models.OboleliVest;
import com.ftn.eUprava.services.OboleliVestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OboleliVestServiceImplementation implements OboleliVestService {

    @Autowired
    private OboleliVestDAO oboleliVestDAO;

    @Override
    public OboleliVest findOne(int oboleliVestID) {
        return oboleliVestDAO.findOne(oboleliVestID);
    }

    @Override
    public List<OboleliVest> findAll() {
        return oboleliVestDAO.findAll();
    }

    @Override
    public OboleliVest save(OboleliVest oboleliVest) {
        oboleliVestDAO.save(oboleliVest);
        return oboleliVest;
    }

    @Override
    public OboleliVest update(OboleliVest oboleliVest) {
        oboleliVestDAO.update(oboleliVest);
        return oboleliVest;
    }

    @Override
    public OboleliVest delete(int oboleliVestID) {
        OboleliVest oboleliVest = oboleliVestDAO.findOne(oboleliVestID);
        if(oboleliVest != null) {
            oboleliVestDAO.delete(oboleliVestID);
        }
        return oboleliVest;
    }

}