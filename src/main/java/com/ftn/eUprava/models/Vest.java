package com.ftn.eUprava.models;

import java.time.LocalDateTime;

public class Vest {
    private int id;
    private String naslov;
    private String sadrzaj;
    private LocalDateTime dvObjave; // datum i vreme objave

    public Vest(int id, String naslov, String sadrzaj, LocalDateTime dvObjave) {
        this.id = id;
        this.naslov = naslov;
        this.sadrzaj = sadrzaj;
        this.dvObjave = dvObjave;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getSadrzaj() {
        return sadrzaj;
    }

    public void setSadrzaj(String sadrzaj) {
        this.sadrzaj = sadrzaj;
    }

    public LocalDateTime getDvObjave() {
        return dvObjave;
    }

    public void setDvObjave(LocalDateTime dvObjave) {
        this.dvObjave = dvObjave;
    }

    @Override
    public String toString() {
        return
                "Vest ID: " + id + "\n" +
                "Naslov: " + naslov + "\n" +
                "Sadrzaj: " + sadrzaj + "\n" +
                "Datum i vreme objave: " + dvObjave;
    }

}
