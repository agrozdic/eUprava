package com.ftn.eUprava.dao.implementations;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.ftn.eUprava.dao.OboleliVestDAO;
import com.ftn.eUprava.models.OboleliVest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class OboleliVestDAOImplementation implements OboleliVestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class OboleliVestRowCallbackHandler implements RowCallbackHandler {

        private Map<Integer, OboleliVest> vesti_oboleli = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int oboleliVestID = resultSet.getInt(index++);
            int brObolelih = resultSet.getInt(index++);
            int brTestiranih = resultSet.getInt(index++);
            int brUkupno = resultSet.getInt(index++);
            int brHospitalizovanih = resultSet.getInt(index++);
            int brNaRespiratoru = resultSet.getInt(index++);
            LocalDateTime datumVreme = resultSet.getTimestamp(index++).toLocalDateTime();

            OboleliVest vestoobolelima = vesti_oboleli.get(oboleliVestID);
            if (vestoobolelima == null) {
                vestoobolelima = new OboleliVest(oboleliVestID, brObolelih, brTestiranih, brUkupno, brHospitalizovanih, brNaRespiratoru, datumVreme);
                vesti_oboleli.put(vestoobolelima.getoboleliVestID(), vestoobolelima);
            }
        }

        public List<OboleliVest> getVesti_oboleli() {
            return new ArrayList<>(vesti_oboleli.values());
        }
    }

    @Override
    public OboleliVest findOne(int oboleliVestID) {
        String sql =
                "SELECT OboleliVestID, BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru " +
                "FROM OboleliVest " +
                "WHERE OboleliVestID = ? +" +
                "ORDER BY OboleliVestID.id";

        OboleliVestRowCallbackHandler rowCallbackHandler = new OboleliVestRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, oboleliVestID);

        return rowCallbackHandler.getVesti_oboleli().get(0);
    }

    @Override
    public List<OboleliVest> findAll() {
        String sql =
                "SELECT OboleliVestID, BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru " +
                "FROM OboleliVest " +
                "ORDER BY OboleliVestID.id";

        OboleliVestRowCallbackHandler rowCallbackHandler = new OboleliVestRowCallbackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getVesti_oboleli();
    }


    @Transactional
    @Override
    public int save(OboleliVest oboleliVest) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO OboleliVest(BrObolelih, BrTestiranih, BrHospitalizovanih, BrNaRespiratoru, DatumVreme) VALUES (?, ?, ?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setInt(index++, oboleliVest.getBrObolelih());
                preparedStatement.setInt(index++, oboleliVest.getBrTestiranih());
                preparedStatement.setInt(index++, oboleliVest.getBrHospitalizovanih());
                preparedStatement.setInt(index++, oboleliVest.getBrNaRespiratoru());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(oboleliVest.getDvObjave()));

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(OboleliVest oboleliVest) {

        String sql = "UPDATE OboleliVest SET BrObolelih = ?, BrTestiranih = ?, BrHospitalizovanih = ?, BrNaRespiratoru = ?, DatumVreme = ? WHERE OboleliVestID = ?";
        boolean uspeh = jdbcTemplate.update(sql, oboleliVest.getBrObolelih(), oboleliVest.getBrTestiranih(), oboleliVest.getBrHospitalizovanih(), oboleliVest.getBrNaRespiratoru(), oboleliVest.getDvObjave(), oboleliVest.getoboleliVestID()) == 1;

        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(int oboleliVestID) {
        String sql = "DELETE FROM OboleliVest WHERE OboleliVestID = ?";
        return jdbcTemplate.update(sql, oboleliVestID);
    }

}
