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

import com.ftn.EUprava.dao.KorisnikDAO;
import com.ftn.EUprava.dao.PrijavaZaVakcinacijuDAO;
import com.ftn.EUprava.dao.VakcinaDAO;
import com.ftn.EUprava.model.EDoza;
import com.ftn.EUprava.model.Korisnik;
import com.ftn.EUprava.model.PrijavaZaVakcinaciju;
import com.ftn.EUprava.model.Vakcina;


@Repository
public class PrijavaZaVakcinacijuDAOImpl implements PrijavaZaVakcinacijuDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VakcinaDAO vakcinaDAO;
	
	@Autowired
	private KorisnikDAO korisnikDAO;
	
	private class PrijavaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, PrijavaZaVakcinaciju> prijaveZaVakcinaciju = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			
			Long korisnikId = resultSet.getLong(index++);
			Korisnik korisnik = korisnikDAO.findOne(korisnikId);
			
			Long vakcinaId = resultSet.getLong(index++);
			Vakcina vakcina = vakcinaDAO.findOne(vakcinaId);
			
			EDoza doza = EDoza.valueOf(resultSet.getString(index++));
			
			LocalDateTime datumIVremePrijave = resultSet.getObject(index++, LocalDateTime.class);
			
			

			PrijavaZaVakcinaciju prijavaZaVakcinaciju = prijaveZaVakcinaciju.get(id);
			if (prijavaZaVakcinaciju == null) {
				prijavaZaVakcinaciju = new PrijavaZaVakcinaciju(id, korisnik, vakcina, doza, datumIVremePrijave);
				prijaveZaVakcinaciju.put(prijavaZaVakcinaciju.getId(), prijavaZaVakcinaciju);
			}
		}

		public List<PrijavaZaVakcinaciju> getPrijaveZaVakcinaciju() {
			return new ArrayList<>(prijaveZaVakcinaciju.values());
		}

	}
	
	public PrijavaZaVakcinaciju findOne(Long id) {
		String sql = 
				"SELECT id, korisnikId, vakcinaId, EDoza, DatumIVremePrijave FROM prijaveZaVakcinaciju " + 
				"WHERE id = ? " + 
				"ORDER BY id";

		PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getPrijaveZaVakcinaciju().get(0);
	}

	public List<PrijavaZaVakcinaciju> findAll() {
		String sql = 
				"SELECT id, korisnikId, vakcinaId, EDoza, DatumIVremePrijave FROM prijaveZaVakcinaciju " + 
						"ORDER BY id";

		PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getPrijaveZaVakcinaciju();
	}
	
	@Transactional
	public int save(PrijavaZaVakcinaciju prijavaZaVakcinaciju) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO prijaveZaVakcinaciju (korisnikId, vakcinaId, EDoza) VALUES (?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setLong(index++, prijavaZaVakcinaciju.getKorisnik().getId());

				preparedStatement.setLong(index++, prijavaZaVakcinaciju.getVakcina().getId());
				
				EDoza doza = prijavaZaVakcinaciju.getDoza();
				preparedStatement.setString(index++, doza.name());
				


				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	

	
	@Transactional
	public int delete(Long id) {
		String sql = "DELETE FROM prijaveZaVakcinaciju WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	public List<PrijavaZaVakcinaciju> find(String ime, String prezime,  String jmbg) {
	    StringBuilder sb = new StringBuilder();
	    sb.append("Select * from prijaveZaVakcinaciju p");
	    List<Object> params = new ArrayList<>();
	    boolean whereIncluded = false;
	    
	    if (ime != null && !ime.trim().isEmpty()) {
	        if (whereIncluded) {
	            sb.append(" and p.korisnikId in (select k.id from korisnici k where k.ime = ?)");
	        } else {
	            sb.append(" where p.korisnikId in (select k.id from korisnici k where k.ime = ?)");
	            whereIncluded = true;
	        }
	        params.add(ime);
	    }
	    if (prezime != null && !prezime.trim().isEmpty()) {
	        if (whereIncluded) {
	            sb.append(" and p.korisnikId in (select k.id from korisnici k where k.prezime = ?)");
	        } else {
	            sb.append(" where  p.korisnikId in  (select k.id from korisnici k where k.prezime = ?)");
	            whereIncluded = true;
	        }
	        params.add(prezime);
	    }
	    if (jmbg != null && !jmbg.trim().isEmpty()) {
	        if (whereIncluded) {
	            sb.append(" and p.korisnikId in (select k.id from korisnici k where k.jmbg = ?)");
	        } else {
	            sb.append(" where  p.korisnikId in (select k.id from korisnici k where k.jmbg = ?)");
	            whereIncluded = true;
	        }
	        params.add(jmbg);
	    }
	    String sql = sb.toString();
	    PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
	    jdbcTemplate.query(sql, params.toArray(), rowCallbackHandler);
	    return rowCallbackHandler.getPrijaveZaVakcinaciju();
	}

	
}
