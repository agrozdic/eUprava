package com.ftn.eUprava.dao.implementations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ftn.eUprava.dao.VakcinacijaDAO;
import com.ftn.eUprava.models.DozaVakcine;
import com.ftn.eUprava.models.Vakcina;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ftn.eUprava.dao.KorisnikDAO;
import com.ftn.eUprava.dao.VakcinaDAO;
import com.ftn.eUprava.models.Korisnik;
import com.ftn.eUprava.models.Vakcinacija;


@Repository
public class VakcinacijaDAOImplementation implements VakcinacijaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VakcinaDAO vakcinaDAO;

    @Autowired
    private KorisnikDAO korisnikDAO;

    private class VakcinacijaRowCallbackHandler implements RowCallbackHandler {

        private Map<Integer, Vakcinacija> vakcinacije = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int vakcinacijaID = resultSet.getInt(index++);
            int korisnikID = resultSet.getInt(index++);
            Korisnik korisnik = korisnikDAO.findOne(korisnikID);
            int vakcinaID = resultSet.getInt(index++);
            Vakcina vakcina = vakcinaDAO.findOne(vakcinaID);
            DozaVakcine doza = DozaVakcine.valueOf(resultSet.getString(index++));
            LocalDateTime datumVreme = resultSet.getObject(index++, LocalDateTime.class);

            Vakcinacija vakcinacija = vakcinacije.get(vakcinacijaID);
            if (vakcinacija == null) {
                vakcinacija = new Vakcinacija(vakcinacijaID, korisnik, vakcina, doza, datumVreme);
                vakcinacije.put(vakcinacija.getVakcinacijaID(), vakcinacija);
            }
        }

        public List<Vakcinacija> getVakcinacije() {
            return new ArrayList<>(vakcinacije.values());
        }

    }

    public List<Vakcinacija> findAll() {
        String sql =
                "SELECT VakcinacijaID, VakcinacijaID, VakcinaID, DozaVakcine, DatumVreme " +
                "FROM Vakcinacija " +
                "ORDER BY VakcinacijaID";

        VakcinacijaRowCallbackHandler rowCallbackHandler = new VakcinacijaRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getVakcinacije();
    }

}