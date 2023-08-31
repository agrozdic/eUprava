package com.ftn.eUprava.models;

import java.time.LocalDateTime;

public class Vakcinacija {

    private int vakcinacijaID;
    private Korisnik korisnik;
    private Vakcina vakcina;
    private DozaVakcine doza;
    private LocalDateTime datumVreme;

    public Vakcinacija(int vakcinacijaID, Korisnik korisnik, Vakcina vakcina, DozaVakcine doza, LocalDateTime datumVreme) {
        this.vakcinacijaID = vakcinacijaID;
        this.korisnik = korisnik;
        this.vakcina = vakcina;
        this.doza = doza;
        this.datumVreme = datumVreme;
    }

    public Vakcinacija(Korisnik korisnik, Vakcina vakcina, DozaVakcine doza, LocalDateTime datumVreme) {
        this.korisnik = korisnik;
        this.vakcina = vakcina;
        this.doza = doza;
        this.datumVreme = datumVreme;
    }

    public int getVakcinacijaID() {
        return vakcinacijaID;
    }

    public void setVakcinacijaID(int vakcinacijaID) {
        this.vakcinacijaID = vakcinacijaID;
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
                "Vakcinacija ID: " + vakcinacijaID + "\n" +
                "Korisnik: " + korisnik.getKorisnikID() + "\n" +
                "Vakcina: " + vakcina.getNaziv() + "\n" +
                "Doza vakcine: " + doza + "\n" +
                "Datum i vreme: " + datumVreme;
    }

}
