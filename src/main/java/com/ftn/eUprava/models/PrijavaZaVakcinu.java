package com.ftn.eUprava.models;

import java.time.LocalDateTime;

public class PrijavaZaVakcinu {

    private int prijavaID;
    private Korisnik korisnik;
    private Vakcina vakcina;
    private DozaVakcine doza;
    private LocalDateTime datumVreme;

    public PrijavaZaVakcinu(int prijavaID, Korisnik korisnik, Vakcina vakcina, DozaVakcine doza, LocalDateTime datumVreme) {
        this.prijavaID = prijavaID;
        this.korisnik = korisnik;
        this.vakcina = vakcina;
        this.doza = doza;
        this.datumVreme = datumVreme;
    }

    public PrijavaZaVakcinu(Korisnik korisnik, Vakcina vakcina, DozaVakcine doza, LocalDateTime datumVreme) {
        this.korisnik = korisnik;
        this.vakcina = vakcina;
        this.doza = doza;
        this.datumVreme = datumVreme;
    }

    public int getPrijavaID() {
        return prijavaID;
    }

    public void setPrijavaID(int prijavaID) {
        this.prijavaID = prijavaID;
    }

    public Korisnik getKorisnik() {
        return korisnik;
    }

    public void setKorisnik(Korisnik korisnik) {
        this.korisnik = korisnik;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    public DozaVakcine getDoza() {
        return doza;
    }

    public void setDoza(DozaVakcine doza) {
        this.doza = doza;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    @Override
    public String toString(){
        return
                "Prijava za vakcinu ID: " + prijavaID + "\n" +
                "Korisnik: " + korisnik.getKorisnikID() + "\n" +
                "Vakcina: " + vakcina.getNaziv() + "\n" +
                "Doza vakcine: " + doza + "\n" +
                "Datum i vreme prijave: " + datumVreme;
    }

}
