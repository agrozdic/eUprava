package com.ftn.eUprava.services;

import java.util.List;
import com.ftn.eUprava.models.*;

public interface OboleliVestService {

    OboleliVest findOne(int oboleliVestID);
    List<OboleliVest> findAll();
    OboleliVest save(OboleliVest oboleliVest);
    OboleliVest update(OboleliVest oboleliVest);
    OboleliVest delete(int oboleliVestID);

}
