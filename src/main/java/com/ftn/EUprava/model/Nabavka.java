package com.ftn.EUprava.model;

import java.time.LocalDateTime;

public class Nabavka {
	private Long id;
	private Vakcina vakcina;
	private Integer kolicina;
	private LocalDateTime datumIVremeNabavke;
	private String komentar;
	private EStatus status;
	
	
	public EStatus getStatus() {
		return status;
	}
	public void setStatus(EStatus status) {
		this.status = status;
	}
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
	public Integer getKolicina() {
		return kolicina;
	}
	public void setKolicina(Integer kolicina) {
		this.kolicina = kolicina;
	}

	public LocalDateTime getDatumIVremeNabavke() {
		return datumIVremeNabavke;
	}
	public void setDatumIVremeNabavke(LocalDateTime datumIVremeNabavke) {
		this.datumIVremeNabavke = datumIVremeNabavke;
	}
	public String getKomentar() {
		return komentar;
	}
	public void setKomentar(String komentar) {
		this.komentar = komentar;
	}
	public Nabavka(Long id, Vakcina vakcina, Integer kolicina, LocalDateTime datumIVremeNabavke, String komentar,
			EStatus status) {
		super();
		this.id = id;
		this.vakcina = vakcina;
		this.kolicina = kolicina;
		this.datumIVremeNabavke = datumIVremeNabavke;
		this.komentar = komentar;
		this.status = status;
	}
	public Nabavka(Vakcina vakcina, Integer kolicina, LocalDateTime datumIVremeNabavke, String komentar,
			EStatus status) {
		super();
		this.vakcina = vakcina;
		this.kolicina = kolicina;
		this.datumIVremeNabavke = datumIVremeNabavke;
		this.komentar = komentar;
		this.status = status;
	}
	public Nabavka(Vakcina vakcina, Integer kolicina, String komentar, EStatus status) {
		super();
		this.vakcina = vakcina;
		this.kolicina = kolicina;
		this.komentar = komentar;
		this.status = status;
	}
	@Override
	public String toString() {
		return "Nabavka [id=" + id + ", vakcina=" + vakcina + ", kolicina=" + kolicina + ", datumIVremeNabavke="
				+ datumIVremeNabavke + ", komentar=" + komentar + ", status=" + status + "]";
	}
	
	
	
}
