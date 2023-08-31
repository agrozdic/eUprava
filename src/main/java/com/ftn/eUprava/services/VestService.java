package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface VestService {

    Vest findOne(int vestID);
    List<Vest> findAll();
    Vest save(Vest vest);
    Vest update(Vest vest);
    Vest delete(int vestID);

}
