package com.ftn.eUprava.models;

public class Proizvodjac {
    private int id;
    private String naziv;
    private String drzava;

    public Proizvodjac(int id, String naziv, String drzava) {
        this.id = id;
        this.naziv = naziv;
        this.drzava = drzava;
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

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @Override
    public String toString() {
        return
                "Proizvodjac ID: " + id + "\n" +
                "Naziv: " + naziv + "\n" +
                "Drzava: " + drzava;
    }

}
