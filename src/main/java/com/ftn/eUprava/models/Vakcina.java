package com.ftn.eUprava.models;

public class Vakcina {
    private int id;
    private String naziv;
    private int dostupnaKolicina;
    private Proizvodjac proizvodjac;

    public Vakcina(int id, String naziv, int dostupnaKolicina, Proizvodjac proizvodjac) {
        this.id = id;
        this.naziv = naziv;
        this.dostupnaKolicina = dostupnaKolicina;
        this.proizvodjac = proizvodjac;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public int getDostupnaKolicina() {
        return dostupnaKolicina;
    }

    public void setDostupnaKolicina(int dostupnaKolicina) {
        this.dostupnaKolicina = dostupnaKolicina;
    }

    public Proizvodjac getProizvodjac() {
        return proizvodjac;
    }

    public void setProizvodjac(Proizvodjac proizvodjac) {
        this.proizvodjac = proizvodjac;
    }

    @Override
    public String toString() {
        return
                "Vakcina ID: " + id + "\n" +
                "Naziv:" + naziv + "\n" +
                "Dostupna kolicina: " + dostupnaKolicina + "\n" +
                "Proizvodjac: " + proizvodjac.getNaziv();
    }

}
