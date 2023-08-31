package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface ProizvodjacDAO {

    Proizvodjac findOne(int proizvodjacID);
    List<Proizvodjac> findAll();
    int save(Proizvodjac proizvodjac);
    int update(Proizvodjac proizvodjac);
    int delete(int proizvodjacID);

}
