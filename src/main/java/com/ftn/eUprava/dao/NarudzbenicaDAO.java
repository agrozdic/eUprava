package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface NarudzbenicaDAO {

    Narudzbenica findOne(int narudzbenicaID);
    List<Narudzbenica> findAll();
    Narudzbenica save(Narudzbenica narudzbenica);
    Narudzbenica update(Narudzbenica narudzbenica);
    Narudzbenica delete(int narudzbenicaID);

}
