package com.ftn.EUprava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;


public class Korisnik {
	private Long id;
	private String email;
	private String lozinka;
	private String ime;
	private String prezime;
	private LocalDate datumRodjenja;
	private String jmbg;
	private String adresa;
	private String brojTelefona;
	private LocalDateTime datumIVremeRegistracije;
	private EUloga uloga;
	
	public Korisnik() {
		super();
	}
	
	
	public Korisnik(String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja, String jmbg,
			String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, EUloga uloga) {
		super();
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.jmbg = jmbg;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}


	public Korisnik(Long id, String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja,
			String jmbg, String adresa, String brojTelefona, LocalDateTime datumIVremeRegistracije, EUloga uloga) {
		super();
		this.id = id;
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.jmbg = jmbg;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
		this.datumIVremeRegistracije = datumIVremeRegistracije;
		this.uloga = uloga;
	}


	public Korisnik( String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja,
			String jmbg, String adresa, String brojTelefona) {
		super();
		
		this.email = email;
		this.lozinka = lozinka;
		this.ime = ime;
		this.prezime = prezime;
		this.datumRodjenja = datumRodjenja;
		this.jmbg = jmbg;
		this.adresa = adresa;
		this.brojTelefona = brojTelefona;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getLozinka() {
		return lozinka;
	}
	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public String getPrezime() {
		return prezime;
	}
	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}
	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}
	
	public String getJmbg() {
		return jmbg;
	}


	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}


	public String getAdresa() {
		return adresa;
	}
	public void setAdresa(String adresa) {
		this.adresa = adresa;
	}
	public String getBrojTelefona() {
		return brojTelefona;
	}
	public void setBrojTelefona(String brojTelefona) {
		this.brojTelefona = brojTelefona;
	}
	public LocalDateTime getDatumIVremeRegistracije() {
		return datumIVremeRegistracije;
	}
	public void setDatumIVremeRegistracije(LocalDateTime datumIVremeRegistracije) {
		this.datumIVremeRegistracije = datumIVremeRegistracije;
	}
	public EUloga getUloga() {
		return uloga;
	}
	public void setUloga(EUloga uloga) {
		this.uloga = uloga;
	}
	@Override
	public String toString() {
		return "Korisnik [id=" + id + ", email=" + email + ", lozinka=" + lozinka + ", ime=" + ime + ", prezime="
				+ prezime + ", datumRodjenja=" + datumRodjenja + ", jmbg=" + jmbg + ", adresa=" + adresa
				+ ", brojTelefona=" + brojTelefona + ", datumIVremeRegistracije=" + datumIVremeRegistracije + ", uloga="
				+ uloga + "]";
	}
	
	
	
}
