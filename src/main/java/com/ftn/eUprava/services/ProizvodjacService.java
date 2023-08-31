package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface ProizvodjacService {

    Proizvodjac findOne(int proizvodjacID);
    List<Proizvodjac> findAll();
    Proizvodjac save(Proizvodjac proizvodjac);
    Proizvodjac update(Proizvodjac proizvodjac);
    Proizvodjac delete(int proizvodjacID);

}
