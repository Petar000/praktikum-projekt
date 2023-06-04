package com.example.projektpraktikum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Sirene {
    private int id_sirene;
    private String lokacija;
    private String stanje;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Sirene() {
    }

    public Sirene(int id_sirene, String lokacija, String stanje, JdbcTemplate jdbcTemplate) {
        this.id_sirene = id_sirene;
        this.lokacija = lokacija;
        this.stanje = stanje;
        this.jdbcTemplate = jdbcTemplate;
    }
    public void paljenje(int sirenaID) {
        System.out.println("Sirena " + sirenaID + " je upaljena.");

        String sql1 = "UPDATE sirene SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene_ured.obavijestiCentarOPaljenju(sirenaID);
    }

    public void gasenje(int sirenaID) {
        System.out.println("Sirena " + sirenaID + " je ugašena.");

        String sql1 = "UPDATE sirene SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene_ured.obavijestiCentarOGasenju(sirenaID);
    }

    public void dojavaStanja(int sirenaID, String novoStanje) {
        System.out.println("Sirena " + sirenaID + " je promijenila stanje u: " + novoStanje);
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
