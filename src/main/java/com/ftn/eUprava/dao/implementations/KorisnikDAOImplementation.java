package com.ftn.eUprava.dao.implementations;

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

import com.ftn.eUprava.dao.KorisnikDAO;
import com.ftn.eUprava.models.Uloga;
import com.ftn.eUprava.models.Korisnik;

@Repository
public class KorisnikDAOImplementation implements KorisnikDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private class KorisnikRowCallBackHandler implements RowCallbackHandler {

        private Map<Integer, Korisnik> korisnici = new LinkedHashMap<>();

        @Override
        public void processRow(ResultSet resultSet) throws SQLException {
            int index = 1;
            int korisnikID = resultSet.getInt(index++);
            String email = resultSet.getString(index++);
            String lozinka = resultSet.getString(index++);
            String ime = resultSet.getString(index++);
            String prezime = resultSet.getString(index++);
            LocalDate datumRodjenja = resultSet.getObject(index++, LocalDate.class);
            String jmbg = resultSet.getString(index++);
            String adresa = resultSet.getString(index++);
            String brojTelefona = resultSet.getString(index++);
            LocalDateTime dvRegistracije = resultSet.getObject(index++, LocalDateTime.class);
            Uloga uloga = Uloga.valueOf(resultSet.getString(index++));

            Korisnik korisnik = korisnici.get(korisnikID);
            if (korisnik == null) {
                korisnik = new Korisnik(korisnikID, email,  lozinka,  ime,  prezime,  datumRodjenja,  jmbg,
                        adresa,  brojTelefona,  dvRegistracije,  uloga);
                korisnici.put(korisnik.getKorisnikID(), korisnik);
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
            LocalDateTime dvRegistracije = resultSet.getObject(index++, LocalDateTime.class);
            Uloga uloga = Uloga.valueOf(resultSet.getString(index++));

            Korisnik korisnik = new Korisnik(email, lozinka, ime, prezime, datumRodjenja, jmbg, adresa, brojTelefona, dvRegistracije, uloga);
            return korisnik;
        }

    }

    @Override
    public Korisnik findOne(String email) {
        try {
            String sql =
                    "SELECT Email, Lozinka, Ime, Prezime, DatumRodjenja, " +
                    "JMBG, Adresa, BrojTelefona,  DatumVremeRegistracije, Uloga" +
                    "FROM Korisnik " +
                    "WHERE Email = ?";

            return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), email);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Korisnik findOneByJMBG(String jmbg) {
        try {
            String sql =
                    "SELECT Email, Lozinka, Ime, Prezime, DatumRodjenja, " +
                    "JMBG, Adresa, BrojTelefona,  DatumVremeRegistracije, Uloga" +
                    "FROM Korisnik " +
                    "WHERE JMBG = ?";

            return jdbcTemplate.queryForObject(sql, new KorisnikRowMapper(), jmbg);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    @Override
    public Korisnik findOne(int korisnikID) {
        String sql =
                "SELECT Email, Lozinka, Ime, Prezime, DatumRodjenja, " +
                "JMBG, Adresa, BrojTelefona,  DatumVremeRegistracije, Uloga" +
                "FROM Korisnik " +
                "WHERE KorisnikID = ? " +
                "ORDER BY KorisnikID";

        KorisnikRowCallBackHandler rowCallbackHandler = new KorisnikRowCallBackHandler();
        jdbcTemplate.query(sql, rowCallbackHandler, korisnikID);

        return rowCallbackHandler.getKorisnici().get(0);
    }

    @Override
    public Korisnik findOne(String email, String lozinka) {
        String sql =
                "SELECT Email, Lozinka, Ime, Prezime, DatumRodjenja, " +
                "JMBG, Adresa, BrojTelefona,  DatumVremeRegistracije, Uloga" +
                "FROM Korisnik " +
                "WHERE Email = ? AND " +
                "WHERE Lozinka = ? " +
                "ORDER BY KorisnikID";

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
                "SELECT Email, Lozinka, Ime, Prezime, DatumRodjenja, " +
                "JMBG, Adresa, BrojTelefona,  DatumVremeRegistracije, Uloga" +
                "FROM Korisnik " +
                "ORDER BY KorisnikID";

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
                String sql =
                        "INSERT INTO Korisnik(Email, Lozinka, Ime, Prezime, DatumRodjenja, JMBG, Adresa, BrojTelefona)" +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int update(Korisnik korisnik) {
        String sql =
                "UPDATE Korisnik SET Email = ?, Lozinka = ?, Ime = ?, Prezime = ?, DatumRodjenja = ?, " +
                "JMBG = ?, Adresa = ?, BrojTelefona = ? " +
                "WHERE KorisnikID = ?";
        boolean uspeh = jdbcTemplate.update(sql, korisnik.getEmail(),korisnik.getLozinka(),korisnik.getIme() , korisnik.getPrezime(),
                korisnik.getDatumRodjenja(), korisnik.getJmbg(), korisnik.getAdresa(), korisnik.getBrojTelefona(),korisnik.getKorisnikID()) == 1;

        return uspeh ? 1 : 0;
    }

    @Transactional
    @Override
    public int delete(int korisnikID) {
        String sql = "DELETE FROM Korisnik WHERE KorisnikID = ?";
        return jdbcTemplate.update(sql, korisnikID);
    }

}
