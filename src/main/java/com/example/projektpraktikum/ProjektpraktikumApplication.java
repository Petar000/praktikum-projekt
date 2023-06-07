package com.example.projektpraktikum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.projektpraktikum")
public class ProjektpraktikumApplication implements CommandLineRunner {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private Provjera_sustava provjeraSustava;

	public static void main(String[] args) {
		SpringApplication.run(ProjektpraktikumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//String sql = "SELECT * FROM sirene";
		//List<Sirene> sireneList = jdbcTemplate.query(sql,
		//		BeanPropertyRowMapper.newInstance(Sirene.class));
		//sireneList.forEach(System.out::println);
		String sql = "SELECT * FROM sirene";
		List<Sirene> sireneList = jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Sirene.class));
		sireneList.forEach(System.out::println);

		String sql2 = "SELECT * FROM ured";
		List<Ured> uredList = jdbcTemplate.query(sql2,
				BeanPropertyRowMapper.newInstance(Ured.class));
		uredList.forEach(System.out::println);

		provjeraSustava.pokreniTimer();

		// Testiranja
		Sirene sirene = new Sirene();
		sirene.setJdbcTemplate(jdbcTemplate);
		int id = 5;
		String obavijest1 = "Ispravno";
		String obavijest2 = "Neispravno";
		String obavijest3 = "Pokvaren zvučnik";

		sirene.paljenje(id, true);

		Sirene_ured sireneUredi = new Sirene_ured();
		sireneUredi.setJdbcTemplate(jdbcTemplate);
		sireneUredi.paljenjeSvihSirena();

		sireneUredi.pratiStanjeSirena();

		String regija = "Pula";
		sireneUredi.gasenjeSirenaRegije(regija);

		int id2 = 4;
		sireneUredi.gasenjeSirene(id2);

		sireneUredi.obavijest(id2, obavijest3);

	}
}
