package com.ftn.eUprava.dao.implementations;

import java.sql.Connection;

import com.ftn.eUprava.dao.ProizvodjacDAO;
import com.ftn.eUprava.dao.VakcinaDAO;
import com.ftn.eUprava.models.Proizvodjac;
import com.ftn.eUprava.models.Vakcina;
import org.springframework.jdbc.core.RowMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class VakcinaDAOImplementation implements VakcinaDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ProizvodjacDAO proizvodjacDAO;

    private class VakcinaRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Vakcina> vakcine = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int vakcinaID = resultSet.getInt(index++);
            String naziv = resultSet.getString(index++);
            int kolicina = resultSet.getInt(index++);
            int proizvodjacID = resultSet.getInt(index++);
            Proizvodjac proizvodjacVakcine = proizvodjacDAO.findOne(proizvodjacID);

            Vakcina vakcina = vakcine.get(vakcinaID);
            if (vakcina == null) {
                vakcina = new Vakcina(vakcinaID, naziv, kolicina, proizvodjacVakcine);
                vakcine.put(vakcina.getVakcinaID(), vakcina);
            }
        }

        public List<Vakcina> getVakcine() {
            return new ArrayList<>(vakcine.values());
        }
    }

    @Override
    public Vakcina findOne(int vakcinaID) {
        String sql =
                "SELECT VakcinaID, Naziv, DostupnaKolicina, ProizvodjacID " +
                "FROM Vakcina " +
                "WHERE VakcinaID = ? " +
                "ORDER BY VakcinaID";
        VakcinaRowCallBackHandler rowCallbackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, vakcinaID);

        return rowCallbackHandler.getVakcine().get(0);
    }

    @Override
    public List<Vakcina> findAll() {
        String sql =
                "SELECT VakcinaID, Naziv, DostupnaKolicina, ProizvodjacID " +
                "FROM Vakcina " +
                "ORDER BY VakcinaID";

        VakcinaRowCallBackHandler rowCallbackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getVakcine();
    }

    @Transactional
    @Override
    public int save(Vakcina vakcina) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO Vakcina (Naziv, ProizvodjacID) VALUES (?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, vakcina.getNaziv());
                preparedStatement.setLong(index++, vakcina.getProizvodjac().getProizvodjacID());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(Vakcina vakcina) {
        String sql = "UPDATE Vakcina SET Naziv = ?, DostupnaKolicina = ?, ProizvodjacID = ? WHERE VakcinaID = ?";
        boolean uspeh = jdbcTemplate.update(sql, vakcina.getNaziv() , vakcina.getDostupnaKolicina(), vakcina.getProizvodjac().getProizvodjacID(), vakcina.getVakcinaID()) == 1;

        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(int vakcinaID) {
        String sql = "DELETE FROM Vakcina WHERE vakcinaID = ?";
        return jdbcTemplate.update(sql, vakcinaID);
    }

    public List<Vakcina> find(String naziv, Integer dostupnaKolicinaMin, Integer dostupnaKolicinaMax, String proizvodjac, String drzava) {
        StringBuilder sb = new StringBuilder();
        sb.append( "SELECT v.VakcinaID, v.Naziv, v.DostupnaKolicina, p.ProizvodjacID, p.Naziv FROM Vakcina v " +
                "LEFT JOIN Proizvodjac p ON v.ProizvodjacID = p.ProizvodjacID");
        List<Object> params = new ArrayList<>();
        boolean whereIncluded = false;

        if (naziv != null && !naziv.isEmpty()) {
            sb.append(" WHERE LOWER(v.Naziv) LIKE LOWER(?)");
            params.add("%" + naziv + "%");
            whereIncluded = true;
        }

        if (dostupnaKolicinaMin != null) {
            if (!whereIncluded) {
                sb.append(" WHERE v.DostupnaKolicina >= ?");
            } else {
                sb.append(" AND v.DostupnaKolicina >= ?");
            }
            params.add(dostupnaKolicinaMin);
            whereIncluded = true;
        }

        if (dostupnaKolicinaMax != null) {
            if (!whereIncluded) {
                sb.append(" WHERE v.DostupnaKolicina <= ?");
            } else {
                sb.append(" AND v.DostupnaKolicina <= ?");
            }
            params.add(dostupnaKolicinaMax);
            whereIncluded = true;
        }

        if (proizvodjac != null && !proizvodjac.trim().isEmpty()) {
            if (whereIncluded) {
                sb.append(" AND v.ProizvodjacID in (SELECT p.ProizvodjacID from Proizvodjac p WHERE p.Naziv = ?)");
            } else {
                sb.append(" WHERE v.proizvodjacId in (SELECT p.ProizvodjacID from Proizvodjac p WHERE p.Naziv = ?)");
                whereIncluded = true;
            }
            params.add(proizvodjac);
        }
        if (drzava != null && !drzava.trim().isEmpty()) {
            if (whereIncluded) {
                sb.append(" AND v.ProizvodjacID in (select p.ProizvodjacID from Proizvodjac p WHERE p.Drzava = ?)");
            } else {
                sb.append(" WHERE v.ProizvodjacID in (select p.ProizvodjacID from Proizvodjac p WHERE p.Drzava = ?)");
                whereIncluded = true;
            }
            params.add(drzava);
        }

        String sql = sb.toString();
        VakcinaRowCallBackHandler rowCallbackHandler = new VakcinaRowCallBackHandler();
        jdbcTemplate.query(sql, params.toArray(), rowCallbackHandler);
        return rowCallbackHandler.getVakcine();
    }

}
