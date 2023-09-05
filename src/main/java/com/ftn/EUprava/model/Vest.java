package com.ftn.EUprava.model;

import java.time.LocalDateTime;

public class Vest {
	private Long id;
	private String nazivVesti;
	private String sadrzajVesti;
	private LocalDateTime datumIVremeObjavljivanja;
	
	
	public Vest() {
		super();
	}
	public Vest(String nazivVesti, String sadrzajVesti, LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.nazivVesti = nazivVesti;
		this.sadrzajVesti = sadrzajVesti;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	
	public Vest(Long id, String nazivVesti, String sadrzajVesti, LocalDateTime datumIVremeObjavljivanja) {
		super();
		this.id = id;
		this.nazivVesti = nazivVesti;
		this.sadrzajVesti = sadrzajVesti;
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNazivVesti() {
		return nazivVesti;
	}
	public void setNazivVesti(String nazivVesti) {
		this.nazivVesti = nazivVesti;
	}
	public String getSadrzajVesti() {
		return sadrzajVesti;
	}
	public void setSadrzajVesti(String sadrzajVesti) {
		this.sadrzajVesti = sadrzajVesti;
	}
	public LocalDateTime getDatumIVremeObjavljivanja() {
		return datumIVremeObjavljivanja;
	}
	public void setDatumIVremeObjavljivanja(LocalDateTime datumIVremeObjavljivanja) {
		this.datumIVremeObjavljivanja = datumIVremeObjavljivanja;
	}
	@Override
	public String toString() {
		return "Vest [id=" + id + ", nazivVesti=" + nazivVesti + ", sadrzajVesti=" + sadrzajVesti
				+ ", datumIVremeObjavljivanja=" + datumIVremeObjavljivanja + "]";
	}
	
	
	
}
