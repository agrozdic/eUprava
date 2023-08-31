package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;
public interface NarudzbenicaService {

    Narudzbenica findOne(int narudzbenicaID);
    List<Narudzbenica> findAll();
    Narudzbenica save(Narudzbenica narudzbenica);
    Narudzbenica update(Narudzbenica narudzbenica);
    Narudzbenica delete(int narudzbenicaID);

}
