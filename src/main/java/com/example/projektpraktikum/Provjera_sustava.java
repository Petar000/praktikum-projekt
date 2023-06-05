package com.example.projektpraktikum;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
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
    public Provjera_sustava(Timer timer, JdbcTemplate jdbcTemplate) {
        this.timer = timer;
        this.jdbcTemplate = jdbcTemplate;
    }
    public void pokreniTimer() {
        timer = new Timer();

        // Postavljanje vriemena za prvi poziv
        /*Calendar firstRunTime = Calendar.getInstance();
        firstRunTime.set(Calendar.DAY_OF_MONTH, 1); // Prvi dan mjeseca
        while (firstRunTime.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
            firstRunTime.add(Calendar.DAY_OF_MONTH, 1);
        }
        firstRunTime.set(Calendar.HOUR_OF_DAY, 12);
        firstRunTime.set(Calendar.MINUTE, 0);
        firstRunTime.set(Calendar.SECOND, 0);
*/
        Calendar firstRunTime = Calendar.getInstance();      // TEST za moje vrijeme
        firstRunTime.set(Calendar.YEAR, 2023);
        firstRunTime.set(Calendar.MONTH, Calendar.JUNE);
        firstRunTime.set(Calendar.DAY_OF_MONTH, 6);
        firstRunTime.set(Calendar.HOUR_OF_DAY, 1);
        firstRunTime.set(Calendar.MINUTE, 14);
        firstRunTime.set(Calendar.SECOND, 0);

        // Provjera je li prvi poziv već prošao ovog mjeseca
        if (firstRunTime.getTimeInMillis() < System.currentTimeMillis()) {
            // Pomičemo prvi poziv na sljedeći mjesec
            firstRunTime.add(Calendar.MONTH, 1);
        }

        // Postavljamo periodično izvršavanje svakog mjeseca
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
            if (now.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY && now.get(Calendar.DAY_OF_MONTH) <= 7 &&
                    now.get(Calendar.HOUR_OF_DAY) == 1 && now.get(Calendar.MINUTE) == 14 && now.get(Calendar.SECOND) == 0) {
                Sirene_ured sireneUredi1 = new Sirene_ured();
                System.out.println("Paljenje svih sirena.");
                sireneUredi1.setJdbcTemplate(jdbcTemplate);
                sireneUredi1.paljenjeSvihSirena();
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
