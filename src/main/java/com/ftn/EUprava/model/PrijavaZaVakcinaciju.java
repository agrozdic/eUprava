package com.ftn.EUprava.model;

import java.time.LocalDateTime;

public class PrijavaZaVakcinaciju {

	private Long id;
	private Korisnik korisnik;
	private Vakcina vakcina;
	private EDoza doza;
	private LocalDateTime datumIVremePrijave;
	
	
	
	public Vakcina getVakcina() {
		return vakcina;
	}
	public void setVakcina(Vakcina vakcina) {
		this.vakcina = vakcina;
	}
	public EDoza getDoza() {
		return doza;
	}
	public void setDoza(EDoza doza) {
		this.doza = doza;
	}
	public LocalDateTime getDatumIVremePrijave() {
		return datumIVremePrijave;
	}
	public void setDatumIVremePrijave(LocalDateTime datumIVremePrijave) {
		this.datumIVremePrijave = datumIVremePrijave;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public PrijavaZaVakcinaciju(Long id, Korisnik korisnik, Vakcina vakcina, EDoza doza,
			LocalDateTime datumIVremePrijave) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.vakcina = vakcina;
		this.doza = doza;
		this.datumIVremePrijave = datumIVremePrijave;
	}
	public PrijavaZaVakcinaciju(Korisnik korisnik, Vakcina vakcina, EDoza doza, LocalDateTime datumIVremePrijave) {
		super();
		this.korisnik = korisnik;
		this.vakcina = vakcina;
		this.doza = doza;
		this.datumIVremePrijave = datumIVremePrijave;
	}
	public PrijavaZaVakcinaciju(Korisnik korisnik, Vakcina vakcina, EDoza doza) {
		super();
		this.korisnik = korisnik;
		this.vakcina = vakcina;
		this.doza = doza;
	}

	
	
}
