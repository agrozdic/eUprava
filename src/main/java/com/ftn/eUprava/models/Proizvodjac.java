package com.ftn.eUprava.models;

public class Proizvodjac {
    private int proizvodjacID;
    private String naziv;
    private String drzava;

    public Proizvodjac(int proizvodjacID, String naziv, String drzava) {
        this.proizvodjacID = proizvodjacID;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public int getProizvodjacID() {
        return proizvodjacID;
    }

    public void setProizvodjacID(int proizvodjacID) {
        this.proizvodjacID = proizvodjacID;
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
                "Proizvodjac ID: " + proizvodjacID + "\n" +
                "Naziv: " + naziv + "\n" +
                "Drzava: " + drzava;
    }

}
