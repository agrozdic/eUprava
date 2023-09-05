package com.ftn.EUprava.dao.impl;

import java.sql.Connection;
import org.springframework.jdbc.core.RowMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
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

import com.ftn.EUprava.dao.NabavkaDAO;
import com.ftn.EUprava.dao.VakcinaDAO;
import com.ftn.EUprava.model.EStatus;
import com.ftn.EUprava.model.Nabavka;
import com.ftn.EUprava.model.Vakcina;


@Repository
public class NabavkaDAOImpl implements NabavkaDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VakcinaDAO vakcinaDAO;
	
	private class NabavkaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Nabavka> nabavke = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Long vakcinaId = resultSet.getLong(index++);
			Vakcina vakcina = vakcinaDAO.findOne(vakcinaId);
			Integer kolicina = resultSet.getInt(index++);
			LocalDateTime datumIVremeNabavke = resultSet.getObject(index++, LocalDateTime.class);
			String komentar = resultSet.getString(index++);
			EStatus status = EStatus.valueOf(resultSet.getString(index++));
			
			
			
			
			
			Nabavka nabavka = nabavke.get(id);
			if (nabavka == null) {
				nabavka = new Nabavka(id, vakcina, kolicina, datumIVremeNabavke, komentar, status);
				nabavke.put(nabavka.getId(), nabavka); 
			}
		}

		public List<Nabavka> getVakcine() {
			return new ArrayList<>(nabavke.values());
		}

	}
	
	@Override
	public Nabavka findOne(Long id) {
		String sql = 
				"SELECT id, vakcinaId, kolicina, datumIVremeNabavke, komentar, eStatus FROM nabavke " + 
				"WHERE id = ? " + 
				"ORDER BY id";

		NabavkaRowCallBackHandler rowCallbackHandler = new NabavkaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getVakcine().get(0);
	}

	@Override
	public List<Nabavka> findAll() {
		String sql = 
				"SELECT id, vakcinaId, kolicina, datumIVremeNabavke, komentar, eStatus FROM nabavke " + 
						"ORDER BY id";

		NabavkaRowCallBackHandler rowCallbackHandler = new NabavkaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getVakcine();
	}
	
	
	@Transactional
	@Override
	public int save(Nabavka nabavka) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO nabavke (vakcinaId, kolicina,  komentar, estatus) VALUES (?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				
				preparedStatement.setLong(index++, nabavka.getVakcina().getId());
				preparedStatement.setInt(index++, nabavka.getKolicina());
				preparedStatement.setString(index++, nabavka.getKomentar());

				EStatus status = nabavka.getStatus();
				preparedStatement.setString(index++, status.name());

				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	@Transactional
	@Override
	public int update(Nabavka nabavka) {
		String sql = "UPDATE nabavke SET vakcinaId = ?, kolicina = ?, komentar = ?, eStatus = ? WHERE id = ?";	
		
		boolean uspeh = jdbcTemplate.update(sql, nabavka.getVakcina().getId(), nabavka.getKolicina(), 
				 nabavka.getKomentar(), nabavka.getStatus().toString(), nabavka.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM nabavke WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	
}
