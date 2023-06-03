package com.example.projektpraktikum;

public class Sirene {
    private int id_sirene;
    private String lokacija;
    private String stanje;

    public Sirene(int id_sirene, String lokacija, String stanje) {
        this.id_sirene = id_sirene;
        this.lokacija = lokacija;
        this.stanje = stanje;
    }

    public void paljenje() {
        System.out.println("Sirena " + id_sirene + " je upaljena.");
    }

    public void gasenje() {
        System.out.println("Sirena " + id_sirene + " je ugašena.");
    }

    public void dojavaStanja(String novoStanje) {
        System.out.println("Sirena " + id_sirene + " je promijenila stanje u: " + novoStanje);
        stanje = novoStanje;
    }

    public int getId_sirene() {
        return id_sirene;
    }

    public void setId_sirene(int id_sirene) {
        this.id_sirene = id_sirene;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public String getStanje() {
        return stanje;
    }

    public void setStanje(String stanje) {
        this.stanje = stanje;
    }

    @Override
    public String toString() {
        return "Sirene{" +
                "id_sirene=" + id_sirene +
                ", lokacija='" + lokacija + '\'' +
                ", stanje='" + stanje + '\'' +
                '}';
    }
}
