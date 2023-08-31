package com.ftn.eUprava.dao.implementations;

import java.sql.Connection;

import com.ftn.eUprava.models.Narudzbenica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import com.ftn.eUprava.dao.NarudzbenicaDAO;
import com.ftn.eUprava.dao.VakcinaDAO;
import com.ftn.eUprava.models.StatusZahteva;
import com.ftn.eUprava.models.Narudzbenica;
import com.ftn.eUprava.models.Vakcina;


@Repository
public class NarudzbenicaDAOImplementation implements NarudzbenicaDAO{
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VakcinaDAO vakcinaDAO;

    private class NarudzbenicaRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Narudzbenica> narudzbenice = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int narudzbenicaID = resultSet.getInt(index++);
            int vakcinaID = resultSet.getInt(index++);
            Vakcina vakcina = vakcinaDAO.findOne(vakcinaID);
            int kolicina = resultSet.getInt(index++);
            LocalDateTime dvNabavke = resultSet.getObject(index++, LocalDateTime.class);
            String komentar = resultSet.getString(index++);
            StatusZahteva status = StatusZahteva.valueOf(resultSet.getString(index++));

            Narudzbenica narudzbenica = narudzbenice.get(narudzbenicaID);
            if (narudzbenica == null) {
                narudzbenica = new Narudzbenica(narudzbenicaID, vakcina, kolicina, dvNabavke, komentar, status);
                narudzbenice.put(narudzbenica.getNarudzbenicaID(), narudzbenica);
            }
        }

        public List<Narudzbenica> getVakcine() {
            return new ArrayList<>(narudzbenice.values());
        }
    }

    @Override
    public Narudzbenica findOne(int narudzbenicaID) {
        String sql =
                "SELECT NarudzbenicaID, VakcinaID, kolicina, DatumVreme, Komentar, StatusZahteva " +
                "FROM Narudzbenica " +
                "WHERE NarudzbenicaID = ? " +
                "ORDER BY NarudzbenicaID";

        NarudzbenicaRowCallBackHandler rowCallbackHandler = new NarudzbenicaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, narudzbenicaID);

        return rowCallbackHandler.getVakcine().get(0);
    }

    @Override
    public List<Narudzbenica> findAll() {
        String sql =
                "SELECT NarudzbenicaID, VakcinaID, kolicina, DatumVreme, Komentar, StatusZahteva " +
                "FROM Narudzbenica " +
                "WHERE NarudzbenicaID = ? " +
                "ORDER BY NarudzbenicaID";

        NarudzbenicaRowCallBackHandler rowCallbackHandler = new NarudzbenicaRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getVakcine();
    }


    @Transactional
    @Override
    public int save(Narudzbenica narudzbenica) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO Narudzbenica(VakcinaID, Kolicina, Komentar, StatusZahteva) VALUES (?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;

                preparedStatement.setInt(index++, narudzbenica.getVakcina().getVakcinaID());
                preparedStatement.setInt(index++, narudzbenica.getKolicina());
                preparedStatement.setString(index++, narudzbenica.getKomentar());

                StatusZahteva status = narudzbenica.getStatus();
                preparedStatement.setString(index++, status.name());

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }
    @Transactional
    @Override
    public int update(Narudzbenica narudzbenica) {
        String sql =
                "UPDATE Narudzbenica " +
                "SET VakcinaID = ?, Kolicina = ?, Komentar = ?, StatusZahteva = ? " +
                "WHERE NarudzbenicaID = ?";

        boolean uspeh = jdbcTemplate.update(sql, narudzbenica.getVakcina().getVakcinaID(), narudzbenica.getKolicina(),
                narudzbenica.getKomentar(), narudzbenica.getStatus().toString(), narudzbenica.getNarudzbenicaID()) == 1;

        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(int narudzbenicaID) {
        String sql = "DELETE FROM Narudzbenica WHERE NarudzbenicaID = ?";
        return jdbcTemplate.update(sql, narudzbenicaID);
    }


}
