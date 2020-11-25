package morozov.ru.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
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
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
    }

    @Bean
    public DecimalFormat decimalFormat() {
        DecimalFormat decimalFormat = new DecimalFormat("#.####");
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(dfs);
        return decimalFormat;
    }

}
