package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface ProizvodjacDAO {

    Proizvodjac findOne(int proizvodjacID);
    List<Proizvodjac> findAll();
    Proizvodjac save(Proizvodjac proizvodjac);
    Proizvodjac update(Proizvodjac proizvodjac);
    Proizvodjac delete(int proizvodjacID);

}
