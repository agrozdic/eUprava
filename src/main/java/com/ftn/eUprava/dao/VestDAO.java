package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface VestDAO {

    Vest findOne(int vestID);
    List<Vest> findAll();
    int save(Vest vest);
    int update(Vest vest);
    int delete(int vestID);

}
