package com.ftn.EUprava.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Repository;

import com.ftn.EUprava.dao.KorisnikDAO;
import com.ftn.EUprava.dao.PrimljenaVakcinaDAO;
import com.ftn.EUprava.dao.VakcinaDAO;
import com.ftn.EUprava.model.EDoza;
import com.ftn.EUprava.model.Korisnik;
import com.ftn.EUprava.model.PrimljenaVakcina;
import com.ftn.EUprava.model.Vakcina;


@Repository
public class PrimljenaVakcinaDAOImpl implements PrimljenaVakcinaDAO{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private VakcinaDAO vakcinaDAO;
	
	@Autowired
	private KorisnikDAO korisnikDAO;
	
	private class PrijavaRowCallBackHandler implements RowCallbackHandler {

		private Map<Long, PrimljenaVakcina> primljeneVakcine = new LinkedHashMap<>();
		
		@Override
		public void processRow(ResultSet resultSet) throws SQLException {
			int index = 1;
			Long id = resultSet.getLong(index++);
			
			Long korisnikId = resultSet.getLong(index++);
			Korisnik korisnik = korisnikDAO.findOne(korisnikId);
			
			Long vakcinaId = resultSet.getLong(index++);
			Vakcina vakcina = vakcinaDAO.findOne(vakcinaId);
			
			EDoza doza = EDoza.valueOf(resultSet.getString(index++));
			
			LocalDateTime datumIVremeVakcinacije = resultSet.getObject(index++, LocalDateTime.class);
			
			

			PrimljenaVakcina primljenaVakcina = primljeneVakcine.get(id);
			if (primljenaVakcina == null) {
				primljenaVakcina = new PrimljenaVakcina(id, korisnik, vakcina, doza, datumIVremeVakcinacije);
				primljeneVakcine.put(primljenaVakcina.getId(), primljenaVakcina);
			}
		}

		public List<PrimljenaVakcina> getPrimljeneVakcine() {
			return new ArrayList<>(primljeneVakcine.values());
		}

	}
	
	

	public List<PrimljenaVakcina> findAll() {
		String sql = 
				"SELECT id, korisnikId, vakcinaId, EDoza, DatumIVremeVakcinacije FROM primljeneVakcine " + 
						"ORDER BY id";

		PrijavaRowCallBackHandler rowCallbackHandler = new PrijavaRowCallBackHandler();
		jdbcTemplate.query(sql, rowCallbackHandler);

		return rowCallbackHandler.getPrimljeneVakcine();
	}
	
	
	
}
