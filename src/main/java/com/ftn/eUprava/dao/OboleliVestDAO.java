package com.ftn.eUprava.dao;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface OboleliVestDAO {

    OboleliVest findOne(int oboleliVestID);
    List<OboleliVest> findAll();
    OboleliVest save(OboleliVest oboleliVest);
    OboleliVest update(OboleliVest oboleliVest);
    OboleliVest delete(int oboleliVestID);

}
