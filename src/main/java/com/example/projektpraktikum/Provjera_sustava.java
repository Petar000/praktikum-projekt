package com.example.projektpraktikum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
@Component
public class Provjera_sustava {
    private Timer timer;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public Provjera_sustava(JdbcTemplate jdbcTemplate) {
        this.timer = new Timer();
        this.jdbcTemplate = jdbcTemplate;
    }
    public void pokreniTimer() {
        // Postavljanje vriemena za prvi poziv
        Calendar firstRunTime = Calendar.getInstance();
        firstRunTime.set(Calendar.DAY_OF_MONTH, 1); // Prvi dan mjeseca
        while (firstRunTime.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            firstRunTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        firstRunTime.set(Calendar.HOUR_OF_DAY, 12);
        firstRunTime.set(Calendar.MINUTE, 0);
        firstRunTime.set(Calendar.SECOND, 0);
/*
        Calendar firstRunTime = Calendar.getInstance();      // TEST za moje vrijeme
        firstRunTime.set(Calendar.YEAR, 2023);
        firstRunTime.set(Calendar.MONTH, Calendar.JUNE);
        firstRunTime.set(Calendar.DAY_OF_MONTH, 13);
        firstRunTime.set(Calendar.HOUR_OF_DAY, 19);
        firstRunTime.set(Calendar.MINUTE, 17);
        firstRunTime.set(Calendar.SECOND, 0);
 */

        // Provjera je li prvi poziv već prošao ovog mjeseca
        if (firstRunTime.getTimeInMillis() < System.currentTimeMillis()) {
            // Pomičemo prvi poziv na sljedeći mjesec
            firstRunTime.add(Calendar.MONTH, 1);
        }

        // Izvršavanje svakog mjeseca
        timer.schedule(new Provjera(), firstRunTime.getTime(), 1000L * 60L * 60L * 24L * 7L);
    }

    public void zaustaviTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    private class Provjera extends TimerTask {
        @Override
        public void run() {
            Calendar now = Calendar.getInstance();
            if (now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && now.get(Calendar.DAY_OF_MONTH) <= 7 &&
                    now.get(Calendar.HOUR_OF_DAY) == 12 && now.get(Calendar.MINUTE) == 0 && now.get(Calendar.SECOND) == 0) {
                String stanje = "Prestanak opasnosti";
                Sirene_ured sireneUredi1 = new Sirene_ured();
                System.out.println("Paljenje svih sirena zbog testiranja sustava.");
                sireneUredi1.setJdbcTemplate(jdbcTemplate);
                sireneUredi1.paljenjeSvihSirena(stanje);
                try {
                    Thread.sleep(10000); // Pauza od 10 sekundi
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Sirene_ured sireneUredi2 = new Sirene_ured();
                System.out.println("Gašenje svih sirena.");
                sireneUredi2.setJdbcTemplate(jdbcTemplate);
                sireneUredi2.gasenjeSvihSirena();
            }
        }
    }
}
