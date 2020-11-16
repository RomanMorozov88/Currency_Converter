package morozov.ru;


import morozov.ru.model.fromxsdcentralbank.ValCurs;
import morozov.ru.model.workingmodel.DateCurs;
import morozov.ru.model.workingmodel.ValuteInfo;
import morozov.ru.service.repository.ValCursRepository;
import morozov.ru.service.repository.ValuteInfoRepository;
import morozov.ru.service.util.ValCursDistillator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * TODO
 * Удалить всё лишнее.
 */
@SpringBootApplication
public class MainClass {

    @Autowired
    private ValCursRepository repository;
    @Autowired
    ValuteInfoRepository infoRepository;

    public static void main(String[] args) {

        SpringApplication.run(MainClass.class, args);

    }

    @Bean
    CommandLineRunner lookup() {
        return args -> {
            ValCurs valCurs = new RestTemplate().getForObject("http://www.cbr.ru/scripts/XML_daily.asp", ValCurs.class);
            DateCurs dateCurs = ValCursDistillator.dateDistillation(valCurs);
            List<ValuteInfo> infos = ValCursDistillator.infoDistillation(valCurs);
            for (ValuteInfo vi : infos) {
                infoRepository.save(vi);
            }
            repository.save(dateCurs);
        };
    }

}