package com.ftn.EUprava.dao.impl;


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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.EUprava.dao.VestDAO;
import com.ftn.EUprava.model.Vest;


@Repository
public class VestDAOImpl implements VestDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	private class VestRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Vest> vesti = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String nazivVesti = resultSet.getString(index++);
			String sadrzajVesti = resultSet.getString(index++);
			LocalDateTime datumIVremeObjavljivanja = resultSet.getTimestamp(index++).toLocalDateTime();

			Vest vest = vesti.get(id);
			if (vest == null) {
				vest = new Vest(id, nazivVesti, sadrzajVesti, datumIVremeObjavljivanja);
				vesti.put(vest.getId(), vest); 
			}
			
		
		}

		public List<Vest> getVesti() {
			return new ArrayList<>(vesti.values());
		}

	}

	@Override
	public Vest findOne(Long id) {
		String sql = 
				"SELECT id, nazivVesti, sadrzajVesti ,datumIVremeObjavljivanja FROM vesti  " + 
				"WHERE id = ? " + 
				"ORDER BY id";

		VestRowCallBackHandler rowCallbackHandler = new VestRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getVesti().get(0);
	}

	@Override
	public List<Vest> findAll() {
		String sql = 
				"SELECT id, nazivVesti, sadrzajVesti ,datumIVremeObjavljivanja FROM vesti  " + 
						"ORDER BY id";

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
				String sql = "INSERT INTO vesti (nazivVesti, sadrzajVesti, datumIVremeObjavljivanja) VALUES (?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, vest.getNazivVesti());
				preparedStatement.setString(index++, vest.getSadrzajVesti());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(vest.getDatumIVremeObjavljivanja()));

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Vest vest) {	
		
		String sql = "UPDATE vesti SET nazivVesti = ?, sadrzajVesti = ?,  datumIVremeObjavljivanja = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, vest.getNazivVesti(), vest.getSadrzajVesti(),  vest.getDatumIVremeObjavljivanja(), vest.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM vesti WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
