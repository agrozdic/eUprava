package com.ftn.EUprava.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ftn.EUprava.dao.KorisnikDAO;
import com.ftn.EUprava.model.EUloga;
import com.ftn.EUprava.model.Korisnik;


@Repository
public class KorisnikDAOImpl implements KorisnikDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private class KorisnikRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, Korisnik> korisnici = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			String email = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			LocalDate datumRodjenja = resultSet.getObject(index++, LocalDate.class);
			String jmbg = resultSet.getString(index++);
			String adresa = resultSet.getString(index++);
			String brojTelefona = resultSet.getString(index++);
			LocalDateTime datumIVremeRegistracije = resultSet.getObject(index++, LocalDateTime.class);
			EUloga uloga = EUloga.valueOf(resultSet.getString(index++));
			

			Korisnik korisnik = korisnici.get(id);
			if (korisnik == null) {
				korisnik = new Korisnik(id, email,  lozinka,  ime,  prezime,  datumRodjenja,  jmbg,
						 adresa,  brojTelefona,  datumIVremeRegistracije,  uloga);
				korisnici.put(korisnik.getId(), korisnik); // dodavanje u kolekciju
			}
		}

		public List<Korisnik> getKorisnici() {
			return new ArrayList<>(korisnici.values());
		}
		

	}
	private class KorisnikRowMapper implements RowMapper<Korisnik> {

		public Korisnik mapRow(ResultSet resultSet, int rowNum) throws SQLException {
			int index = 1;
			String email = resultSet.getString(index++);
			String lozinka = resultSet.getString(index++);
			String ime = resultSet.getString(index++);
			String prezime = resultSet.getString(index++);
			LocalDate datumRodjenja = resultSet.getObject(index++, LocalDate.class);
			String jmbg = resultSet.getString(index++);
			String adresa = resultSet.getString(index++);
			String brojTelefona = resultSet.getString(index++);
			LocalDateTime datumIVremeRegistracije = resultSet.getObject(index++, LocalDateTime.class);
			EUloga uloga = EUloga.valueOf(resultSet.getString(index++));
			

			Korisnik korisnik = new Korisnik(email,  lozinka,  ime,  prezime,  datumRodjenja,  jmbg,
					 adresa,  brojTelefona,  datumIVremeRegistracije,  uloga);
			return korisnik;
		}

	}
	@Override
	public Korisnik findOne(String email) {
		try {
			String sql = 
					"SELECT kor.email, kor.lozinka, kor.ime, kor.prezime, kor.datumRodjenja,"
							+ " kor.jmbg, kor.adresa, kor.brojTelefona,  kor.datumIVremeRegistracije, kor.Euloga"
							+ " FROM korisnici kor " +  
					"WHERE kor.email = ? " ;
					
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), email);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	@Override
	public Korisnik findOneByJMBG(String jmbg) {
		try {
			String sql = 
					"SELECT kor.email, kor.lozinka, kor.ime, kor.prezime, kor.datumRodjenja,"
							+ " kor.jmbg, kor.adresa, kor.brojTelefona,  kor.datumIVremeRegistracije, kor.Euloga"
							+ " FROM korisnici kor " +  
					"WHERE kor.jmbg = ? " ;
					
			return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), jmbg);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	@Override
	public Korisnik findOne(Long id) {
		String sql = 
				"SELECT kor.id, kor.email, kor.lozinka, kor.ime, kor.prezime, kor.datumRodjenja,"
				+ " kor.jmbg, kor.adresa, kor.brojTelefona,  kor.datumIVremeRegistracije, kor.Euloga"
				+ " FROM korisnici kor " + 
				"WHERE kor.id = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, id);

		return rowCallbackHandler.getKorisnici().get(0);
	}
	

	@Override
	public Korisnik findOne(String email, String lozinka) {
		String sql = 
				"SELECT kor.id, kor.email, kor.lozinka, kor.ime, kor.prezime, "
				+ "kor.datumRodjenja, kor.jmbg, kor.adresa, kor.brojTelefona, "
				+ " kor.datumIVremeRegistracije, kor.Euloga FROM korisnici kor " + 
				"WHERE kor.email = ? AND " +
				"kor.lozinka = ? " + 
				"ORDER BY kor.id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler, email, lozinka);

		if(rowCallbackHandler.getKorisnici().size() == 0) {
			return null;
		}
		
		return rowCallbackHandler.getKorisnici().get(0);
	}

	@Override
	public List<Korisnik> findAll() {
		String sql = 
				"SELECT id, email, lozinka, ime, prezime, datumRodjenja,"
						+ " jmbg, adresa, brojTelefona,  datumIVremeRegistracije, Euloga"
						+ " FROM korisnici  " +  
				"ORDER BY id";

		KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getKorisnici();
	}
	
	@Transactional
	@Override
	public int save(Korisnik korisnik) {
		PreparedStatementCreator preparedStatementCreator = new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				String sql = "INSERT INTO korisnici (email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int index = 1;
				preparedStatement.setString(index++, korisnik.getEmail());
				preparedStatement.setString(index++, korisnik.getLozinka());
				preparedStatement.setString(index++, korisnik.getIme());
				preparedStatement.setString(index++, korisnik.getPrezime());
				preparedStatement.setObject(index++, korisnik.getDatumRodjenja());
				preparedStatement.setString(index++, korisnik.getJmbg());
				preparedStatement.setString(index++, korisnik.getAdresa());
				preparedStatement.setString(index++, korisnik.getBrojTelefona());
			
				
				
				return preparedStatement;
			}

		};
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		boolean uspeh = jdbcTemplate.update(preparedStatementCreator, keyHolder) == 1;
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int update(Korisnik korisnik) {
		String sql = "UPDATE korisnici SET email = ?, lozinka = ?, ime = ?, prezime = ?, datumRodjenja = ?, "
				+ " jmbg = ? , adresa = ?, brojTelefona = ? "
				+ "  WHERE id = ?";	
		boolean uspeh = jdbcTemplate.update(sql, korisnik.getEmail(),korisnik.getLozinka(),korisnik.getIme() , korisnik.getPrezime(), 
				korisnik.getDatumRodjenja(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrojTelefona(),korisnik.getId()) == 1;
		
		return uspeh?1:0;
	}
	
	@Transactional
	@Override
	public int delete(Long id) {
		String sql = "DELETE FROM korisnici WHERE id = ?";
		return jdbcTemplate.update(sql, id);
	}
	
	
	
}
