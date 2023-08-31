package com.ftn.eUprava.services.implementations;

import java.util.List;

import com.ftn.eUprava.dao.VestDAO;
import com.ftn.eUprava.models.Vest;
import com.ftn.eUprava.services.VestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VestServiceImplementation implements VestService {

    @Autowired
    private VestDAO vestDAO;

    @Override
    public Vest findOne(int vestID) {
        return vestDAO.findOne(vestID);
    }

    @Override
    public List<Vest> findAll() {
        return vestDAO.findAll();
    }

    @Override
    public Vest save(Vest vest) {
        vestDAO.save(vest);
        return vest;
    }

    @Override
    public Vest update(Vest vest) {
        vestDAO.update(vest);
        return vest;
    }

    @Override
    public Vest delete(int vestID) {
        Vest vest = vestDAO.findOne(vestID);
        if(vest != null) {
            vestDAO.delete(vestID);
        }
        return vest;
    }

}

