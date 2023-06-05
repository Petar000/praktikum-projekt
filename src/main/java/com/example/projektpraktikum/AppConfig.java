package com.example.projektpraktikum;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.Timer;
@Configuration
public class AppConfig {

    @Bean
    public Timer timer() {
        return new Timer();
    }
}
