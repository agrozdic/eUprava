package com.ftn.eUprava.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ftn.eUprava.models.DozaVakcine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.eUprava.dao.KorisnikDAO;
import com.ftn.eUprava.dao.PrijavaZaVakcinuDAO;
import com.ftn.eUprava.dao.VakcinaDAO;
import com.ftn.eUprava.models.Korisnik;
import com.ftn.eUprava.models.PrijavaZaVakcinu;
import com.ftn.eUprava.models.Vakcina;


@Repository
public class PrijavaZaVakcinuDAOImplementation implements PrijavaZaVakcinuDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VakcinaDAO vakcinaDAO;

    @Autowired
    private KorisnikDAO korisnikDAO;

    private class PrijavaRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, PrijavaZaVakcinu> prijave = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int prijavaID = resultSet.getInt(index++);
            int korisnikID = resultSet.getInt(index++);
            Korisnik korisnik = korisnikDAO.findOne(korisnikID);
            int vakcinaID = resultSet.getInt(index++);
            Vakcina vakcina = vakcinaDAO.findOne(vakcinaID);
            DozaVakcine doza = DozaVakcine.valueOf(resultSet.getString(index++));
            LocalDateTime dvPrijave = resultSet.getObject(index++, LocalDateTime.class);

            PrijavaZaVakcinu prijava = prijave.get(prijavaID);
            if (prijava == null) {
                prijava = new PrijavaZaVakcinu(prijavaID, korisnik, vakcina, doza, dvPrijave);
                prijave.put(prijava.getPrijavaID(), prijava);
            }
        }

        public List<PrijavaZaVakcinu> getPrijave() {
            return new ArrayList<>(prijave.values());
        }
    }

    public PrijavaZaVakcinu findOne(int prijavaID) {
        String sql =
                "SELECT PrijavaID, KorisnikID, VakcinaID, DozaVakcine, DatumVreme " +
                "FROM PrijavaZaVakcinu " +
                "WHERE PrijavaID = ? " +
                "ORDER BY PrijavaID";

        PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, prijavaID);

        return rowCallbackHandler.getPrijave().get(0);
    }

    public List<PrijavaZaVakcinu> findAll() {
        String sql =
                "SELECT PrijavaID, KorisnikID, VakcinaID, DozaVakcine, DatumVreme " +
                "FROM PrijavaZaVakcinu " +
                "ORDER BY PrijavaID";

        PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getPrijave();
    }

    @Transactional
    public int save(PrijavaZaVakcinu prijava) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO PrijavaZaVakcinu(KorisnikID, VakcinaID, DozaVakcine) VALUES (?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, prijava.getKorisnik().getKorisnikID());
                preparedStatement.setInt(index++, prijava.getVakcina().getVakcinaID());
                DozaVakcine doza = prijava.getDoza();
                preparedStatement.setString(index++, doza.name());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }

    @Transactional
    public int delete(int prijavaID) {
        String sql = "DELETE FROM PrijavaZaVakcinu WHERE PrijavaID = ?";
        return jdbcTemplate.update(sql, prijavaID);
    }

    public List<PrijavaZaVakcinu> find(String ime, String prezime,  String jmbg) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * from PrijavaZaVakcinu p");
        List<Object> params = new ArrayList<>();
        boolean whereIncluded = false;

        if (ime != null && !ime.trim().isEmpty()) {
            if (whereIncluded) {
                sb.append(" AND p.KorisnikID in (SELECT k.KorisnikID from Korisnik k WHERE k.Ime = ?)");
            } else {
                sb.append(" WHERE p.KorisnikID in (SELECT k.KorisnikID from Korisnik k WHERE k.Ime = ?)");
                whereIncluded = true;
            }
            params.add(ime);
        }
        if (prezime != null && !prezime.trim().isEmpty()) {
            if (whereIncluded) {
                sb.append(" AND p.KorisnikID in (SELECT k.KorisnikID from Korisnik k WHERE k.Prezime = ?)");
            } else {
                sb.append(" WHERE  p.KorisnikID in  (SELECT k.KorisnikID from Korisnik k WHERE k.Prezime = ?)");
                whereIncluded = true;
            }
            params.add(prezime);
        }
        if (jmbg != null && !jmbg.trim().isEmpty()) {
            if (whereIncluded) {
                sb.append(" AND p.KorisnikID in (SELECT k.KorisnikID from Korisnik k WHERE k.JMBG = ?)");
            } else {
                sb.append(" WHERE  p.KorisnikID in (SELECT k.KorisnikID from Korisnik k WHERE k.JMBG = ?)");
                whereIncluded = true;
            }
            params.add(jmbg);
        }
        String sql = sb.toString();
        PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
        jdbcTemplate.query(sql, params.toArray(), rowCallbackHandler);
        return rowCallbackHandler.getPrijave();
    }

}
