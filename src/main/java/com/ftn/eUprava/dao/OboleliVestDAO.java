package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface OboleliVestDAO {

    OboleliVest findOne(int oboleliVestID);
    List<OboleliVest> findAll();
    int save(OboleliVest oboleliVest);
    int update(OboleliVest oboleliVest);
    int delete(int oboleliVestID);

}
