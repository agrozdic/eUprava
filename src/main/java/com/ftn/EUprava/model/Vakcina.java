package com.ftn.EUprava.model;



public class Vakcina {
	private Long id;
	private String ime;
	private Integer dostupnaKolicina;
	private ProizvodjacVakcine proizvodjac;
	
	public Vakcina() {
		super();
	}
	public Vakcina(String ime, Integer dostupnaKolicina, ProizvodjacVakcine proizvodjac) {
		super();
		this.ime = ime;
		this.dostupnaKolicina = dostupnaKolicina;
		this.proizvodjac = proizvodjac;
	}
	
	

	
	public Vakcina(String ime, ProizvodjacVakcine proizvodjac) {
		super();
		this.ime = ime;
		this.proizvodjac = proizvodjac;
	}

	public Vakcina(Long id, String ime, Integer dostupnaKolicina) {
		super();
		this.id = id;
		this.ime = ime;
		this.dostupnaKolicina = dostupnaKolicina;
	}

	public Vakcina(Long id, String ime, Integer dostupnaKolicina, ProizvodjacVakcine proizvodjac) {
		super();
		this.id = id;
		this.ime = ime;
		this.dostupnaKolicina = dostupnaKolicina;
		this.proizvodjac = proizvodjac;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public Integer getDostupnaKolicina() {
		return dostupnaKolicina;
	}
	public void setDostupnaKolicina(Integer dostupnaKolicina) {
		this.dostupnaKolicina = dostupnaKolicina;
	}
	public ProizvodjacVakcine getProizvodjac() {
		return proizvodjac;
	}
	public void setProizvodjac(ProizvodjacVakcine proizvodjac) {
		this.proizvodjac = proizvodjac;
	}
	
	@Override
	public String toString() {
		return "Vakcina [id=" + id + ", ime=" + ime + ", dostupnaKolicina=" + dostupnaKolicina + ", proizvodjac="
				+ proizvodjac + "]";
	}
	
	
}
