package com.example.projektpraktikum;

public class Regije {
    private int id_regije;
    private String naziv_regije;

    public int getId_regije() {
        return id_regije;
    }

    public void setId_regije(int id_regije) {
        this.id_regije = id_regije;
    }

    public String getNaziv_regije() {
        return naziv_regije;
    }

    public void setNaziv_regije(String naziv_regije) {
        this.naziv_regije = naziv_regije;
    }

    @Override
    public String toString() {
        return "Regije{" +
                "id_regije=" + id_regije +
                ", naziv_regije='" + naziv_regije + '\'' +
                '}';
    }
}

