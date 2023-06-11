package com.example.projektpraktikum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class Sirene_ured {
    private List<Sirene> sveSirene;
    private List<Ured> uredi;
    private int id;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Sirene_ured() {
    }

    public Sirene_ured(List<Sirene> sveSirene, List<Ured> uredi, int id, JdbcTemplate jdbcTemplate) {
        this.sveSirene = sveSirene;
        this.uredi = uredi;
        this.id = id;
        this.jdbcTemplate = jdbcTemplate;
    }

    public void paljenjeSvihSirena(String stanje) {
        System.out.println("Paljenje svih sirena. Novo stanje: " + stanje);
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql1 = "UPDATE sirene SET stanje = ?";
        String updateSql2 = "UPDATE sirene_ured SET stanje = ?";
        jdbcTemplate.update(updateSql1, stanje);
        jdbcTemplate.update(updateSql2, stanje);
    }

    public void gasenjeSvihSirena() {
        System.out.println("Gašenje svih sirena. ");
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql1 = "UPDATE sirene SET stanje = 'Ugašeno'";
        String updateSql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno'";
        jdbcTemplate.update(updateSql1);
        jdbcTemplate.update(updateSql2);
    }

    public void paljenjeSirene(int sirenaID, String stanje) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je upaljena.");

        String sql1 = "UPDATE sirene SET stanje = ? WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = ? WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, stanje, sirenaID);
        jdbcTemplate.update(sql2, stanje, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));

    }
    public void gasenjeSirene(int sirenaID) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je ugašena.");

        String sql1 = "UPDATE sirene SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));

    }
    public void paljenjeSirenaRegije(String regija, String stanje) {
        String sql = "SELECT * FROM sirene_ured WHERE regija = ?";
        List<Sirene_ured> sireneUredi = jdbcTemplate.query(sql, new Object[]{regija}, BeanPropertyRowMapper.newInstance(Sirene_ured.class));

        if (!sireneUredi.isEmpty()) {
            System.out.println("Paljenje sirena u regiji: " + regija + ". Novo stanje: " + stanje);

            String updateSql1 = "UPDATE sirene SET stanje = ? WHERE regija = ?";
            jdbcTemplate.update(updateSql1, stanje, regija);

            String updateSql2 = "UPDATE sirene_ured SET stanje = ? WHERE regija = ?";
            jdbcTemplate.update(updateSql2, stanje, regija);

        } else {
            System.out.println("Nema sirena u regiji: " + regija);
        }
    }

    public void gasenjeSirenaRegije(String regija) {
        String sql = "SELECT * FROM sirene_ured WHERE regija = ?";
        List<Sirene_ured> sireneUredi = jdbcTemplate.query(sql, new Object[]{regija}, BeanPropertyRowMapper.newInstance(Sirene_ured.class));

        if (!sireneUredi.isEmpty()) {
            System.out.println("Gašenje sirena u regiji: " + regija);

            String updateSql1 = "UPDATE sirene SET stanje = 'Ugašeno' WHERE regija = ?";
            jdbcTemplate.update(updateSql1, regija);

            String updateSql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno' WHERE regija = ?";
            jdbcTemplate.update(updateSql2, regija);

        } else {
            System.out.println("Nema sirena u regiji: " + regija);
        }
    }
    public void ispravnost(int sirenaID, String ispravnost) {
        System.out.println("Sirena " + sirenaID + " je promijenila stanje o ispravnosti: " + ispravnost);
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        String sql1 = "UPDATE sirene SET ispravnost = ? WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET ispravnost = ? WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, ispravnost, sirenaID);
        jdbcTemplate.update(sql2, ispravnost, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));
    }
    public void ispravnostZaSve(String ispravnost){
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql = "UPDATE sirene SET ispravnost = ?";
        for (Sirene sirena : sireneList) {
            jdbcTemplate.update(updateSql, ispravnost);
        }
    }

    public void pratiStanjeSirena() {
        String sql = "SELECT * FROM sirene";
        List<Sirene> sveSirene = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        System.out.println("Stanje sirena na području Istarske županije:");
        for (Sirene sirena : sveSirene) {
            sirena.setJdbcTemplate(jdbcTemplate);
            System.out.println("Sirena ID: " + sirena.getId_sirene() + ", Lokacija: " + sirena.getLokacija() +
                    ", Stanje: " + sirena.getStanje() + ", Ispravnost: " + sirena.getIspravnost());
        }
    }
    public static void obavijestiCentarOPaljenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se upalila ručno.");
    }
    public static void obavijestiCentarOGasenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se ugasila ručno.");
    }

    public List<Sirene> getSveSirene() {
        return sveSirene;
    }

    public void setSveSirene(List<Sirene> sveSirene) {
        this.sveSirene = sveSirene;
    }

    public List<Ured> getUredi() {
        return uredi;
    }
    public void setUredi(List<Ured> uredi) {
        this.uredi = uredi;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
