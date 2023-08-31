package com.ftn.eUprava.models;

public class Vakcina {
    private int vakcinaID;
    private String naziv;
    private int dostupnaKolicina;
    private Proizvodjac proizvodjac;

    public Vakcina(int vakcinaID, String naziv, int dostupnaKolicina, Proizvodjac proizvodjac) {
        this.vakcinaID = vakcinaID;
        this.naziv = naziv;
        this.dostupnaKolicina = dostupnaKolicina;
        this.proizvodjac = proizvodjac;
    }

    public Vakcina(String naziv, int dostupnaKolicina, Proizvodjac proizvodjac) {
        this.naziv = naziv;
        this.dostupnaKolicina = dostupnaKolicina;
        this.proizvodjac = proizvodjac;
    }

    public int getVakcinaID() {
        return vakcinaID;
    }

    public void setVakcinaID(int vakcinaID) {
        this.vakcinaID = vakcinaID;
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
                "Vakcina ID: " + vakcinaID + "\n" +
                "Naziv:" + naziv + "\n" +
                "Dostupna kolicina: " + dostupnaKolicina + "\n" +
                "Proizvodjac: " + proizvodjac.getNaziv();
    }

}
