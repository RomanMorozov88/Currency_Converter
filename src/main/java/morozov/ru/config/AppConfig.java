package morozov.ru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean("date_bean")
    public SimpleDateFormat simpleDateFormatForDate() {
        return new SimpleDateFormat("dd.MM.yyyy");
    }

    @Bean("time_bean")
    public SimpleDateFormat simpleDateFormatForTime() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
    }

}
