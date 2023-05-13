package com.example.projektpraktikum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.List;
@SpringBootApplication
public class ProjektpraktikumApplication implements CommandLineRunner {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(ProjektpraktikumApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{
		String sql = "SELECT * FROM sirene";
		List<Sirene> sireneList = jdbcTemplate.query(sql,
				BeanPropertyRowMapper.newInstance(Sirene.class));
		sireneList.forEach(System.out::println);
	}
}