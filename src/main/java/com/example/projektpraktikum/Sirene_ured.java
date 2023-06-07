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

    public void paljenjeSvihSirena() {
        System.out.println("Paljenje svih sirena. ");
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql = "UPDATE sirene SET stanje = 'Upaljeno'";
        jdbcTemplate.update(updateSql);
    }

    public void gasenjeSvihSirena() {
        System.out.println("Gašenje svih sirena. ");
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql = "UPDATE sirene SET stanje = 'Ugašeno'";
        jdbcTemplate.update(updateSql);
    }

    public void paljenjeSirene(int sirenaID) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je upaljena.");

        String sql1 = "UPDATE sirene SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Upaljeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

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
    public void paljenjeSirenaRegije(String regija) {
        String sql = "SELECT * FROM sirene_ured WHERE regija = ?";
        List<Sirene_ured> sireneUredi = jdbcTemplate.query(sql, new Object[]{regija}, BeanPropertyRowMapper.newInstance(Sirene_ured.class));

        if (!sireneUredi.isEmpty()) {
            System.out.println("Paljenje sirena u regiji: " + regija);

            String updateSql1 = "UPDATE sirene SET stanje = 'Upaljeno' WHERE regija = ?";
            jdbcTemplate.update(updateSql1, regija);

            String updateSql2 = "UPDATE sirene_ured SET stanje = 'Upaljeno' WHERE regija = ?";
            jdbcTemplate.update(updateSql2, regija);

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
    public void obavijest(int sirenaID, String obavijest) {
        System.out.println("Sirena " + sirenaID + " ima novu obavijest: " + obavijest);
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        String sql1 = "UPDATE sirene SET obavijest = ? WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET obavijest = ? WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, obavijest, sirenaID);
        jdbcTemplate.update(sql2, obavijest, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));
    }
    public void obavijestZaSve(String obavijest){
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql = "UPDATE sirene SET obavijest = ?";
        for (Sirene sirena : sireneList) {
            jdbcTemplate.update(updateSql, obavijest);
        }
    }

    public void pratiStanjeSirena() {
        String sql = "SELECT * FROM sirene";
        List<Sirene> sveSirene = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        System.out.println("Stanje sirena na području Istarske županije:");
        for (Sirene sirena : sveSirene) {
            sirena.setJdbcTemplate(jdbcTemplate);
            System.out.println("Sirena ID: " + sirena.getId_sirene() + ", Lokacija: " + sirena.getLokacija() +
                    ", Stanje: " + sirena.getStanje() + ", Obavijest: " + sirena.getObavijest());
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
