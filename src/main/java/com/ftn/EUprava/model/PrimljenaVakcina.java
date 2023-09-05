package com.ftn.EUprava.model;

import java.time.LocalDateTime;

public class PrimljenaVakcina {
	
	private Long id;
	private Korisnik korisnik;
	private Vakcina vakcina;
	private EDoza doza;
	private LocalDateTime datumIVremeVakcinacije;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
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
	public LocalDateTime getDatumIVremeVakcinacije() {
		return datumIVremeVakcinacije;
	}
	public void setDatumIVremeVakcinacije(LocalDateTime datumIVremeVakcinacije) {
		this.datumIVremeVakcinacije = datumIVremeVakcinacije;
	}
	public Korisnik getKorisnik() {
		return korisnik;
	}
	public void setKorisnik(Korisnik korisnik) {
		this.korisnik = korisnik;
	}
	public PrimljenaVakcina(Long id, Korisnik korisnik, Vakcina vakcina, EDoza doza,
			LocalDateTime datumIVremeVakcinacije) {
		super();
		this.id = id;
		this.korisnik = korisnik;
		this.vakcina = vakcina;
		this.doza = doza;
		this.datumIVremeVakcinacije = datumIVremeVakcinacije;
	}
	public PrimljenaVakcina(Korisnik korisnik, Vakcina vakcina, EDoza doza, LocalDateTime datumIVremeVakcinacije) {
		super();
		this.korisnik = korisnik;
		this.vakcina = vakcina;
		this.doza = doza;
		this.datumIVremeVakcinacije = datumIVremeVakcinacije;
	}
	@Override
	public String toString() {
		return "PrimljenaVakcina [id=" + id + ", korisnik=" + korisnik + ", vakcina=" + vakcina + ", doza=" + doza
				+ ", datumIVremeVakcinacije=" + datumIVremeVakcinacije + "]";
	}

	
	
	
	
}
