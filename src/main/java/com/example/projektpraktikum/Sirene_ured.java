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
        String sql = "SELECT * FROM sirene";
        List<Sirene> sveSirene = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        for (Sirene sirena : sveSirene) {
            sirena.paljenje(sirena.getId_sirene());
        }
        obavijestOPaljenjuSvih();
    }

    public void gasenjeSvihSirena() {
        String sql = "SELECT * FROM sirene";
        List<Sirene> sveSirene = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        for (Sirene sirena : sveSirene) {
            sirena.gasenje(sirena.getId_sirene());
        }
        obavijestOGasenjuSvih();
    }

    public void paljenjeSirene(int sirenaID) {
        for (Sirene sirena : sveSirene) {
            if (sirena.getId_sirene() == sirenaID) {
                sirena.paljenje(sirenaID);
                break;
            }
        }
    }
    public void gasenjeSirene(int sirenaID) {
        for (Sirene sirena : sveSirene) {
            if (sirena.getId_sirene() == sirenaID) {
                sirena.gasenje(sirenaID);
                break;
            }
        }
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

    public void pratiStanjeSirena() {
        System.out.println("Stanje sirena na području Istarske županije:");
        for (Sirene sirena : sveSirene) {
            System.out.println("Sirena ID: " + sirena.getId_sirene() + ", Lokacija: " + sirena.getLokacija() +
                    ", Stanje: " + sirena.getStanje());
        }
    }
    public static void obavijestiCentarOPaljenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se upalila ručno.");
    }
    public static void obavijestiCentarOGasenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se ugasila ručno.");
    }

    public static void obavijestOPaljenjuSvih() {
        System.out.println("Sve sirene su upaljene.");
    }
    public static void obavijestOGasenjuSvih() {
        System.out.println("Sve sirene su ugašene.");
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
