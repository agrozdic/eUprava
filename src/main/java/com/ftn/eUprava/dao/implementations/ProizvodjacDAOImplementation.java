package com.ftn.eUprava.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ftn.eUprava.models.Proizvodjac;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.eUprava.dao.*;

@Repository
public class ProizvodjacDAOImpelementation implements ProizvodjacDAO{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class ProizvodjacRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Proizvodjac> proizvodjaci = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int proizvodjacID = resultSet.getInt(index++);
            String naziv = resultSet.getString(index++);
            String drzava = resultSet.getString(index++);
            Proizvodjac proizvodjac = proizvodjaci.get(id);
            if (proizvodjac == null) {
                proizvodjac = new Proizvodjac(proizvodjacID, naziv, drzava);
                proizvodjaci.put(proizvodjac.getProizvodjacID(), proizvodjac);
            }
        }

        public List<Proizvodjac> getProizvodjaci() {
            return new ArrayList<>(proizvodjaci.values());
        }
    }

    @Override
    public Proizvodjac findOne(int proizvodjacID) {
        String sql =
                "SELECT ProizvodjacID, Naziv, Drzava " +
                "FROM Proizvodjaci  " +
                "WHERE ProizvodjacID = ? " +
                "ORDER BY ProizvodjacID";

        ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, proizvodjacID);

        return rowCallbackHandler.getProizvodjaci().get(0);
    }

    @Override
    public List<Proizvodjac> findAll() {
        String sql =
                "SELECT ProizvodjacID, Naziv, Drzava " +
                "FROM Proizvodjaci  " +
                "ORDER BY ProizvodjacID";

        ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getProizvodjaci();
    }

    @Transactional
    @Override
    public int save(Proizvodjac proizvodjac) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO Proizvodjac(Naziv, Drzava) VALUES (?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, proizvodjac.getNaziv());
                preparedStatement.setString(index++, proizvodjac.getDrzava());

                return preparedStatement;
            }
        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(Proizvodjac proizvodjac) {
        String sql = "UPDATE Proizvodjac SET Naziv = ?, Drzava = ? WHERE ProizvodjacID = ?";
        boolean uspeh = jdbcTemplate.update(sql, proizvodjac.getNaziv() , proizvodjac.getDrzava(), proizvodjac.getProizvodjacID()) == 1;

        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(int proizvodjacID) {
        String sql = "DELETE FROM Proizvodjac WHERE ProizvodjacID = ?";
        return jdbcTemplate.update(sql, proizvodjacID);
    }

}