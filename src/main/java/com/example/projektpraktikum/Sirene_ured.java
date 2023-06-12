package com.example.projektpraktikum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.List;
@RestController
@RequestMapping("/sireneured")
public class Sirene_ured {
    private int id_sirene;
    private String lokacija;
    private String stanje;
    private String regija;
    private String ispravnost;
    private int id_ureda;
    private int id;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Sirene_ured() {
    }

    public Sirene_ured(int id_sirene, String lokacija, String stanje, String regija, String ispravnost, int id_ureda, int id, JdbcTemplate jdbcTemplate) {
        this.id_sirene = id_sirene;
        this.lokacija = lokacija;
        this.stanje = stanje;
        this.regija = regija;
        this.ispravnost = ispravnost;
        this.id_ureda = id_ureda;
        this.id = id;
        this.jdbcTemplate = jdbcTemplate;
    }
    @PutMapping("/paljenje-svih")
    public void paljenjeSvihSirena(@RequestBody String stanje) {
        System.out.println("Paljenje svih sirena. Novo stanje: " + stanje);
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql1 = "UPDATE sirene SET stanje = ?";
        String updateSql2 = "UPDATE sirene_ured SET stanje = ?";
        jdbcTemplate.update(updateSql1, stanje);
        jdbcTemplate.update(updateSql2, stanje);
    }

    @PutMapping("/gasenje-svih")
    public void gasenjeSvihSirena() {
        System.out.println("Gašenje svih sirena. ");
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql1 = "UPDATE sirene SET stanje = 'Ugašeno'";
        String updateSql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno'";
        jdbcTemplate.update(updateSql1);
        jdbcTemplate.update(updateSql2);
    }

    @PatchMapping("/{sirenaID}/paljenje-sirene")
    public void paljenjeSirene(@PathVariable int sirenaID, @RequestBody String stanje) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je upaljena.");

        String sql1 = "UPDATE sirene SET stanje = ? WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = ? WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, stanje, sirenaID);
        jdbcTemplate.update(sql2, stanje, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));
    }

    @PatchMapping("/{sirenaID}/gasenje-sirene")
    public void gasenjeSirene(@PathVariable int sirenaID) {
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        System.out.println("Sirena " + sirenaID + " je ugašena.");

        String sql1 = "UPDATE sirene SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET stanje = 'Ugašeno' WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, sirenaID);
        jdbcTemplate.update(sql2, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));

    }

    @PatchMapping("/{regija}/paljenje-regije")
    public void paljenjeSirenaRegije(@PathVariable String regija, @RequestBody String stanje) {
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

    @PatchMapping("/{regija}/gasenje-regije")
    public void gasenjeSirenaRegije(@PathVariable String regija) {
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
    @PatchMapping("/{sirenaID}/ispravnost")
    public void ispravnost(@PathVariable int sirenaID, @RequestBody String ispravnost) {
        System.out.println("Sirena " + sirenaID + " je promijenila stanje o ispravnosti: " + ispravnost);
        String sql = "SELECT * FROM sirene WHERE id_sirene = ?";
        String sql1 = "UPDATE sirene SET ispravnost = ? WHERE id_sirene = ?";
        String sql2 = "UPDATE sirene_ured SET ispravnost = ? WHERE id_sirene = ?";
        jdbcTemplate.update(sql1, ispravnost, sirenaID);
        jdbcTemplate.update(sql2, ispravnost, sirenaID);

        Sirene sirena = jdbcTemplate.queryForObject(sql, new Object[]{sirenaID}, BeanPropertyRowMapper.newInstance(Sirene.class));
    }
    @PatchMapping("/ispravnost-za-sve")
    public void ispravnostZaSve(@RequestBody String ispravnost){
        String sql = "SELECT * FROM sirene";
        List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
        String updateSql1 = "UPDATE sirene SET ispravnost = ?";
        String updateSql2 = "UPDATE sirene_ured SET ispravnost = ?";
        for (Sirene sirena : sireneList) {
            jdbcTemplate.update(updateSql1, ispravnost);
            jdbcTemplate.update(updateSql2, ispravnost);
        }
    }

    @GetMapping("/sve-sirene")
    public List<Sirene> pratiStanjeSirena() {
        String sql = "SELECT * FROM sirene";
        List<Sirene> sveSirene = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));

        return sveSirene;
    }

    @DeleteMapping("/izbrisi")
    public void obrisiSirenu(@RequestParam String lokacija) {

        String deleteSql1 = "DELETE FROM sirene WHERE lokacija = ?";
        String deleteSql2 = "DELETE FROM sirene_ured WHERE lokacija = ?";
        jdbcTemplate.update(deleteSql1, lokacija);
        jdbcTemplate.update(deleteSql2, lokacija);

        System.out.println("Sirena na lokaciji: " + lokacija + " je izbrisana.");
    }

    @PostMapping("/dodaj")
    public ResponseEntity<String> dodajSirenuUred(@RequestBody Sirene_ured novaSirenaUred) {

        String insertSirenaSql = "INSERT INTO sirene (lokacija, stanje, regija, id_ureda, ispravnost) " +
                "VALUES (?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertSirenaSql, novaSirenaUred.getLokacija(),
                novaSirenaUred.getStanje(), novaSirenaUred.getRegija(), novaSirenaUred.getId_ureda(), novaSirenaUred.getIspravnost());

        // Dohvati ime ureda na temelju id_ureda
        String selectImeUredaSql = "SELECT ime FROM ured WHERE id_ureda = ?";
        String imeUreda = jdbcTemplate.queryForObject(selectImeUredaSql, String.class, novaSirenaUred.getId_ureda());

        String insertSirenaUredSql = "INSERT INTO sirene_ured (id_sirene, lokacija, stanje, regija, id_ureda, ime, ispravnost) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(insertSirenaUredSql, novaSirenaUred.getId_sirene(), novaSirenaUred.getLokacija(),
                novaSirenaUred.getStanje(), novaSirenaUred.getRegija(), novaSirenaUred.getId_ureda(),
                imeUreda, novaSirenaUred.getIspravnost());

        return ResponseEntity.ok("Sirena u uredu uspješno dodana");
    }

    public static void obavijestiCentarOPaljenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se upalila ručno.");
    }
    public static void obavijestiCentarOGasenju(String lokacija) {
        System.out.println("Sirena na lokaciji " + lokacija + " se ugasila ručno.");
    }

    public int getId_sirene() {
        return id_sirene;
    }

    public String getLokacija() {
        return lokacija;
    }

    public String getStanje() {
        return stanje;
    }

    public String getRegija() {
        return regija;
    }

    public String getIspravnost() {
        return ispravnost;
    }

    public int getId_ureda() {
        return id_ureda;
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
