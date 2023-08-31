package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface NarudzbenicaDAO {

    Narudzbenica findOne(int narudzbenicaID);
    List<Narudzbenica> findAll();
    int save(Narudzbenica narudzbenica);
    int update(Narudzbenica narudzbenica);
    int delete(int narudzbenicaID);

}
