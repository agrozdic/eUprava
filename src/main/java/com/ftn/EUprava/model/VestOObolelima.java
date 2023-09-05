package com.ftn.EUprava.model;

import java.time.LocalDateTime;

public class VestOObolelima {
	private Long id;
	private Integer oboleliUPoslednja24h;
	private Integer testiraniUPoslednja24h;
	private Integer ukupnoOboleliOdPocetkaPandemije;
	private Integer hospitalizovani;
	private Integer naRespiratoru;
	private LocalDateTime datumIVremeObjavljivanja;
	
	
	public VestOObolelima() {
		super();
	}
	public VestOObolelima(Integer oboleliUPoslednja24h, Integer testiraniUPoslednja24h,
			Integer ukupnoOboleliOdPocetkaPandemije, Integer hospitalizovani, Integer naRespiratoru,
			LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.oboleliUPoslednja24h = oboleliUPoslednja24h;
		this.testiraniUPoslednja24h = testiraniUPoslednja24h;
		this.ukupnoOboleliOdPocetkaPandemije = ukupnoOboleliOdPocetkaPandemije;
		this.hospitalizovani = hospitalizovani;
		this.naRespiratoru = naRespiratoru;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	
	public VestOObolelima(Long id, Integer oboleliUPoslednja24h, Integer testiraniUPoslednja24h,
			Integer ukupnoOboleliOdPocetkaPandemije, Integer hospitalizovani, Integer naRespiratoru,
			LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.id = id;
		this.oboleliUPoslednja24h = oboleliUPoslednja24h;
		this.testiraniUPoslednja24h = testiraniUPoslednja24h;
		this.ukupnoOboleliOdPocetkaPandemije = ukupnoOboleliOdPocetkaPandemije;
		this.hospitalizovani = hospitalizovani;
		this.naRespiratoru = naRespiratoru;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	
	public VestOObolelima(Integer oboleliUPoslednja24h, Integer testiraniUPoslednja24h, Integer hospitalizovani,
			Integer naRespiratoru, LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.oboleliUPoslednja24h = oboleliUPoslednja24h;
		this.testiraniUPoslednja24h = testiraniUPoslednja24h;
		this.hospitalizovani = hospitalizovani;
		this.naRespiratoru = naRespiratoru;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	public VestOObolelima(Long id, Integer oboleliUPoslednja24h, Integer testiraniUPoslednja24h,
			Integer hospitalizovani, Integer naRespiratoru, LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.id = id;
		this.oboleliUPoslednja24h = oboleliUPoslednja24h;
		this.testiraniUPoslednja24h = testiraniUPoslednja24h;
		this.hospitalizovani = hospitalizovani;
		this.naRespiratoru = naRespiratoru;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getOboleliUPoslednja24h() {
		return oboleliUPoslednja24h;
	}
	public void setOboleliUPoslednja24h(Integer oboleliUPoslednja24h) {
		this.oboleliUPoslednja24h = oboleliUPoslednja24h;
	}
	public Integer getTestiraniUPoslednja24h() {
		return testiraniUPoslednja24h;
	}
	public void setTestiraniUPoslednja24h(Integer testiraniUPoslednja24h) {
		this.testiraniUPoslednja24h = testiraniUPoslednja24h;
	}
	public Integer getUkupnoOboleliOdPocetkaPandemije() {
		return ukupnoOboleliOdPocetkaPandemije;
	}
	public void setUkupnoOboleliOdPocetkaPandemije(Integer ukupnoOboleliOdPocetkaPandemije) {
		this.ukupnoOboleliOdPocetkaPandemije = ukupnoOboleliOdPocetkaPandemije;
	}
	public Integer getHospitalizovani() {
		return hospitalizovani;
	}
	public void setHospitalizovani(Integer hospitalizovani) {
		this.hospitalizovani = hospitalizovani;
	}
	public Integer getNaRespiratoru() {
		return naRespiratoru;
	}
	public void setNaRespiratoru(Integer naRespiratoru) {
		this.naRespiratoru = naRespiratoru;
	}
	public LocalDateTime getDatumIVremeObjavljivanja() {
		return datumIVremeObjavljivanja;
	}
	public void setDatumIVremeObjavljivanja(LocalDateTime datumIVremeObjavljivanja) {
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	@Override
	public String toString() {
		return "VestOObolelima [id=" + id + ", oboleliUPoslednja24h=" + oboleliUPoslednja24h
				+ ", testiraniUPoslednja24h=" + testiraniUPoslednja24h + ", ukupnoOboleliOdPočetkaPandemije="
				+ ukupnoOboleliOdPocetkaPandemije + ", hospitalizovani=" + hospitalizovani + ", naRespiratoru="
				+ naRespiratoru + ", datumIVremeObjavljivanja=" + datumIVremeObjavljivanja + "]";
	}
	
}
