package com.ftn.EUprava.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

import com.ftn.EUprava.dao.ProizvodjacDAO;
import com.ftn.EUprava.dao.VakcinaDAO;
import com.ftn.EUprava.model.ProizvodjacVakcine;
import com.ftn.EUprava.model.Vakcina;

@Repository
public class ProizvodjacVakcineDAOImpl implements ProizvodjacDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class ProizvodjacRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, ProizvodjacVakcine> proizvodjaciVakcine = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String proizvodjac = resultSet.getString(index++);
			String drzavaProizvodnje = resultSet.getString(index++);
			

			ProizvodjacVakcine proizvodjacVakcine = proizvodjaciVakcine.get(id);
			
			if (proizvodjacVakcine == null) {
				proizvodjacVakcine = new ProizvodjacVakcine(id, proizvodjac, drzavaProizvodnje);
				proizvodjaciVakcine.put(proizvodjacVakcine.getId(), proizvodjacVakcine); 
			}
		}

		public List<ProizvodjacVakcine> getProizvodjaciVakcine() {
			return new ArrayList<>(proizvodjaciVakcine.values());
		}

	}
	
	@Override
	public ProizvodjacVakcine findOne(Long id) {
		String sql = 
				"SELECT id, proizvodjac, drzavaProizvodnje FROM proizvodjaciVakcine  " + 
				"WHERE id = ? " + 
				"ORDER BY id";

		ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getProizvodjaciVakcine().get(0);
	}

	@Override
	public List<ProizvodjacVakcine> findAll() {
		String sql = 
				"SELECT id, proizvodjac, drzavaProizvodnje FROM proizvodjaciVakcine " +  
						"ORDER BY id";

		ProizvodjacRowCallBackHandler rowCallbackHandler = new ProizvodjacRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getProizvodjaciVakcine();
	}
	
	@Transactional
	@Override
	public int save(ProizvodjacVakcine proizvodjacVakcine) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO proizvodjaciVakcine (proizvodjac, drzavaProizvodnje) VALUES (?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, proizvodjacVakcine.getProizvodjac());
				preparedStatement.setString(index++, proizvodjacVakcine.getDrzavaProizvodnje());
				
				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(ProizvodjacVakcine proizvodjacVakcine) {
		String sql = "UPDATE proizvodjaciVakcine SET proizvodjac = ?, drzavaProizvodnje = ? WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, proizvodjacVakcine.getProizvodjac() , proizvodjacVakcine.getDrzavaProizvodnje(), proizvodjacVakcine.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM proizvodjaciVakcine WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}

}

