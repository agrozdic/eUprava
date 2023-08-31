package com.ftn.eUprava.models;

import java.time.LocalDateTime;

public class Narudzbenica {
    private int narudzbenicaID;
    private Vakcina vakcina;
    private int kolicina;
    private LocalDateTime datumVreme;
    private String komentar;
    private StatusZahteva status;

    public Narudzbenica(int narudzbenicaID, Vakcina vakcina, int kolicina, LocalDateTime datumVreme, String komentar, StatusZahteva status) {
        this.narudzbenicaID = narudzbenicaID;
        this.vakcina = vakcina;
        this.kolicina = kolicina;
        this.datumVreme = datumVreme;
        this.komentar = komentar;
        this.status = status;
    }

    public Narudzbenica(Vakcina vakcina, int kolicina, LocalDateTime datumVreme, String komentar, StatusZahteva status) {
        this.vakcina = vakcina;
        this.kolicina = kolicina;
        this.datumVreme = datumVreme;
        this.komentar = komentar;
        this.status = status;
    }

    public Narudzbenica(Vakcina vakcina, int kolicina, String komentar, StatusZahteva status) {
        this.vakcina = vakcina;
        this.kolicina = kolicina;
        this.komentar = komentar;
        this.status = status;
    }

    public int getNarudzbenicaID() {
        return narudzbenicaID;
    }

    public void setNarudzbenicaID(int narudzbenicaID) {
        this.narudzbenicaID = narudzbenicaID;
    }

    public Vakcina getVakcina() {
        return vakcina;
    }

    public void setVakcina(Vakcina vakcina) {
        this.vakcina = vakcina;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public LocalDateTime getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(LocalDateTime datumVreme) {
        this.datumVreme = datumVreme;
    }

    public String getKomentar() {
        return komentar;
    }

    public void setKomentar(String komentar) {
        this.komentar = komentar;
    }

    public StatusZahteva getStatus() {
        return status;
    }

    public void setStatus(StatusZahteva status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "Narudzbenica ID: " + narudzbenicaID + "\n" +
                "Vakcina: " + vakcina + "\n" +
                "Kolicina: " + kolicina + "\n" +
                "Datum i vreme: " + datumVreme + "\n" +
                "Komentar: " + komentar + "\n" +
                "Status zahteva: " + status;
    }



}
