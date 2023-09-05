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

import com.ftn.EUprava.dao.VestOObolelimaDAO;
import com.ftn.EUprava.model.VestOObolelima;


@Repository
public class VestOObolelimaDAOImpl implements VestOObolelimaDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	
	
	private class VestOObolelimaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, VestOObolelima> vestiOObolelima = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			Integer oboleliUPoslednja24h = resultSet.getInt(index++);
			Integer testiraniUPoslednja24h = resultSet.getInt(index++);
			Integer ukupnoOboleliOdPocetkaPandemije = resultSet.getInt(index++);
			Integer hospitalizovani = resultSet.getInt(index++);
			Integer naRespiratoru = resultSet.getInt(index++);
			LocalDateTime datumIVremeRegistracije = resultSet.getTimestamp(index++).toLocalDateTime();

			VestOObolelima vestOObolelima = vestiOObolelima.get(id);
			if (vestOObolelima == null) {
				vestOObolelima = new VestOObolelima(id, oboleliUPoslednja24h, testiraniUPoslednja24h,ukupnoOboleliOdPocetkaPandemije,hospitalizovani, naRespiratoru,datumIVremeRegistracije);
				vestiOObolelima.put(vestOObolelima.getId(), vestOObolelima); 
			}
			
		
		}

		public List<VestOObolelima> getVestiOObolelima() {
			return new ArrayList<>(vestiOObolelima.values());
		}

	}
	

	@Override
	public VestOObolelima findOne(Long id) {
		String sql =  "SELECT a.id, a.OboleliUPoslednja24h, a.TestiraniUPoslednja24h, b.UkupnoOboleliOdPocetkaPandemije,"
				+ " a.Hospitalizovani, a.NaRespiratoru, a.DatumIVremeObjavljivanja FROM vestioobolelima "
				+ "a JOIN (SELECT SUM(OboleliUPoslednja24h) AS UkupnoOboleliOdPocetkaPandemije FROM vestioobolelima) b WHERE a.id = ? ORDER BY a.id";


		VestOObolelimaRowCallBackHandler rowCallbackHandler = new VestOObolelimaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getVestiOObolelima().get(0);
	}

	@Override
	public List<VestOObolelima> findAll() {
		String sql =  "SELECT a.id, a.OboleliUPoslednja24h, a.TestiraniUPoslednja24h, b.UkupnoOboleliOdPocetkaPandemije,"
				+ " a.Hospitalizovani, a.NaRespiratoru, a.DatumIVremeObjavljivanja FROM vestioobolelima "
				+ "a JOIN (SELECT SUM(OboleliUPoslednja24h) AS UkupnoOboleliOdPocetkaPandemije FROM vestioobolelima) b";


		VestOObolelimaRowCallBackHandler rowCallbackHandler = new VestOObolelimaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getVestiOObolelima();
	}
	

	@Transactional
	@Override
	public int save(VestOObolelima vestOObolelima) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO vestiOObolelima (oboleliUPoslednja24h, testiraniUPoslednja24h,"
						+ "hospitalizovani, naRespiratoru ,datumIVremeObjavljivanja) VALUES (?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setInt(index++, vestOObolelima.getOboleliUPoslednja24h());
				preparedStatement.setInt(index++, vestOObolelima.getTestiraniUPoslednja24h());
				preparedStatement.setInt(index++, vestOObolelima.getHospitalizovani());
				preparedStatement.setInt(index++, vestOObolelima.getNaRespiratoru());
				preparedStatement.setTimestamp(index++, Timestamp.valueOf(vestOObolelima.getDatumIVremeObjavljivanja()));

				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(VestOObolelima vestObolelima) {	
		
		String sql = "UPDATE vestiOObolelima SET oboleliUPoslednja24h = ?, testiraniUPoslednja24h = ?, hospitalizovani = ?, naRespiratoru = ?,datumIVremeObjavljivanja = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, vestObolelima.getOboleliUPoslednja24h(), vestObolelima.getTestiraniUPoslednja24h(),
				vestObolelima.getHospitalizovani(),
				vestObolelima.getNaRespiratoru(),
				vestObolelima.getDatumIVremeObjavljivanja(), vestObolelima.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM vestiOObolelima WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
}
