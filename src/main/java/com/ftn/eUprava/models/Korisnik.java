package com.ftn.eUprava.models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Korisnik {

    private int korisnikID;
    private String email;
    private String lozinka;
    private String ime;
    private String prezime;
    private LocalDate datumRodjenja;
    private String jmbg;
    private String adresa;
    private String brojTelefona;
    private LocalDateTime dvRegistracije; //datum i vreme registracije
    private Uloga uloga;

    public Korisnik(int korisnikID, String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja, String jmbg,
                    String adresa, String brojTelefona, LocalDateTime dvRegistracije, Uloga uloga) {
        this.korisnikID = korisnikID;
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.dvRegistracije = dvRegistracije;
        this.uloga = uloga;
    }

    public Korisnik(String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja, String jmbg, String adresa, String brojTelefona, LocalDateTime dvRegistracije, Uloga uloga) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
        this.dvRegistracije = dvRegistracije;
        this.uloga = uloga;
    }

    public Korisnik(String email, String lozinka, String ime, String prezime, LocalDate datumRodjenja, String jmbg, String adresa, String brojTelefona) {
        this.email = email;
        this.lozinka = lozinka;
        this.ime = ime;
        this.prezime = prezime;
        this.datumRodjenja = datumRodjenja;
        this.jmbg = jmbg;
        this.adresa = adresa;
        this.brojTelefona = brojTelefona;
    }

    public int getKorisnikID() {
        return korisnikID;
    }

    public void setKorisnikID(int korisnikID) {
        this.korisnikID = korisnikID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public LocalDate getDatumRodjenja() {
        return datumRodjenja;
    }

    public void setDatumRodjenja(LocalDate datumRodjenja) {
        this.datumRodjenja = datumRodjenja;
    }

    public String getJmbg() {
        return jmbg;
    }

    public void setJmbg(String jmbg) {
        this.jmbg = jmbg;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public String getBrojTelefona() {
        return brojTelefona;
    }

    public void setBrojTelefona(String brojTelefona) {
        this.brojTelefona = brojTelefona;
    }

    public LocalDateTime getDvRegistracije() {
        return dvRegistracije;
    }

    public void setDvRegistracije(LocalDateTime dvRegistracije) {
        this.dvRegistracije = dvRegistracije;
    }

    public Uloga getUloga() {
        return uloga;
    }

    public void setUloga(Uloga uloga) {
        this.uloga = uloga;
    }

    @Override
    public String toString() {
        return
                "Korisnik ID"  + korisnikID + "\n" +
                "Email: " + email + "\n" +
                "Lozinka: " + lozinka + "\n" +
                "Ime: " + ime + "\n" +
                "Prezime: " + prezime + "\n" +
                "Datum rodjenja: " + datumRodjenja + "\n" +
                "JMBG: " + jmbg + "\n" +
                "Adresa: " + adresa + "\n" +
                "Broj telefona: " + brojTelefona + "\n" +
                "Datum i vreme registracije: " + dvRegistracije + "\n" +
                "Uloga: " + uloga;
    }

}
