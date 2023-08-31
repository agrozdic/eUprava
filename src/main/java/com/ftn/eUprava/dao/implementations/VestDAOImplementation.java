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

import com.ftn.eUprava.dao.VestDAO;
import com.ftn.eUprava.models.Vest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public class VestDAOImplementation implements VestDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class VestRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Vest> vesti = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int vestID = resultSet.getInt(index++);
            String naslov = resultSet.getString(index++);
            String sadrzaj = resultSet.getString(index++);
            LocalDateTime datumVreme = resultSet.getTimestamp(index++).toLocalDateTime();

            Vest vest = vesti.get(vestID);
            if (vest == null) {
                vest = new Vest(vestID, naslov, sadrzaj, datumVreme);
                vesti.put(vest.getVestID(), vest);
            }
        }

        public List<Vest> getVesti() {
            return new ArrayList<>(vesti.values());
        }
    }

    @Override
    public Vest findOne(int vestID) {
        String sql =
                "SELECT VestID, Naslov, Sadrzaj, DatumVreme FROM Vest " +
                "WHERE VestID = ? " +
                "ORDER BY VestID";

        VestRowCallBackHandler rowCallbackHandler = new VestRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, vestID);

        return rowCallbackHandler.getVesti().get(0);
    }

    @Override
    public List<Vest> findAll() {
        String sql =
                "SELECT VestID, Naslov, Sadrzaj, DatumVreme FROM Vest " +
                "ORDER BY VestID";

        VestRowCallBackHandler rowCallbackHandler = new VestRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler);

        return rowCallbackHandler.getVesti();
    }

    @Transactional
    @Override
    public int save(Vest vest) {
        PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                String sql = "INSERT INTO vesti (Naslov, Sadrzaj, DatumVreme) VALUES (?, ?, ?)";

                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                int index = 1;
                preparedStatement.setString(index++, vest.getNaslov());
                preparedStatement.setString(index++, vest.getSadrzaj());
                preparedStatement.setTimestamp(index++, Timestamp.valueOf(vest.getDvObjave()));

                return preparedStatement;
            }

        };
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(Vest vest) {

        String sql = "UPDATE Vest SET Naslov = ?, Sadrzaj = ?,  DatumVreme = ? WHERE VestID = ?";
        boolean uspeh = jdbcTemplate.update(sql, vest.getNaslov(), vest.getSadrzaj(),  vest.getDvObjave(), vest.getVestID()) == 1;

        return uspeh?1:0;
    }

    @Transactional
    @Override
    public int delete(int vestID) {
        String sql = "DELETE FROM Vest WHERE VestID = ?";
        return jdbcTemplate.update(sql, vestID);
    }
}
