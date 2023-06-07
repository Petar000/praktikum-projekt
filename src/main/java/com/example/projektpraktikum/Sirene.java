package com.example.projektpraktikum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class Sirene {
    private int id_sirene;
    private String lokacija;
    private String stanje;
    private String regija;
    private String obavijest;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Sirene() {
    }

    public Sirene(int id_sirene, String lokacija, String stanje, String regija, String obavijest, JdbcTemplate jdbcTemplate) {
        this.id_sirene = id_sirene;
        this.lokacija = lokacija;
        this.stanje = stanje;
        this.regija = regija;
        this.obavijest = obavijest;
        this.jdbcTemplate = jdbcTemplate;
    }
    public void paljenje(int sirenaID, boolean ručno) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je upaljena.");

        String sql1 = "UPDATE sirene SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));

        Sirene_ured.obavijestiCentarOPaljenju(sirena.getLokacija());
    }

    public void gasenje(int sirenaID, boolean ručno) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je ugašena.");

        String sql1 = "UPDATE sirene SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));

        Sirene_ured.obavijestiCentarOGasenju(sirena.getLokacija());
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
    public String getRegija() {
        return regija;
    }
    public void setRegija(String regija) {
        this.regija = regija;
    }
    public String getObavijest() {
        return obavijest;
    }

    public void setObavijest(String obavijest) {
        this.obavijest = obavijest;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public String toString() {
        return "Sirene{" +
                "id_sirene=" + id_sirene +
                ", lokacija='" + lokacija + '\'' +
                ", stanje='" + stanje + '\'' +
                ", regija='" + regija + '\'' +
                ", obavijest='" + obavijest + '\'' +
                '}';
    }
}
